package fonea.ped.domain.convention.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResiliationTest {

    @Test
    @DisplayName("Creer une resiliation avec motif")
    void doitCreerResiliationAvecMotif() {
        MotifResiliation mr = new MotifResiliation("MR1", "Fin de contrat");
        Resiliation r = new Resiliation("R1", LocalDate.of(2026, 10, 23), "AGENT1", "C1", mr);
        assertThat(r.getIdResiliation()).isEqualTo("R1");
        assertThat(r.getDateResiliation()).isEqualTo(LocalDate.of(2026, 10, 23));
        assertThat(r.getInitiateur()).isEqualTo("AGENT1");
        assertThat(r.getIdConvention()).isEqualTo("C1");
        assertThat(r.getMotifResiliation()).isEqualTo(mr);
    }

    @Test
    @DisplayName("Creer une resiliation avec constructeur partiel")
    void doitCreerResiliationPartielle() {
        Resiliation r = new Resiliation("R2", LocalDate.of(2026, 9, 1), "AGENT2", "C2");
        assertThat(r.getIdResiliation()).isEqualTo("R2");
        assertThat(r.getInitiateur()).isEqualTo("AGENT2");
        assertThat(r.getIdConvention()).isEqualTo("C2");
        assertThat(r.getDateResiliation()).isEqualTo(LocalDate.of(2026, 9, 1));
    }

    @Test
    @DisplayName("Equals et hashCode sur idResiliation")
    void equalsHashCode() {
        Resiliation r1 = new Resiliation("R3", LocalDate.now(), "A", "C3");
        Resiliation r2 = new Resiliation("R3", LocalDate.now(), "B", "C4");
        Resiliation r3 = new Resiliation("R4", LocalDate.now(), "A", "C3");
        assertThat(r1).isEqualTo(r2);
        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
        assertThat(r1).isNotEqualTo(r3);
    }
}
