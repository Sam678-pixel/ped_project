package fonea.ped.domain.exceptions;

public class CandidatureIntrouvableException extends DomaineException {
	    public CandidatureIntrouvableException(Object object) {
	        super("La candidature avec l'id " + object + " est introuvable.");
	    }
}
