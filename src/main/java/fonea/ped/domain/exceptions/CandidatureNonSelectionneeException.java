package fonea.ped.domain.exceptions;

public class CandidatureNonSelectionneeException extends DomaineException {
    public CandidatureNonSelectionneeException(String idSelection, String statutActuel) {
        super("La sélection " + idSelection +
              " ne peut pas donner lieu à une mise en stage (statut actuel : " + statutActuel + ").");
    }
}
