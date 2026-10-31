package fonea.ped.domain.securite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AgentFoneaTest {

    @Test
    @DisplayName("Le nom de l'agent est obligatoire")
    void nomObligatoire() {
        assertThatThrownBy(() -> new AgentFonea("A1", null, "Jean", "Chargé", "U1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nom");
        assertThatThrownBy(() -> new AgentFonea("A1", "   ", "Jean", "Chargé", "U1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Getters de l'agent")
    void getters() {
        AgentFonea a = new AgentFonea("A1", "Mugisha", "Jean", "Chargé de suivi", "U1");
        assertThat(a.getIdAgent()).isEqualTo("A1");
        assertThat(a.getNom()).isEqualTo("Mugisha");
        assertThat(a.getPrenom()).isEqualTo("Jean");
        assertThat(a.getFonction()).isEqualTo("Chargé de suivi");
        assertThat(a.getIdUtilisateur()).isEqualTo("U1");
    }

    @Test
    @DisplayName("Egalite basee sur l'identifiant")
    void egaliteParId() {
        assertThat(new AgentFonea("A1", "Mugisha", "Jean", "X", "U1"))
                .isEqualTo(new AgentFonea("A1", "Autre", "Autre", "Y", "U2"));
        assertThat(new AgentFonea("A1", "Mugisha", "Jean", "X", "U1"))
                .isNotEqualTo(new AgentFonea("A2", "Mugisha", "Jean", "X", "U1"));
    }
}
