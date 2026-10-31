package fonea.ped.domain.insertion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TypeContratTest {

    @Test
    @DisplayName("Creer un TypeContrat")
    void doitCreerTypeContrat() {
        TypeContrat tc = new TypeContrat("TC1", "CDD");
        assertThat(tc.getIdTypeContrat()).isEqualTo("TC1");
        assertThat(tc.getLibelle()).isEqualTo("CDD");
    }

    @Test
    @DisplayName("Equals et hashCode sur idTypeContrat")
    void equalsHashCode() {
        TypeContrat tc1 = new TypeContrat("TC2", "CDI");
        TypeContrat tc2 = new TypeContrat("TC2", "CAE");
        TypeContrat tc3 = new TypeContrat("TC3", "CDI");
        assertThat(tc1).isEqualTo(tc2);
        assertThat(tc1.hashCode()).isEqualTo(tc2.hashCode());
        assertThat(tc1).isNotEqualTo(tc3);
    }
}
