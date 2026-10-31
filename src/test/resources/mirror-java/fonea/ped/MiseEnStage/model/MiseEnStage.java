package fonea.ped.MiseEnStage.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import fonea.ped.convention.model.StatutConvention;

public class MiseEnStage {

    private String idMiseStage;
    private LocalDate dateDebut;
    private LocalDate dateFinStage;
    private StatutStage statutStage;
    private String idSelection;

    public MiseEnStage(String idMiseStage,
                       LocalDate dateDebut,
                       LocalDate dateFinStage,
                       StatutStage statutStage,
                       String idSelection) {
        this.idMiseStage = idMiseStage;
        this.dateDebut = dateDebut;
        this.dateFinStage = dateFinStage;
        this.statutStage = statutStage != null ? statutStage : StatutStage.EN_ATTENTE;
        this.idSelection = idSelection;
    }

    // ----- Méthodes métier -----

    /**
     * Démarre le stage.
     */
    public void demarrer() {
        if (this.statutStage == StatutStage.TERMINE
                || this.statutStage == StatutStage.ANNULE
                || this.statutStage == StatutStage.DECROCHE) {
            throw new IllegalStateException(
                    "Impossible de démarrer un stage au statut " + this.statutStage + ".");
        }
        this.dateDebut = LocalDate.now();
        this.statutStage = StatutStage.EN_COURS;
    }

    /**
     * Termine le stage.
     */
    public void terminer() {
        if (this.statutStage != StatutStage.EN_COURS) {
            throw new IllegalStateException("Seul un stage en cours peut être terminé.");
        }
        this.dateFinStage = LocalDate.now();
        this.statutStage = StatutStage.TERMINE;
    }

    /**
     * Change le statut du stage.
     */
    public void changerStatut(StatutStage nouveauStatut) {
        if (nouveauStatut == null) {
            throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
        }
        this.statutStage = nouveauStatut;
    }

    /**
     * Indique si le stage est en cours.
     */
    public boolean estEnCours() {
        return this.statutStage == StatutStage.EN_COURS;
    }

    /**
     * Calcule le nombre de jours restants avant la fin du stage.
     * Retourne 0 si le stage est terminé ou si la date de fin est passée.
     */
    public int calculerDureeRestante() {
        if (this.dateFinStage == null) {
            throw new IllegalStateException("La date de fin de stage n'est pas définie.");
        }
        LocalDate aujourdHui = LocalDate.now();
        if (!aujourdHui.isBefore(this.dateFinStage)) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(aujourdHui, this.dateFinStage);
    }

    /**
     * Renouvelle le stage et crée un Renouvellement.
     */
    public Renouvellement renouveler(String idRenouvellement,
                                     LocalDate nouvelleDateDebut,
                                     LocalDate nouvelleDateFin) {
        if (nouvelleDateDebut == null || nouvelleDateFin == null) {
            throw new IllegalArgumentException("Les dates de début et de fin ne peuvent pas être null.");
        }
        if (nouvelleDateFin.isBefore(nouvelleDateDebut)) {
            throw new IllegalArgumentException(
                    "La date de fin doit être postérieure à la date de début.");
        }
        if (this.statutStage == StatutStage.TERMINE
                || this.statutStage == StatutStage.ANNULE
                || this.statutStage == StatutStage.DECROCHE) {
            throw new IllegalStateException(
                    "Impossible de renouveler un stage au statut " + this.statutStage + ".");
        }

        this.statutStage = StatutStage.RENOUVELE;

        return new Renouvellement(
                idRenouvellement,
                nouvelleDateDebut,
                nouvelleDateFin,
                this.idMiseStage
        );
    }

    /**
     * Déclare un décrochage et crée un Decrochage.
     */
    public Decrochage declarerDecrochage(String idDecrochage,
                                         String motif,
                                         String idEntreprise) {
        if (motif == null || motif.isBlank()) {
            throw new IllegalArgumentException("Le motif de décrochage ne peut pas être vide.");
        }
        if (idEntreprise == null) {
            throw new IllegalArgumentException("L'identifiant de l'entreprise ne peut pas être null.");
        }
        if (this.statutStage == StatutStage.TERMINE) {
            throw new IllegalStateException("Impossible de déclarer un décrochage sur un stage terminé.");
        }

        this.statutStage = StatutStage.DECROCHE;

        return new Decrochage(
                idDecrochage,
                LocalDate.now(),
                this.idMiseStage,
                idEntreprise,
                null
        );
    }

    // ----- Getters -----

    public String getIdMiseStage() {
        return idMiseStage;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFinStage() {
        return dateFinStage;
    }

    public StatutStage getStatutStage() {
        return statutStage;
    }

    public String getIdSelection() {
        return idSelection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiseEnStage that = (MiseEnStage) o;
        return Objects.equals(idMiseStage, that.idMiseStage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMiseStage);
    }

    @Override
    public String toString() {
        return "MiseEnStage{idMiseStage=" + idMiseStage +
                ", dateDebut=" + dateDebut +
                ", dateFinStage=" + dateFinStage +
                ", statutStage=" + statutStage +
                ", idSelection=" + idSelection + "}";
    }
}
