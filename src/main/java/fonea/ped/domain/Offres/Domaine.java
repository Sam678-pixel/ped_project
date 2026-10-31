package fonea.ped.domain.Offres;

import java.util.Objects;

public class Domaine {

    private String idDomaine;
    private String libelle;

    public Domaine(String idDomaine, String libelle) {
        this.idDomaine = idDomaine;
        this.libelle = libelle;
    }

    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdDomaine() {
        return idDomaine;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Domaine domaine = (Domaine) o;
        return Objects.equals(idDomaine, domaine.idDomaine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDomaine);
    }

    @Override
    public String toString() {
        return "Domaine{idDomaine=" + idDomaine + ", libelle='" + libelle + "'}";
    }
}