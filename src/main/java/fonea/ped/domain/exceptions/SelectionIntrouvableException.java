package fonea.ped.domain.exceptions;

public class SelectionIntrouvableException extends DomaineException {
    public SelectionIntrouvableException(String idSelection) {
        super("La sélection avec l'id " + idSelection + " est introuvable.");
    }
}
