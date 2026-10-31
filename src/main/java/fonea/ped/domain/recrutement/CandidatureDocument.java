package fonea.ped.domain.recrutement;

import java.util.Objects;

/**
 * piece_à_joindre = (#idCandidature, #idDocument)
 */
public class CandidatureDocument {

    private String idCandidature;
    private String idDocument;

    public CandidatureDocument(String idCandidature, String idDocument) {
        this.idCandidature = idCandidature;
        this.idDocument = idDocument;
    }

    public String getIdCandidature() { return idCandidature; }
    public String getIdDocument() { return idDocument; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidatureDocument that = (CandidatureDocument) o;
        return Objects.equals(idCandidature, that.idCandidature)
                && Objects.equals(idDocument, that.idDocument);
    }
    @Override public int hashCode() { return Objects.hash(idCandidature, idDocument); }
    @Override public String toString() {
        return "CandidatureDocument{idCandidature='" + idCandidature +
                "', idDocument='" + idDocument + "'}";
    }
}
