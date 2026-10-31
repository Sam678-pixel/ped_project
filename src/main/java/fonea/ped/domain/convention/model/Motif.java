package fonea.ped.domain.convention.model;

import java.util.Objects;

public class Motif {

    private String idMotif;
    private String libelle;

    public Motif(String idMotif, String libelle) {
        this.idMotif = idMotif;
        this.libelle = libelle;
    }

    /**
     * Renomme le motif.
     */
    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdMotif() {
        return idMotif;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Motif motif = (Motif) o;
        return Objects.equals(idMotif, motif.idMotif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMotif);
    }

    @Override
    public String toString() {
        return "Motif{idMotif=" + idMotif + ", libelle='" + libelle + "'}";
    }
}