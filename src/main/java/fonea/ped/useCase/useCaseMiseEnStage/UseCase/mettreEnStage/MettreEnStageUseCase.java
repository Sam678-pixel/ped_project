package fonea.ped.useCase.useCaseMiseEnStage.UseCase.mettreEnStage;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fonea.ped.domain.MiseEnStage.MiseEnStage;
import fonea.ped.domain.MiseEnStage.StatutStage;
import fonea.ped.domain.exceptions.CandidatureNonSelectionneeException;
import fonea.ped.domain.exceptions.DatesStageInvalidesException;
import fonea.ped.domain.exceptions.MiseEnStageDejaExistanteException;
import fonea.ped.domain.exceptions.SelectionIntrouvableException;
import fonea.ped.domain.recrutement.Selection;
import fonea.ped.domain.recrutement.StatutCandidature;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;
import fonea.ped.useCase.useCaseMiseEnStage.service.ChaineRecrutement;
import fonea.ped.useCase.useCaseRecrutement.ports.SelectionRepository;

/**
 * Cas d'usage « mettre en stage », réalisé par l'entreprise.
 *
 * <p>Corrections :</p>
 * <ul>
 *   <li>seule une sélection {@code SELECTIONNEE} est acceptée (avant,
 *       {@code PRESELECTIONNEE} passait aussi) ;</li>
 *   <li>l'entreprise fournie doit être celle de l'offre (contrôle de
 *       propriété), sinon n'importe qui pouvait placer un candidat ailleurs.</li>
 * </ul>
 */
@Service
@Transactional
public class MettreEnStageUseCase {

    private final SelectionRepository selectionRepository;
    private final MiseEnStageRepository miseEnStageRepository;
    private final ChaineRecrutement chaineRecrutement;

    public MettreEnStageUseCase(SelectionRepository selectionRepository,
                                MiseEnStageRepository miseEnStageRepository,
                                ChaineRecrutement chaineRecrutement) {
        this.selectionRepository = selectionRepository;
        this.miseEnStageRepository = miseEnStageRepository;
        this.chaineRecrutement = chaineRecrutement;
    }

    public MettreEnStageResponse executer(MettreEnStageRequest request) {

        // 1. Vérifier l'existence de la sélection
        Selection selection = selectionRepository.trouverParId(request.getIdSelection())
                .orElseThrow(() -> new SelectionIntrouvableException(request.getIdSelection()));

        // 2. Vérifier que la sélection est validée
        if (selection.getStatutSelection() != StatutCandidature.SELECTIONNEE) {
            throw new CandidatureNonSelectionneeException(
                    selection.getIdSelection(),
                    selection.getStatutSelection().name());
        }

        // 3. Vérifier que l'entreprise est bien celle de l'offre
        String entrepriseProprietaire = chaineRecrutement
                .entrepriseDeLaSelection(selection.getIdSelection())
                .orElseThrow(() -> new IllegalStateException(
                        "Impossible de déterminer l'entreprise de la sélection "
                                + selection.getIdSelection() + " (chaîne métier incomplète)."));
        if (request.getIdEntreprise() == null
                || !entrepriseProprietaire.equals(request.getIdEntreprise())) {
            throw new IllegalArgumentException(
                    "L'entreprise " + request.getIdEntreprise()
                            + " n'est pas propriétaire de la sélection "
                            + selection.getIdSelection() + ".");
        }

        // 4. Vérifier qu'aucune mise en stage n'existe déjà
        if (miseEnStageRepository.existeParSelection(selection.getIdSelection())) {
            throw new MiseEnStageDejaExistanteException(selection.getIdSelection());
        }

        // 5. Vérifier les dates
        if (request.getDateDebut() == null || request.getDateFinStage() == null) {
            throw new DatesStageInvalidesException("Les dates de début et de fin sont obligatoires.");
        }
        if (request.getDateFinStage().isBefore(request.getDateDebut())) {
            throw new DatesStageInvalidesException(
                    "La date de fin doit être postérieure ou égale à la date de début.");
        }

        // 6. Création de la mise en stage
        String nouvelId = miseEnStageRepository.prochainId();
        MiseEnStage miseEnStage = new MiseEnStage(
                nouvelId,
                request.getDateDebut(),
                request.getDateFinStage(),
                StatutStage.EN_ATTENTE,
                selection.getIdSelection(),
                entrepriseProprietaire
        );
        miseEnStageRepository.sauvegarder(miseEnStage);

        // 7. Réponse
        return new MettreEnStageResponse(
                miseEnStage.getIdMiseStage(),
                miseEnStage.getDateDebut(),
                miseEnStage.getDateFin(),
                miseEnStage.getStatutStage(),
                miseEnStage.getIdSelection()
        );
    }
}
