package fonea.ped.domain.exceptions;

public class CandidatureNonPreselectionneeException extends DomaineException {
    public CandidatureNonPreselectionneeException(String idCandidature, String statutActuel) {
        super("La candidature " + idCandidature +
              " n'est pas présélectionnée (statut actuel : " + statutActuel + ").");
    }
}
