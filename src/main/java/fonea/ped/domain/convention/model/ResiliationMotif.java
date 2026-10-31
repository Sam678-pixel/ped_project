package fonea.ped.domain.convention.model;

import java.util.Objects;

/**
 * est_justifiee_par = (#idResiliation, #idMotifResiliation)
 */
public class ResiliationMotif {

    private String idResiliation;
    private String idMotifResiliation;

    public ResiliationMotif(String idResiliation, String idMotifResiliation) {
        this.idResiliation = idResiliation;
        this.idMotifResiliation = idMotifResiliation;
    }

    public String getIdResiliation() { return idResiliation; }
    public String getIdMotifResiliation() { return idMotifResiliation; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResiliationMotif that = (ResiliationMotif) o;
        return Objects.equals(idResiliation, that.idResiliation)
                && Objects.equals(idMotifResiliation, that.idMotifResiliation);
    }
    @Override public int hashCode() { return Objects.hash(idResiliation, idMotifResiliation); }
    @Override public String toString() {
        return "ResiliationMotif{idResiliation='" + idResiliation +
                "', idMotifResiliation='" + idMotifResiliation + "'}";
    }
}