package fonea.ped.domain.exceptions;

public class DecrochageDejaDeclareException extends DomaineException {
    public DecrochageDejaDeclareException(String idMiseStage) {
        super("Un décrochage a déjà été déclaré pour la mise en stage " + idMiseStage + ".");
    }
}
