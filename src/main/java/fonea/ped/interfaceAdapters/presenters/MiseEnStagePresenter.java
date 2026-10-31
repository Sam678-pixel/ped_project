package fonea.ped.interfaceAdapters.presenters;

import fonea.ped.interfaceAdapters.viewModels.MiseEnStageViewModel;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.declarerDecrochage.DeclarerDecrochageResponse;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.mettreEnStage.MettreEnStageResponse;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.renouvelerstage.RenouvelerStageResponse;

import java.time.format.DateTimeFormatter;

/**
 * Presenter pour le module Mises en stage.
 */
public class MiseEnStagePresenter {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public MiseEnStageViewModel presenterMiseEnStage(MettreEnStageResponse response) {
        MiseEnStageViewModel vm = new MiseEnStageViewModel();
        vm.setId(String.valueOf(response.getIdMiseStage()));
        vm.setDateDebut(response.getDateDebut() != null
                ? response.getDateDebut().format(DATE_FORMAT) : "—");
        vm.setDateFin(response.getDateFinStage() != null
                ? response.getDateFinStage().format(DATE_FORMAT) : "—");
        vm.setStatut(response.getStatutStage() != null
                ? response.getStatutStage().name() : "—");
        vm.setIdSelection(String.valueOf(response.getIdSelection()));
        vm.setMessage("✅ Mise en stage créée.");
        return vm;
    }

    public MiseEnStageViewModel presenterRenouvellement(RenouvelerStageResponse response) {
        MiseEnStageViewModel vm = new MiseEnStageViewModel();
        vm.setId(String.valueOf(response.getIdRenouvellement()));
        vm.setDateDebut(response.getDateDebutStage() != null
                ? response.getDateDebutStage().format(DATE_FORMAT) : "—");
        vm.setDateFin(response.getDateFinStage() != null
                ? response.getDateFinStage().format(DATE_FORMAT) : "—");
        vm.setStatut(response.getStatutMiseStage() != null
                ? response.getStatutMiseStage().name() : "—");
        vm.setIdMiseStage(String.valueOf(response.getIdMiseStage()));
        vm.setMessage("✅ Stage renouvelé.");
        return vm;
    }

    public MiseEnStageViewModel presenterDecrochage(DeclarerDecrochageResponse response) {
        MiseEnStageViewModel vm = new MiseEnStageViewModel();
        vm.setId(String.valueOf(response.getIdDecrochage()));
        vm.setDateDebut(response.getDateDecrochage() != null
                ? response.getDateDecrochage().format(DATE_FORMAT) : "—");
        vm.setIdMiseStage(String.valueOf(response.getIdMiseStage()));
        vm.setRaison(response.getRaison());
        vm.setStatut("Décroché");
        vm.setMessage("✅ Décrochage déclaré.");
        return vm;
    }
}
