package fonea.ped.domain.Offres;

import java.util.Objects;

/**
 * requiert_technique = (#idCritere, #idTechLing)
 */
public class CritereTechniqueLinguistique {

    private String idCritere;
    private String idTechniqueLinguistique;

    public CritereTechniqueLinguistique(String idCritere, String idTechniqueLinguistique) {
        this.idCritere = idCritere;
        this.idTechniqueLinguistique = idTechniqueLinguistique;
    }

    public String getIdCritere() { return idCritere; }
    public String getIdTechniqueLinguistique() { return idTechniqueLinguistique; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CritereTechniqueLinguistique that = (CritereTechniqueLinguistique) o;
        return Objects.equals(idCritere, that.idCritere)
                && Objects.equals(idTechniqueLinguistique, that.idTechniqueLinguistique);
    }
    @Override public int hashCode() { return Objects.hash(idCritere, idTechniqueLinguistique); }
    @Override public String toString() {
        return "CritereTechniqueLinguistique{idCritere='" + idCritere +
                "', idTechLing='" + idTechniqueLinguistique + "'}";
    }
}
