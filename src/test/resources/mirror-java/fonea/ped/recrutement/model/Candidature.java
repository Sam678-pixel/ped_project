package fonea.ped.recrutement.model;

import fonea.ped.recrutement.model.StatutCandidature;

import java.time.LocalDate;
import java.util.Objects;

public class Candidature {

    private String idCandidature;
    private LocalDate dateDepot;
    private StatutCandidature statutCandidature;
    private String idDiplome;
    private Document document;
    private String idPublication;

    public Candidature(String idCandidature,
                       LocalDate dateDepot,
                       StatutCandidature statutCandidature,
                       String idDiplome,
                       Document document,
                       String idPublication) {
        this.idCandidature = idCandidature;
        this.dateDepot = dateDepot;
        this.statutCandidature = statutCandidature != null ? statutCandidature : StatutCandidature.EN_COURS;
        this.idDiplome = idDiplome;
        this.document = document;
        this.idPublication = idPublication;
    }

    // ----- Méthodes métier -----

    /**
     * Dépose la candidature : vérifie la recevabilité et fixe la date de dépôt.
     */
    public void deposer() {
        if (!estRecevable()) {
            throw new IllegalStateException("La candidature n'est pas recevable : documents incomplets.");
        }
        this.dateDepot = LocalDate.now();
        this.statutCandidature = StatutCandidature.EN_COURS;
    }

    /**
     * Change le statut de la candidature.
     */
    public void changerStatut(StatutCandidature nouveauStatut) {
        if (nouveauStatut == null) {
            throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
        }
        this.statutCandidature = nouveauStatut;
    }

    /**
     * Présélectionne la candidature et crée une Preselection.
     */
    public Preselection preselectionner(String idPreselection) {
        if (this.statutCandidature != StatutCandidature.EN_COURS
                && this.statutCandidature != StatutCandidature.EN_COURS) {
            throw new IllegalStateException(
                    "La candidature doit être DEPOSEE ou EN_COURS pour être présélectionnée.");
        }
        this.statutCandidature = StatutCandidature.PRESELECTIONNEE;

        return new Preselection(
                idPreselection,
                LocalDate.now(),
                StatutCandidature.PRESELECTIONNEE,
                this.idCandidature
        );
    }

    /**
     * Rejette la candidature.
     */
    public void rejeter() {
        this.statutCandidature = StatutCandidature.REJETEE;
    }

    /**
     * Retire la candidature.
     */
    public void retirer() {
        if (this.statutCandidature == StatutCandidature.SELECTIONNEE) {
            throw new IllegalStateException("Impossible de retirer une candidature déjà sélectionnée.");
        }
        this.statutCandidature = StatutCandidature.RETIREE;
    }

    /**
     * Vérifie si la candidature est recevable (documents complets).
     */
    public boolean estRecevable() {
        return document != null && document.estComplet();
    }

    // ----- Getters -----

    public String getIdCandidature() {
        return idCandidature;
    }

    public LocalDate getDateDepot() {
        return dateDepot;
    }

    public StatutCandidature getStatutCandidature() {
        return statutCandidature;
    }

    public String getIdDiplome() {
        return idDiplome;
    }

    public Document getDocument() {
        return document;
    }

    public String getIdPublication() {
        return idPublication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidature that = (Candidature) o;
        return Objects.equals(idCandidature, that.idCandidature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCandidature);
    }

    @Override
    public String toString() {
        return "Candidature{idCandidature=" + idCandidature +
                ", dateDepot=" + dateDepot +
                ", statutCandidature=" + statutCandidature +
                ", idDiplome=" + idDiplome +
                ", idPublication=" + idPublication + "}";
    }
}
