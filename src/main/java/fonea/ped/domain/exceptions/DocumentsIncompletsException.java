package fonea.ped.domain.exceptions;

public class DocumentsIncompletsException extends DomaineException {
    public DocumentsIncompletsException() {
        super("Les documents fournis sont incomplets (CV, CNI et Diplôme obligatoires).");
    }
}
