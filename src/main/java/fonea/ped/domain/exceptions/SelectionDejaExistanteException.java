package fonea.ped.domain.exceptions;

public class SelectionDejaExistanteException extends DomaineException {
    public SelectionDejaExistanteException(String idPreselection) {
        super("Une sélection existe déjà pour la présélection " + idPreselection + ".");
    }
}