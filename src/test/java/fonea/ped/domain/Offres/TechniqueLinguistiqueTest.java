package fonea.ped.domain.Offres;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TechniqueLinguistiqueTest {

    @Test
    @DisplayName("Le libelle est obligatoire a la creation")
    void libelleObligatoire() {
        assertThatThrownBy(() -> new TechniqueLinguistique("T1", null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new TechniqueLinguistique("T1", "  "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Renommer une technique/linguistique")
    void doitRenommer() {
        TechniqueLinguistique t = new TechniqueLinguistique("T1", "Anglais");
        t.renommer("Français");
        assertThat(t.getLibelle()).isEqualTo("Français");
        assertThat(t.getIdTechniqueLinguistique()).isEqualTo("T1");
    }

    @Test
    @DisplayName("Interdire un renommage vide")
    void refuserRenommageVide() {
        TechniqueLinguistique t = new TechniqueLinguistique("T1", "Anglais");
        assertThatThrownBy(() -> t.renommer(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Egalite basee sur l'identifiant")
    void egaliteParId() {
        assertThat(new TechniqueLinguistique("T1", "Anglais"))
                .isEqualTo(new TechniqueLinguistique("T1", "Espagnol"));
    }
}
