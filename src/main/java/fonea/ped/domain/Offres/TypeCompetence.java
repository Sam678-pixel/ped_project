package fonea.ped.domain.Offres;

import java.util.Objects;

public class TypeCompetence {

    private String idTypeCompetence;
    private String libelle;

    public TypeCompetence(String idTypeCompetence, String libelle) {
        this.idTypeCompetence = idTypeCompetence;
        this.libelle = libelle;
    }

    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdTypeCompetence() {
        return idTypeCompetence;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeCompetence that = (TypeCompetence) o;
        return Objects.equals(idTypeCompetence, that.idTypeCompetence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTypeCompetence);
    }

    @Override
    public String toString() {
        return "TypeCompetence{idTypeCompetence=" + idTypeCompetence + ", libelle='" + libelle + "'}";
    }
}
