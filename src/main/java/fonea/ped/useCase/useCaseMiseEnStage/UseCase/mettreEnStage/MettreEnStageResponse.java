package fonea.ped.useCase.useCaseMiseEnStage.UseCase.mettreEnStage;

import java.time.LocalDate;

import fonea.ped.domain.MiseEnStage.StatutStage;

public class MettreEnStageResponse {
	
	private final String idMiseStage;
    private final LocalDate dateDebut;
    private final LocalDate dateFinStage;
    private final StatutStage statutStage;
    private final String idSelection;

    public MettreEnStageResponse(String idMiseStage,
                                 LocalDate dateDebut,
                                 LocalDate dateFinStage,
                                 StatutStage statutStage,
                                 String idSelection) {
        this.idMiseStage = idMiseStage;
        this.dateDebut = dateDebut;
        this.dateFinStage = dateFinStage;
        this.statutStage = statutStage;
        this.idSelection = idSelection;
    }

    public String getIdMiseStage() { return idMiseStage; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFinStage() { return dateFinStage; }
    public StatutStage getStatutStage() { return statutStage; }
    public String getIdSelection() { return idSelection; }
}
