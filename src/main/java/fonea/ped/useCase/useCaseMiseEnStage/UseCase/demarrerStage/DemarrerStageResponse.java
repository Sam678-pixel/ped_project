package fonea.ped.useCase.useCaseMiseEnStage.UseCase.demarrerStage;

import java.time.LocalDate;

import fonea.ped.domain.MiseEnStage.StatutStage;

/**
 * Résultat du démarrage d'une mise en stage.
 */
public class DemarrerStageResponse {

    private final String idMiseStage;
    private final LocalDate dateDebut;
    private final LocalDate dateFin;
    private final StatutStage statutStage;

    public DemarrerStageResponse(String idMiseStage,
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
