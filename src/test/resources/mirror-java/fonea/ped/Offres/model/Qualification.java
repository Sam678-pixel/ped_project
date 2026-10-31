package fonea.ped.Offres.model;

import java.util.Objects;

public class Qualification {

    private String idQualification;
    private String idMetier;
    private String idTypeCompetence;
    private String idrec;

    public Qualification(String idQualification, String idMetier, String idTypeCompetence, String idrec) {
        this.idQualification = idQualification;
        this.idMetier = idMetier;
        this.idTypeCompetence = idTypeCompetence;
        this.idrec = idrec;
    }

    /**
     * Rattache la qualification à un métier.
     */
    public void rattacherMetier(String idMetier) {
        if (idMetier == null) {
            throw new IllegalArgumentException("L'identifiant du métier ne peut pas être null.");
        }
        this.idMetier = idMetier;
    }

    public String getIdQualification() {
        return idQualification;
    }

    public String getIdMetier() {
        return idMetier;
    }

    public String getIdTypeCompetence() {
        return idTypeCompetence;
    }

    public String getIdrec() {
        return idrec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Qualification that = (Qualification) o;
        return Objects.equals(idQualification, that.idQualification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQualification);
    }

    @Override
    public String toString() {
        return "Qualification{idQualification=" + idQualification +
                ", idMetier=" + idMetier +
                ", idTypeCompetence=" + idTypeCompetence +
                ", idrec=" + idrec + "}";
    }
}