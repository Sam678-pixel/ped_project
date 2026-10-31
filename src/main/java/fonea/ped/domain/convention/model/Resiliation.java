package fonea.ped.domain.convention.model;

import java.time.LocalDate;
import java.util.Objects;

public class Resiliation {

    private String idResiliation;
    private LocalDate dateResiliation;
    private String initiateur;
    private String idConvention;
    private MotifResiliation motifResiliation;

    public Resiliation(String idResiliation, LocalDate dateResiliation,
                       String initiateur, String idConvention,MotifResiliation motifResiliation) {
        this.idResiliation = idResiliation;
        this.dateResiliation = dateResiliation;
        this.initiateur = initiateur;
        this.idConvention = idConvention;
        this.motifResiliation= motifResiliation;
    }

    public Resiliation(String idResiliation2, LocalDate dateResiliation2, String initiateur2, String idConvention2) {
    	this.idResiliation= idResiliation2;
    	this.dateResiliation=dateResiliation2;
    	this.initiateur= initiateur2;
    	this.idConvention= idConvention2;
    	
		// TODO Auto-generated constructor stub
	}

	public String getIdResiliation() { return idResiliation; }
    public LocalDate getDateResiliation() { return dateResiliation; }
    public String getInitiateur() { return initiateur; }
    public String getIdConvention() { return idConvention; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resiliation that = (Resiliation) o;
        return Objects.equals(idResiliation, that.idResiliation);
    }
    @Override public int hashCode() { return Objects.hash(idResiliation); }
    @Override public String toString() {
        return "Resiliation{idResiliation='" + idResiliation +
                "', dateResiliation=" + dateResiliation +
                "', initiateur='" + initiateur + "'}";
    }

	public MotifResiliation getMotifResiliation() {
		
		return motifResiliation;
	}
}
