package fonea.ped.useCase.useCaseMiseEnStage.UseCase.terminerStage;

import java.time.LocalDate;

import fonea.ped.domain.MiseEnStage.StatutStage;

/**
 * Résultat de la clôture d'une mise en stage.
 */
public class TerminerStageResponse {

    private final String idMiseStage;
    private final LocalDate dateDebut;
    private final LocalDate dateFin;
    private final StatutStage statutStage;

    public TerminerStageResponse(String idMiseStage,
                                 LocalDate dateDebut,
                                 LocalDate dateFin,
                                 StatutStage statutStage) {
        this.idMiseStage = idMiseStage;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statutStage = statutStage;
    }

    public String getIdMiseStage() { return idMiseStage; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public StatutStage getStatutStage() { return statutStage; }
}
