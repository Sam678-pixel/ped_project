package fonea.ped.interfaceAdapters.presenters;

import fonea.ped.interfaceAdapters.viewModels.DiplomeViewModel;
import fonea.ped.useCase.useCaseDiplome.useCase.creerDiplome.CreerDiplomeResponse;

import java.time.format.DateTimeFormatter;

/**
 * Presenter pour le module Diplôme.
 */
public class DiplomePresenter {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DiplomeViewModel presenterCreation(CreerDiplomeResponse response) {
        DiplomeViewModel vm = new DiplomeViewModel();
        vm.setId(String.valueOf(response.getIdDiplome()));
        vm.setNom(response.getNom());
        vm.setPrenom(response.getPrenom());
        vm.setDateNaissance(response.getDateNaissance() != null
                ? response.getDateNaissance().format(DATE_FORMAT) : "—");
        vm.setAge(response.getAge() + " ans");
        vm.setMessage("✅ Diplômé créé avec succès.");
        return vm;
    }
}
