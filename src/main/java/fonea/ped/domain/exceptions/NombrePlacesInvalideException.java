package fonea.ped.domain.exceptions;

public class NombrePlacesInvalideException extends DomaineException {
    public NombrePlacesInvalideException() {
        super("Le nombre de places doit être strictement positif.");
    }
}
