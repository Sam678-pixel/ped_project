package fonea.ped.useCase.useCaseMiseEnStage.UseCase.renouvelerstage;




import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.RenouvellementRepository;

import fonea.ped.domain.MiseEnStage.*;
import fonea.ped.domain.exceptions.DatesRenouvellementInvalidesException;
import fonea.ped.domain.exceptions.MiseEnStageIntrouvableException;
import fonea.ped.domain.exceptions.RenouvellementDejaExistantException;
import fonea.ped.domain.exceptions.RenouvellementImpossibleException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RenouvelerStageUseCase {
	
	private final MiseEnStageRepository miseEnStageRepository;
    private final RenouvellementRepository renouvellementRepository;

    public RenouvelerStageUseCase(MiseEnStageRepository miseEnStageRepository,
                                  RenouvellementRepository renouvellementRepository) {
        this.miseEnStageRepository = miseEnStageRepository;
        this.renouvellementRepository = renouvellementRepository;
    }

    public RenouvelerStageResponse executer(RenouvelerStageRequest request) {

        // 1. Vérifier l'existence de la mise en stage
        MiseEnStage miseEnStage = miseEnStageRepository.trouverParId(request.getIdMiseStage())
                .orElseThrow(() -> new MiseEnStageIntrouvableException(request.getIdMiseStage()));

        // 2. Vérifier que le statut est TERMINE
        if (miseEnStage.getStatutStage() != StatutStage.TERMINE) {
            throw new RenouvellementImpossibleException(
                    miseEnStage.getIdMiseStage(),
                    miseEnStage.getStatutStage().name());
        }

        // 3. Vérifier qu'aucun renouvellement n'existe déjà
        if (renouvellementRepository.existeParMiseEnStage(miseEnStage.getIdMiseStage())) {
            throw new RenouvellementDejaExistantException(miseEnStage.getIdMiseStage());
        }

        // 4. Vérifier les dates
        if (request.getNouvelleDateDebut() == null || request.getNouvelleDateFin() == null) {
            throw new DatesRenouvellementInvalidesException(
                    "Les dates de début et de fin du renouvellement sont obligatoires.");
        }
        if (request.getNouvelleDateFin().isBefore(request.getNouvelleDateDebut())) {
            throw new DatesRenouvellementInvalidesException(
                    "La date de fin du renouvellement doit être postérieure ou égale à la date de début.");
        }

        // 5. Création du renouvellement
        String nouvelId = renouvellementRepository.prochainId();
        Renouvellement renouvellement = new Renouvellement(
                nouvelId,
                request.getNouvelleDateDebut(),
                request.getNouvelleDateFin(),
                miseEnStage.getIdMiseStage()
        );
        renouvellementRepository.sauvegarder(renouvellement);

        // 6. Passage de la mise en stage au statut RENOUVELE
        miseEnStage.changerStatut(StatutStage.RENOUVELE);
        miseEnStageRepository.sauvegarder(miseEnStage);

        // 7. Réponse
        return new RenouvelerStageResponse(
                renouvellement.getIdRenouvellement(),
                renouvellement.getDateDebut(),
                renouvellement.getDateFin(),
                miseEnStage.getIdMiseStage(),
                miseEnStage.getStatutStage()
        );
    }
}
