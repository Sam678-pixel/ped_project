package fonea.ped.useCase.useCaseOffres.UseCaseOffres.publierOffre;

import java.time.LocalDate;

/**
 * Entrée du cas d'usage « publier une offre » (action du FONEA).
 */
public class PublierOffreRequest {

    private final String idOffre;
    private final String idAgent;
    private final LocalDate dateExpiration;

    public PublierOffreRequest(String idOffre, String idAgent, LocalDate dateExpiration) {
        this.idOffre = idOffre;
        this.idAgent = idAgent;
        this.dateExpiration = dateExpiration;
    }

    public String getIdOffre() { return idOffre; }
    public String getIdAgent() { return idAgent; }
    public LocalDate getDateExpiration() { return dateExpiration; }
}
