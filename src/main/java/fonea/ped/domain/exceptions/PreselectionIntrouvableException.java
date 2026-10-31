package fonea.ped.domain.exceptions;

public class PreselectionIntrouvableException extends DomaineException {
    public PreselectionIntrouvableException(String idPreselection) {
        super("La présélection avec l'id " + idPreselection + " est introuvable.");
    }
}
