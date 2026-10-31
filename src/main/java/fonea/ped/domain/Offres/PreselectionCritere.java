package fonea.ped.domain.Offres;

import java.util.Objects;

/**
 * evaluer = (#idPreselection, #idCritere, estSatisfait VARCHAR(50))
 */
public class PreselectionCritere {

    private String idPreselection;
    private String idCritere;
    private String estSatisfait;

    public PreselectionCritere(String idPreselection, String idCritere, String estSatisfait) {
        this.idPreselection = idPreselection;
        this.idCritere = idCritere;
        this.estSatisfait = estSatisfait;
    }

    public void changerSatisfaction(String estSatisfait) {
        this.estSatisfait = estSatisfait;
    }

    public String getIdPreselection() { return idPreselection; }
    public String getIdCritere() { return idCritere; }
    public String getEstSatisfait() { return estSatisfait; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreselectionCritere that = (PreselectionCritere) o;
        return Objects.equals(idPreselection, that.idPreselection)
                && Objects.equals(idCritere, that.idCritere);
    }
    @Override public int hashCode() { return Objects.hash(idPreselection, idCritere); }
    @Override public String toString() {
        return "PreselectionCritere{idPreselection='" + idPreselection +
                "', idCritere='" + idCritere +
                "', estSatisfait='" + estSatisfait + "'}";
    }
}
