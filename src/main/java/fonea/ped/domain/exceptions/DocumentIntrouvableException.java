package fonea.ped.domain.exceptions;

public class DocumentIntrouvableException extends DomaineException {
    public DocumentIntrouvableException(String idDocument) {
        super("Le document avec l'id " + idDocument + " est introuvable.");
    }
}
