package fonea.ped.useCase.UseCaseConvention.useCase.validerConvention;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fonea.ped.domain.convention.model.Convention;
import fonea.ped.domain.convention.model.StatutConvention;
import fonea.ped.domain.exceptions.ConventionDejaResilieeException;
import fonea.ped.domain.exceptions.ConventionIntrouvableException;
import fonea.ped.useCase.UseCaseConvention.ports.ConventionRepository;

/**
 * Cas d'usage « le FONEA valide la convention signée par l'entreprise ».
 *
 * <p>Ce cas d'usage manquait : les conventions restaient en {@code BROUILLON}
 * pour toujours, or {@code CreerOffreUseCase} exige une convention
 * {@code ACTIVE}. Tout le flux était donc bloqué dès la première étape.</p>
 */
@Service
@Transactional
public class ValiderConventionUseCase {

    private final ConventionRepository conventionRepository;

    public ValiderConventionUseCase(ConventionRepository conventionRepository) {
        this.conventionRepository = conventionRepository;
    }

    public ValiderConventionResponse executer(ValiderConventionRequest request) {

        Convention convention = conventionRepository.trouverParId(request.getIdConvention())
                .orElseThrow(() -> new ConventionIntrouvableException(request.getIdConvention()));

        if (convention.getStatutConvention() == StatutConvention.RESILIEE) {
            throw new ConventionDejaResilieeException(convention.getIdConvention());
        }

        // Idempotent : valider une convention déjà active ne casse rien.
        if (convention.getStatutConvention() != StatutConvention.ACTIVE) {
            convention.activer();
            conventionRepository.sauvegarder(convention);
        }

        return new ValiderConventionResponse(
                convention.getIdConvention(),
                convention.getStatutConvention(),
                convention.getDateEffet());
    }
}
