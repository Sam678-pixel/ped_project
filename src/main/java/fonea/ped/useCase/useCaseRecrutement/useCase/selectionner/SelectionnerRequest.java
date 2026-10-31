package fonea.ped.useCase.useCaseRecrutement.useCase.selectionner;

public class SelectionnerRequest {
	
	private final String idPreselection;

    public SelectionnerRequest(String idPreselection) {
        this.idPreselection = idPreselection;
    }

    public String getIdPreselection() { return idPreselection; }
}
