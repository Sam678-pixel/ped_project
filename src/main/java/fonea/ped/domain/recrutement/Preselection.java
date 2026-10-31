package fonea.ped.domain.recrutement;

import java.time.LocalDate;
import java.util.Objects;

public class Preselection {

    private String idPreselection;
    private LocalDate datePreselection;
    private StatutCandidature statutPreselection;
    private String idAgent;
    private String idCandidature;

    public Preselection(String idPreselection, LocalDate datePreselection,
    		StatutCandidature statutPreselection, String idAgent, String idCandidature) {
        this.idPreselection = idPreselection;
        this.datePreselection = datePreselection;
        this.statutPreselection = statutPreselection != null ? statutPreselection : StatutCandidature.EN_COURS;
        this.idAgent = idAgent;
        this.idCandidature = idCandidature;
    }

    public void changerStatut(StatutCandidature nouveauStatut) {
        if (nouveauStatut == null ) {
            throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
        }
        this.statutPreselection = nouveauStatut;
    }

    public String getIdPreselection() { return idPreselection; }
    public LocalDate getDatePreselection() { return datePreselection; }
    public StatutCandidature getStatutPreselection() { return statutPreselection; }
    public String getIdAgent() { return idAgent; }
    public String getIdCandidature() { return idCandidature; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Preselection that = (Preselection) o;
        return Objects.equals(idPreselection, that.idPreselection);
    }
    @Override public int hashCode() { return Objects.hash(idPreselection); }
    @Override public String toString() {
        return "Preselection{idPreselection='" + idPreselection +
                "', statut='" + statutPreselection + "'}";
    }

	public StatutCandidature getStatut() {
		// TODO Auto-generated method stub
		return statutPreselection;
	}
}
