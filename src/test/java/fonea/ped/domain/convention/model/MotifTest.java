package fonea.ped.domain.convention.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MotifTest {

    @Test
    @DisplayName("Creer un motif")
    void doitCreerMotif() {
        Motif m = new Motif("M1", "Non respect des engagements");
        assertThat(m.getIdMotif()).isEqualTo("M1");
        assertThat(m.getLibelle()).isEqualTo("Non respect des engagements");
    }

    @Test
    @DisplayName("Renommer un motif avec un libelle valide")
    void doitRenommerMotif() {
        Motif m = new Motif("M2", "Ancien libelle");
        m.renommer("Nouveau libelle");
        assertThat(m.getLibelle()).isEqualTo("Nouveau libelle");
    }

    @Test
    @DisplayName("Ne pas renommer avec libelle null ou vide")
    void nePasRenommerMotifInvalide() {
        Motif m = new Motif("M3", "Libelle");
        assertThatThrownBy(() -> m.renommer(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("vide");
        assertThatThrownBy(() -> m.renommer("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("vide");
    }

    @Test
    @DisplayName("Equals et hashCode se basent sur idMotif")
    void equalsHashCodeSurId() {
        Motif m1 = new Motif("M4", "A");
        Motif m2 = new Motif("M4", "B");
        Motif m3 = new Motif("M5", "A");
        assertThat(m1).isEqualTo(m2);
        assertThat(m1.hashCode()).isEqualTo(m2.hashCode());
        assertThat(m1).isNotEqualTo(m3);
    }
}
