package fonea.ped.useCase.useCaseMiseEnStage.UseCase.demarrerStage;

/**
 * Demande de démarrage d'une mise en stage.
 */
public class DemarrerStageRequest {

    private final String idMiseStage;

    public DemarrerStageRequest(String idMiseStage) {
        this.idMiseStage = idMiseStage;
    }

    public String getIdMiseStage() {
        return idMiseStage;
    }
}
