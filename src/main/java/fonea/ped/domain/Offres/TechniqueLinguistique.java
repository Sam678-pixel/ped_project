package fonea.ped.domain.Offres;

import java.util.Objects;

/**
 * TechniqueLinguistique = (idTechLing VARCHAR(50), libelle VARCHAR(50))
 */
public class TechniqueLinguistique {

    private String idTechniqueLinguistique;
    private String libelle;

    public TechniqueLinguistique(String idTechniqueLinguistique, String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé technique/linguistique est obligatoire.");
        }
        this.idTechniqueLinguistique = idTechniqueLinguistique;
        this.libelle = libelle;
    }

    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdTechniqueLinguistique() { return idTechniqueLinguistique; }
    public String getLibelle() { return libelle; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechniqueLinguistique that = (TechniqueLinguistique) o;
        return Objects.equals(idTechniqueLinguistique, that.idTechniqueLinguistique);
    }
    @Override public int hashCode() { return Objects.hash(idTechniqueLinguistique); }
    @Override public String toString() {
        return "TechniqueLinguistique{idTechniqueLinguistique='" + idTechniqueLinguistique +
                "', libelle='" + libelle + "'}";
    }
}