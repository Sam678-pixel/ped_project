package fonea.ped.domain.exceptions;

public class InsertionImpossibleException extends DomaineException {
    public InsertionImpossibleException(String idMiseStage, String statutActuel) {
        super("L'insertion est impossible pour la mise en stage " + idMiseStage +
              " (statut actuel : " + statutActuel + ", statut attendu : TERMINE).");
    }
}
