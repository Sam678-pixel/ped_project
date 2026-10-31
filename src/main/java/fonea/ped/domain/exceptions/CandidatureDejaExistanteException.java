package fonea.ped.domain.exceptions;

public class CandidatureDejaExistanteException extends DomaineException {
    public CandidatureDejaExistanteException(String idDiplome, String idOffre) {
        super("Le diplômé " + idDiplome + " a déjà postulé à l'offre " + idOffre + ".");
    }
}
