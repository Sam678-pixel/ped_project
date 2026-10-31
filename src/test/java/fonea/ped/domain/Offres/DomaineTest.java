package fonea.ped.domain.Offres;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DomaineTest {

    @Test
    @DisplayName("Getters du domaine")
    void getters() {
        Domaine d = new Domaine("D1", "Développement");
        assertThat(d.getIdDomaine()).isEqualTo("D1");
        assertThat(d.getLibelle()).isEqualTo("Développement");
    }

    @Test
    @DisplayName("Renommer un domaine")
    void doitRenommer() {
        Domaine d = new Domaine("D1", "Développement");
        d.renommer("Data");
        assertThat(d.getLibelle()).isEqualTo("Data");
    }

    @Test
    @DisplayName("Interdire un renommage vide ou null")
    void refuserRenommageVide() {
        Domaine d = new Domaine("D1", "Développement");
        assertThatThrownBy(() -> d.renommer(null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> d.renommer("  "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Egalite basee sur l'identifiant")
    void egaliteParId() {
        assertThat(new Domaine("D1", "A"))
                .isEqualTo(new Domaine("D1", "B"));
        assertThat(new Domaine("D1", "A"))
                .isNotEqualTo(new Domaine("D2", "A"));
    }
}
