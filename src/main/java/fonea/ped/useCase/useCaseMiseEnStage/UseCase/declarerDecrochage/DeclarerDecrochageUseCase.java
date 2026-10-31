package fonea.ped.useCase.useCaseMiseEnStage.UseCase.declarerDecrochage;

import java.time.LocalDate;

import fonea.ped.domain.MiseEnStage.Decrochage;
import fonea.ped.domain.MiseEnStage.MiseEnStage;
import fonea.ped.domain.MiseEnStage.StatutStage;

import fonea.ped.domain.exceptions.DecrochageDejaDeclareException;
import fonea.ped.domain.exceptions.DecrochageImpossibleException;
import fonea.ped.domain.exceptions.EntrepriseIntrouvableException;
import fonea.ped.domain.exceptions.MiseEnStageIntrouvableException;
import fonea.ped.domain.exceptions.RaisonDecrochageObligatoireException;

import fonea.ped.useCase.useCaseEntreprise.port.EntrepriseRepository;


import fonea.ped.useCase.useCaseMiseEnStage.ports.DecrochageRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
        if (request.getRaison() == null) {
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
                miseEnStage.getIdMiseStage()

        );
        
        /*
         *  public Decrochage(String idDecrochage, LocalDate dateDecrochage, String idMiseStage) {
        this.idDecrochage = idDecrochage;
        this.dateDecrochage = dateDecrochage;
        this.idMiseStage = idMiseStage;
         */
        decrochageRepository.sauvegarder(decrochage);

        // 7. Passage de la mise en stage au statut DECROCHE
        miseEnStage.changerStatut(StatutStage.DECROCHE);
        miseEnStageRepository.sauvegarder(miseEnStage);

        // 8. Réponse
        return new DeclarerDecrochageResponse(
                decrochage.getIdDecrochage(),
                decrochage.getDateDecrochage(),
                decrochage.getIdMiseStage(),
                request.getRaison()
        );
    }
}
