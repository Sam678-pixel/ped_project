package fonea.ped.MiseEnStage.model;

import java.time.LocalDate;
import java.util.Objects;

public class Renouvellement {

    private String idRenouvellement;
    private LocalDate dateDebutStage;
    private LocalDate dateFinStage;
    private String idMiseStage;

    public Renouvellement(String idRenouvellement,
                          LocalDate dateDebutStage,
                          LocalDate dateFinStage,
                          String idMiseStage) {
        this.idRenouvellement = idRenouvellement;
        this.dateDebutStage = dateDebutStage;
        this.dateFinStage = dateFinStage;
        this.idMiseStage = idMiseStage;
    }

    /**
     * ProStringe la date de fin de stage.
     */
    public void proStringer(LocalDate nouvelleDateFin) {
        if (nouvelleDateFin == null) {
            throw new IllegalArgumentException("La nouvelle date de fin ne peut pas être null.");
        }
        if (this.dateFinStage != null && nouvelleDateFin.isBefore(this.dateFinStage)) {
            throw new IllegalArgumentException(
                    "La nouvelle date de fin doit être postérieure à la date de fin actuelle.");
        }
        this.dateFinStage = nouvelleDateFin;
    }

    public String getIdRenouvellement() {
        return idRenouvellement;
    }

    public LocalDate getDateDebutStage() {
        return dateDebutStage;
    }

    public LocalDate getDateFinStage() {
        return dateFinStage;
    }

    public String getIdMiseStage() {
        return idMiseStage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Renouvellement that = (Renouvellement) o;
        return Objects.equals(idRenouvellement, that.idRenouvellement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRenouvellement);
    }

    @Override
    public String toString() {
        return "Renouvellement{idRenouvellement=" + idRenouvellement +
                ", dateDebutStage=" + dateDebutStage +
                ", dateFinStage=" + dateFinStage +
                ", idMiseStage=" + idMiseStage + "}";
    }
}

