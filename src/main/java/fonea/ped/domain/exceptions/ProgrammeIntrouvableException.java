package fonea.ped.domain.exceptions;

public class ProgrammeIntrouvableException extends DomaineException {
    public ProgrammeIntrouvableException(String idProgramme) {
        super("Le programme avec l'id " + idProgramme + " est introuvable.");
    }
}