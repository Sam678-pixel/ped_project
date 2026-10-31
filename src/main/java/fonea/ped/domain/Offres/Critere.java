package fonea.ped.domain.Offres;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Critere {

    private String idCritere;
    private Integer ageMin;
    private Integer ageMax;
    private String niveauDiplome;

    public Critere(String idCritere, Integer ageMin, Integer ageMax, String niveauDiplome) {
        this.idCritere = idCritere;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.niveauDiplome = niveauDiplome;
    }

    public void definirTrancheAge(int ageMin, int ageMax) {
        if (ageMin < 0 || ageMax < 0) {
            throw new IllegalArgumentException("Les âges ne peuvent pas être négatifs.");
        }
        if (ageMin > ageMax) {
            throw new IllegalArgumentException("L'âge minimum ne peut pas être supérieur à l'âge maximum.");
        }
        this.ageMin = ageMin;
        this.ageMax = ageMax;
    }

    public String getIdCritere() { return idCritere; }
    public Integer getAgeMin() { return ageMin; }
    public Integer getAgeMax() { return ageMax; }
    public String getNiveauDiplome() { return niveauDiplome; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Critere critere = (Critere) o;
        return Objects.equals(idCritere, critere.idCritere);
    }
    @Override public int hashCode() { return Objects.hash(idCritere); }
    @Override public String toString() {
        return "Critere{idCritere='" + idCritere +
                "', ageMin=" + ageMin + ", ageMax=" + ageMax +
                ", niveauDiplome='" + niveauDiplome + "'}";
    }
}
