package fonea.ped.interfaceAdapters.viewModels;

/**
 * ViewModel pour l'affichage d'une Offre de stage.
 */
public class OffreViewModel {

    private String id;
    private String intitule;
    private String villeStage;
    private String duree;
    private String nbrePlaces;
    private String statut;

    private String idConvention;
    private String idDomaine;
    private String idPoste;

    private String message;

    public OffreViewModel() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }

    public String getVilleStage() { return villeStage; }
    public void setVilleStage(String villeStage) { this.villeStage = villeStage; }

    public String getDuree() { return duree; }
    public void setDuree(String duree) { this.duree = duree; }

    public String getNbrePlaces() { return nbrePlaces; }
    public void setNbrePlaces(String nbrePlaces) { this.nbrePlaces = nbrePlaces; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getIdConvention() { return idConvention; }
    public void setIdConvention(String idConvention) { this.idConvention = idConvention; }

    public String getIdDomaine() { return idDomaine; }
    public void setIdDomaine(String idDomaine) { this.idDomaine = idDomaine; }

    public String getIdPoste() { return idPoste; }
    public void setIdPoste(String idPoste) { this.idPoste = idPoste; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════╗\n");
        sb.append("║ OFFRE #").append(id).append("\n");
        sb.append("╠══════════════════════════════════╣\n");
        if (intitule != null)     sb.append("║ Intitulé      : ").append(intitule).append("\n");
        if (villeStage != null)   sb.append("║ Ville         : ").append(villeStage).append("\n");
        if (duree != null)        sb.append("║ Durée         : ").append(duree).append("\n");
        if (nbrePlaces != null)   sb.append("║ Places        : ").append(nbrePlaces).append("\n");
        if (statut != null)       sb.append("║ Statut        : ").append(statut).append("\n");
        if (idConvention != null) sb.append("║ Convention    : ").append(idConvention).append("\n");
        if (idDomaine != null)    sb.append("║ Domaine       : ").append(idDomaine).append("\n");
        if (idPoste != null)      sb.append("║ Poste         : ").append(idPoste).append("\n");
        if (message != null)      sb.append("║ ").append(message).append("\n");
        sb.append("╚══════════════════════════════════╝");
        return sb.toString();
    }
}