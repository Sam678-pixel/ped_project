package fonea.ped.domain.recrutement;

import java.util.Objects;

public class Document {

    private String idDocument;
    private String cheminFichier;
    private String idDiplome;
    private String idTypeDocument;

    public Document(String idDocument, String cheminFichier,
                    String idDiplome, String idTypeDocument) {
        if (cheminFichier == null || cheminFichier.isBlank()) {
            throw new IllegalArgumentException("Le chemin du fichier est obligatoire.");
        }
        this.idDocument = idDocument;
        this.cheminFichier = cheminFichier;
        this.idDiplome = idDiplome;
        this.idTypeDocument = idTypeDocument;
    }

    public String getIdDocument() { return idDocument; }
    public String getCheminFichier() { return cheminFichier; }
    public String getIdDiplome() { return idDiplome; }
    public String getIdTypeDocument() { return idTypeDocument; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(idDocument, document.idDocument);
    }
    @Override public int hashCode() { return Objects.hash(idDocument); }
    @Override public String toString() {
        return "Document{idDocument='" + idDocument +
                "', cheminFichier='" + cheminFichier + "'}";
    }


}
