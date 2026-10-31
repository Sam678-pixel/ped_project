package fonea.ped.domain.entreprise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntrepriseTest {

    @Test
    @DisplayName("Creer une entreprise avec raison sociale obligatoire")
    void doitCreerEntreprise() {
        Entreprise e = new Entreprise("E1", "FOUNDRY SARL", "Douala", "NIU123", "U1");
        assertThat(e.getIdEntreprise()).isEqualTo("E1");
        assertThat(e.getRaisonSociale()).isEqualTo("FOUNDRY SARL");
        assertThat(e.getAdresse()).isEqualTo("Douala");
        assertThat(e.getNiu()).isEqualTo("NIU123");
        assertThat(e.getIdUtilisateur()).isEqualTo("U1");
    }

    @Test
    @DisplayName("Ne pas creer une entreprise sans raison sociale")
    void nePasCreerSansRaisonSociale() {
        assertThatThrownBy(() -> new Entreprise("E2", null, "Yaounde", "NIU456", "U2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("raison sociale");
        assertThatThrownBy(() -> new Entreprise("E3", "   ", "Yaounde", "NIU456", "U3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("raison sociale");
    }

    @Test
    @DisplayName("Mettre a jour les coordonnees")
    void doitMettreAJourCoordonnees() {
        Entreprise e = new Entreprise("E4", "TEST SARL", null, null, "U4");
        e.mettreAJourCoordonnees("Bafoussam", "NIU789");
        assertThat(e.getAdresse()).isEqualTo("Bafoussam");
        assertThat(e.getNiu()).isEqualTo("NIU789");
    }

    @Test
    @DisplayName("Equals et hashCode sur idEntreprise")
    void equalsHashCode() {
        Entreprise e1 = new Entreprise("E5", "A", "X", "N1", "U5");
        Entreprise e2 = new Entreprise("E5", "B", "Y", "N2", "U6");
        Entreprise e3 = new Entreprise("E6", "A", "X", "N1", "U5");
        assertThat(e1).isEqualTo(e2);
        assertThat(e1.hashCode()).isEqualTo(e2.hashCode());
        assertThat(e1).isNotEqualTo(e3);
    }
}
