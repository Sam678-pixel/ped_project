package fonea.ped.domain.exceptions;

public class MotifObligatoireException extends DomaineException {
    public MotifObligatoireException() {
        super("Le motif de résiliation est obligatoire.");
    }
}
