package fonea.ped.domain.Offres;

import java.util.Objects;

public class OffreCritere {
	
	private String idOffreCritere;
    private String idOffre;
    private String idCritere;

    public OffreCritere(String idOffreCritere, String idOffre, String idCritere) {
        if (idOffre == null) {
            throw new IllegalArgumentException("L'id de l'offre est obligatoire.");
        }
        if (idCritere == null) {
            throw new IllegalArgumentException("L'id du critère est obligatoire.");
        }
        this.idOffreCritere = idOffreCritere;
        this.idOffre = idOffre;
        this.idCritere = idCritere;
    }
    
    public OffreCritere( String idOffre, String idCritere) {
        if (idOffre == null) {
            throw new IllegalArgumentException("L'id de l'offre est obligatoire.");
        }
        if (idCritere == null) {
            throw new IllegalArgumentException("L'id du critère est obligatoire.");
        }
        this.idOffre = idOffre;
        this.idCritere = idCritere;
    }

    public String getIdOffreCritere() { return idOffreCritere; }
    public String getIdOffre() { return idOffre; }
    public String getIdCritere() { return idCritere; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffreCritere that = (OffreCritere) o;
        return Objects.equals(idOffreCritere, that.idOffreCritere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOffreCritere);
    }

    @Override
    public String toString() {
        return "OffreCritere{idOffreCritere=" + idOffreCritere +
                ", idOffre=" + idOffre +
                ", idCritere=" + idCritere + "}";
    }
}
