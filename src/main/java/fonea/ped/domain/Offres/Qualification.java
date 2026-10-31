package fonea.ped.domain.Offres;

import java.util.Objects;

public class Qualification {

    private String idQualification;
    private String libelle;
    private String idMetier;

    public Qualification(String idQualification, String libelle, String idMetier) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé de la qualification est obligatoire.");
        }
        this.idQualification = idQualification;
        this.libelle = libelle;
        this.idMetier = idMetier;
    }

    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public void rattacherMetier(String idMetier) {
        if (idMetier == null || idMetier.isBlank()) {
            throw new IllegalArgumentException("L'identifiant du métier est obligatoire.");
        }
        this.idMetier = idMetier;
    }

    public String getIdQualification() { return idQualification; }
    public String getLibelle() { return libelle; }
    public String getIdMetier() { return idMetier; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Qualification that = (Qualification) o;
        return Objects.equals(idQualification, that.idQualification);
    }
    @Override public int hashCode() { return Objects.hash(idQualification); }
    @Override public String toString() {
        return "Qualification{idQualification='" + idQualification +
                "', libelle='" + libelle + "', idMetier='" + idMetier + "'}";
    }
}