package fonea.ped.interfaceAdapters.viewModels;

/**
 * ViewModel pour l'affichage d'une Insertion et de son Contrat associé.
 */
public class InsertionViewModel {

    private String idInsertion;
    private String dateEmbauche;
    private String idPoste;
    private String idTypeContrat;
    private String idMiseStage;

    private String idContrat;
    private String dateDebutContrat;
    private String dateFinContrat;
    private String salaire;
    private String statutContrat;

    private String message;

    public InsertionViewModel() {
    }

    public String getIdInsertion() { return idInsertion; }
    public void setIdInsertion(String idInsertion) { this.idInsertion = idInsertion; }

    public String getDateEmbauche() { return dateEmbauche; }
    public void setDateEmbauche(String dateEmbauche) { this.dateEmbauche = dateEmbauche; }

    public String getIdPoste() { return idPoste; }
    public void setIdPoste(String idPoste) { this.idPoste = idPoste; }

    public String getIdTypeContrat() { return idTypeContrat; }
    public void setIdTypeContrat(String idTypeContrat) { this.idTypeContrat = idTypeContrat; }

    public String getIdMiseStage() { return idMiseStage; }
    public void setIdMiseStage(String idMiseStage) { this.idMiseStage = idMiseStage; }

    public String getIdContrat() { return idContrat; }
    public void setIdContrat(String idContrat) { this.idContrat = idContrat; }

    public String getDateDebutContrat() { return dateDebutContrat; }
    public void setDateDebutContrat(String dateDebutContrat) { this.dateDebutContrat = dateDebutContrat; }

    public String getDateFinContrat() { return dateFinContrat; }
    public void setDateFinContrat(String dateFinContrat) { this.dateFinContrat = dateFinContrat; }

    public String getSalaire() { return salaire; }
    public void setSalaire(String salaire) { this.salaire = salaire; }

    public String getStatutContrat() { return statutContrat; }
    public void setStatutContrat(String statutContrat) { this.statutContrat = statutContrat; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════╗\n");
        sb.append("║ INSERTION #").append(idInsertion).append("\n");
        sb.append("╠══════════════════════════════════╣\n");
        if (dateEmbauche != null)      sb.append("║ Embauche      : ").append(dateEmbauche).append("\n");
        if (idPoste != null)           sb.append("║ Poste         : ").append(idPoste).append("\n");
        if (idTypeContrat != null)     sb.append("║ Type contrat  : ").append(idTypeContrat).append("\n");
        if (idMiseStage != null)       sb.append("║ Mise en stage : ").append(idMiseStage).append("\n");
        sb.append("╟──────────────────────────────────╢\n");
        if (idContrat != null)         sb.append("║ Contrat #").append(idContrat).append("\n");
        if (dateDebutContrat != null)  sb.append("║ Début contrat : ").append(dateDebutContrat).append("\n");
        if (dateFinContrat != null)    sb.append("║ Fin contrat   : ").append(dateFinContrat).append("\n");
        if (salaire != null)           sb.append("║ Salaire       : ").append(salaire).append("\n");
        if (statutContrat != null)     sb.append("║ Statut        : ").append(statutContrat).append("\n");
        if (message != null)           sb.append("║ ").append(message).append("\n");
        sb.append("╚══════════════════════════════════╝");
        return sb.toString();
    }
}
