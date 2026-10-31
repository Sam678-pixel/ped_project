package fonea.ped.domain.MiseEnStage;

import java.time.LocalDate;
import java.util.Objects;

public class Decrochage {

    private String idDecrochage;
    private LocalDate dateDecrochage;
    private String idMiseStage;

    public Decrochage(String idDecrochage, LocalDate dateDecrochage, String idMiseStage) {
        this.idDecrochage = idDecrochage;
        this.dateDecrochage = dateDecrochage;
        this.idMiseStage = idMiseStage;
    }

    public String getIdDecrochage() { return idDecrochage; }
    public LocalDate getDateDecrochage() { return dateDecrochage; }
    public String getIdMiseStage() { return idMiseStage; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Decrochage that = (Decrochage) o;
        return Objects.equals(idDecrochage, that.idDecrochage);
    }
    @Override public int hashCode() { return Objects.hash(idDecrochage); }
    @Override public String toString() {
        return "Decrochage{idDecrochage='" + idDecrochage +
                "', dateDecrochage=" + dateDecrochage + "}";
    }
}
