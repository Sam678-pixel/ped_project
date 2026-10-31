package fonea.ped.useCase.useCaseRecrutement.useCase;

import java.time.LocalDate;

import fonea.ped.domain.recrutement.StatutCandidature;

public class PreselectionnerResponse {
	
	 private final String idPreselection;
	    private final LocalDate datePreselection;
	    private final StatutCandidature statut;
	    private final String idCandidature;

	    public PreselectionnerResponse(String idPreselection,
	                                   LocalDate datePreselection,
	                                   StatutCandidature statut,
	                                   String idCandidature) {
	        this.idPreselection = idPreselection;
	        this.datePreselection = datePreselection;
	        this.statut = statut;
	        this.idCandidature = idCandidature;
	    }

	    public String getIdPreselection() { return idPreselection; }
	    public LocalDate getDatePreselection() { return datePreselection; }
	    public StatutCandidature getStatut() { return statut; }
	    public String getIdCandidature() { return idCandidature; }
}
