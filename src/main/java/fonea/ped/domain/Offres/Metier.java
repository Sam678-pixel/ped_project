package fonea.ped.domain.Offres;

import java.util.Objects;

public class Metier {

    private String idMetier;
    private String libelle;
    private String idSecteur;

    public Metier(String idMetier, String libelle, String idSecteur) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé du métier est obligatoire.");
        }
        this.idMetier = idMetier;
        this.libelle = libelle;
        this.idSecteur = idSecteur;
    }

    public void renommer(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelle = libelle;
    }

    public void rattacherSecteur(String idSecteur) {
        if (idSecteur == null || idSecteur.isBlank()) {
            throw new IllegalArgumentException("L'identifiant du secteur est obligatoire.");
        }
        this.idSecteur = idSecteur;
    }

    public String getIdMetier() { return idMetier; }
    public String getLibelle() { return libelle; }
    public String getIdSecteur() { return idSecteur; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metier metier = (Metier) o;
        return Objects.equals(idMetier, metier.idMetier);
    }
    @Override public int hashCode() { return Objects.hash(idMetier); }
    @Override public String toString() {
        return "Metier{idMetier='" + idMetier +
                "', libelle='" + libelle + "', idSecteur='" + idSecteur + "'}";
    }
}