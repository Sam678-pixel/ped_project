package fonea.ped.domain.MiseEnStage;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DecrochageTest {

    @Test
    @DisplayName("Creer un decrochage")
    void doitCreerDecrochage() {
        Decrochage d = new Decrochage("D1", LocalDate.of(2026, 10, 15), "MS1");
        assertThat(d.getIdDecrochage()).isEqualTo("D1");
        assertThat(d.getDateDecrochage()).isEqualTo(LocalDate.of(2026, 10, 15));
        assertThat(d.getIdMiseStage()).isEqualTo("MS1");
    }

    @Test
    @DisplayName("Equals et hashCode sur idDecrochage")
    void equalsHashCode() {
        Decrochage d1 = new Decrochage("D2", LocalDate.now(), "MS2");
        Decrochage d2 = new Decrochage("D2", LocalDate.now(), "MS3");
        Decrochage d3 = new Decrochage("D3", LocalDate.now(), "MS2");
        assertThat(d1).isEqualTo(d2);
        assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
        assertThat(d1).isNotEqualTo(d3);
    }
}
