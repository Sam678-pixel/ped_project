package fonea.ped.useCase.useCaseRecrutement.useCase.selectionner;

import java.time.LocalDate;

import fonea.ped.domain.exceptions.CandidatureIntrouvableException;
import fonea.ped.domain.exceptions.CandidatureNonPreselectionneeException;
import fonea.ped.domain.exceptions.PreselectionIntrouvableException;
import fonea.ped.domain.exceptions.SelectionDejaExistanteException;

import fonea.ped.domain.recrutement.*;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.PreselectionRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.SelectionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SelectionnerUseCase {
	private final PreselectionRepository preselectionRepository;
    private final SelectionRepository selectionRepository;
    private final CandidatureRepository candidatureRepository;

    public SelectionnerUseCase(PreselectionRepository preselectionRepository,
                               SelectionRepository selectionRepository,
                               CandidatureRepository candidatureRepository) {
        this.preselectionRepository = preselectionRepository;
        this.selectionRepository = selectionRepository;
        this.candidatureRepository = candidatureRepository;
    }

    public SelectionnerResponse executer(SelectionnerRequest request) {

        // 1. Vérifier l'existence de la présélection
        Preselection preselection = preselectionRepository.trouverParId(request.getIdPreselection())
                .orElseThrow(() -> new PreselectionIntrouvableException(request.getIdPreselection()));

        // 2. Charger la candidature liée
        Candidature candidature = candidatureRepository.trouverParId(preselection.getIdCandidature())
                .orElseThrow(() -> new CandidatureIntrouvableException(preselection.getIdCandidature()));

        // 3. Vérifier que la candidature est présélectionnée
        if (candidature.getStatutCandidature() != StatutCandidature.PRESELECTIONNEE) {
            throw new CandidatureNonPreselectionneeException(
                    candidature.getIdCandidature(),
                    candidature.getStatutCandidature().name());
        }

        // 4. Vérifier qu'aucune sélection n'existe déjà
        if (selectionRepository.existeParPreselection(preselection.getIdPreselection())) {
            throw new SelectionDejaExistanteException(preselection.getIdPreselection());
        }

        // 5. Création de la sélection (validation directe)
        String nouvelId = selectionRepository.prochainId();
        Selection selection = new Selection(
                nouvelId,
                LocalDate.now(),
                StatutCandidature.SELECTIONNEE,
                preselection.getIdPreselection()
        );
        selectionRepository.sauvegarder(selection);

        // 6. Mettre à jour la présélection
        preselection.changerStatut(StatutCandidature.SELECTIONNEE);
        preselectionRepository.sauvegarder(preselection);

        // 7. Mettre à jour la candidature
        candidature.changerStatut(StatutCandidature.SELECTIONNEE);
        candidatureRepository.sauvegarder(candidature);

        // 8. Réponse
        return new SelectionnerResponse(
                selection.getIdSelection(),
                selection.getDateSelection(),
                selection.getStatutSelection(),
                selection.getIdPreselection()
        );
    }
}
