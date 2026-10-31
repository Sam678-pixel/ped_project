package fonea.ped.useCase.useCaseMiseEnStage.UseCase.demarrerStage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fonea.ped.domain.MiseEnStage.MiseEnStage;
import fonea.ped.domain.exceptions.MiseEnStageIntrouvableException;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;

/**
 * Cas d'usage « démarrer un stage ».
 *
 * <p>Fait passer une mise en stage de {@code EN_ATTENTE} à {@code EN_COURS}.
 * Sans ce cas d'usage, aucun stage créé par l'API ne pouvait atteindre le
 * statut {@code TERMINE}, et l'insertion professionnelle restait donc
 * inatteignable.</p>
 */
@Service
@Transactional
public class DemarrerStageUseCase {

    private final MiseEnStageRepository miseEnStageRepository;

    public DemarrerStageUseCase(MiseEnStageRepository miseEnStageRepository) {
        this.miseEnStageRepository = miseEnStageRepository;
    }

    public DemarrerStageResponse executer(DemarrerStageRequest request) {
        MiseEnStage miseEnStage = miseEnStageRepository
                .trouverParId(request.getIdMiseStage())
                .orElseThrow(() -> new MiseEnStageIntrouvableException(
                        request.getIdMiseStage()));

        miseEnStage.demarrer();
        miseEnStageRepository.sauvegarder(miseEnStage);

        return new DemarrerStageResponse(
                miseEnStage.getIdMiseStage(),
                miseEnStage.getDateDebut(),
                miseEnStage.getDateFin(),
                miseEnStage.getStatutStage());
    }
}
