package fonea.ped.useCase.useCaseMiseEnStage.UseCase.declarerDecrochage;

import fonea.ped.domain.MiseEnStage.MotifDecrochage;

public class DeclarerDecrochageRequest {
	
	  private final String idMiseStage;
	    private final MotifDecrochage raison;
	    private final String idEntreprise;

	    public DeclarerDecrochageRequest(String idMiseStage,
	                                     MotifDecrochage raison,
	                                     String idEntreprise) {
	        this.idMiseStage = idMiseStage;
	        this.raison = raison;
	        this.idEntreprise = idEntreprise;
	    }

	    public String getIdMiseStage() { return idMiseStage; }
	    public MotifDecrochage getRaison() { return raison; }
	    public String getIdEntreprise() { return idEntreprise; }
}
