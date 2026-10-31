package fonea.ped.recrutement.model;

import fonea.ped.recrutement.model.StatutCandidature;
import java.time.LocalDate;
import java.util.Objects;

public class Preselection {

    private String idPreselection;
    private LocalDate datePreselection;
    private StatutCandidature statut;
    private String idCandidature;

    public Preselection(String idPreselection,
                        LocalDate datePreselection,
                        StatutCandidature statut,
                        String idCandidature) {
        this.idPreselection = idPreselection;
        this.datePreselection = datePreselection;
        this.statut = statut != null ? statut : StatutCandidature.PRESELECTIONNEE;
        this.idCandidature = idCandidature;
    }

    // ----- Méthodes métier -----

    /**
     * Évalue la présélection selon un critère.
     * La logique réelle d'évaluation appartient au use case.
     * Ici on expose un point d'entrée métier.
     */
    public boolean evaluer(boolean resultatCritere) {
        return resultatCritere;
    }

    /**
     * Change le statut de la présélection.
     */
    public void changerStatut(StatutCandidature nouveauStatut) {
        if (nouveauStatut == null) {
            throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
        }
        this.statut = nouveauStatut;
    }

    /**
     * Sélectionne le candidat présélectionné et crée une Selection.
     */
    public Selection selectionner(String idSelection) {
        if (this.statut != StatutCandidature.PRESELECTIONNEE) {
            throw new IllegalStateException(
                    "La présélection doit être au statut PRESELECTIONNEE pour être sélectionnée.");
        }
        this.statut = StatutCandidature.SELECTIONNEE;

        return new Selection(
                idSelection,
                LocalDate.now(),
                fonea.ped.recrutement.model.StatutCandidature.SELECTIONNEE,
                this.idPreselection
        );
    }
    

    // ----- Getters -----

    public String getIdPreselection() {
        return idPreselection;
    }

    public LocalDate getDatePreselection() {
        return datePreselection;
    }

    public StatutCandidature getStatut() {
        return statut;
    }

    public String getIdCandidature() {
        return idCandidature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Preselection that = (Preselection) o;
        return Objects.equals(idPreselection, that.idPreselection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPreselection);
    }

    @Override
    public String toString() {
        return "Preselection{idPreselection=" + idPreselection +
                ", datePreselection=" + datePreselection +
                ", statut=" + statut +
                ", idCandidature=" + idCandidature + "}";
    }
}
