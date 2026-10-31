package fonea.ped.interfaceAdapters.viewModels;

import fonea.ped.domain.MiseEnStage.MotifDecrochage;

/**
 * ViewModel pour l'affichage d'une Mise en stage / Renouvellement / Décrochage.
 */
public class MiseEnStageViewModel {

    private String id;
    private String dateDebut;
    private String dateFin;
    private String statut;

    private String idSelection;
    private String idMiseStage;
    private MotifDecrochage raison;

    private String message;

    public MiseEnStageViewModel() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDateDebut() { return dateDebut; }
    public void setDateDebut(String dateDebut) { this.dateDebut = dateDebut; }

    public String getDateFin() { return dateFin; }
    public void setDateFin(String dateFin) { this.dateFin = dateFin; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getIdSelection() { return idSelection; }
    public void setIdSelection(String idSelection) { this.idSelection = idSelection; }

    public String getIdMiseStage() { return idMiseStage; }
    public void setIdMiseStage(String idMiseStage) { this.idMiseStage = idMiseStage; }

    public MotifDecrochage getRaison() { return raison; }
    public void setRaison(MotifDecrochage motifDecrochage) { this.raison = motifDecrochage; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════╗\n");
        sb.append("║ MISE EN STAGE #").append(id).append("\n");
        sb.append("╠══════════════════════════════════╣\n");
        if (dateDebut != null)   sb.append("║ Début         : ").append(dateDebut).append("\n");
        if (dateFin != null)     sb.append("║ Fin           : ").append(dateFin).append("\n");
        if (statut != null)      sb.append("║ Statut        : ").append(statut).append("\n");
        if (idSelection != null) sb.append("║ Sélection     : ").append(idSelection).append("\n");
        if (idMiseStage != null) sb.append("║ Mise en stage : ").append(idMiseStage).append("\n");
        if (raison != null)      sb.append("║ Raison        : ").append(raison).append("\n");
        if (message != null)     sb.append("║ ").append(message).append("\n");
        sb.append("╚══════════════════════════════════╝");
        return sb.toString();
    }
}
