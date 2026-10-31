package fonea.ped.domain.Offres;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PublicationTest {

    private Publication creerPublication(LocalDate expiration) {
        return new Publication("PUB1", LocalDate.now().minusDays(2), expiration, "AG1", "O1");
    }

    @Test
    @DisplayName("Une publication sans date d'expiration n'est pas active")
    void pasActiveSansDateExpiration() {
        assertThat(creerPublication(null).estActive()).isFalse();
    }

    @Test
    @DisplayName("Une publication dont l'expiration est future est active")
    void activeSiExpirationFuture() {
        assertThat(creerPublication(LocalDate.now().plusDays(5)).estActive()).isTrue();
    }

    @Test
    @DisplayName("Une publication dont l'expiration est passee n'est pas active")
    void inactiveSiExpirationPassee() {
        assertThat(creerPublication(LocalDate.now().minusDays(1)).estActive()).isFalse();
    }

    @Test
    @DisplayName("Expirer fixe la date d'expiration a aujourd'hui et desactive la publication")
    void expirerDesactivePublication() {
        Publication p = creerPublication(LocalDate.now().plusDays(10));
        p.expirer();
        assertThat(p.getDateExpiration()).isEqualTo(LocalDate.now());
        assertThat(p.estActive()).isFalse();
    }

    @Test
    @DisplayName("Prolonger avec une date posterieure")
    void doitProlonger() {
        Publication p = creerPublication(LocalDate.now().plusDays(5));
        LocalDate nouvelle = LocalDate.now().plusDays(20);
        p.prolonger(nouvelle);
        assertThat(p.getDateExpiration()).isEqualTo(nouvelle);
    }

    @Test
    @DisplayName("Refuser une prolongation vers une date anterieure")
    void refuserProlongationAnterieure() {
        Publication p = creerPublication(LocalDate.now().plusDays(5));
        assertThatThrownBy(() -> p.prolonger(LocalDate.now().plusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("postérieure");
    }

    @Test
    @DisplayName("Refuser une prolongation avec une date null")
    void refuserProlongationNull() {
        Publication p = creerPublication(LocalDate.now());
        assertThatThrownBy(() -> p.prolonger(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Getters et egalite par identifiant")
    void gettersEtEgalite() {
        Publication p = creerPublication(LocalDate.of(2027, 1, 1));
        assertThat(p.getIdPublication()).isEqualTo("PUB1");
        assertThat(p.getDatePublication()).isEqualTo(LocalDate.now().minusDays(2));
        assertThat(p.getDateExpiration()).isEqualTo(LocalDate.of(2027, 1, 1));
        assertThat(p.getIdAgent()).isEqualTo("AG1");
        assertThat(p.getIdOffre()).isEqualTo("O1");
        assertThat(p).isEqualTo(new Publication("PUB1", LocalDate.now(), null, "AG9", "O9"));
    }
}
