package fonea.ped.useCase.useCaseMiseEnStage.UseCase.terminerStage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fonea.ped.domain.MiseEnStage.MiseEnStage;
import fonea.ped.domain.exceptions.MiseEnStageIntrouvableException;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;

/**
 * Cas d'usage « terminer un stage ».
 *
 * <p>Fait passer une mise en stage de {@code EN_COURS} (ou {@code RENOUVELE})
 * à {@code TERMINE}. C'est la condition nécessaire à l'insertion
 * professionnelle, qui exige un stage terminé.</p>
 */
@Service
@Transactional
public class TerminerStageUseCase {

    private final MiseEnStageRepository miseEnStageRepository;

    public TerminerStageUseCase(MiseEnStageRepository miseEnStageRepository) {
        this.miseEnStageRepository = miseEnStageRepository;
    }

    public TerminerStageResponse executer(TerminerStageRequest request) {
        MiseEnStage miseEnStage = miseEnStageRepository
                .trouverParId(request.getIdMiseStage())
                .orElseThrow(() -> new MiseEnStageIntrouvableException(
                        request.getIdMiseStage()));

        miseEnStage.terminer();
        miseEnStageRepository.sauvegarder(miseEnStage);

        return new TerminerStageResponse(
                miseEnStage.getIdMiseStage(),
                miseEnStage.getDateDebut(),
                miseEnStage.getDateFin(),
                miseEnStage.getStatutStage());
    }
}
