package fonea.ped.domain.convention.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConventionTest {

    private Convention creerConventionActive() {
        Convention c = new Convention("C1", LocalDate.now(), 1000.0, 2000.0,
                StatutConvention.BROUILLON, null, "E1");
        c.activer();
        return c;
    }

    @Test
    @DisplayName("Creer une convention avec statut BROUILLON par defaut si null")
    void doitAvoirStatutBrouillonSiNull() {
        Convention c = new Convention("C2", LocalDate.now(), 500.0, 1000.0,
                null, null, "E2");
        assertThat(c.getStatutConvention()).isEqualTo(StatutConvention.BROUILLON);
    }

    @Test
    @DisplayName("Activer une convention non resiliee")
    void doitActiverConvention() {
        Convention c = new Convention("C3", LocalDate.now(), 500.0, 1000.0,
                StatutConvention.BROUILLON, null, "E3");
        c.activer();
        assertThat(c.getStatutConvention()).isEqualTo(StatutConvention.ACTIVE);
    }

    @Test
    @DisplayName("Ne pas activer une convention resiliee")
    void neDoitPasActiverConventionResiliee() {
        Convention c = new Convention("C4", LocalDate.now(), 500.0, 1000.0,
                StatutConvention.BROUILLON, null, "E4");
        c.resilier("R1", "Fin de partenariat", "AGENT1");
        assertThatThrownBy(c::activer)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("résiliée");
    }

    @Test
    @DisplayName("Suspendre uniquement une convention active")
    void doitSuspendreConventionActive() {
        Convention c = creerConventionActive();
        c.suspendre();
        assertThat(c.getStatutConvention()).isEqualTo(StatutConvention.SUSPENDUE);
    }

    @Test
    @DisplayName("Ne pas suspendre une convention non active")
    void neDoitPasSuspendreConventionNonActive() {
        Convention c = new Convention("C5", LocalDate.now(), 500.0, 1000.0,
                StatutConvention.BROUILLON, null, "E5");
        assertThatThrownBy(c::suspendre)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("active");
    }

    @Test
    @DisplayName("Changer de statut avec valeur non nulle")
    void doitChangerStatut() {
        Convention c = new Convention("C6", LocalDate.now(), 500.0, 1000.0,
                StatutConvention.BROUILLON, null, "E6");
        c.changerStatut(StatutConvention.SUSPENDUE);
        assertThat(c.getStatutConvention()).isEqualTo(StatutConvention.SUSPENDUE);
    }

    @Test
    @DisplayName("Ne pas changer de statut avec null")
    void neDoitPasChangerStatutNull() {
        Convention c = new Convention("C7", LocalDate.now(), 500.0, 1000.0,
                StatutConvention.BROUILLON, null, "E7");
        assertThatThrownBy(() -> c.changerStatut(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ne peut pas être null");
    }

    @Test
    @DisplayName("estActive renvoie true uniquement pour ACTIVE")
    void estActiveDoitEtreCorrect() {
        Convention c1 = creerConventionActive();
        assertThat(c1.estActive()).isTrue();

        Convention c2 = new Convention("C8", LocalDate.now(), 500.0, 1000.0,
                StatutConvention.SUSPENDUE, null, "E8");
        assertThat(c2.estActive()).isFalse();
    }

    @Test
    @DisplayName("Resilier une convention active doit creer une resiliation")
    void resilierConventionActive() {
        Convention c = creerConventionActive();
        Resiliation r = c.resilier("R2", "Rupture", "AGENT2");
        assertThat(r).isNotNull();
        assertThat(r.getIdResiliation()).isEqualTo("R2");
        assertThat(r.getInitiateur()).isEqualTo("AGENT2");
        assertThat(r.getIdConvention()).isEqualTo("C1");
        assertThat(c.getStatutConvention()).isEqualTo(StatutConvention.RESILIEE);
    }

    @Test
    @DisplayName("Ne pas resilier une convention deja resiliee")
    void nePasResilierDejaResiliee() {
        Convention c = creerConventionActive();
        c.resilier("R3", "Motif", "AGENT3");
        assertThatThrownBy(() -> c.resilier("R4", "Motif2", "AGENT4"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("déjà résiliée");
    }

    @Test
    @DisplayName("Ne pas resilier si motif est vide ou null")
    void nePasResilierSansMotif() {
        Convention c = creerConventionActive();
        assertThatThrownBy(() -> c.resilier("R5", null, "AGENT5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("motif");
        assertThatThrownBy(() -> c.resilier("R6", "   ", "AGENT6"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("motif");
    }

    @Test
    @DisplayName("Ne pas resilier si initiateur est vide ou null")
    void nePasResilierSansInitiateur() {
        Convention c = creerConventionActive();
        assertThatThrownBy(() -> c.resilier("R7", "Motif", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("initiateur");
        assertThatThrownBy(() -> c.resilier("R8", "Motif", "   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("initiateur");
    }

    @Test
    @DisplayName("Getters doivent retourner les valeurs")
    void gettersDoiventFonctionner() {
        LocalDate date = LocalDate.of(2026, 10, 23);
        Convention c = new Convention("C9", date, 1500.0, 2500.0,
                StatutConvention.BROUILLON, null, "E9");
        assertThat(c.getIdConvention()).isEqualTo("C9");
        assertThat(c.getDateEffet()).isEqualTo(date);
        assertThat(c.getTauxFonea()).isEqualTo(1500.0);
        assertThat(c.getTauxEntreprise()).isEqualTo(2500.0);
        assertThat(c.getStatutConvention()).isEqualTo(StatutConvention.BROUILLON);
        assertThat(c.getIdEntreprise()).isEqualTo("E9");
    }
}
