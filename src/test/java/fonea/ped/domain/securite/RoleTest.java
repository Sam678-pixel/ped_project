package fonea.ped.domain.securite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoleTest {

    @Test
    @DisplayName("Le code du role est obligatoire")
    void codeObligatoire() {
        assertThatThrownBy(() -> new Role("R1", null, "Administrateur"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("code");
    }

    @Test
    @DisplayName("Le libelle du role est obligatoire")
    void libelleObligatoire() {
        assertThatThrownBy(() -> new Role("R1", "ADMIN", "  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("libellé");
    }

    @Test
    @DisplayName("Renommer un role")
    void doitRenommer() {
        Role r = new Role("R1", "ADMIN", "Administrateur");
        r.renommer("Super administrateur");
        assertThat(r.getLibelleRole()).isEqualTo("Super administrateur");
        assertThat(r.getIdRole()).isEqualTo("R1");
        assertThat(r.getCodeRole()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Interdire un renommage vide")
    void refuserRenommageVide() {
        Role r = new Role("R1", "ADMIN", "Administrateur");
        assertThatThrownBy(() -> r.renommer(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Egalite basee sur l'identifiant")
    void egaliteParId() {
        assertThat(new Role("R1", "ADMIN", "Administrateur"))
                .isEqualTo(new Role("R1", "AUTRE", "Autre"));
    }
}
