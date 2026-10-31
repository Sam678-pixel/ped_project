package fonea.ped.domain.exceptions;

public class ConventionDejaResilieeException  extends DomaineException {
    public ConventionDejaResilieeException(String idConvention) {
        super("La convention " + idConvention + " est déjà résiliée.");
    }
}
