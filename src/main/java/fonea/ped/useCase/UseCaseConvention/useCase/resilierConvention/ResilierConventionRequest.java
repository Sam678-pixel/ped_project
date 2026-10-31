package fonea.ped.useCase.UseCaseConvention.useCase.resilierConvention;

public class ResilierConventionRequest {
	
	 private final String idConvention;
	    private final String motif;
	    private final String initiateur;

	    public ResilierConventionRequest(String idConvention, String motif, String initiateur) {
	        this.idConvention = idConvention;
	        this.motif = motif;
	        this.initiateur = initiateur;
	    }

	    public String getIdConvention() { return idConvention; }
	    public String getMotif() { return motif; }
	    public String getInitiateur() { return initiateur; }
}
