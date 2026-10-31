package fonea.ped.interfaceAdapters.presenters;

import fonea.ped.domain.convention.model.StatutConvention;
import fonea.ped.interfaceAdapters.viewModels.ConventionViewModel;
import fonea.ped.useCase.UseCaseConvention.useCase.creerConvention.CreerConventionResponse;
import fonea.ped.useCase.UseCaseConvention.useCase.resilierConvention.ResilierConventionResponse;

import java.time.format.DateTimeFormatter;

/**
 * Presenter pour le module Conventions.
 * Transforme les Response DTOs en ViewModels formatés pour l'affichage.
 */
public class ConventionPresenter {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ConventionViewModel presenterCreation(CreerConventionResponse response) {
        ConventionViewModel vm = new ConventionViewModel();
        vm.setId(String.valueOf(response.getIdConvention()));
        vm.setDateEffet(response.getDateEffet() != null
                ? response.getDateEffet().format(DATE_FORMAT) : "—");
        vm.setTauxFonea(formatTaux(response.getTauxFonea()));
        vm.setTauxEntreprise(formatTaux(response.getTauxEntreprise()));
        vm.setStatut(traduireStatut(response.getStatut()));
        vm.setMessage("✅ Convention créée avec succès.");
        return vm;
    }

    public ConventionViewModel presenterResiliation(ResilierConventionResponse response) {
        ConventionViewModel vm = new ConventionViewModel();
        vm.setId(String.valueOf(response.getIdConvention()));
        vm.setDateResiliation(response.getDateResiliation() != null
                ? response.getDateResiliation().format(DATE_FORMAT) : "—");
        vm.setMotif(response.getMotif());
        vm.setInitiateur(response.getInitiateur());
        vm.setStatut("Résiliée");
        vm.setMessage("✅ Convention résiliée. Offres annulées : "
                + (response.getOffresAnnulees() != null ? response.getOffresAnnulees().size() : 0));
        return vm;
    }

    // ---- Helpers ----

    private String formatTaux(Double taux) {
        if (taux == null) return "—";
        return String.format("%.2f %%", taux);
    }

    private String traduireStatut(StatutConvention statut) {
        if (statut == null) return "Inconnu";
        return switch (statut) {
            case BROUILLON -> "Brouillon";
            case ACTIVE -> "Active";
            case SUSPENDUE -> "Suspendue";
            case RESILIEE -> "Résiliée";
            case EXPIREE -> "Expirée";
        };
    }
}