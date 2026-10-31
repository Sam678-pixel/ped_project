package fonea.ped.domain.exceptions;

public class InsertionDejaExistanteException  extends DomaineException {
    public InsertionDejaExistanteException(String idMiseStage) {
        super("Une insertion existe déjà pour la mise en stage " + idMiseStage + ".");
    }
}
