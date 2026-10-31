package fonea.ped.interfaceAdapters.viewModels;

import fonea.ped.domain.convention.model.MotifResiliation;

/**
 * ViewModel pour l'affichage d'une Convention.
 * Tous les champs sont des String formatées pour l'IHM.
 */
public class ConventionViewModel {

    private String id;
    private String dateEffet;
    private String tauxFonea;
    private String tauxEntreprise;
    private String statut;

    // Résiliation
    private String dateResiliation;
    private MotifResiliation motif;
    private String initiateur;

    private String message;

    public ConventionViewModel() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDateEffet() { return dateEffet; }
    public void setDateEffet(String dateEffet) { this.dateEffet = dateEffet; }

    public String getTauxFonea() { return tauxFonea; }
    public void setTauxFonea(String tauxFonea) { this.tauxFonea = tauxFonea; }

    public String getTauxEntreprise() { return tauxEntreprise; }
    public void setTauxEntreprise(String tauxEntreprise) { this.tauxEntreprise = tauxEntreprise; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getDateResiliation() { return dateResiliation; }
    public void setDateResiliation(String dateResiliation) { this.dateResiliation = dateResiliation; }

    public MotifResiliation getMotif() { return motif; }
    public void setMotif(MotifResiliation motifResiliation) { this.motif = motifResiliation; }

    public String getInitiateur() { return initiateur; }
    public void setInitiateur(String initiateur) { this.initiateur = initiateur; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════╗\n");
        sb.append("║ CONVENTION #").append(id).append("\n");
        sb.append("╠══════════════════════════════════╣\n");
        if (dateEffet != null)      sb.append("║ Date d'effet  : ").append(dateEffet).append("\n");
        if (tauxFonea != null)      sb.append("║ Taux FONEA    : ").append(tauxFonea).append("\n");
        if (tauxEntreprise != null) sb.append("║ Taux Entrepr. : ").append(tauxEntreprise).append("\n");
        if (statut != null)         sb.append("║ Statut        : ").append(statut).append("\n");
        if (dateResiliation != null) sb.append("║ Résiliée le   : ").append(dateResiliation).append("\n");
        if (motif != null)          sb.append("║ Motif         : ").append(motif).append("\n");
        if (initiateur != null)     sb.append("║ Initiateur    : ").append(initiateur).append("\n");
        if (message != null)        sb.append("║ ").append(message).append("\n");
        sb.append("╚══════════════════════════════════╝");
        return sb.toString();
    }
}
