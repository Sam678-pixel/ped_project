package fonea.ped.domain.Offres;

import java.util.Objects;

/**
 * developpe = (#idQualification, #idCompetence)
 */
public class QualificationCompetence {

    private String idQualification;
    private String idCompetence;

    public QualificationCompetence(String idQualification, String idCompetence) {
        this.idQualification = idQualification;
        this.idCompetence = idCompetence;
    }

    public String getIdQualification() { return idQualification; }
    public String getIdCompetence() { return idCompetence; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualificationCompetence that = (QualificationCompetence) o;
        return Objects.equals(idQualification, that.idQualification)
                && Objects.equals(idCompetence, that.idCompetence);
    }
    @Override public int hashCode() { return Objects.hash(idQualification, idCompetence); }
    @Override public String toString() {
        return "QualificationCompetence{idQualification='" + idQualification +
                "', idCompetence='" + idCompetence + "'}";
    }
}
