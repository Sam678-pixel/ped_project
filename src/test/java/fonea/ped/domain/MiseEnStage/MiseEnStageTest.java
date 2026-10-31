package fonea.ped.domain.MiseEnStage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MiseEnStageTest {

    @Test
    @DisplayName("Creer une mise en stage avec statut EN_ATTENTE par defaut")
    void doitAvoirStatutEnAttenteParDefaut() {
        MiseEnStage ms = new MiseEnStage("MS1", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 12, 1),
                null, "S1", "E1");
        assertThat(ms.getStatutStage()).isEqualTo(StatutStage.EN_ATTENTE);
    }

    @Test
    @DisplayName("Demarrer un stage en attente")
    void doitDemarrerStage() {
        MiseEnStage ms = new MiseEnStage("MS2", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 12, 1),
                StatutStage.EN_ATTENTE, "S2", "E2");
        ms.demarrer();
        assertThat(ms.getStatutStage()).isEqualTo(StatutStage.EN_COURS);
    }

    @Test
    @DisplayName("Ne pas demarrer un stage termine/annule/decroche")
    void nePasDemarrerStageTermine() {
        MiseEnStage ms = new MiseEnStage("MS3", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 12, 1),
                StatutStage.TERMINE, "S3", "E3");
        assertThatThrownBy(ms::demarrer).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("Terminer un stage en cours")
    void doitTerminerStage() {
        MiseEnStage ms = new MiseEnStage("MS4", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 12, 1),
                StatutStage.EN_COURS, "S4", "E4");
        ms.terminer();
        assertThat(ms.getStatutStage()).isEqualTo(StatutStage.TERMINE);
    }

    @Test
    @DisplayName("Ne pas terminer un stage qui n'est pas en cours")
    void nePasTerminerStageNonEnCours() {
        MiseEnStage ms = new MiseEnStage("MS5", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 12, 1),
                StatutStage.EN_ATTENTE, "S5", "E5");
        assertThatThrownBy(ms::terminer).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("Changer le statut d'un stage")
    void doitChangerStatut() {
        MiseEnStage ms1 = new MiseEnStage("MS6", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 12, 1),
                StatutStage.EN_ATTENTE, "S6", "E6");
        ms1.changerStatut(StatutStage.ANNULE);
        assertThat(ms1.getStatutStage()).isEqualTo(StatutStage.ANNULE);

        MiseEnStage ms2 = new MiseEnStage("MS7", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 12, 1),
                StatutStage.EN_COURS, "S7", "E7");
        ms2.changerStatut(StatutStage.DECROCHE);
        assertThat(ms2.getStatutStage()).isEqualTo(StatutStage.DECROCHE);
    }

    @Test
    @DisplayName("Refuser un statut null")
    void nePasChangerStatutNull() {
        MiseEnStage ms = new MiseEnStage("MS8", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 12, 1),
                StatutStage.EN_ATTENTE, "S8", "E8");
        assertThatThrownBy(() -> ms.changerStatut(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("estEnCours vrai uniquement pour EN_COURS")
    void estEnCoursSelonStatut() {
        MiseEnStage enCours = new MiseEnStage("MS9", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 12, 1),
                StatutStage.EN_COURS, "S9", "E9");
        MiseEnStage enAttente = new MiseEnStage("MS10", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 12, 1),
                StatutStage.EN_ATTENTE, "S10", "E10");
        assertThat(enCours.estEnCours()).isTrue();
        assertThat(enAttente.estEnCours()).isFalse();
    }

    @Test
    @DisplayName("Calculer la duree restante en jours")
    void doitCalculerDureeRestante() {
        MiseEnStage ms = new MiseEnStage("MS11", LocalDate.now().minusDays(5), LocalDate.now().plusDays(10),
                StatutStage.EN_COURS, "S11", "E11");
        assertThat(ms.calculerDureeRestante()).isEqualTo(10);
    }

    @Test
    @DisplayName("Une duree restante nulle si la date de fin est depassee")
    void dureeRestanteNulleSiDepassee() {
        MiseEnStage ms = new MiseEnStage("MS12", LocalDate.now().minusDays(30), LocalDate.now().minusDays(1),
                StatutStage.EN_COURS, "S12", "E12");
        assertThat(ms.calculerDureeRestante()).isZero();
    }

    @Test
    @DisplayName("Impossible de calculer la duree restante sans date de fin")
    void dureeRestanteSansDateFin() {
        MiseEnStage ms = new MiseEnStage("MS13", LocalDate.now(), null,
                StatutStage.EN_COURS, "S13", "E13");
        assertThatThrownBy(ms::calculerDureeRestante)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("Equals et hashCode sur idMiseStage")
    void equalsHashCode() {
        MiseEnStage ms1 = new MiseEnStage("MS14", LocalDate.now(), LocalDate.now().plusMonths(3),
                StatutStage.EN_ATTENTE, "S14", "E14");
        MiseEnStage ms2 = new MiseEnStage("MS14", LocalDate.now(), LocalDate.now().plusMonths(3),
                StatutStage.EN_COURS, "S15", "E15");
        MiseEnStage ms3 = new MiseEnStage("MS15", LocalDate.now(), LocalDate.now().plusMonths(3),
                StatutStage.EN_ATTENTE, "S14", "E14");
        assertThat(ms1).isEqualTo(ms2);
        assertThat(ms1.hashCode()).isEqualTo(ms2.hashCode());
        assertThat(ms1).isNotEqualTo(ms3);
    }

    @Test
    @DisplayName("Terminer un stage renouvele")
    void doitTerminerStageRenouvele() {
        MiseEnStage ms = new MiseEnStage("MS16", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 12, 1),
                StatutStage.RENOUVELE, "S16", "E16");
        ms.terminer();
        assertThat(ms.getStatutStage()).isEqualTo(StatutStage.TERMINE);
        assertThat(ms.getDateFin()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("Ne pas demarrer un stage deja en cours ou renouvele")
    void nePasDemarrerUnStageActif() {
        MiseEnStage enCours = new MiseEnStage("MS17", LocalDate.of(2026, 9, 1),
                LocalDate.of(2026, 12, 1), StatutStage.EN_COURS, "S17", "E17");
        assertThatThrownBy(enCours::demarrer).isInstanceOf(IllegalStateException.class);

        MiseEnStage renouvele = new MiseEnStage("MS18", LocalDate.of(2026, 9, 1),
                LocalDate.of(2026, 12, 1), StatutStage.RENOUVELE, "S18", "E18");
        assertThatThrownBy(renouvele::demarrer).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("Demarrer conserve la date de debut planifiee")
    void demarrerConserveLaDateDeDebutPlanifiee() {
        LocalDate debutPlanifie = LocalDate.of(2026, 9, 15);
        MiseEnStage ms = new MiseEnStage("MS19", debutPlanifie, LocalDate.of(2026, 12, 15),
                StatutStage.EN_ATTENTE, "S19", "E19");
        ms.demarrer();
        assertThat(ms.getDateDebut()).isEqualTo(debutPlanifie);
        assertThat(ms.getStatutStage()).isEqualTo(StatutStage.EN_COURS);
    }
}
