package fonea.ped.recrutement.model;

import java.time.LocalDate;
import java.util.Objects;
import fonea.ped.recrutement.model.StatutCandidature;

public class Selection {

    private String idSelection;
    private LocalDate dateSelection;
    private StatutSelection statutSelection;
    private String idPreselection;

    public Selection(String idSelection,
                     LocalDate dateSelection,
                     StatutSelection selectionnee,
                     String idPreselection) {
        this.idSelection = idSelection;
        this.dateSelection = dateSelection;
        this.statutSelection = selectionnee != null ? selectionnee : StatutSelection.EN_ATTENTE;
        this.idPreselection = idPreselection;
    }
    
    public Selection(String idSelection,
            LocalDate dateSelection,
            StatutCandidature selectionnee,
            String idPreselection) {
this.idSelection = idSelection;
this.dateSelection = dateSelection;
this.statutSelection = selectionnee != null ? statutSelection : StatutSelection.EN_ATTENTE;
this.idPreselection = idPreselection;
}

    // ----- Méthodes métier -----

    /**
     * Confirme la sélection.
     */
    public void confirmer() {
        if (this.statutSelection == StatutSelection.ANNULEE) {
            throw new IllegalStateException("Impossible de confirmer une sélection annulée.");
        }
        this.statutSelection = StatutSelection.CONFIRMEE;
    }

    /**
     * Change le statut de la sélection.
     */
    public void changerStatut(StatutSelection nouveauStatut) {
        if (nouveauStatut == null) {
            throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
        }
        this.statutSelection = nouveauStatut;
    }

    /**
     * Indique si la sélection est validée.
     */
    public boolean estValidee() {
        return this.statutSelection == StatutSelection.CONFIRMEE;
    }

    // ----- Getters -----

    public String getIdSelection() {
        return idSelection;
    }

    public LocalDate getDateSelection() {
        return dateSelection;
    }

    public StatutSelection getStatutSelection() {
        return statutSelection;
    }

    public String getIdPreselection() {
        return idPreselection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Selection selection = (Selection) o;
        return Objects.equals(idSelection, selection.idSelection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSelection);
    }

    @Override
    public String toString() {
        return "Selection{idSelection=" + idSelection +
                ", dateSelection=" + dateSelection +
                ", statutSelection=" + statutSelection +
                ", idPreselection=" + idPreselection + "}";
    }
}
