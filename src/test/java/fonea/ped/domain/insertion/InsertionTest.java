package fonea.ped.domain.insertion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InsertionTest {

    @Test
    @DisplayName("Creer une insertion")
    void doitCreerInsertion() {
        Insertion i = new Insertion("I1", LocalDate.of(2026, 9, 1), "P1", "MS1");
        assertThat(i.getIdInsertion()).isEqualTo("I1");
        assertThat(i.getDateEmbauche()).isEqualTo(LocalDate.of(2026, 9, 1));
        assertThat(i.getIdPoste()).isEqualTo("P1");
        assertThat(i.getIdMiseStage()).isEqualTo("MS1");
    }

    @Test
    @DisplayName("Enregistrer une embauche avec date valide")
    void doitEnregistrerEmbauche() {
        Insertion i = new Insertion("I2", null, "P2", "MS2");
        i.enregistrerEmbauche(LocalDate.of(2026, 10, 1));
        assertThat(i.getDateEmbauche()).isEqualTo(LocalDate.of(2026, 10, 1));
    }

    @Test
    @DisplayName("Ne pas enregistrer une embauche sans date")
    void nePasEnregistrerEmbaucheSansDate() {
        Insertion i = new Insertion("I3", null, "P3", "MS3");
        assertThatThrownBy(() -> i.enregistrerEmbauche(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("date d'embauche");
    }

    @Test
    @DisplayName("Associer un poste avec id valide")
    void doitAssocierPoste() {
        Insertion i = new Insertion("I4", LocalDate.now(), "P4", "MS4");
        i.associerPoste("P5");
        assertThat(i.getIdPoste()).isEqualTo("P5");
    }

    @Test
    @DisplayName("Ne pas associer un poste vide ou null")
    void nePasAssocierPosteInvalide() {
        Insertion i = new Insertion("I5", LocalDate.now(), "P5", "MS5");
        assertThatThrownBy(() -> i.associerPoste(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("poste");
        assertThatThrownBy(() -> i.associerPoste("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("poste");
    }

    @Test
    @DisplayName("Equals et hashCode sur idInsertion")
    void equalsHashCode() {
        Insertion i1 = new Insertion("I6", LocalDate.now(), "P6", "MS6");
        Insertion i2 = new Insertion("I6", LocalDate.now(), "P7", "MS7");
        Insertion i3 = new Insertion("I7", LocalDate.now(), "P6", "MS6");
        assertThat(i1).isEqualTo(i2);
        assertThat(i1.hashCode()).isEqualTo(i2.hashCode());
        assertThat(i1).isNotEqualTo(i3);
    }
}
