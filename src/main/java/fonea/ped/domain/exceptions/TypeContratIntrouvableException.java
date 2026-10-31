package fonea.ped.domain.exceptions;

public class TypeContratIntrouvableException extends DomaineException {
    public TypeContratIntrouvableException(String idTypeContrat) {
        super("Le type de contrat avec l'id " + idTypeContrat + " est introuvable.");
    }
}
