package fonea.ped.domain.securite;

import java.util.Objects;

/**
 * AgentFonea = (idAgent VARCHAR(50), nom VARCHAR(50), prenom VARCHAR(50),
 *               fonction VARCHAR(50), #idUtilisateur)
 */
public class AgentFonea {

    private String idAgent;
    private String nom;
    private String prenom;
    private String fonction;
    private String idUtilisateur;

    public AgentFonea(String idAgent, String nom, String prenom,
                      String fonction, String idUtilisateur) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom de l'agent est obligatoire.");
        }
        this.idAgent = idAgent;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.idUtilisateur = idUtilisateur;
    }

    public String getIdAgent() { return idAgent; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getFonction() { return fonction; }
    public String getIdUtilisateur() { return idUtilisateur; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentFonea that = (AgentFonea) o;
        return Objects.equals(idAgent, that.idAgent);
    }
    @Override public int hashCode() { return Objects.hash(idAgent); }
    @Override public String toString() {
        return "AgentFonea{idAgent='" + idAgent + "', nom='" + nom +
                "', prenom='" + prenom + "'}";
    }
}
