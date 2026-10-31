package fonea.ped.domain.Offres;

import java.time.LocalDate;
import java.util.Objects;

public class OffresStages {

    private String idOffre;
    private String intitule;
    private String description;
    private String villeStage;
    private Integer dureeMois;
    private StatutOffre statutOffre;
    private Integer nbrePlaces;
    private String idPoste;
    private String idDomaine;
    private String idConvention;

    public OffresStages(String idOffre, String intitule, String description,
                        String villeStage, Integer dureeMois, StatutOffre statutOffre,
                        Integer nbrePlaces, String idPoste, String idDomaine,
                        String idConvention) {
        this.idOffre = idOffre;
        this.intitule = intitule;
        this.description = description;
        this.villeStage = villeStage;
        this.dureeMois = dureeMois;
        this.statutOffre = statutOffre != null ? statutOffre : StatutOffre.BROUILLON;
        this.nbrePlaces = nbrePlaces != null ? nbrePlaces : 0;
        this.idPoste = idPoste;
        this.idDomaine = idDomaine;
        this.idConvention = idConvention;
    }

    public void publier() {
        if (this.statutOffre == StatutOffre.CLOTUREE) {
            throw new IllegalStateException("Impossible de publier une offre clôturée.");
        }
        this.statutOffre = StatutOffre.PUBLIEE;
    }

    public void cloturer() { this.statutOffre = StatutOffre.CLOTUREE; }

    public void changerStatut(StatutOffre annulee) {
        if (annulee == null) {
            throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
        }
        this.statutOffre = annulee;
    }

    public boolean verifierDisponibilite() {
        return nbrePlaces != null && nbrePlaces > 0;
    }

    public void decrementerPlaces() {
        if (nbrePlaces == null || nbrePlaces <= 0) {
            throw new IllegalStateException("Aucune place disponible à décrémenter.");
        }
        this.nbrePlaces--;
    }

    public boolean estComplete() {
        return nbrePlaces == null || nbrePlaces <= 0;
    }

    public String getIdOffre() { return idOffre; }
    public String getIntitule() { return intitule; }
    public String getDescription() { return description; }
    public String getVilleStage() { return villeStage; }
    public Integer getDureeMois() { return dureeMois; }
    public StatutOffre getStatutOffre() { return statutOffre; }
    public Integer getNbrePlaces() { return nbrePlaces; }
    public String getIdPoste() { return idPoste; }
    public String getIdDomaine() { return idDomaine; }
    public String getIdConvention() { return idConvention; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffresStages that = (OffresStages) o;
        return Objects.equals(idOffre, that.idOffre);
    }
    @Override public int hashCode() { return Objects.hash(idOffre); }
    @Override public String toString() {
        return "OffresStages{idOffre='" + idOffre + "', intitule='" + intitule +
                "', statutOffre='" + statutOffre + "'}";
    }
}
