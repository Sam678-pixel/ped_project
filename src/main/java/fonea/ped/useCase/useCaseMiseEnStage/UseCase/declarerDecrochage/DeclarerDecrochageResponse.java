package fonea.ped.useCase.useCaseMiseEnStage.UseCase.declarerDecrochage;

import java.time.LocalDate;

import fonea.ped.domain.MiseEnStage.MotifDecrochage;

public class DeclarerDecrochageResponse {
	
	private final String idDecrochage;
    private final LocalDate dateDecrochage;
    private final String idMiseStage;
    private final MotifDecrochage raison;

    public DeclarerDecrochageResponse(String idDecrochage,
                                      LocalDate dateDecrochage,
                                      String idMiseStage,
                                      MotifDecrochage raison) {
        this.idDecrochage = idDecrochage;
        this.dateDecrochage = dateDecrochage;
        this.idMiseStage = idMiseStage;
        this.raison = raison;
    }

    public String getIdDecrochage() { return idDecrochage; }
    public LocalDate getDateDecrochage() { return dateDecrochage; }
    public String getIdMiseStage() { return idMiseStage; }
    public MotifDecrochage getRaison() { return raison; }
}
