package fonea.ped.useCase.UseCaseConvention.useCase.resilierConvention;

import java.time.LocalDate;
import java.util.List;

import fonea.ped.domain.convention.model.MotifResiliation;

public class ResilierConventionResponse {
	
	private final String idResiliation;
    private final String idConvention;
    private final LocalDate dateResiliation;
    private final String initiateur;
    private final MotifResiliation motif;
    private final List<String> offresAnnulees;

    public ResilierConventionResponse(String idResiliation,
                                      String idConvention,
                                      LocalDate dateResiliation,
                                      String initiateur,
                                      MotifResiliation motif,
                                      List<String> offresAnnulees) {
        this.idResiliation = idResiliation;
        this.idConvention = idConvention;
        this.dateResiliation = dateResiliation;
        this.initiateur = initiateur;
        this.motif = motif;
        this.offresAnnulees = offresAnnulees;
    }

    public String getIdResiliation() { return idResiliation; }
    public String getIdConvention() { return idConvention; }
    public LocalDate getDateResiliation() { return dateResiliation; }
    public String getInitiateur() { return initiateur; }
    public MotifResiliation getMotif() { return motif; }
    public List<String> getOffresAnnulees() { return offresAnnulees; }
}
