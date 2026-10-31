package fonea.ped.domain.entreprise;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntrepriseSecteurTest {

    @Test
    @DisplayName("Creer une liaison EntrepriseSecteur")
    void doitCreerLiaison() {
        EntrepriseSecteur es = new EntrepriseSecteur("E1", "S1");
        assertThat(es.getIdEntreprise()).isEqualTo("E1");
        assertThat(es.getIdSecteur()).isEqualTo("S1");
    }

    @Test
    @DisplayName("Equals et hashCode sur cle composite")
    void equalsHashCodeComposite() {
        EntrepriseSecteur es1 = new EntrepriseSecteur("E2", "S2");
        EntrepriseSecteur es2 = new EntrepriseSecteur("E2", "S2");
        EntrepriseSecteur es3 = new EntrepriseSecteur("E3", "S2");
        assertThat(es1).isEqualTo(es2);
        assertThat(es1.hashCode()).isEqualTo(es2.hashCode());
        assertThat(es1).isNotEqualTo(es3);
    }
}
