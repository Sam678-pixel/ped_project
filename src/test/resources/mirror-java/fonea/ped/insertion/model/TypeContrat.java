package fonea.ped.insertion.model;

import java.util.Objects;

public class TypeContrat {

    private String idTypeContrat;
    private String libelle;

    public TypeContrat(String idTypeContrat, String libelle) {
        this.idTypeContrat = idTypeContrat;
        this.libelle = libelle;
    }

    /**
     * Renomme le type de contrat.
     */
    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdTypeContrat() {
        return idTypeContrat;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeContrat that = (TypeContrat) o;
        return Objects.equals(idTypeContrat, that.idTypeContrat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTypeContrat);
    }

    @Override
    public String toString() {
        return "TypeContrat{idTypeContrat=" + idTypeContrat +
                ", libelle='" + libelle + "'}";
    }
}
