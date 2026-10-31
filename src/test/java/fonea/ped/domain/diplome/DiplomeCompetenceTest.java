package fonea.ped.domain.diplome;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiplomeCompetenceTest {

    @Test
    @DisplayName("Creer une liaison DiplomeCompetence")
    void doitCreerLiaison() {
        DiplomeCompetence dc = new DiplomeCompetence("D1", "C1");
        assertThat(dc.getIdDiplome()).isEqualTo("D1");
        assertThat(dc.getIdCompetence()).isEqualTo("C1");
    }

    @Test
    @DisplayName("Equals et hashCode sur cle composite")
    void equalsHashCodeComposite() {
        DiplomeCompetence dc1 = new DiplomeCompetence("D2", "C2");
        DiplomeCompetence dc2 = new DiplomeCompetence("D2", "C2");
        DiplomeCompetence dc3 = new DiplomeCompetence("D3", "C2");
        assertThat(dc1).isEqualTo(dc2);
        assertThat(dc1.hashCode()).isEqualTo(dc2.hashCode());
        assertThat(dc1).isNotEqualTo(dc3);
    }
}
