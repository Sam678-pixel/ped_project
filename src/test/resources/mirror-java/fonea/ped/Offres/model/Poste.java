package fonea.ped.Offres.model;

import java.util.Objects;

import java.util.Objects;

public class Poste {

    private String idPoste;
    private String libelle;

    public Poste(String idPoste, String libelle) {
        this.idPoste = idPoste;
        this.libelle = libelle;
    }

    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdPoste() {
        return idPoste;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poste poste = (Poste) o;
        return Objects.equals(idPoste, poste.idPoste);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPoste);
    }

    @Override
    public String toString() {
        return "Poste{idPoste=" + idPoste + ", libelle='" + libelle + "'}";
    }
}