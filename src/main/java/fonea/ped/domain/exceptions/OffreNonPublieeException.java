package fonea.ped.domain.exceptions;

public class OffreNonPublieeException extends DomaineException {
    public OffreNonPublieeException(String idOffre, String statutActuel) {
        super("L'offre " + idOffre + " n'est pas publiée (statut actuel : " + statutActuel + ").");
    }
}