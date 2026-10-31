package fonea.ped.useCase.useCaseMiseEnStage.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import fonea.ped.domain.convention.model.Convention;
import fonea.ped.useCase.UseCaseConvention.ports.ConventionRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffreRepository;
import fonea.ped.useCase.useCaseOffres.ports.PublicationRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.PreselectionRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.SelectionRepository;

/**
 * Résout la chaîne métier
 * {@code Sélection → Présélection → Candidature → Publication → Offre → Convention}
 * afin de retrouver l'entreprise réellement propriétaire d'une ressource.
 *
 * <p>Sans ce contrôle, n'importe quelle entreprise pouvait placer un candidat
 * en stage chez une autre en fournissant un {@code idEntreprise} arbitraire,
 * ou consulter les candidatures et stages d'une autre entreprise.</p>
 */
@Service
public class ChaineRecrutement {

    private final SelectionRepository selectionRepository;
    private final PreselectionRepository preselectionRepository;
    private final CandidatureRepository candidatureRepository;
    private final PublicationRepository publicationRepository;
    private final OffreRepository offreRepository;
    private final ConventionRepository conventionRepository;
    private final MiseEnStageRepository miseEnStageRepository;

    public ChaineRecrutement(SelectionRepository selectionRepository,
                             PreselectionRepository preselectionRepository,
                             CandidatureRepository candidatureRepository,
                             PublicationRepository publicationRepository,
                             OffreRepository offreRepository,
                             ConventionRepository conventionRepository,
                             MiseEnStageRepository miseEnStageRepository) {
        this.selectionRepository = selectionRepository;
        this.preselectionRepository = preselectionRepository;
        this.candidatureRepository = candidatureRepository;
        this.publicationRepository = publicationRepository;
        this.offreRepository = offreRepository;
        this.conventionRepository = conventionRepository;
        this.miseEnStageRepository = miseEnStageRepository;
    }

    /** @return l'id de l'entreprise propriétaire de la convention de l'offre. */
    public Optional<String> entrepriseDeLOffre(String idOffre) {
        return offreRepository.trouverParId(idOffre)
                .flatMap(offre -> conventionRepository.trouverParId(offre.getIdConvention()))
                .map(Convention::getIdEntreprise);
    }

    /** @return l'id de l'entreprise propriétaire de la publication. */
    public Optional<String> entrepriseDeLaPublication(String idPublication) {
        return publicationRepository.trouverParId(idPublication)
                .flatMap(publication -> entrepriseDeLOffre(publication.getIdOffre()));
    }

    /** @return l'id de l'entreprise propriétaire de la candidature. */
    public Optional<String> entrepriseDeLaCandidature(String idCandidature) {
        return candidatureRepository.trouverParId(idCandidature)
                .flatMap(candidature -> entrepriseDeLaPublication(candidature.getIdPublication()));
    }

    /** @return l'id de l'entreprise propriétaire de la présélection. */
    public Optional<String> entrepriseDeLaPreselection(String idPreselection) {
        return preselectionRepository.trouverParId(idPreselection)
                .flatMap(preselection -> entrepriseDeLaCandidature(preselection.getIdCandidature()));
    }

    /** @return l'id de l'entreprise propriétaire de la sélection, si la chaîne est complète. */
    public Optional<String> entrepriseDeLaSelection(String idSelection) {
        return selectionRepository.trouverParId(idSelection)
                .flatMap(selection -> entrepriseDeLaPreselection(selection.getIdPreselection()));
    }

    /** @return l'id de l'entreprise propriétaire de la mise en stage, si la chaîne est complète. */
    public Optional<String> entrepriseDeLaMiseEnStage(String idMiseStage) {
        return miseEnStageRepository.trouverParId(idMiseStage)
                .flatMap(mise -> entrepriseDeLaSelection(mise.getIdSelection()));
    }
}
