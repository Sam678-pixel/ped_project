package fonea.ped.domain.recrutement;

import java.time.LocalDate;
import java.util.Objects;

public class Selection {

    private String idSelection;
    private LocalDate dateSelection;
    private StatutCandidature statutSelection;
    private String idPreselection;

    public Selection(String idSelection, LocalDate dateSelection,
                     StatutCandidature statutSelection, String idPreselection) {
        this.idSelection = idSelection;
        this.dateSelection = dateSelection;
        this.statutSelection = statutSelection != null ? statutSelection : StatutCandidature.EN_COURS;
        this.idPreselection = idPreselection;
    }

    public void confirmer() {
        if (statutSelection == StatutCandidature.REJETEE) {
            throw new IllegalStateException("Impossible de confirmer une sélection annulée.");
        }
        this.statutSelection = StatutCandidature.SELECTIONNEE;
    }

    public void changerStatut(StatutCandidature nouveauStatut) {
        if (nouveauStatut == null ) {
            throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
        }
        this.statutSelection = nouveauStatut;
    }

    public boolean estValidee() { return this.statutSelection==StatutCandidature.SELECTIONNEE; }

    public String getIdSelection() { return idSelection; }
    public LocalDate getDateSelection() { return dateSelection; }
    public StatutCandidature getStatutSelection() { return statutSelection; }
    public String getIdPreselection() { return idPreselection; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Selection that = (Selection) o;
        return Objects.equals(idSelection, that.idSelection);
    }
    @Override public int hashCode() { return Objects.hash(idSelection); }
    @Override public String toString() {
        return "Selection{idSelection='" + idSelection +
                "', statut='" + statutSelection + "'}";
    }
}
