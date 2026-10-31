package fonea.ped.convention.model;

import java.util.Objects;

public class Programme {

    private String idProgramme;
    private String libelle;
    private String typeProgramme;

    public Programme(String idProgramme, String libelle, String typeProgramme) {
        this.idProgramme = idProgramme;
        this.libelle = libelle;
        this.typeProgramme = typeProgramme;
    }

    /**
     * Renomme le programme.
     */
    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdProgramme() {
        return idProgramme;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getTypeProgramme() {
        return typeProgramme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Programme programme = (Programme) o;
        return Objects.equals(idProgramme, programme.idProgramme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProgramme);
    }

    @Override
    public String toString() {
        return "Programme{idProgramme=" + idProgramme +
                ", libelle='" + libelle + '\'' +
                ", typeProgramme='" + typeProgramme + '\'' + "}";
    }
}
