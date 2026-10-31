package fonea.ped.domain.convention.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MotifResiliationTest {

    @Test
    @DisplayName("Creer un motif de resiliation")
    void doitCreerMotifResiliation() {
        MotifResiliation mr = new MotifResiliation("MR1", "Insolvabilité");
        assertThat(mr.getIdMotifResiliation()).isEqualTo("MR1");
        assertThat(mr.getLibelle()).isEqualTo("Insolvabilité");
    }

    @Test
    @DisplayName("Ne pas creer de motif de resiliation sans libelle")
    void nePasCreerSansLibelle() {
        assertThatThrownBy(() -> new MotifResiliation("MR2", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("obligatoire");
        assertThatThrownBy(() -> new MotifResiliation("MR3", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("obligatoire");
    }

    @Test
    @DisplayName("Renommer un motif de resiliation")
    void doitRenommerMotifResiliation() {
        MotifResiliation mr = new MotifResiliation("MR4", "Ancien");
        mr.renommer("Nouveau");
        assertThat(mr.getLibelle()).isEqualTo("Nouveau");
    }

    @Test
    @DisplayName("Equals et hashCode sur idMotifResiliation")
    void equalsHashCode() {
        MotifResiliation mr1 = new MotifResiliation("MR5", "A");
        MotifResiliation mr2 = new MotifResiliation("MR5", "B");
        assertThat(mr1).isEqualTo(mr2);
        assertThat(mr1.hashCode()).isEqualTo(mr2.hashCode());
    }
}
