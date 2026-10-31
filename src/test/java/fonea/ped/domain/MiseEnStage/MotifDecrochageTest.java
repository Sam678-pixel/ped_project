package fonea.ped.domain.MiseEnStage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MotifDecrochageTest {

    @Test
    @DisplayName("Le libelle du motif est obligatoire")
    void libelleObligatoire() {
        assertThatThrownBy(() -> new MotifDecrochage("M1", null, "ABSENCE"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("libellé");
    }

    @Test
    @DisplayName("Getters du motif de decrochage")
    void getters() {
        MotifDecrochage m = new MotifDecrochage("M1", "Absences répétées", "ABSENCE");
        assertThat(m.getIdMotifDecrochage()).isEqualTo("M1");
        assertThat(m.getLibelle()).isEqualTo("Absences répétées");
        assertThat(m.getTypeMotif()).isEqualTo("ABSENCE");
    }

    @Test
    @DisplayName("Renommer le motif")
    void doitRenommer() {
        MotifDecrochage m = new MotifDecrochage("M1", "Absences", "ABSENCE");
        m.renommer("Abandon");
        assertThat(m.getLibelle()).isEqualTo("Abandon");
    }

    @Test
    @DisplayName("Interdire un renommage vide")
    void refuserRenommageVide() {
        MotifDecrochage m = new MotifDecrochage("M1", "Absences", "ABSENCE");
        assertThatThrownBy(() -> m.renommer("  "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Egalite basee sur l'identifiant")
    void egaliteParId() {
        assertThat(new MotifDecrochage("M1", "A", "X"))
                .isEqualTo(new MotifDecrochage("M1", "B", "Y"));
    }
}
