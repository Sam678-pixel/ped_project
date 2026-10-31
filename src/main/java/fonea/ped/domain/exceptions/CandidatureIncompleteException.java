package fonea.ped.domain.exceptions;

public class CandidatureIncompleteException  extends DomaineException {
    public CandidatureIncompleteException(String idCandidature) {
        super("La candidature " + idCandidature + " est incomplète : documents manquants.");
    }
}