package fonea.ped.interfaceAdapters.presenters;

import fonea.ped.interfaceAdapters.viewModels.EntrepriseViewModel;
import fonea.ped.useCase.useCaseEntreprise.useCase.CreerEntrepriseResponse;

/**
 * Presenter pour le module Entreprises.
 */
public class EntreprisePresenter {

    public EntrepriseViewModel presenterCreation(CreerEntrepriseResponse response) {
        EntrepriseViewModel vm = new EntrepriseViewModel();
        vm.setId(String.valueOf(response.getIdEntreprise()));
        vm.setRaisonSocial(response.getRaisonSocial());
        vm.setAdresse(response.getAdresse() != null ? response.getAdresse() : "—");
        vm.setNiu(response.getNiu() != null ? response.getNiu() : "—");
        vm.setIdSecteur(response.getIdUtilisateur() != null
                ? String.valueOf(response.getIdUtilisateur()) : "—");
        vm.setMessage("✅ Entreprise créée avec succès.");
        return vm;
    }
}
