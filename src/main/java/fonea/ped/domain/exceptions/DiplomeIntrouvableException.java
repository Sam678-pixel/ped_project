package fonea.ped.domain.exceptions;

public class DiplomeIntrouvableException extends DomaineException {
    public DiplomeIntrouvableException(String idDiplome) {
        super("Le diplômé avec l'id " + idDiplome + " est introuvable.");
    }
}