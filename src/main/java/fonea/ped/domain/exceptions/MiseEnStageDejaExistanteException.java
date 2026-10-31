package fonea.ped.domain.exceptions;

public class MiseEnStageDejaExistanteException extends DomaineException {
    public MiseEnStageDejaExistanteException(String idSelection) {
        super("Une mise en stage existe déjà pour la sélection " + idSelection + ".");
    }
}
