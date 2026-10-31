package fonea.ped.domain.exceptions;

public class OffreIntrouvableException extends DomaineException {
    public OffreIntrouvableException(String idOffre) {
        super("L'offre avec l'id " + idOffre + " est introuvable.");
    }
}
