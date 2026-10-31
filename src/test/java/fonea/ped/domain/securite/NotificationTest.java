package fonea.ped.domain.securite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NotificationTest {

    @Test
    @DisplayName("Le message est obligatoire")
    void messageObligatoire() {
        assertThatThrownBy(() -> new Notification("N1", "INFO", null, "U1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("message");
        assertThatThrownBy(() -> new Notification("N1", "INFO", "   ", "U1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Une notification est non lue et horodatee a la creation")
    void etatInitial() {
        LocalDateTime avant = LocalDateTime.now().minusSeconds(1);
        Notification n = new Notification("N1", "INFO", "Bienvenue", "U1");
        assertThat(n.isLue()).isFalse();
        assertThat(n.getDateEnvoi()).isAfter(avant);
    }

    @Test
    @DisplayName("Marquer une notification comme lue")
    void doitMarquerLue() {
        Notification n = new Notification("N1", "INFO", "Bienvenue", "U1");
        n.marquerLue();
        assertThat(n.isLue()).isTrue();
    }

    @Test
    @DisplayName("Getters et egalite par identifiant")
    void gettersEtEgalite() {
        Notification n = new Notification("N1", "ALERTE", "Stage terminé", "U1");
        assertThat(n.getIdNotification()).isEqualTo("N1");
        assertThat(n.getTypeNotification()).isEqualTo("ALERTE");
        assertThat(n.getMessage()).isEqualTo("Stage terminé");
        assertThat(n.getIdUtilisateur()).isEqualTo("U1");
        assertThat(n).isEqualTo(new Notification("N1", "INFO", "Autre", "U2"));
    }
}
