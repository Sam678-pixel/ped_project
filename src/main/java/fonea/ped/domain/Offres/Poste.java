package fonea.ped.domain.Offres;

import java.util.Objects;

import java.util.Objects;

public class Poste {

    private String idPoste;
    private String libelle;
    private String idMetier;

    public Poste(String idPoste, String libelle, String idMetier) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé du poste est obligatoire.");
        }
        this.idPoste = idPoste;
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

    public String getIdPoste() { return idPoste; }
    public String getLibelle() { return libelle; }
    public String getIdMetier() { return idMetier; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poste poste = (Poste) o;
        return Objects.equals(idPoste, poste.idPoste);
    }
    @Override public int hashCode() { return Objects.hash(idPoste); }
    @Override public String toString() {
        return "Poste{idPoste='" + idPoste +
                "', libelle='" + libelle + "', idMetier='" + idMetier + "'}";
    }
}