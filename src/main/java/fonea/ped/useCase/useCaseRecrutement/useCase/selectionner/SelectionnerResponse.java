package fonea.ped.useCase.useCaseRecrutement.useCase.selectionner;

import java.time.LocalDate;

import fonea.ped.domain.recrutement.StatutCandidature;


public class SelectionnerResponse {
	
	private final String idSelection;
    private final LocalDate dateSelection;
    private final StatutCandidature statutSelection;
    private final String idPreselection;

    public SelectionnerResponse(String idSelection,
                                LocalDate dateSelection,
                                StatutCandidature statutSelection,
                                String idPreselection) {
        this.idSelection = idSelection;
        this.dateSelection = dateSelection;
        this.statutSelection = statutSelection;
        this.idPreselection = idPreselection;
    }

    public String getIdSelection() { return idSelection; }
    public LocalDate getDateSelection() { return dateSelection; }
    public StatutCandidature getStatutSelection() { return statutSelection; }
    public String getIdPreselection() { return idPreselection; }
}
