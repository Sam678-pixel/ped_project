package fonea.ped.domain.Offres;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CritereTest {

    @Test
    @DisplayName("Definir une tranche d'age valide")
    void definirTrancheValide() {
        Critere c = new Critere("CR1", null, null, "Licence");
        c.definirTrancheAge(20, 30);
        assertThat(c.getAgeMin()).isEqualTo(20);
        assertThat(c.getAgeMax()).isEqualTo(30);
    }

    @Test
    @DisplayName("Refuser des ages negatifs")
    void refuserAgesNegatifs() {
        Critere c = new Critere("CR1", null, null, null);
        assertThatThrownBy(() -> c.definirTrancheAge(-1, 30))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("négatifs");
    }

    @Test
    @DisplayName("Refuser un age minimum superieur au maximum")
    void refuserAgeMinSuperieur() {
        Critere c = new Critere("CR1", null, null, null);
        assertThatThrownBy(() -> c.definirTrancheAge(35, 20))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("supérieur");
    }

    @Test
    @DisplayName("Un age minimum egal au maximum est accepte")
    void accepterBorneEgale() {
        Critere c = new Critere("CR1", null, null, null);
        c.definirTrancheAge(25, 25);
        assertThat(c.getAgeMin()).isEqualTo(25);
        assertThat(c.getAgeMax()).isEqualTo(25);
    }

    @Test
    @DisplayName("Getters et egalite par identifiant")
    void gettersEtEgalite() {
        Critere c = new Critere("CR1", 18, 40, "Master");
        assertThat(c.getIdCritere()).isEqualTo("CR1");
        assertThat(c.getAgeMin()).isEqualTo(18);
        assertThat(c.getAgeMax()).isEqualTo(40);
        assertThat(c.getNiveauDiplome()).isEqualTo("Master");
        assertThat(c).isEqualTo(new Critere("CR1", null, null, null));
    }
}
