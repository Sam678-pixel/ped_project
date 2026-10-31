package fonea.ped.domain.exceptions;

public class RenouvellementImpossibleException extends DomaineException {
    public RenouvellementImpossibleException(String idMiseStage, String statutActuel) {
        super("Le renouvellement est impossible pour la mise en stage " + idMiseStage +
              " (statut actuel : " + statutActuel + ", statut attendu : TERMINE).");
    }
}
