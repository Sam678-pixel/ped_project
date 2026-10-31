package fonea.ped.domain.securite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UtilisateurTest {

    private Utilisateur creerUtilisateur() {
        return new Utilisateur("U1", "jean@fonea.rw", "secret", "0788000000", "R1");
    }

    @Test
    @DisplayName("L'email est obligatoire")
    void emailObligatoire() {
        assertThatThrownBy(() -> new Utilisateur("U1", null, "secret", null, "R1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("email");
        assertThatThrownBy(() -> new Utilisateur("U1", "  ", "secret", null, "R1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Le mot de passe est obligatoire")
    void passwordObligatoire() {
        assertThatThrownBy(() -> new Utilisateur("U1", "jean@fonea.rw", null, null, "R1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("mot de passe");
    }

    @Test
    @DisplayName("Aucune derniere connexion a la creation")
    void derniereConnexionNulleALaCreation() {
        assertThat(creerUtilisateur().getDerniereConnexion()).isNull();
    }

    @Test
    @DisplayName("Enregistrer une connexion renseigne la date courante")
    void doitEnregistrerConnexion() {
        Utilisateur u = creerUtilisateur();
        LocalDateTime avant = LocalDateTime.now().minusSeconds(1);
        u.enregistrerConnexion();
        assertThat(u.getDerniereConnexion())
                .isAfter(avant)
                .isBefore(LocalDateTime.now().plusSeconds(1));
    }

    @Test
    @DisplayName("Changer le mot de passe")
    void doitChangerMotDePasse() {
        Utilisateur u = creerUtilisateur();
        u.changerMotDePasse("nouveauSecret");
        assertThat(u.getPassword()).isEqualTo("nouveauSecret");
    }

    @Test
    @DisplayName("Refuser un mot de passe vide")
    void refuserMotDePasseVide() {
        Utilisateur u = creerUtilisateur();
        assertThatThrownBy(() -> u.changerMotDePasse("  "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Changer de role")
    void doitChangerRole() {
        Utilisateur u = creerUtilisateur();
        u.changerRole("R2");
        assertThat(u.getIdRole()).isEqualTo("R2");
    }

    @Test
    @DisplayName("Refuser un role vide")
    void refuserRoleVide() {
        Utilisateur u = creerUtilisateur();
        assertThatThrownBy(() -> u.changerRole(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Getters et egalite par identifiant")
    void gettersEtEgalite() {
        Utilisateur u = creerUtilisateur();
        assertThat(u.getIdUtilisateur()).isEqualTo("U1");
        assertThat(u.getEmail()).isEqualTo("jean@fonea.rw");
        assertThat(u.getPassword()).isEqualTo("secret");
        assertThat(u.getTelephone()).isEqualTo("0788000000");
        assertThat(u.getIdRole()).isEqualTo("R1");
        assertThat(u).isEqualTo(new Utilisateur("U1", "autre@fonea.rw", "autre", null, "R9"));
    }
}
