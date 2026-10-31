package fonea.ped.domain.exceptions;

public class MiseEnStageIntrouvableException extends DomaineException {
    public MiseEnStageIntrouvableException(String idMiseStage) {
        super("La mise en stage avec l'id " + idMiseStage + " est introuvable.");
    }
}
