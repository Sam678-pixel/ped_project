package fonea.ped.domain.exceptions;

import java.time.LocalDate;

public class DelaiPostulationDepasseException extends DomaineException {
    public DelaiPostulationDepasseException(String idOffre, LocalDate dateExpiration) {
        super("Le délai de postulation pour l'offre " + idOffre +
              " est dépassé (expiré le " + dateExpiration + ").");
    }
}
