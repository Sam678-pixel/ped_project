package fonea.ped.useCase.UseCaseConvention.useCase.creerConvention;

import java.time.LocalDate;


import fonea.ped.domain.convention.model.StatutConvention;

public class CreerConventionResponse {
	
	private final String idConvention;
    private final LocalDate dateEffet;
    private final Double tauxFonea;
    private final Double tauxEntreprise;
    private final StatutConvention statut;
    private final String idProgramme;
    private final String idEntreprise;

    public CreerConventionResponse(String idConvention,
                                   LocalDate dateEffet,
                                   Double tauxFonea,
                                   Double tauxEntreprise,
                                   StatutConvention statut,
                                   String idProgramme,
                                   String idEntreprise) {
        this.idConvention = idConvention;
        this.dateEffet = dateEffet;
        this.tauxFonea = tauxFonea;
        this.tauxEntreprise = tauxEntreprise;
        this.statut = statut;
        this.idProgramme = idProgramme;
        this.idEntreprise = idEntreprise;
    }

    public String getIdConvention() { return idConvention; }
    public LocalDate getDateEffet() { return dateEffet; }
    public Double getTauxFonea() { return tauxFonea; }
    public Double getTauxEntreprise() { return tauxEntreprise; }
    public StatutConvention getStatut() { return statut; }
    public String getIdProgramme() { return idProgramme; }
    public String getIdEntreprise() { return idEntreprise; }
}
