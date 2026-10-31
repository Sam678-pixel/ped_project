package fonea.ped.domain.securite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PermissionTest {

    @Test
    @DisplayName("Le code de la permission est obligatoire")
    void codeObligatoire() {
        assertThatThrownBy(() -> new Permission("P1", null, "Créer une offre"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("code");
    }

    @Test
    @DisplayName("Getters de la permission")
    void getters() {
        Permission p = new Permission("P1", "OFFRE_CREATE", "Créer une offre");
        assertThat(p.getIdPermission()).isEqualTo("P1");
        assertThat(p.getCodePermission()).isEqualTo("OFFRE_CREATE");
        assertThat(p.getDescription()).isEqualTo("Créer une offre");
    }

    @Test
    @DisplayName("Egalite basee sur l'identifiant")
    void egaliteParId() {
        assertThat(new Permission("P1", "A", "x")).isEqualTo(new Permission("P1", "B", "y"));
        assertThat(new Permission("P1", "A", "x")).isNotEqualTo(new Permission("P2", "A", "x"));
    }
}
