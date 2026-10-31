package fonea.ped.useCase.useCaseEntreprise.useCase;

public class CreerEntrepriseResponse {
	
	private final String idEntreprise;
    private final String raisonSocial;
    private final String adresse;
    private final String niu;
    private final String idUtilisateur;

    public CreerEntrepriseResponse(String idEntreprise,
                                   String raisonSocial,
                                   String adresse,
                                   String niu,
                                   String idUtilisateur) {
        this.idEntreprise = idEntreprise;
        this.raisonSocial = raisonSocial;
        this.adresse = adresse;
        this.niu = niu;
        this.idUtilisateur = idUtilisateur;
    }

    public String getIdEntreprise() { return idEntreprise; }
    public String getRaisonSocial() { return raisonSocial; }
    public String getAdresse() { return adresse; }
    public String getNiu() { return niu; }
    public String getIdUtilisateur() { return idUtilisateur; }
}
