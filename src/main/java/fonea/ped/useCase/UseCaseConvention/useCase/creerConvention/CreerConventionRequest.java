package fonea.ped.useCase.UseCaseConvention.useCase.creerConvention;

import java.time.LocalDate;

import fonea.ped.domain.convention.model.StatutConvention;
import fonea.ped.domain.convention.model.TypeConvention;

public class CreerConventionRequest {
	private final LocalDate dateEffet;
    private final Double tauxFonea;
    private final Double tauxEntreprise;
    private final StatutConvention statutConvention;
    private final String idEntreprise;
    private final String idProgramme;
    private final TypeConvention typeConvention;

    public CreerConventionRequest(LocalDate dateEffet,
                                  Double tauxFonea,
                                  Double tauxEntreprise,
                                  StatutConvention statutConvention,
                                  String idEntreprise,
                                  TypeConvention typeConvention,
                                  String idProgramme) {
        this.dateEffet = dateEffet;
        this.tauxFonea = tauxFonea;
        this.tauxEntreprise = tauxEntreprise;
        this.statutConvention= statutConvention;
        this.idEntreprise = idEntreprise;
		this.typeConvention = typeConvention;
		this.idProgramme= idProgramme;
    }

    public LocalDate getDateEffet() { return dateEffet; }
    public Double getTauxFonea() { return tauxFonea; }
    public Double getTauxEntreprise() { return tauxEntreprise; }
    public String getIdProgramme() { return idProgramme; }
    public String getIdEntreprise() { return idEntreprise; }
    public StatutConvention getStatutConvention() {return statutConvention ;}

	public TypeConvention getTypeConvention() {
		return  typeConvention;
	}
}
