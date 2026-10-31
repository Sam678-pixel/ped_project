package fonea.ped.domain.securite;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Utilisateur = (idUtilisateur VARCHAR(50), email VARCHAR(50), password VARCHAR(50),
 *                derniereConnexion DATETIME, telephone VARCHAR(50), #idRole)
 */
public class Utilisateur {

    private String idUtilisateur;
    private String email;
    private String password;
    private LocalDateTime derniereConnexion;
    private String telephone;
    private String idRole;

    public Utilisateur(String idUtilisateur, String email, String password,
                       String telephone, String idRole) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("L'email est obligatoire.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire.");
        }
        this.idUtilisateur = idUtilisateur;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.idRole = idRole;
    }

    public void changerMotDePasse(String nouveauPassword) {
        if (nouveauPassword == null || nouveauPassword.isBlank()) {
            throw new IllegalArgumentException("Le nouveau mot de passe ne peut pas être vide.");
        }
        this.password = nouveauPassword;
    }

    public void enregistrerConnexion() {
        this.derniereConnexion = LocalDateTime.now();
    }

    public void changerRole(String idRole) {
        if (idRole == null || idRole.isBlank()) {
            throw new IllegalArgumentException("L'identifiant du rôle est obligatoire.");
        }
        this.idRole = idRole;
    }

    public String getIdUtilisateur() { return idUtilisateur; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public LocalDateTime getDerniereConnexion() { return derniereConnexion; }
    public String getTelephone() { return telephone; }
    public String getIdRole() { return idRole; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(idUtilisateur, that.idUtilisateur);
    }
    @Override public int hashCode() { return Objects.hash(idUtilisateur); }
    @Override public String toString() {
        return "Utilisateur{idUtilisateur='" + idUtilisateur +
                "', email='" + email + "', idRole='" + idRole + "'}";
    }
}
