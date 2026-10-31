package fonea.ped.interfaceAdapters.presenters;

import fonea.ped.interfaceAdapters.viewModels.CandidatureViewModel;
import fonea.ped.useCase.useCaseDiplome.useCase.postuler.PostulerResponse;
import fonea.ped.useCase.useCaseRecrutement.useCase.PreselectionnerResponse;
import fonea.ped.useCase.useCaseRecrutement.useCase.selectionner.SelectionnerResponse;

import java.time.format.DateTimeFormatter;

/**
 * Presenter pour le module Recrutement.
 * Couvre : postuler, présélectionner, sélectionner.
 */
public class RecrutementPresenter {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CandidatureViewModel presenterPostulation(PostulerResponse response) {
        CandidatureViewModel vm = new CandidatureViewModel();
        vm.setId(String.valueOf(response.getIdCandidature()));
        vm.setDateDepot(response.getDateDepot() != null
                ? response.getDateDepot().format(DATE_FORMAT) : "—");
        vm.setStatut(response.getStatutCandidature() != null
                ? response.getStatutCandidature().name() : "—");
        vm.setIdDiplome(String.valueOf(response.getIdDiplome()));
        vm.setIdPublication(String.valueOf(response.getIdPublication()));
        vm.setMessage("✅ Candidature déposée avec succès.");
        return vm;
    }

    public CandidatureViewModel presenterPreselection(PreselectionnerResponse response) {
        CandidatureViewModel vm = new CandidatureViewModel();
        vm.setId(String.valueOf(response.getIdPreselection()));
        vm.setDateDepot(response.getDatePreselection() != null
                ? response.getDatePreselection().format(DATE_FORMAT) : "—");
        vm.setStatut(response.getStatut() != null ? response.getStatut().name() : "—");
        vm.setIdCandidature(String.valueOf(response.getIdCandidature()));
        vm.setMessage("✅ Candidature présélectionnée.");
        return vm;
    }

    public CandidatureViewModel presenterSelection(SelectionnerResponse response) {
        CandidatureViewModel vm = new CandidatureViewModel();
        vm.setId(String.valueOf(response.getIdSelection()));
        vm.setDateDepot(response.getDateSelection() != null
                ? response.getDateSelection().format(DATE_FORMAT) : "—");
        vm.setStatut(response.getStatutSelection() != null
                ? response.getStatutSelection().name() : "—");
        vm.setIdCandidature(String.valueOf(response.getIdPreselection()));
        vm.setMessage("✅ Candidat sélectionné.");
        return vm;
    }
}
