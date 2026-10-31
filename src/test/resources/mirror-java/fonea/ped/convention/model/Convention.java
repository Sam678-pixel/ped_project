package fonea.ped.convention.model;

import java.time.LocalDate;
import java.util.Objects;

public class Convention {

    private String idConvention;
    private LocalDate dateEffet;
    private Double tauxFonea;
    private Double tauxEntreprise;
    private StatutConvention statutConvention;

    public Convention(String idConvention,
                      LocalDate dateEffet,
                      Double tauxFonea,
                      Double tauxEntreprise,
                      StatutConvention statutConvention) {
        this.idConvention = idConvention;
        this.dateEffet = dateEffet;
        this.tauxFonea = tauxFonea;
        this.tauxEntreprise = tauxEntreprise;
        this.statutConvention = statutConvention != null ? statutConvention : StatutConvention.ACTIVE;
    }

    // ----- Méthodes métier -----

    /**
     * Active la convention.
     */
    public void activer() {
        if (this.statutConvention == StatutConvention.RESILIEE) {
            throw new IllegalStateException("Impossible d'activer une convention résiliée.");
        }
        this.statutConvention = StatutConvention.ACTIVE;
    }

    /**
     * Suspend la convention.
     */
    public void suspendre() {
        if (this.statutConvention != StatutConvention.ACTIVE) {
            throw new IllegalStateException("Seule une convention active peut être suspendue.");
        }
        this.statutConvention = StatutConvention.SUSPENDUE;
    }

    /**
     * Change le statut de la convention.
     */
    public void changerStatut(StatutConvention nouveauStatut) {
        if (nouveauStatut == null) {
            throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
        }
        this.statutConvention = nouveauStatut;
    }

    /**
     * Indique si la convention est active.
     */
    public boolean estActive() {
        return this.statutConvention == StatutConvention.ACTIVE;
    }

    /**
     * Résilie la convention et crée une Resiliation.
     */
    public Resiliation resilier(String idResiliation, String motif, String initiateur) {
        if (motif == null || motif.isBlank()) {
            throw new IllegalArgumentException("Le motif de résiliation ne peut pas être vide.");
        }
        if (initiateur == null || initiateur.isBlank()) {
            throw new IllegalArgumentException("L'initiateur ne peut pas être vide.");
        }
        if (this.statutConvention == StatutConvention.RESILIEE) {
            throw new IllegalStateException("La convention est déjà résiliée.");
        }

        this.statutConvention = StatutConvention.RESILIEE;

        return new Resiliation(
                idResiliation,
                LocalDate.now(),
                initiateur,
                motif,
                null,
                this.idConvention
        );
    }

    // ----- Getters -----

    public String getIdConvention() {
        return idConvention;
    }

    public LocalDate getDateEffet() {
        return dateEffet;
    }

    public Double getTauxFonea() {
        return tauxFonea;
    }

    public Double getTauxEntreprise() {
        return tauxEntreprise;
    }

    public StatutConvention getStatutConvention() {
        return statutConvention;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Convention that = (Convention) o;
        return Objects.equals(idConvention, that.idConvention);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConvention);
    }

    @Override
    public String toString() {
        return "Convention{idConvention=" + idConvention +
                ", dateEffet=" + dateEffet +
                ", tauxFonea=" + tauxFonea +
                ", tauxEntreprise=" + tauxEntreprise +
                ", statutConvention=" + statutConvention + "}";
    }
}
