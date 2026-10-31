package fonea.ped.domain.MiseEnStage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RenouvellementTest {

    @Test
    @DisplayName("Creer un renouvellement")
    void doitCreerRenouvellement() {
        Renouvellement r = new Renouvellement("RN1", LocalDate.of(2027, 1, 1), LocalDate.of(2027, 3, 1), "MS1");
        assertThat(r.getIdRenouvellement()).isEqualTo("RN1");
        assertThat(r.getDateDebut()).isEqualTo(LocalDate.of(2027, 1, 1));
        assertThat(r.getDateFin()).isEqualTo(LocalDate.of(2027, 3, 1));
        assertThat(r.getIdMiseStage()).isEqualTo("MS1");
    }

    @Test
    @DisplayName("Prolonger avec une date de fin posterieure")
    void doitProlonger() {
        Renouvellement r = new Renouvellement("RN2", LocalDate.of(2027, 1, 1), LocalDate.of(2027, 3, 1), "MS2");
        LocalDate nouvelleFin = LocalDate.of(2027, 6, 1);
        r.prolonger(nouvelleFin);
        assertThat(r.getDateFin()).isEqualTo(nouvelleFin);
    }

    @Test
    @DisplayName("Refuser une prolongation vers une date anterieure")
    void nePasProlongerVersDateAnterieure() {
        Renouvellement r = new Renouvellement("RN3", LocalDate.of(2027, 1, 1), LocalDate.of(2027, 3, 1), "MS3");
        assertThatThrownBy(() -> r.prolonger(LocalDate.of(2027, 2, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("postérieure");
    }

    @Test
    @DisplayName("Refuser une prolongation avec une date null")
    void nePasProlongerAvecNull() {
        Renouvellement r = new Renouvellement("RN3", LocalDate.of(2027, 1, 1), LocalDate.of(2027, 3, 1), "MS3");
        assertThatThrownBy(() -> r.prolonger(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Equals et hashCode sur idRenouvellement")
    void equalsHashCode() {
        Renouvellement r1 = new Renouvellement("RN4", LocalDate.now(), LocalDate.now().plusMonths(2), "MS4");
        Renouvellement r2 = new Renouvellement("RN4", LocalDate.now(), LocalDate.now().plusMonths(3), "MS5");
        Renouvellement r3 = new Renouvellement("RN5", LocalDate.now(), LocalDate.now().plusMonths(2), "MS4");
        assertThat(r1).isEqualTo(r2);
        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
        assertThat(r1).isNotEqualTo(r3);
    }
}
