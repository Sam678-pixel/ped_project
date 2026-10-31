package fonea.ped.useCase.useCaseMiseEnStage.UseCase.terminerStage;

/**
 * Demande de clôture d'une mise en stage.
 */
public class TerminerStageRequest {

    private final String idMiseStage;

    public TerminerStageRequest(String idMiseStage) {
        this.idMiseStage = idMiseStage;
    }

    public String getIdMiseStage() {
        return idMiseStage;
    }
}
