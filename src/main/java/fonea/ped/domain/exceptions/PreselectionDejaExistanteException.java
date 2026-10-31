package fonea.ped.domain.exceptions;

public class PreselectionDejaExistanteException extends DomaineException {
    public PreselectionDejaExistanteException(String idCandidature) {
        super("Une présélection existe déjà pour la candidature " + idCandidature + ".");
    }
}
