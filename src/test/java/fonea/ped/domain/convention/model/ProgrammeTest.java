package fonea.ped.domain.convention.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProgrammeTest {

    @Test
    @DisplayName("Creer un programme")
    void doitCreerProgramme() {
        Programme p = new Programme("P1", "Programme Jeunes", "FORMATION");
        assertThat(p.getIdProgramme()).isEqualTo("P1");
        assertThat(p.getLibelle()).isEqualTo("Programme Jeunes");
        assertThat(p.getTypeProgramme()).isEqualTo("FORMATION");
    }

    @Test
    @DisplayName("Renommer un programme avec un libelle valide")
    void doitRenommerProgramme() {
        Programme p = new Programme("P2", "Ancien", "ALTERNANCE");
        p.renommer("Nouveau");
        assertThat(p.getLibelle()).isEqualTo("Nouveau");
    }

    @Test
    @DisplayName("Ne pas renommer avec libelle vide ou null")
    void nePasRenommerProgrammeInvalide() {
        Programme p = new Programme("P3", "Libelle", "FORMATION");
        assertThatThrownBy(() -> p.renommer(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("vide");
        assertThatThrownBy(() -> p.renommer("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("vide");
    }

    @Test
    @DisplayName("Equals et hashCode sur idProgramme")
    void equalsHashCode() {
        Programme p1 = new Programme("P4", "A", "T1");
        Programme p2 = new Programme("P4", "B", "T2");
        Programme p3 = new Programme("P5", "A", "T1");
        assertThat(p1).isEqualTo(p2);
        assertThat(p1.hashCode()).isEqualTo(p2.hashCode());
        assertThat(p1).isNotEqualTo(p3);
    }
}
