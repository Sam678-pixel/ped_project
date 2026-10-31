package fonea.ped.useCase.useCaseInsertion.useCase.inserer;

import java.time.LocalDate;

import fonea.ped.domain.MiseEnStage.MiseEnStage;
import fonea.ped.domain.MiseEnStage.StatutStage;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;

import fonea.ped.useCase.useCaseOffres.ports.PosteRepository;
import fonea.ped.domain.exceptions.*;
import fonea.ped.domain.insertion.Contrat;
import fonea.ped.domain.insertion.Insertion;
import fonea.ped.domain.insertion.StatutContrat;
import fonea.ped.useCase.useCaseInsertion.ports.ContratRepository;
import fonea.ped.useCase.useCaseInsertion.ports.InsertionRepository;
import fonea.ped.useCase.useCaseInsertion.ports.TypeContratRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InsererUseCase {
	private final MiseEnStageRepository miseEnStageRepository;
    private final InsertionRepository insertionRepository;
    private final ContratRepository contratRepository;
    private final TypeContratRepository typeContratRepository;
    private final PosteRepository posteRepository;

    public InsererUseCase(MiseEnStageRepository miseEnStageRepository,
                          InsertionRepository insertionRepository,
                          ContratRepository contratRepository,
                          TypeContratRepository typeContratRepository,
                          PosteRepository posteRepository) {
        this.miseEnStageRepository = miseEnStageRepository;
        this.insertionRepository = insertionRepository;
        this.contratRepository = contratRepository;
        this.typeContratRepository = typeContratRepository;
        this.posteRepository = posteRepository;
    }

    public InsererResponse executer(InsererRequest request) {

        // 1. Vérifier l'existence de la mise en stage
        MiseEnStage miseEnStage = miseEnStageRepository.trouverParId(request.getIdMiseStage())
                .orElseThrow(() -> new MiseEnStageIntrouvableException(request.getIdMiseStage()));

        // 2. Vérifier que la mise en stage est TERMINEE
        if (miseEnStage.getStatutStage() != StatutStage.TERMINE) {
            throw new InsertionImpossibleException(
                    miseEnStage.getIdMiseStage(),
                    miseEnStage.getStatutStage().name());
        }

        // 3. Vérifier qu'aucune insertion n'existe déjà
        if (insertionRepository.existeParMiseEnStage(miseEnStage.getIdMiseStage())) {
            throw new InsertionDejaExistanteException(miseEnStage.getIdMiseStage());
        }

        // 4. Vérifier le poste
        if (!posteRepository.existeParId(request.getIdPoste())) {
            throw new PosteIntrouvableException(request.getIdPoste());
        }

        // 5. Vérifier le type de contrat
        if (!typeContratRepository.existeParId(request.getIdTypeContrat())) {
            throw new TypeContratIntrouvableException(request.getIdTypeContrat());
        }

        // 6. Vérifier la date d'embauche
        if (request.getDateEmbauche() == null) {
            throw new DateEmbaucheInvalideException("La date d'embauche est obligatoire.");
        }

        // 7. Vérifier le salaire
        if (request.getSalaire() == null || request.getSalaire() <= 0) {
            throw new SalaireInvalideException("Le salaire doit être strictement positif.");
        }

        // 8. Vérifier les dates du contrat
        if (request.getDateDebutContrat() == null) {
            throw new DatesContratInvalidesException("La date de début du contrat est obligatoire.");
        }
        if (request.getDateFinContrat() != null
                && request.getDateFinContrat().isBefore(request.getDateDebutContrat())) {
            throw new DatesContratInvalidesException(
                    "La date de fin du contrat doit être postérieure ou égale à la date de début.");
        }

        // 9. Création de l'insertion
        String idInsertion = insertionRepository.prochainId();
        Insertion insertion = new Insertion(
                idInsertion,
                request.getDateEmbauche(),
                request.getIdPoste(),
                miseEnStage.getIdMiseStage()
        );
        insertionRepository.sauvegarder(insertion);

        // 10. Création du contrat associé
        String idContrat = contratRepository.prochainId();
        Contrat contrat = new Contrat(
                idContrat,
                request.getDateDebutContrat(),
                request.getDateFinContrat(),
                request.getSalaire(),
                request.getIdTypeContrat(),
                insertion.getIdInsertion()
        );

        contratRepository.sauvegarder(contrat);

        // 11. Réponse
        return new InsererResponse(
                insertion.getIdInsertion(),
                insertion.getDateEmbauche(),
                insertion.getIdPoste(),
                contrat.getIdTypeContrat(),
                insertion.getIdMiseStage(),
                contrat.getIdContrat(),
                contrat.getDateDebut(),
                contrat.getDateFin(),
                contrat.getSalaire(),
                contrat.getStatut()
                /*
                 * String idInsertion,
                           LocalDate dateEmbauche,
                           String idPoste,
                           String idTypeContrat,
                           String idMiseStage,
                           String idContrat,
                           LocalDate dateDebutContrat,
                           LocalDate dateFinContrat,
                           Double salaire,
                           StatutContrat statutContrat)
                 */
        );
    }
}