package fonea.ped.useCase.useCaseMiseEnStage.UseCase.renouvelerstage;

import java.time.LocalDate;

public class RenouvelerStageRequest {
	private final String idMiseStage;
    private final LocalDate nouvelleDateDebut;
    private final LocalDate nouvelleDateFin;

    public RenouvelerStageRequest(String idMiseStage,
                                  LocalDate nouvelleDateDebut,
                                  LocalDate nouvelleDateFin) {
        this.idMiseStage = idMiseStage;
        this.nouvelleDateDebut = nouvelleDateDebut;
        this.nouvelleDateFin = nouvelleDateFin;
    }

    public String getIdMiseStage() { return idMiseStage; }
    public LocalDate getNouvelleDateDebut() { return nouvelleDateDebut; }
    public LocalDate getNouvelleDateFin() { return nouvelleDateFin; }
}
