package fonea.ped.MiseEnStage.model;

import java.util.Objects;

public class MotifDecrochage {

    private String idMotifDecrochage;
    private String libelle;

    public MotifDecrochage(String idMotifDecrochage, String libelle) {
        this.idMotifDecrochage = idMotifDecrochage;
        this.libelle = libelle;
    }

    /**
     * Renomme le motif de décrochage.
     */
    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdMotifDecrochage() {
        return idMotifDecrochage;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotifDecrochage that = (MotifDecrochage) o;
        return Objects.equals(idMotifDecrochage, that.idMotifDecrochage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMotifDecrochage);
    }

    @Override
    public String toString() {
        return "MotifDecrochage{idMotifDecrochage=" + idMotifDecrochage +
                ", libelle='" + libelle + "'}";
    }
}
