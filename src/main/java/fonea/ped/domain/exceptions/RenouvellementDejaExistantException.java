package fonea.ped.domain.exceptions;

public class RenouvellementDejaExistantException extends DomaineException {
    public RenouvellementDejaExistantException(String idMiseStage) {
        super("Un renouvellement a déjà été effectué pour la mise en stage " + idMiseStage + ".");
    }
}