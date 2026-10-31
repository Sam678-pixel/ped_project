package fonea.ped.Offres.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Critere {

    private String idCritere;
    private String idDiplome;
    private Integer ageMin;
    private Integer ageMax;

    public Critere(String idCritere, String idDiplome, Integer ageMin, Integer ageMax) {
        this.idCritere = idCritere;
        this.idDiplome = idDiplome;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
    }

    /**
     * Définit la tranche d'âge autorisée.
     */
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

    /**
     * Vérifie si la date de naissance respecte la tranche d'âge.
     */
    public boolean verifierEligibiliteAge(LocalDate dateNaissance) {
        if (dateNaissance == null) {
            throw new IllegalArgumentException("La date de naissance ne peut pas être null.");
        }
        int age = Period.between(dateNaissance, LocalDate.now()).getYears();
        boolean minOk = (ageMin == null) || (age >= ageMin);
        boolean maxOk = (ageMax == null) || (age <= ageMax);
        return minOk && maxOk;
    }

    /**
     * Vérifie si le diplôme fourni correspond au diplôme requis.
     * Comparaison basée sur l'identifiant.
     */
    public boolean verifierEligibiliteDiplome(String idDiplomeCandidat) {
        if (this.idDiplome == null) {
            return true; // aucun diplôme exigé
        }
        if (idDiplomeCandidat == null) {
            return false;
        }
        return this.idDiplome.equals(idDiplomeCandidat);
    }

    public String getIdCritere() {
        return idCritere;
    }

    public String getIdDiplome() {
        return idDiplome;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Critere critere = (Critere) o;
        return Objects.equals(idCritere, critere.idCritere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCritere);
    }

    @Override
    public String toString() {
        return "Critere{idCritere=" + idCritere +
                ", idDiplome=" + idDiplome +
                ", ageMin=" + ageMin +
                ", ageMax=" + ageMax + "}";
    }
}
