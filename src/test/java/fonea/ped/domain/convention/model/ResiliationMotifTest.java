package fonea.ped.domain.convention.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResiliationMotifTest {

    @Test
    @DisplayName("Creer une liaison ResiliationMotif")
    void doitCreerResiliationMotif() {
        ResiliationMotif rm = new ResiliationMotif("R1", "MR1");
        assertThat(rm.getIdResiliation()).isEqualTo("R1");
        assertThat(rm.getIdMotifResiliation()).isEqualTo("MR1");
    }

    @Test
    @DisplayName("Equals et hashCode sur la cle composite")
    void equalsHashCodeComposite() {
        ResiliationMotif rm1 = new ResiliationMotif("R2", "MR2");
        ResiliationMotif rm2 = new ResiliationMotif("R2", "MR2");
        ResiliationMotif rm3 = new ResiliationMotif("R3", "MR2");
        assertThat(rm1).isEqualTo(rm2);
        assertThat(rm1.hashCode()).isEqualTo(rm2.hashCode());
        assertThat(rm1).isNotEqualTo(rm3);
    }
}
