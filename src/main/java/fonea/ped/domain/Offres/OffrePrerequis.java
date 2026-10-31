package fonea.ped.domain.Offres;

import java.util.Objects;

public class OffrePrerequis {
	
	private String idOffrePrerequis;
    private String idOffre;
    private String idPrerequis;

    public OffrePrerequis(String idOffrePrerequis, String idOffre, String idPrerequis) {
        if (idOffre == null) {
            throw new IllegalArgumentException("L'id de l'offre est obligatoire.");
        }
        if (idPrerequis == null) {
            throw new IllegalArgumentException("L'id du prérequis est obligatoire.");
        }
        this.idOffrePrerequis = idOffrePrerequis;
        this.idOffre = idOffre;
        this.idPrerequis = idPrerequis;
    }
    
    public OffrePrerequis( String idOffre, String idPrerequis) {
        if (idOffre == null) {
            throw new IllegalArgumentException("L'id de l'offre est obligatoire.");
        }
        if (idPrerequis == null) {
            throw new IllegalArgumentException("L'id du prérequis est obligatoire.");
        }
        this.idOffre = idOffre;
        this.idPrerequis = idPrerequis;
    }

    public String getIdOffrePrerequis() { return idOffrePrerequis; }
    public String getIdOffre() { return idOffre; }
    public String getIdPrerequis() { return idPrerequis; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffrePrerequis that = (OffrePrerequis) o;
        return Objects.equals(idOffrePrerequis, that.idOffrePrerequis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOffrePrerequis);
    }

    @Override
    public String toString() {
        return "OffrePrerequis{idOffrePrerequis=" + idOffrePrerequis +
                ", idOffre=" + idOffre +
                ", idPrerequis=" + idPrerequis + "}";
    }
}
