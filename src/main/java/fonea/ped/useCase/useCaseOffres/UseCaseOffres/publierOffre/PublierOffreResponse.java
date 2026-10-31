package fonea.ped.useCase.useCaseOffres.UseCaseOffres.publierOffre;

import java.time.LocalDate;

import fonea.ped.domain.Offres.StatutOffre;

public class PublierOffreResponse {

    private final String idPublication;
    private final String idOffre;
    private final LocalDate datePublication;
    private final LocalDate dateExpiration;
    private final StatutOffre statutOffre;

    public PublierOffreResponse(String idPublication, String idOffre, LocalDate datePublication,
                                LocalDate dateExpiration, StatutOffre statutOffre) {
        this.idPublication = idPublication;
        this.idOffre = idOffre;
        this.datePublication = datePublication;
        this.dateExpiration = dateExpiration;
        this.statutOffre = statutOffre;
    }

    public String getIdPublication() { return idPublication; }
    public String getIdOffre() { return idOffre; }
    public LocalDate getDatePublication() { return datePublication; }
    public LocalDate getDateExpiration() { return dateExpiration; }
    public StatutOffre getStatutOffre() { return statutOffre; }
}
