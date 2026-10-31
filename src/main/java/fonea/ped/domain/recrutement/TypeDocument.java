package fonea.ped.domain.recrutement;

import java.util.Objects;

/**
 * TypeDocument = (idTypedocument VARCHAR(50), libelle VARCHAR(50))
 */
public class TypeDocument {

    private String idTypeDocument;
    private String libelle;

    public TypeDocument(String idTypeDocument, String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé du type de document est obligatoire.");
        }
        this.idTypeDocument = idTypeDocument;
        this.libelle = libelle;
    }

    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdTypeDocument() { return idTypeDocument; }
    public String getLibelle() { return libelle; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeDocument that = (TypeDocument) o;
        return Objects.equals(idTypeDocument, that.idTypeDocument);
    }
    @Override public int hashCode() { return Objects.hash(idTypeDocument); }
    @Override public String toString() {
        return "TypeDocument{idTypeDocument='" + idTypeDocument +
                "', libelle='" + libelle + "'}";
    }
}
