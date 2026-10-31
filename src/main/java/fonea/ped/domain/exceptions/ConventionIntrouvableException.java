package fonea.ped.domain.exceptions;

public class ConventionIntrouvableException extends DomaineException {
    public ConventionIntrouvableException(String idConvention) {
        super("La convention avec l'id " + idConvention + " est introuvable.");
    }
}
