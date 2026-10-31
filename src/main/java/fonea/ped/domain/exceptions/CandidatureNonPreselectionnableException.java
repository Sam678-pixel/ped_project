package fonea.ped.domain.exceptions;

public class CandidatureNonPreselectionnableException extends DomaineException {
    public CandidatureNonPreselectionnableException(String idCandidature, String statutActuel) {
        super("La candidature " + idCandidature +
              " ne peut pas être présélectionnée (statut actuel : " + statutActuel + ").");
    }
}
