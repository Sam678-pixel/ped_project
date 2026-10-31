package fonea.ped.Offres.model;

import java.util.Objects;

public class Secteur {

    private String idSecteur;
    private String libelle;

    public Secteur(String idSecteur, String libelle) {
        this.idSecteur = idSecteur;
        this.libelle = libelle;
    }

    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public String getIdSecteur() {
        return idSecteur;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Secteur secteur = (Secteur) o;
        return Objects.equals(idSecteur, secteur.idSecteur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSecteur);
    }

    @Override
    public String toString() {
        return "Secteur{idSecteur=" + idSecteur + ", libelle='" + libelle + "'}";
    }
}
