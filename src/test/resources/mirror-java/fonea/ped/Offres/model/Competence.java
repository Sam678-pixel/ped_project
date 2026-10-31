package fonea.ped.Offres.model;

import java.util.Objects;

public class Competence {

    private String idCompetence;
    private String idPoste;
    private String idQualification;
    private String idrec;

    public Competence(String idCompetence, String idPoste, String idQualification, String idrec) {
        this.idCompetence = idCompetence;
        this.idPoste = idPoste;
        this.idQualification = idQualification;
        this.idrec = idrec;
    }

    /**
     * Rattache la compétence à un poste.
     */
    public void rattacherPoste(String idPoste) {
        if (idPoste == null) {
            throw new IllegalArgumentException("L'identifiant du poste ne peut pas être null.");
        }
        this.idPoste = idPoste;
    }

    /**
     * Rattache la compétence à une qualification.
     */
    public void rattacherQualification(String idQualification) {
        if (idQualification == null) {
            throw new IllegalArgumentException("L'identifiant de la qualification ne peut pas être null.");
        }
        this.idQualification = idQualification;
    }

    public String getIdCompetence() {
        return idCompetence;
    }

    public String getIdPoste() {
        return idPoste;
    }

    public String getIdQualification() {
        return idQualification;
    }

    public String getIdrec() {
        return idrec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competence that = (Competence) o;
        return Objects.equals(idCompetence, that.idCompetence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompetence);
    }

    @Override
    public String toString() {
        return "Competence{idCompetence=" + idCompetence +
                ", idPoste=" + idPoste +
                ", idQualification=" + idQualification +
                ", idrec=" + idrec + "}";
    }
}
