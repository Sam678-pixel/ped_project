package fonea.ped.domain.MiseEnStage;

import java.util.Objects;

/**
 * est_justifie_par = (#idDecrochage, #idMotifDecrochage)
 */
public class DecrochageMotif {

    private String idDecrochage;
    private String idMotifDecrochage;

    public DecrochageMotif(String idDecrochage, String idMotifDecrochage) {
        this.idDecrochage = idDecrochage;
        this.idMotifDecrochage = idMotifDecrochage;
    }

    public String getIdDecrochage() { return idDecrochage; }
    public String getIdMotifDecrochage() { return idMotifDecrochage; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecrochageMotif that = (DecrochageMotif) o;
        return Objects.equals(idDecrochage, that.idDecrochage)
                && Objects.equals(idMotifDecrochage, that.idMotifDecrochage);
    }
    @Override public int hashCode() { return Objects.hash(idDecrochage, idMotifDecrochage); }
    @Override public String toString() {
        return "DecrochageMotif{idDecrochage='" + idDecrochage +
                "', idMotifDecrochage='" + idMotifDecrochage + "'}";
    }
}
