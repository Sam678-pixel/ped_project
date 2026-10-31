package fonea.ped.domain.convention.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TypeConventionTest {

    @Test
    @DisplayName("Creer un TypeConvention")
    void doitCreerTypeConvention() {
        TypeConvention tc = new TypeConvention("TC1", "P1", "E1");
        assertThat(tc.getIdTypeConvention()).isEqualTo("TC1");
        assertThat(tc.getIdProgramme()).isEqualTo("P1");
        assertThat(tc.getIdEntreprise()).isEqualTo("E1");
    }

    @Test
    @DisplayName("Equals et hashCode sur idTypeConvention")
    void equalsHashCode() {
        TypeConvention tc1 = new TypeConvention("TC2", "P1", "E1");
        TypeConvention tc2 = new TypeConvention("TC2", "P2", "E2");
        TypeConvention tc3 = new TypeConvention("TC3", "P1", "E1");
        assertThat(tc1).isEqualTo(tc2);
        assertThat(tc1.hashCode()).isEqualTo(tc2.hashCode());
        assertThat(tc1).isNotEqualTo(tc3);
    }
}
