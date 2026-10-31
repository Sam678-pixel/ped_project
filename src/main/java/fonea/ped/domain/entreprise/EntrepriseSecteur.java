package fonea.ped.domain.entreprise;

import java.util.Objects;

/**
 * appartient_à = (#idEntreprise, #idSecteur)
 */
public class EntrepriseSecteur {

    private String idEntreprise;
    private String idSecteur;

    public EntrepriseSecteur(String idEntreprise, String idSecteur) {
        this.idEntreprise = idEntreprise;
        this.idSecteur = idSecteur;
    }

    public String getIdEntreprise() { return idEntreprise; }
    public String getIdSecteur() { return idSecteur; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntrepriseSecteur that = (EntrepriseSecteur) o;
        return Objects.equals(idEntreprise, that.idEntreprise)
                && Objects.equals(idSecteur, that.idSecteur);
    }
    @Override public int hashCode() { return Objects.hash(idEntreprise, idSecteur); }
    @Override public String toString() {
        return "EntrepriseSecteur{idEntreprise='" + idEntreprise +
                "', idSecteur='" + idSecteur + "'}";
    }
}
