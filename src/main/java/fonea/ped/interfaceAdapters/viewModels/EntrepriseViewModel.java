package fonea.ped.interfaceAdapters.viewModels;

/**
 * ViewModel pour l'affichage d'une Entreprise.
 */
public class EntrepriseViewModel {

    private String id;
    private String raisonSocial;
    private String adresse;
    private String niu;
    private String email;
    private String idSecteur;

    private String message;

    public EntrepriseViewModel() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRaisonSocial() { return raisonSocial; }
    public void setRaisonSocial(String raisonSocial) { this.raisonSocial = raisonSocial; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getIdSecteur() { return idSecteur; }
    public void setIdSecteur(String idSecteur) { this.idSecteur = idSecteur; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════╗\n");
        sb.append("║ ENTREPRISE #").append(id).append("\n");
        sb.append("╠══════════════════════════════════╣\n");
        if (raisonSocial != null) sb.append("║ Raison sociale: ").append(raisonSocial).append("\n");
        if (adresse != null)      sb.append("║ Adresse       : ").append(adresse).append("\n");
        if (niu != null)    sb.append("║ Niu     : ").append(niu).append("\n");
        if (email != null)        sb.append("║ Email         : ").append(email).append("\n");
        if (idSecteur != null)    sb.append("║ Secteur       : ").append(idSecteur).append("\n");
        if (message != null)      sb.append("║ ").append(message).append("\n");
        sb.append("╚══════════════════════════════════╝");
        return sb.toString();
    }

    public String getNiu() {return niu;}
	public void setNiu(String niu) {this.niu= niu;}
}