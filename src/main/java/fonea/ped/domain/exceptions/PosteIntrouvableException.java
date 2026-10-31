package fonea.ped.domain.exceptions;

public class PosteIntrouvableException extends DomaineException {
    public PosteIntrouvableException(String idPoste) {
        super("Le poste avec l'id " + idPoste + " est introuvable.");
    }
}
