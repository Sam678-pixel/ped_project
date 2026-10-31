package fonea.ped.domain.exceptions;

public class ConventionNonActiveException extends DomaineException {
    public ConventionNonActiveException(String idConvention, String statutActuel) {
        super("La convention " + idConvention + " ne peut pas être résiliée car son statut est : " + statutActuel);
    }
}
