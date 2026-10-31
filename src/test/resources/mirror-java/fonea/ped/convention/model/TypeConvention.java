package fonea.ped.convention.model;

import java.util.Objects;

public class TypeConvention {

    private String idTypeConvention;
    private String idProgramme;
    private String idEntreprise;

    public TypeConvention(String idTypeConvention, String idProgramme, String idEntreprise) {
        this.idTypeConvention = idTypeConvention;
        this.idProgramme = idProgramme;
        this.idEntreprise = idEntreprise;
    }

    public String getIdTypeConvention() {
        return idTypeConvention;
    }

    public String getIdProgramme() {
        return idProgramme;
    }

    public String getIdEntreprise() {
        return idEntreprise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeConvention that = (TypeConvention) o;
        return Objects.equals(idTypeConvention, that.idTypeConvention);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTypeConvention);
    }

    @Override
    public String toString() {
        return "TypeConvention{idTypeConvention=" + idTypeConvention +
                ", idProgramme=" + idProgramme +
                ", idEntreprise=" + idEntreprise + "}";
    }
}
