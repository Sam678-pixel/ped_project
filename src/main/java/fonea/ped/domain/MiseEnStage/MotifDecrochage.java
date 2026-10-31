package fonea.ped.domain.MiseEnStage;

import java.util.Objects;

public class MotifDecrochage {

    private String idMotifDecrochage;
    private String libelle;
    private String typeMotif;

    public MotifDecrochage(String idMotifDecrochage, String libelle, String typeMotif) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé du motif est obligatoire.");
        }
        this.idMotifDecrochage = idMotifDecrochage;
        this.libelle = libelle;
        this.typeMotif = typeMotif;
    }

    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdMotifDecrochage() { return idMotifDecrochage; }
    public String getLibelle() { return libelle; }
    public String getTypeMotif() { return typeMotif; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotifDecrochage that = (MotifDecrochage) o;
        return Objects.equals(idMotifDecrochage, that.idMotifDecrochage);
    }
    @Override public int hashCode() { return Objects.hash(idMotifDecrochage); }
    @Override public String toString() {
        return "MotifDecrochage{idMotifDecrochage='" + idMotifDecrochage +
                "', libelle='" + libelle + "', typeMotif='" + typeMotif + "'}";
    }
}
