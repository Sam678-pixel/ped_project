package fonea.ped.useCase.useCaseOffres.UseCaseOffres.creerOffre;

import java.util.List;

import fonea.ped.domain.Offres.StatutOffre;

public class CreerOffreResponse {
	private final String idOffre;
    private final String intitule;
    private final String villeStage;
    private final Integer duree;
    private final Integer nbrePlaces;
    private final StatutOffre statut;
    private final String idConvention;
    private final String idDomaine;
    private final String idPoste;
    private final String idMetier;
    private final String idSecteur;
    private final String idEntreprise;
    private final List<String> idsCompetences;
    private final List<String> idsCriteres;
    private final List<String> idsPrerequis;
    private final List<String> idsQualifications;

    public CreerOffreResponse(String idOffre,
                              String intitule,
                              String villeStage,
                              Integer duree,
                              Integer nbrePlaces,
                              StatutOffre statut,
                              String idConvention,
                              String idDomaine,
                              String idPoste,
                              String idMetier,
                              String idSecteur,
                              String idEntreprise,
                              List<String> idsCompetences,
                              List<String> idsCriteres,
                              List<String> idsPrerequis,
                              List<String> idsQualifications) {
        this.idOffre = idOffre;
        this.intitule = intitule;
        this.villeStage = villeStage;
        this.duree = duree;
        this.nbrePlaces = nbrePlaces;
        this.statut = statut;
        this.idConvention = idConvention;
        this.idDomaine = idDomaine;
        this.idPoste = idPoste;
        this.idMetier = idMetier;
        this.idSecteur = idSecteur;
        this.idEntreprise = idEntreprise;
        this.idsCompetences = idsCompetences;
        this.idsCriteres = idsCriteres;
        this.idsPrerequis = idsPrerequis;
        this.idsQualifications = idsQualifications;
    }

    public String getIdOffre() { return idOffre; }
    public String getIntitule() { return intitule; }
    public String getVilleStage() { return villeStage; }
    public Integer getDuree() { return duree; }
    public Integer getNbrePlaces() { return nbrePlaces; }
    public StatutOffre getStatut() { return statut; }
    public String getIdConvention() { return idConvention; }
    public String getIdDomaine() { return idDomaine; }
    public String getIdPoste() { return idPoste; }
    public String getIdMetier() { return idMetier; }
    public String getIdSecteur() { return idSecteur; }
    public String getIdEntreprise() { return idEntreprise; }
    public List<String> getIdsCompetences() { return idsCompetences; }
    public List<String> getIdsCriteres() { return idsCriteres; }
    public List<String> getIdsPrerequis() { return idsPrerequis; }
    public List<String> getIdsQualifications() { return idsQualifications; }
}
