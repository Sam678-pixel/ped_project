package fonea.ped.useCase.useCaseOffres.UseCaseOffres.publierOffre;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fonea.ped.domain.Offres.OffresStages;
import fonea.ped.domain.Offres.Publication;
import fonea.ped.domain.Offres.StatutOffre;
import fonea.ped.domain.convention.model.Convention;
import fonea.ped.domain.convention.model.StatutConvention;
import fonea.ped.domain.exceptions.ConventionIntrouvableException;
import fonea.ped.domain.exceptions.ConventionNonActiveException;
import fonea.ped.domain.exceptions.DateInvalideException;
import fonea.ped.domain.exceptions.OffreIntrouvableException;
import fonea.ped.useCase.UseCaseConvention.ports.ConventionRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffreRepository;
import fonea.ped.useCase.useCaseOffres.ports.PublicationRepository;

/**
 * Cas d'usage « le FONEA publie une offre de stage ».
 *
 * <p>Ce cas d'usage manquait totalement : {@code OffresStages.publier()} n'était
 * appelé nulle part et aucune {@code Publication} n'était jamais créée. Les
 * offres restaient donc en {@code BROUILLON} et {@code PostulerUseCase} les
 * rejetait systématiquement.</p>
 */
@Service
@Transactional
public class PublierOffreUseCase {

    private final OffreRepository offreRepository;
    private final PublicationRepository publicationRepository;
    private final ConventionRepository conventionRepository;

    public PublierOffreUseCase(OffreRepository offreRepository,
                               PublicationRepository publicationRepository,
                               ConventionRepository conventionRepository) {
        this.offreRepository = offreRepository;
        this.publicationRepository = publicationRepository;
        this.conventionRepository = conventionRepository;
    }

    public PublierOffreResponse executer(PublierOffreRequest request) {

        OffresStages offre = offreRepository.trouverParId(request.getIdOffre())
                .orElseThrow(() -> new OffreIntrouvableException(request.getIdOffre()));

        // 1. La convention doit toujours être active.
        Convention convention = conventionRepository.trouverParId(offre.getIdConvention())
                .orElseThrow(() -> new ConventionIntrouvableException(offre.getIdConvention()));
        if (convention.getStatutConvention() != StatutConvention.ACTIVE) {
            throw new ConventionNonActiveException(
                    convention.getIdConvention(), convention.getStatutConvention().name());
        }

        // 2. Une offre annulée ou clôturée ne peut pas être publiée.
        if (offre.getStatutOffre() == StatutOffre.ANNULEE || offre.getStatutOffre() == StatutOffre.CLOTUREE) {
            throw new IllegalStateException(
                    "Impossible de publier une offre au statut " + offre.getStatutOffre() + ".");
        }

        // 3. Pas de double publication active.
        if (publicationRepository.trouverActiveParOffre(offre.getIdOffre()).isPresent()) {
            throw new IllegalStateException(
                    "L'offre " + offre.getIdOffre() + " est déjà publiée et active.");
        }

        // 4. Date d'expiration obligatoire et future.
        LocalDate expiration = request.getDateExpiration();
        if (expiration == null || !expiration.isAfter(LocalDate.now())) {
            throw new DateInvalideException(
                    "La date d'expiration de la publication doit être postérieure à aujourd'hui.");
        }

        // 5. Publication
        offre.publier();
        offreRepository.sauvegarder(offre);

        Publication publication = new Publication(
                publicationRepository.prochainId(),
                LocalDate.now(),
                expiration,
                request.getIdAgent(),
                offre.getIdOffre());
        publicationRepository.sauvegarder(publication);

        return new PublierOffreResponse(
                publication.getIdPublication(),
                offre.getIdOffre(),
                publication.getDatePublication(),
                publication.getDateExpiration(),
                offre.getStatutOffre());
    }
}
