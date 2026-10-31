package fonea.ped.domain.Offres;

import java.util.Objects;

public class Competence {

    private String idCompetence;
    private String libelle;

    public Competence(String idCompetence, String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé de la compétence est obligatoire.");
        }
        this.idCompetence = idCompetence;
        this.libelle = libelle;
    }

    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdCompetence() { return idCompetence; }
    public String getLibelle() { return libelle; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competence that = (Competence) o;
        return Objects.equals(idCompetence, that.idCompetence);
    }
    @Override public int hashCode() { return Objects.hash(idCompetence); }
    @Override public String toString() {
        return "Competence{idCompetence='" + idCompetence + "', libelle='" + libelle + "'}";
    }
}