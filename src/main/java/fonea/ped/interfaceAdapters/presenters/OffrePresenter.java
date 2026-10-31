package fonea.ped.interfaceAdapters.presenters;

import fonea.ped.domain.Offres.StatutOffre;
import fonea.ped.interfaceAdapters.viewModels.OffreViewModel;
import fonea.ped.useCase.useCaseOffres.UseCaseOffres.creerOffre.CreerOffreResponse;

/**
 * Presenter pour le module Offres.
 */
public class OffrePresenter {

    public OffreViewModel presenterCreation(CreerOffreResponse response) {
        OffreViewModel vm = new OffreViewModel();
        vm.setId(String.valueOf(response.getIdOffre()));
        vm.setIntitule(response.getIntitule());
        vm.setVilleStage(response.getVilleStage());
        vm.setDuree(response.getDuree() != null
                ? response.getDuree() + " jours" : "—");
        vm.setNbrePlaces(String.valueOf(response.getNbrePlaces()));
        vm.setStatut(traduireStatut(response.getStatut()));
        vm.setIdConvention(String.valueOf(response.getIdConvention()));
        vm.setIdDomaine(String.valueOf(response.getIdDomaine()));
        vm.setIdPoste(String.valueOf(response.getIdPoste()));
        vm.setMessage("✅ Offre créée avec succès.");
        return vm;
    }

    private String traduireStatut(StatutOffre statut) {
        if (statut == null) return "Inconnu";
        return switch (statut) {
            case BROUILLON -> "Brouillon";
            case PUBLIEE -> "Publiée";
            case CLOTUREE -> "Clôturée";
            case ANNULEE -> "Annulée";
		default -> throw new IllegalArgumentException("Unexpected value: " + statut);
        };
    }
}
