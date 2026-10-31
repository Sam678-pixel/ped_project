package fonea.ped.useCase.useCaseRecrutement.useCase;

public class PreselectionnerRequest {
	
	private final String idCandidature;
	private  String idAgent ;
	
	public PreselectionnerRequest(String idCandidature2) {
    	this.idCandidature = idCandidature2;
	}

    public PreselectionnerRequest(String idCandidature,String idAgent ) {
        this.idCandidature = idCandidature;
        this.idAgent= idAgent;
    }

    

	public String getIdCandidature() { return idCandidature; }
    public String getIdAgent() { return idAgent; }
}
