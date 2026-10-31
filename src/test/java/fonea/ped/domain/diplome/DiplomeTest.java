package fonea.ped.domain.diplome;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiplomeTest {

    @Test
    @DisplayName("Creer un diplome avec des valeurs valides")
    void doitCreerDiplome() {
        Diplome d = new Diplome("D1", "KAMGA", "Paul", LocalDate.of(2000, 5, 10),
                "M", "Licence", "2023", "Informatique", "U1");
        assertThat(d.getIdDiplome()).isEqualTo("D1");
        assertThat(d.getNom()).isEqualTo("KAMGA");
        assertThat(d.getPrenom()).isEqualTo("Paul");
        assertThat(d.getDateNaissance()).isEqualTo(LocalDate.of(2000, 5, 10));
        assertThat(d.getSexe()).isEqualTo("M");
        assertThat(d.getNiveauDiplome()).isEqualTo("Licence");
        assertThat(d.getAnneeObtention()).isEqualTo("2023");
        assertThat(d.getFiliere()).isEqualTo("Informatique");
        assertThat(d.getIdUtilisateur()).isEqualTo("U1");
    }

    @Test
    @DisplayName("Ne pas creer un diplome sans nom")
    void nePasCreerSansNom() {
        assertThatThrownBy(() -> new Diplome("D2", null, "Paul", LocalDate.of(2000, 1, 1),
                "M", "Licence", "2023", "Info", "U2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nom");
        assertThatThrownBy(() -> new Diplome("D3", "   ", "Paul", LocalDate.of(2000, 1, 1),
                "M", "Licence", "2023", "Info", "U3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nom");
    }

    @Test
    @DisplayName("Ne pas creer un diplome sans prenom")
    void nePasCreerSansPrenom() {
        assertThatThrownBy(() -> new Diplome("D4", "KAMGA", null, LocalDate.of(2000, 1, 1),
                "M", "Licence", "2023", "Info", "U4"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("prénom");
        assertThatThrownBy(() -> new Diplome("D5", "KAMGA", "   ", LocalDate.of(2000, 1, 1),
                "M", "Licence", "2023", "Info", "U5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("prénom");
    }

    @Test
    @DisplayName("Equals et hashCode sur idDiplome")
    void equalsHashCode() {
        Diplome d1 = new Diplome("D6", "A", "B", LocalDate.of(2000, 1, 1), "M", "L", "2023", "F", "U6");
        Diplome d2 = new Diplome("D6", "C", "D", LocalDate.of(1999, 1, 1), "F", "M", "2022", "G", "U7");
        Diplome d3 = new Diplome("D7", "A", "B", LocalDate.of(2000, 1, 1), "M", "L", "2023", "F", "U6");
        assertThat(d1).isEqualTo(d2);
        assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
        assertThat(d1).isNotEqualTo(d3);
    }

    @Test
    @DisplayName("ToString contient idDiplome")
    void toStringContientId() {
        Diplome d = new Diplome("D8", "KAMGA", "Paul", LocalDate.of(2000, 5, 10),
                "M", "Licence", "2023", "Informatique", "U8");
        assertThat(d.toString()).contains("D8");
    }
}
