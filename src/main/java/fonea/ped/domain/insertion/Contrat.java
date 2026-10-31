package fonea.ped.domain.insertion;

import java.time.LocalDate;
import java.util.Objects;

public class Contrat {

    private String idContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double salaire;
    private String idTypeContrat;
    private String idInsertion;
    private StatutContrat statutContrat;

    public Contrat(String idContrat, LocalDate dateDebut, LocalDate dateFin,
                   Double salaire, String idTypeContrat, String idInsertion) {
        this.idContrat = idContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.salaire = salaire;
        this.idTypeContrat = idTypeContrat;
        this.idInsertion = idInsertion;
    }
    
    public Contrat(String idContrat, LocalDate dateDebut, LocalDate dateFin,
            Double salaire, String idTypeContrat, String idInsertion,StatutContrat statutContrat) {
 this.idContrat = idContrat;
 this.dateDebut = dateDebut;
 this.dateFin = dateFin;
 this.salaire = salaire;
 this.idTypeContrat = idTypeContrat;
 this.idInsertion = idInsertion;
 this.statutContrat= statutContrat;
}

    public String getIdContrat() { return idContrat; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public Double getSalaire() { return salaire; }
    public String getIdTypeContrat() { return idTypeContrat; }
    public String getIdInsertion() { return idInsertion; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrat contrat = (Contrat) o;
        return Objects.equals(idContrat, contrat.idContrat);
    }
    @Override public int hashCode() { return Objects.hash(idContrat); }
    @Override public String toString() {
        return "Contrat{idContrat='" + idContrat + "', salaire=" + salaire + "}";
    }

	public StatutContrat getStatut() {
		return statutContrat;
	}
}
