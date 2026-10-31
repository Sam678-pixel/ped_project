package fonea.ped.domain.exceptions;

public class EntrepriseIntrouvableException extends DomaineException {
    public EntrepriseIntrouvableException(String idEntreprise) {
        super("L'entreprise avec l'id " + idEntreprise + " est introuvable.");
    }
}
