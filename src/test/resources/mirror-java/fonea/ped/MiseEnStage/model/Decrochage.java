package fonea.ped.MiseEnStage.model;

import java.time.LocalDate;
import java.util.Objects;

public class Decrochage {

    private String idDecrochage;
    private LocalDate dateDecrochage;
    private String idMiseStage;
    private String idEntreprise;
    private String idMotifDecrochage;

    public Decrochage(String idDecrochage,
                      LocalDate dateDecrochage,
                      String idMiseStage,
                      String idEntreprise,
                      String idMotifDecrochage) {
        this.idDecrochage = idDecrochage;
        this.dateDecrochage = dateDecrochage;
        this.idMiseStage = idMiseStage;
        this.idEntreprise = idEntreprise;
        this.idMotifDecrochage = idMotifDecrochage;
    }

    /**
     * Associe un motif de décrochage.
     */
    public void associerMotif(String idMotifDecrochage) {
        if (idMotifDecrochage == null) {
            throw new IllegalArgumentException("L'identifiant du motif ne peut pas être null.");
        }
        this.idMotifDecrochage = idMotifDecrochage;
    }

    public String getIdDecrochage() {
        return idDecrochage;
    }

    public LocalDate getDateDecrochage() {
        return dateDecrochage;
    }

    public String getIdMiseStage() {
        return idMiseStage;
    }

    public String getIdEntreprise() {
        return idEntreprise;
    }

    public String getIdMotifDecrochage() {
        return idMotifDecrochage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Decrochage that = (Decrochage) o;
        return Objects.equals(idDecrochage, that.idDecrochage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDecrochage);
    }

    @Override
    public String toString() {
        return "Decrochage{idDecrochage=" + idDecrochage +
                ", dateDecrochage=" + dateDecrochage +
                ", idMiseStage=" + idMiseStage +
                ", idEntreprise=" + idEntreprise +
                ", idMotifDecrochage=" + idMotifDecrochage + "}";
    }
}
