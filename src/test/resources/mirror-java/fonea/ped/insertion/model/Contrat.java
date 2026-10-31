package fonea.ped.insertion.model;

import java.time.LocalDate;
import java.util.Objects;
import fonea.ped.insertion.model.StatutContrat;

public class Contrat {

    private String idContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin; // null si CDI
    private Double salaire;
    private StatutContrat statut;
    private String idTypeContrat;
    private String idInsertion;

    public Contrat(String idContrat,
                   LocalDate dateDebut,
                   LocalDate dateFin,
                   Double salaire,
                   StatutContrat statut,
                   String idTypeContrat,
                   String idInsertion) {
        this.idContrat = idContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.salaire = salaire;
        this.statut = statut != null ? statut : StatutContrat.INACTIVE;
        this.idTypeContrat = idTypeContrat;
        this.idInsertion = idInsertion;
    }

    // ----- Méthodes métier (ajoutées par déduction) -----

    /**
     * Active le contrat.
     */
    public void activer() {
        if (this.statut == StatutContrat.RESILIE || this.statut == StatutContrat.TERMINE) {
            throw new IllegalStateException(
                    "Impossible d'activer un contrat au statut " + this.statut + ".");
        }
        this.statut = StatutContrat.ACTIF;
    }

    /**
     * Termine le contrat.
     */
    public void terminer(LocalDate dateFinEffective) {
        if (dateFinEffective == null) {
            throw new IllegalArgumentException("La date de fin effective ne peut pas être null.");
        }
        if (this.dateDebut != null && dateFinEffective.isBefore(this.dateDebut)) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début.");
        }
        this.dateFin = dateFinEffective;
        this.statut = StatutContrat.TERMINE;
    }

    /**
     * Résilie le contrat de manière anticipée.
     */
    public void resilier() {
        if (this.statut == StatutContrat.TERMINE) {
            throw new IllegalStateException("Impossible de résilier un contrat déjà terminé.");
        }
        this.statut = StatutContrat.RESILIE;
    }

    /**
     * Change le statut du contrat.
     */
    public void changerStatut(StatutContrat nouveauStatut) {
        if (nouveauStatut == null) {
            throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
        }
        this.statut = nouveauStatut;
    }

    /**
     * Indique si le contrat est actif.
     */
    public boolean estActif() {
        return this.statut == StatutContrat.ACTIF;
    }

    // ----- Getters -----

    public String getIdContrat() {
        return idContrat;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Double getSalaire() {
        return salaire;
    }

    public StatutContrat getStatut() {
        return statut;
    }

    public String getIdTypeContrat() {
        return idTypeContrat;
    }

    public String getIdInsertion() {
        return idInsertion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrat contrat = (Contrat) o;
        return Objects.equals(idContrat, contrat.idContrat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContrat);
    }

    @Override
    public String toString() {
        return "Contrat{idContrat=" + idContrat +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", salaire=" + salaire +
                ", statut=" + statut +
                ", idTypeContrat=" + idTypeContrat +
                ", idInsertion=" + idInsertion + "}";
    }
}
