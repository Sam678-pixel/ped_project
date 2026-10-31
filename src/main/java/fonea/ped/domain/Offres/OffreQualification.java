package fonea.ped.domain.Offres;

import java.util.Objects;

public class OffreQualification {
	
	private String idOffreQualification;
    private String idOffre;
    private String idQualification;

    public OffreQualification(String idOffreQualification, String idOffre, String idQualification) {
        if (idOffre == null) {
            throw new IllegalArgumentException("L'id de l'offre est obligatoire.");
        }
        if (idQualification == null) {
            throw new IllegalArgumentException("L'id de la qualification est obligatoire.");
        }
        this.idOffreQualification = idOffreQualification;
        this.idOffre = idOffre;
        this.idQualification = idQualification;
    }
    
    public OffreQualification( String idOffre, String idQualification) {
        if (idOffre == null) {
            throw new IllegalArgumentException("L'id de l'offre est obligatoire.");
        }
        if (idQualification == null) {
            throw new IllegalArgumentException("L'id de la qualification est obligatoire.");
        }
        this.idOffre = idOffre;
        this.idQualification = idQualification;
    }

    public String getIdOffreQualification() { return idOffreQualification; }
    public String getIdOffre() { return idOffre; }
    public String getIdQualification() { return idQualification; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffreQualification that = (OffreQualification) o;
        return Objects.equals(idOffreQualification, that.idOffreQualification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOffreQualification);
    }

    @Override
    public String toString() {
        return "OffreQualification{idOffreQualification=" + idOffreQualification +
                ", idOffre=" + idOffre +
                ", idQualification=" + idQualification + "}";
    }
}
