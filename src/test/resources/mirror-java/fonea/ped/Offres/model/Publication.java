package fonea.ped.Offres.model;

import java.time.LocalDate;
import java.util.Objects;

public class Publication {

    private String idPublication;
    private LocalDate datePublication;
    private LocalDate dateExpiration;
    private String idAgent;
    private String idOffre;

    public Publication(String idPublication,
                       LocalDate datePublication,
                       LocalDate dateExpiration,
                       String idAgent,
                       String idOffre) {
        this.idPublication = idPublication;
        this.datePublication = datePublication;
        this.dateExpiration = dateExpiration;
        this.idAgent = idAgent;
        this.idOffre = idOffre;
    }

    /**
     * Fait expirer la publication immédiatement.
     */
    public void expirer() {
        this.dateExpiration = LocalDate.now();
    }

    /**
     * Indique si la publication est encore active.
     */
    public boolean estActive() {
        if (dateExpiration == null) {
            return false;
        }
        return dateExpiration.isAfter(LocalDate.now());
    }

    /**
     * ProStringe la publication jusqu'à une nouvelle date d'expiration.
     */
    public void proStringer(LocalDate nouvelleDateExpiration) {
        if (nouvelleDateExpiration == null) {
            throw new IllegalArgumentException("La nouvelle date d'expiration ne peut pas être null.");
        }
        if (this.dateExpiration != null && nouvelleDateExpiration.isBefore(this.dateExpiration)) {
            throw new IllegalArgumentException(
                    "La nouvelle date doit être postérieure à la date d'expiration actuelle.");
        }
        this.dateExpiration = nouvelleDateExpiration;
    }

    public String getIdPublication() {
        return idPublication;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public String getIdAgent() {
        return idAgent;
    }

    public String getIdOffre() {
        return idOffre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(idPublication, that.idPublication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPublication);
    }

    @Override
    public String toString() {
        return "Publication{idPublication=" + idPublication +
                ", datePublication=" + datePublication +
                ", dateExpiration=" + dateExpiration +
                ", idAgent=" + idAgent +
                ", idOffre=" + idOffre + "}";
    }
}