package fonea.ped.domain.MiseEnStage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MiseEnStage {

    private String idMiseStage;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutStage statutStage;
    private String idSelection;
    private String idEntreprise;

    public MiseEnStage(String idMiseStage, LocalDate dateDebut, LocalDate dateFin,
                       StatutStage statutStage, String idSelection, String idEntreprise) {
        this.idMiseStage = idMiseStage;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statutStage = statutStage != null ? statutStage : StatutStage.EN_ATTENTE;
        this.idSelection = idSelection;
        this.idEntreprise = idEntreprise;
    }

    /**
     * Démarre le stage : seul un stage {@code EN_ATTENTE} peut être démarré.
     *
     * <p>La date de début planifiée lors de la mise en stage est conservée ;
     * elle n'est renseignée à la date du jour que si elle était absente.</p>
     */
    public void demarrer() {
        if (statutStage != StatutStage.EN_ATTENTE) {
            throw new IllegalStateException(
                    "Seul un stage en attente peut être démarré (statut actuel : "
                            + statutStage + ").");
        }
        if (this.dateDebut == null) {
            this.dateDebut = LocalDate.now();
        }
        this.statutStage = StatutStage.EN_COURS;
    }

    /**
     * Termine le stage.
     *
     * <p>Un stage {@code RENOUVELE} est un stage dont la période a été
     * prolongée : il est donc actif et peut être terminé comme un stage
     * {@code EN_COURS}. Sans cette règle, un stage renouvelé ne pouvait plus
     * jamais être clôturé, ni donner lieu à une insertion.</p>
     */
    public void terminer() {
        if (statutStage != StatutStage.EN_COURS && statutStage != StatutStage.RENOUVELE) {
            throw new IllegalStateException(
                    "Seul un stage en cours ou renouvelé peut être terminé (statut actuel : "
                            + statutStage + ").");
        }
        this.dateFin = LocalDate.now();
        this.statutStage = StatutStage.TERMINE;
    }

    public void changerStatut(StatutStage nouveauStatut) {
        if (nouveauStatut == null) {
            throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
        }
        this.statutStage = nouveauStatut;
    }

    public boolean estEnCours() { return statutStage == StatutStage.EN_COURS; }

    public int calculerDureeRestante() {
        if (dateFin == null) throw new IllegalStateException("La date de fin n'est pas définie.");
        LocalDate auj = LocalDate.now();
        if (!auj.isBefore(dateFin)) return 0;
        return (int) ChronoUnit.DAYS.between(auj, dateFin);
    }

    public String getIdMiseStage() { return idMiseStage; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public StatutStage getStatutStage() { return statutStage; }
    public String getIdSelection() { return idSelection; }
    public String getIdEntreprise() { return idEntreprise; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiseEnStage that = (MiseEnStage) o;
        return Objects.equals(idMiseStage, that.idMiseStage);
    }
    @Override public int hashCode() { return Objects.hash(idMiseStage); }
    @Override public String toString() {
        return "MiseEnStage{idMiseStage='" + idMiseStage +
                "', statut='" + statutStage + "'}";
    }
}
