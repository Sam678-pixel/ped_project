package fonea.ped.domain.diplome;

import java.util.Objects;

/**
 * possede = (#idDiplomé, #idCompetence)
 */
public class DiplomeCompetence {

    private String idDiplome;
    private String idCompetence;

    public DiplomeCompetence(String idDiplome, String idCompetence) {
        this.idDiplome = idDiplome;
        this.idCompetence = idCompetence;
    }

    public String getIdDiplome() { return idDiplome; }
    public String getIdCompetence() { return idCompetence; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiplomeCompetence that = (DiplomeCompetence) o;
        return Objects.equals(idDiplome, that.idDiplome)
                && Objects.equals(idCompetence, that.idCompetence);
    }
    @Override public int hashCode() { return Objects.hash(idDiplome, idCompetence); }
    @Override public String toString() {
        return "DiplomeCompetence{idDiplome='" + idDiplome +
                "', idCompetence='" + idCompetence + "'}";
    }
}
