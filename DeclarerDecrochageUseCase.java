package fonea.ped.useCase.useCsaeMiseEnStage.UseCase.declarerDecrochage;

import java.time.LocalDate;

import fonea.ped.domain.MiseEnStage.Decrochage;
import fonea.ped.domain.MiseEnStage.MiseEnStage;
import fonea.ped.domain.MiseEnStage.StatutStage;

import fonea.ped.useCase.useCsaeMiseEnStage.ports.*;


import fonea.ped.MiseEnStage.ports.DecrochageRepository;
import fonea.ped.MiseEnStage.ports.MiseEnStageRepository;
import fonea.ped.entreprise.port.EntrepriseRepository;
import fonea.ped.exceptions.*;
public class DeclarerDecrochageUseCase {
	
	private final MiseEnStageRepository miseEnStageRepository;
    private final DecrochageRepository decrochageRepository;
    private final EntrepriseRepository entrepriseRepository;

    public DeclarerDecrochageUseCase(MiseEnStageRepository miseEnStageRepository,
                                     DecrochageRepository decrochageRepository,
                                     EntrepriseRepository entrepriseRepository) {
        this.miseEnStageRepository = miseEnStageRepository;
        this.decrochageRepository = decrochageRepository;
        this.entrepriseRepository = entrepriseRepository;
    }

    public DeclarerDecrochageResponse executer(DeclarerDecrochageRequest request) {

        // 1. Vérifier l'existence de la mise en stage
        MiseEnStage miseEnStage = miseEnStageRepository.trouverParId(request.getIdMiseStage())
                .orElseThrow(() -> new MiseEnStageIntrouvableException(request.getIdMiseStage()));

        // 2. Vérifier le statut de la mise en stage
        StatutStage statut = miseEnStage.getStatutStage();
        if (statut != StatutStage.EN_COURS && statut != StatutStage.RENOUVELE) {
            throw new DecrochageImpossibleException(miseEnStage.getIdMiseStage(), statut.name());
        }

        // 3. Vérifier qu'aucun décrochage n'existe déjà
        if (decrochageRepository.existeParMiseEnStage(miseEnStage.getIdMiseStage())) {
            throw new DecrochageDejaDeclareException(miseEnStage.getIdMiseStage());
        }

        // 4. Vérifier la raison
        if (request.getRaison() == null || request.getRaison().isBlank()) {
            throw new RaisonDecrochageObligatoireException();
        }

        // 5. Vérifier l'entreprise
        if (request.getIdEntreprise() == null
                || !entrepriseRepository.existeParId(request.getIdEntreprise())) {
            throw new EntrepriseIntrouvableException(request.getIdEntreprise());
        }

        // 6. Création du décrochage
        String nouvelId = decrochageRepository.prochainId();
        Decrochage decrochage = new Decrochage(
                nouvelId,
                LocalDate.now(),
                miseEnStage.getIdMiseStage(),
                request.getIdEntreprise(),
                null
        );
        decrochageRepository.sauvegarder(decrochage);

        // 7. Passage de la mise en stage au statut DECROCHE
        miseEnStage.changerStatut(StatutStage.DECROCHE);
        miseEnStageRepository.sauvegarder(miseEnStage);

        // 8. Réponse
        return new DeclarerDecrochageResponse(
                decrochage.getIdDecrochage(),
                decrochage.getDateDecrochage(),
                decrochage.getIdMiseStage(),
                decrochage.getIdEntreprise(),
                request.getRaison()
        );
    }
}
