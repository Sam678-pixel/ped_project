package fonea.ped.useCase.useCaseMiseEnStage.UseCase.renouvelerstage;

import java.time.LocalDate;

import fonea.ped.domain.MiseEnStage.StatutStage;

public class RenouvelerStageResponse {
	
	 private final String idRenouvellement;
	    private final LocalDate dateDebutStage;
	    private final LocalDate dateFinStage;
	    private final String idMiseStage;
	    private final fonea.ped.domain.MiseEnStage.StatutStage statutMiseStage;

	    public RenouvelerStageResponse(String idRenouvellement,
	                                   LocalDate dateDebutStage,
	                                   LocalDate dateFinStage,
	                                   String idMiseStage,
	                                   fonea.ped.domain.MiseEnStage.StatutStage statutMiseStage) {
	        this.idRenouvellement = idRenouvellement;
	        this.dateDebutStage = dateDebutStage;
	        this.dateFinStage = dateFinStage;
	        this.idMiseStage = idMiseStage;
	        this.statutMiseStage = statutMiseStage;
	    }

	    public String getIdRenouvellement() { return idRenouvellement; }
	    public LocalDate getDateDebutStage() { return dateDebutStage; }
	    public LocalDate getDateFinStage() { return dateFinStage; }
	    public String getIdMiseStage() { return idMiseStage; }
	    public StatutStage getStatutMiseStage() { return statutMiseStage; }
}
