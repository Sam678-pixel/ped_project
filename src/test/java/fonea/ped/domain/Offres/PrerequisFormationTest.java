package fonea.ped.domain.Offres;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PrerequisFormationTest {

    @Test
    @DisplayName("Getters du prerequis de formation")
    void getters() {
        TechniqueLinguistique tl = new TechniqueLinguistique("T1", "Anglais");
        PrerequisFormation p = new PrerequisFormation("PR1", "Bac+3", tl, "Niveau requis");
        assertThat(p.getIdPrerequis()).isEqualTo("PR1");
        assertThat(p.getLibelleFormation()).isEqualTo("Bac+3");
        assertThat(p.getTechniqueLinguistique()).isSameAs(tl);
        assertThat(p.getLibelle()).isEqualTo("Niveau requis");
    }

    @Test
    @DisplayName("Renommer le libelle de formation")
    void doitRenommer() {
        PrerequisFormation p = new PrerequisFormation("PR1", "Bac+3", null, "Niveau");
        p.renommer("Bac+5");
        assertThat(p.getLibelleFormation()).isEqualTo("Bac+5");
    }

    @Test
    @DisplayName("Interdire un libelle de formation vide")
    void refuserLibelleVide() {
        PrerequisFormation p = new PrerequisFormation("PR1", "Bac+3", null, "Niveau");
        assertThatThrownBy(() -> p.renommer("   "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Egalite basee sur l'identifiant")
    void egaliteParId() {
        assertThat(new PrerequisFormation("PR1", "Bac+3", null, "A"))
                .isEqualTo(new PrerequisFormation("PR1", "Bac+5", null, "B"));
    }
}
