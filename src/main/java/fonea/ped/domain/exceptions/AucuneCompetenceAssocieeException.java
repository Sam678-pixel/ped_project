package fonea.ped.domain.exceptions;

public class AucuneCompetenceAssocieeException extends DomaineException {
    public AucuneCompetenceAssocieeException() {
        super("Au moins une compétence doit être associée à l'offre.");
    }
}