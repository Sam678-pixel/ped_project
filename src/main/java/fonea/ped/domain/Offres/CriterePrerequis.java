package fonea.ped.domain.Offres;

import java.util.Objects;

/**
 * requiert_prerequis = (#idCritere, #idPrerequis)
 */
public class CriterePrerequis {

    private String idCritere;
    private String idPrerequis;

    public CriterePrerequis(String idCritere, String idPrerequis) {
        this.idCritere = idCritere;
        this.idPrerequis = idPrerequis;
    }

    public String getIdCritere() { return idCritere; }
    public String getIdPrerequis() { return idPrerequis; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CriterePrerequis that = (CriterePrerequis) o;
        return Objects.equals(idCritere, that.idCritere)
                && Objects.equals(idPrerequis, that.idPrerequis);
    }
    @Override public int hashCode() { return Objects.hash(idCritere, idPrerequis); }
    @Override public String toString() {
        return "CriterePrerequis{idCritere='" + idCritere +
                "', idPrerequis='" + idPrerequis + "'}";
    }
}
