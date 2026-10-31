package fonea.ped.interfaceAdapters.viewModels;

/**
 * ViewModel pour l'affichage d'un Diplômé.
 */
public class DiplomeViewModel {

    private String id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String age;

    private String message;

    public DiplomeViewModel() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════╗\n");
        sb.append("║ DIPLÔMÉ #").append(id).append("\n");
        sb.append("╠══════════════════════════════════╣\n");
        if (nom != null)           sb.append("║ Nom           : ").append(nom).append("\n");
        if (prenom != null)        sb.append("║ Prénom        : ").append(prenom).append("\n");
        if (dateNaissance != null) sb.append("║ Naissance     : ").append(dateNaissance).append("\n");
        if (age != null)           sb.append("║ Âge           : ").append(age).append("\n");
        if (message != null)       sb.append("║ ").append(message).append("\n");
        sb.append("╚══════════════════════════════════╝");
        return sb.toString();
    }
}
