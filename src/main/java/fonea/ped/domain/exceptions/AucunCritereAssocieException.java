package fonea.ped.domain.exceptions;

public class AucunCritereAssocieException extends DomaineException {
    public AucunCritereAssocieException() {
        super("Au moins un critère doit être associé à l'offre.");
    }
}
