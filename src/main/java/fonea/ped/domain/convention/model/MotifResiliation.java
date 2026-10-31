package fonea.ped.domain.convention.model;

import java.util.Objects;

/**
 * MotifResiliation = (idMotifResiliation VARCHAR(50), libelle VARCHAR(50))
 */
public class MotifResiliation {

    private String idMotifResiliation;
    private String libelle;

    public MotifResiliation(String idMotifResiliation, String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé du motif de résiliation est obligatoire.");
        }
        this.idMotifResiliation = idMotifResiliation;
        this.libelle = libelle;
    }

    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdMotifResiliation() { return idMotifResiliation; }
    public String getLibelle() { return libelle; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotifResiliation that = (MotifResiliation) o;
        return Objects.equals(idMotifResiliation, that.idMotifResiliation);
    }
    @Override public int hashCode() { return Objects.hash(idMotifResiliation); }
    @Override public String toString() {
        return "MotifResiliation{idMotifResiliation='" + idMotifResiliation +
                "', libelle='" + libelle + "'}";
    }
}
