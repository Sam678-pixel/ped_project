package fonea.ped.domain.Offres;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SecteurTest {

    @Test
    @DisplayName("Le libelle du secteur est obligatoire")
    void libelleObligatoire() {
        assertThatThrownBy(() -> new Secteur("S1", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("secteur");
        assertThatThrownBy(() -> new Secteur("S1", "   "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Renommer un secteur")
    void doitRenommer() {
        Secteur s = new Secteur("S1", "Informatique");
        s.renommer("Numérique");
        assertThat(s.getLibelle()).isEqualTo("Numérique");
        assertThat(s.getIdSecteur()).isEqualTo("S1");
    }

    @Test
    @DisplayName("Interdire un renommage vide")
    void refuserRenommageVide() {
        Secteur s = new Secteur("S1", "Informatique");
        assertThatThrownBy(() -> s.renommer(" "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Egalite basee sur l'identifiant")
    void egaliteParId() {
        Secteur a = new Secteur("S1", "Informatique");
        Secteur b = new Secteur("S1", "Autre");
        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
        assertThat(a).isNotEqualTo(new Secteur("S2", "Informatique"));
    }
}
