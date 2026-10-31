package fonea.ped.domain.exceptions;

public class DecrochageImpossibleException extends DomaineException {
    public DecrochageImpossibleException(String idMiseStage, String statutActuel) {
        super("Le décrochage est impossible pour la mise en stage " + idMiseStage +
              " (statut actuel : " + statutActuel + ").");
    }
}
