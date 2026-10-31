package fonea.ped.interfaceAdapters.viewModels;

/**
 * ViewModel pour l'affichage d'une Candidature / Présélection / Sélection.
 */
public class CandidatureViewModel {

    private String id;
    private String dateDepot;
    private String statut;

    private String idDiplome;
    private String idPublication;
    private String idCandidature;

    private String message;

    public CandidatureViewModel() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDateDepot() { return dateDepot; }
    public void setDateDepot(String dateDepot) { this.dateDepot = dateDepot; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getIdDiplome() { return idDiplome; }
    public void setIdDiplome(String idDiplome) { this.idDiplome = idDiplome; }

    public String getIdPublication() { return idPublication; }
    public void setIdPublication(String idPublication) { this.idPublication = idPublication; }

    public String getIdCandidature() { return idCandidature; }
    public void setIdCandidature(String idCandidature) { this.idCandidature = idCandidature; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════╗\n");
        sb.append("║ CANDIDATURE #").append(id).append("\n");
        sb.append("╠══════════════════════════════════╣\n");
        if (dateDepot != null)     sb.append("║ Date          : ").append(dateDepot).append("\n");
        if (statut != null)        sb.append("║ Statut        : ").append(statut).append("\n");
        if (idDiplome != null)     sb.append("║ Diplômé       : ").append(idDiplome).append("\n");
        if (idPublication != null) sb.append("║ Publication   : ").append(idPublication).append("\n");
        if (idCandidature != null) sb.append("║ Candidature   : ").append(idCandidature).append("\n");
        if (message != null)       sb.append("║ ").append(message).append("\n");
        sb.append("╚══════════════════════════════════╝");
        return sb.toString();
    }
}
