package fonea.ped.useCase.useCaseMiseEnStage.UseCase.mettreEnStage;

import java.time.LocalDate;

public class MettreEnStageRequest {
	
	 private final String idSelection;
	    private final LocalDate dateDebut;
	    private final LocalDate dateFinStage;
	    private final String idEntreprise;

	    public MettreEnStageRequest(String idSelection,
	                                LocalDate dateDebut,
	                                LocalDate dateFinStage,
	                                String idEntreprise) {
	        this.idSelection = idSelection;
	        this.dateDebut = dateDebut;
	        this.dateFinStage = dateFinStage;
	        this.idEntreprise= idEntreprise;
	    }

	    public String getIdSelection() { return idSelection; }
	    public LocalDate getDateDebut() { return dateDebut; }
	    public LocalDate getDateFinStage() { return dateFinStage; }

		public String getIdEntreprise() {
			// TODO Auto-generated method stub
			return idEntreprise;
		}
}
