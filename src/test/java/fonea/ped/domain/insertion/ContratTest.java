package fonea.ped.domain.insertion;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContratTest {

    @Test
    @DisplayName("Creer un contrat avec 6 arguments")
    void doitCreerContrat() {
        Contrat c = new Contrat("CT1", LocalDate.of(2026, 9, 1), LocalDate.of(2027, 9, 1),
                150000.0, "T1", "I1");
        assertThat(c.getIdContrat()).isEqualTo("CT1");
        assertThat(c.getDateDebut()).isEqualTo(LocalDate.of(2026, 9, 1));
        assertThat(c.getDateFin()).isEqualTo(LocalDate.of(2027, 9, 1));
        assertThat(c.getSalaire()).isEqualTo(150000.0);
        assertThat(c.getIdTypeContrat()).isEqualTo("T1");
        assertThat(c.getIdInsertion()).isEqualTo("I1");
    }

    @Test
    @DisplayName("Le constructeur a 6 arguments laisse le statut a null")
    void statutNullSansArgumentStatut() {
        Contrat c = new Contrat("CT1", LocalDate.now(), LocalDate.now().plusYears(1),
                150000.0, "T1", "I1");
        assertThat(c.getStatut()).isNull();
    }

    @Test
    @DisplayName("Creer un contrat avec 7 arguments et statut")
    void doitCreerContratAvecStatut() {
        Contrat c = new Contrat("CT2", LocalDate.of(2026, 9, 1), LocalDate.of(2027, 9, 1),
                200000.0, "T2", "I2", StatutContrat.EN_PREPARATION);
        assertThat(c.getStatut()).isEqualTo(StatutContrat.EN_PREPARATION);
        assertThat(c.getSalaire()).isEqualTo(200000.0);
    }

    @Test
    @DisplayName("Les statuts de contrat disponibles couvrent tout le cycle de vie")
    void statutsDisponibles() {
        assertThat(StatutContrat.values()).contains(
                StatutContrat.EN_PREPARATION,
                StatutContrat.ACTIF,
                StatutContrat.TERMINE,
                StatutContrat.RESILIE,
                StatutContrat.INACTIVE);
    }

    @Test
    @DisplayName("Equals et hashCode sur idContrat")
    void equalsHashCode() {
        Contrat c1 = new Contrat("CT9", LocalDate.now(), LocalDate.now().plusMonths(2), 150000.0, "T9", "I9");
        Contrat c2 = new Contrat("CT9", LocalDate.now(), LocalDate.now().plusMonths(2), 200000.0, "T10", "I10");
        Contrat c3 = new Contrat("CT10", LocalDate.now(), LocalDate.now().plusMonths(2), 150000.0, "T9", "I9");
        assertThat(c1).isEqualTo(c2);
        assertThat(c1.hashCode()).isEqualTo(c2.hashCode());
        assertThat(c1).isNotEqualTo(c3);
    }
}
