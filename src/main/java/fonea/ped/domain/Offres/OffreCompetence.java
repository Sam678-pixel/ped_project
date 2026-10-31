package fonea.ped.domain.Offres;

import java.util.Objects;

public class OffreCompetence {
	private String idOffreCompetence;
    private String idOffre;
    private String idCompetence;

    public OffreCompetence(String idOffreCompetence, String idOffre, String idCompetence) {
        if (idOffre == null) {
            throw new IllegalArgumentException("L'id de l'offre est obligatoire.");
        }
        if (idCompetence == null) {
            throw new IllegalArgumentException("L'id de la compétence est obligatoire.");
        }
        this.idOffreCompetence = idOffreCompetence;
        this.idOffre = idOffre;
        this.idCompetence = idCompetence;
    }
    
    public OffreCompetence( String idOffre, String idCompetence) {
        if (idOffre == null) {
            throw new IllegalArgumentException("L'id de l'offre est obligatoire.");
        }
        if (idCompetence == null) {
            throw new IllegalArgumentException("L'id de la compétence est obligatoire.");
        }
        this.idOffre = idOffre;
        this.idCompetence = idCompetence;
    }

    public String getIdOffreCompetence() { return idOffreCompetence; }
    public String getIdOffre() { return idOffre; }
    public String getIdCompetence() { return idCompetence; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffreCompetence that = (OffreCompetence) o;
        return Objects.equals(idOffreCompetence, that.idOffreCompetence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOffreCompetence);
    }

    @Override
    public String toString() {
        return "OffreCompetence{idOffreCompetence=" + idOffreCompetence +
                ", idOffre=" + idOffre +
                ", idCompetence=" + idCompetence + "}";
    }
}
