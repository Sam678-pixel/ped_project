package fonea.ped.domain.Offres;

import java.util.Objects;

/**
 * necessite = (#idCompetence, #idPoste)
 */
public class CompetencePoste {

    private String idCompetence;
    private String idPoste;

    public CompetencePoste(String idCompetence, String idPoste) {
        this.idCompetence = idCompetence;
        this.idPoste = idPoste;
    }

    public String getIdCompetence() { return idCompetence; }
    public String getIdPoste() { return idPoste; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompetencePoste that = (CompetencePoste) o;
        return Objects.equals(idCompetence, that.idCompetence)
                && Objects.equals(idPoste, that.idPoste);
    }
    @Override public int hashCode() { return Objects.hash(idCompetence, idPoste); }
    @Override public String toString() {
        return "CompetencePoste{idCompetence='" + idCompetence +
                "', idPoste='" + idPoste + "'}";
    }
}