package fonea.ped.domain.Offres;

import java.util.Objects;

/**
 * exige = (#idCritere, #idCompetence)
 */
public class CritereCompetence {
private String idCritere;
private String idCompetence;

public CritereCompetence(String idCritere, String idCompetence) {
    this.idCritere = idCritere;
    this.idCompetence = idCompetence;
}

public String getIdCritere() { return idCritere; }
public String getIdCompetence() { return idCompetence; }

@Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CritereCompetence that = (CritereCompetence) o;
    return Objects.equals(idCritere, that.idCritere)
            && Objects.equals(idCompetence, that.idCompetence);
}
@Override public int hashCode() { return Objects.hash(idCritere, idCompetence); }
@Override public String toString() {
    return "CritereCompetence{idCritere='" + idCritere +
            "', idCompetence='" + idCompetence + "'}";
}
}
