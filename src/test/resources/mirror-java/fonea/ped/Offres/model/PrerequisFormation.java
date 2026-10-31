package fonea.ped.Offres.model;

import java.util.Objects;

public class PrerequisFormation {

    private String idPrerequis;
    private String libelleFormation;
    private TechniqueLinguistique techniqueLinguistique;
    private String libelle;

    public PrerequisFormation(String idPrerequis,
                              String libelleFormation,
                              TechniqueLinguistique techniqueLinguistique,
                              String libelle) {
        this.idPrerequis = idPrerequis;
        this.libelleFormation = libelleFormation;
        this.techniqueLinguistique = techniqueLinguistique;
        this.libelle = libelle;
    }

    public void renommer(String libelleFormation) {
        if (libelleFormation == null || libelleFormation.isBlank()) {
            throw new IllegalArgumentException("Le libellé de formation ne peut pas être vide.");
        }
        this.libelleFormation = libelleFormation;
    }

    public String getIdPrerequis() {
        return idPrerequis;
    }

    public String getLibelleFormation() {
        return libelleFormation;
    }

    public TechniqueLinguistique getTechniqueLinguistique() {
        return techniqueLinguistique;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrerequisFormation that = (PrerequisFormation) o;
        return Objects.equals(idPrerequis, that.idPrerequis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrerequis);
    }

    @Override
    public String toString() {
        return "PrerequisFormation{idPrerequis=" + idPrerequis +
                ", libelleFormation='" + libelleFormation + '\'' +
                ", techniqueLinguistique=" + techniqueLinguistique +
                ", libelle='" + libelle + '\'' + "}";
    }
}
