package fonea.ped.useCase.useCaseDiplome.useCase.postuler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fonea.ped.domain.Offres.OffresStages;
import fonea.ped.domain.Offres.Publication;
import fonea.ped.domain.Offres.StatutOffre;
import fonea.ped.domain.exceptions.AucunDocumentFourniException;
import fonea.ped.domain.exceptions.CandidatureDejaExistanteException;
import fonea.ped.domain.exceptions.DelaiPostulationDepasseException;
import fonea.ped.domain.exceptions.DiplomeIntrouvableException;
import fonea.ped.domain.exceptions.DocumentsIncompletsException;
import fonea.ped.domain.exceptions.OffreIntrouvableException;
import fonea.ped.domain.exceptions.OffreNonPublieeException;
import fonea.ped.domain.recrutement.Candidature;
import fonea.ped.domain.recrutement.CandidatureDocument;
import fonea.ped.domain.recrutement.Document;
import fonea.ped.domain.recrutement.StatutCandidature;
import fonea.ped.useCase.useCaseDiplome.port.DiplomeRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffreRepository;
import fonea.ped.useCase.useCaseOffres.ports.PublicationRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureDocumentRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.DocumentRepository;

/**
 * Cas d'usage « postuler ».
 *
 * <p>Corrections apportées :</p>
 * <ul>
 *   <li>la candidature est <b>créée</b> par ce use case (avant, il exigeait une
 *       candidature préexistante qu'aucun autre use case ne créait) ;</li>
 *   <li>les documents sont réellement persistés puis rattachés à la
 *       candidature via {@code piece_a_joindre} (avant, ils étaient ignorés) ;</li>
 *   <li>les contrôles portent sur les documents de la requête, pas sur ceux
 *       d'une candidature fantôme.</li>
 * </ul>
 */
@Service
@Transactional
public class PostulerUseCase {

    private final CandidatureRepository candidatureRepository;
    private final OffreRepository offreRepository;
    private final PublicationRepository publicationRepository;
    private final DiplomeRepository diplomeRepository;
    private final DocumentRepository documentRepository;
    private final CandidatureDocumentRepository candidatureDocumentRepository;

    public PostulerUseCase(CandidatureRepository candidatureRepository,
                           OffreRepository offreRepository,
                           PublicationRepository publicationRepository,
                           DiplomeRepository diplomeRepository,
                           DocumentRepository documentRepository,
                           CandidatureDocumentRepository candidatureDocumentRepository) {
        this.candidatureRepository = candidatureRepository;
        this.offreRepository = offreRepository;
        this.publicationRepository = publicationRepository;
        this.diplomeRepository = diplomeRepository;
        this.documentRepository = documentRepository;
        this.candidatureDocumentRepository = candidatureDocumentRepository;
    }

    public PostulerResponse executer(PostulerRequest request) {

        // 1. Vérifier le diplômé
        if (request.getIdDiplome() == null || !diplomeRepository.existeParId(request.getIdDiplome())) {
            throw new DiplomeIntrouvableException(request.getIdDiplome());
        }

        // 2. Vérifier l'offre
        OffresStages offre = offreRepository.trouverParId(request.getIdOffre())
                .orElseThrow(() -> new OffreIntrouvableException(request.getIdOffre()));

        // 3. Vérifier le statut de l'offre
        if (offre.getStatutOffre() != StatutOffre.PUBLIEE) {
            throw new OffreNonPublieeException(offre.getIdOffre(), offre.getStatutOffre().name());
        }

        // 4. Vérifier la publication active + délai
        Publication publication = publicationRepository.trouverActiveParOffre(offre.getIdOffre())
                .orElseThrow(() -> new OffreNonPublieeException(
                        offre.getIdOffre(), "AUCUNE PUBLICATION ACTIVE"));

        if (!publication.estActive()) {
            throw new DelaiPostulationDepasseException(
                    offre.getIdOffre(), publication.getDateExpiration());
        }

        // 5. Vérifier doublon
        if (candidatureRepository.existeParDiplomeEtPublication(
                request.getIdDiplome(), publication.getIdPublication())) {
            throw new CandidatureDejaExistanteException(
                    request.getIdDiplome(), offre.getIdOffre());
        }

        // 6. Vérifier les documents fournis
        List<Document> documents = request.getDocuments();
        if (documents == null || documents.isEmpty()) {
            throw new AucunDocumentFourniException();
        }
        List<Document> documentsEnregistres = persisterDocuments(documents, request.getIdDiplome());

        // 7. Créer la candidature
        Candidature candidature = new Candidature(
                candidatureRepository.prochainId(),
                LocalDate.now(),
                StatutCandidature.EN_COURS,
                request.getIdDiplome(),
                publication.getIdPublication(),
                documentsEnregistres
        );

        if (!candidature.sontDocumentsComplets()) {
            throw new DocumentsIncompletsException();
        }

        candidature.deposer();
        candidatureRepository.sauvegarder(candidature);

        // 8. Rattacher les documents à la candidature
        for (Document document : documentsEnregistres) {
            candidatureDocumentRepository.sauvegarder(
                    new CandidatureDocument(candidature.getIdCandidature(), document.getIdDocument()));
        }

        // 9. Réponse
        return new PostulerResponse(
                candidature.getIdCandidature(),
                candidature.getDateDepot(),
                candidature.getStatutCandidature(),
                candidature.getIdDiplome(),
                candidature.getIdPublication(),
                documentsEnregistres
        );
    }

    private List<Document> persisterDocuments(List<Document> documents, String idDiplome) {
        List<Document> resultat = new ArrayList<>(documents.size());
        for (Document fourni : documents) {
            if (fourni == null || fourni.getCheminFichier() == null || fourni.getCheminFichier().isBlank()) {
                throw new AucunDocumentFourniException();
            }
            String idDocument = (fourni.getIdDocument() == null || fourni.getIdDocument().isBlank())
                    ? documentRepository.prochainId()
                    : fourni.getIdDocument();
            Document document = new Document(
                    idDocument,
                    fourni.getCheminFichier(),
                    idDiplome,
                    fourni.getIdTypeDocument());
            documentRepository.sauvegarder(document);
            resultat.add(document);
        }
        return resultat;
    }
}
