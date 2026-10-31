package fonea.ped.domain.exceptions;

public class ReferenceIntrouvableException extends DomaineException {
    public ReferenceIntrouvableException(String type, String id) {
        super("La référence '" + type + "' avec l'id " + id + " est introuvable.");
    }
}
