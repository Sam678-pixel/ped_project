package fonea.ped.interfaceAdapters.presenters;

import fonea.ped.interfaceAdapters.viewModels.InsertionViewModel;
import fonea.ped.useCase.useCaseInsertion.useCase.inserer.InsererResponse;

import java.time.format.DateTimeFormatter;

/**
 * Presenter pour le module Insertion.
 */
public class InsertionPresenter {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public InsertionViewModel presenterInsertion(InsererResponse response) {
        InsertionViewModel vm = new InsertionViewModel();
        vm.setIdInsertion(String.valueOf(response.getIdInsertion()));
        vm.setDateEmbauche(response.getDateEmbauche() != null
                ? response.getDateEmbauche().format(DATE_FORMAT) : "—");
        vm.setIdPoste(String.valueOf(response.getIdPoste()));
        vm.setIdTypeContrat(String.valueOf(response.getIdTypeContrat()));
        vm.setIdMiseStage(String.valueOf(response.getIdMiseStage()));
        vm.setIdContrat(String.valueOf(response.getIdContrat()));
        vm.setDateDebutContrat(response.getDateDebutContrat() != null
                ? response.getDateDebutContrat().format(DATE_FORMAT) : "—");
        vm.setDateFinContrat(response.getDateFinContrat() != null
                ? response.getDateFinContrat().format(DATE_FORMAT) : "CDI");
        vm.setSalaire(formatSalaire(response.getSalaire()));
        vm.setStatutContrat(response.getStatutContrat() != null
                ? response.getStatutContrat().name() : "—");
        vm.setMessage("✅ Insertion enregistrée avec contrat associé.");
        return vm;
    }

    private String formatSalaire(Double salaire) {
        if (salaire == null) return "—";
        return String.format("%,.0f FCFA", salaire);
    }
}
