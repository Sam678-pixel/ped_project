package fonea.ped.domain.recrutement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PreselectionTest {

    private Preselection creerPreselection(StatutCandidature statut) {
        return new Preselection("PRE1", LocalDate.of(2026, 10, 1), statut, "AG1", "CAND1");
    }

    @Test
    @DisplayName("Statut EN_COURS par defaut si null")
    void statutParDefaut() {
        assertThat(creerPreselection(null).getStatutPreselection())
                .isEqualTo(StatutCandidature.EN_COURS);
        assertThat(creerPreselection(null).getStatut())
                .isEqualTo(StatutCandidature.EN_COURS);
    }

    @Test
    @DisplayName("Changer le statut de la preselection")
    void doitChangerStatut() {
        Preselection p = creerPreselection(null);
        p.changerStatut(StatutCandidature.PRESELECTIONNEE);
        assertThat(p.getStatutPreselection()).isEqualTo(StatutCandidature.PRESELECTIONNEE);
        assertThat(p.getStatut()).isEqualTo(StatutCandidature.PRESELECTIONNEE);
    }

    @Test
    @DisplayName("Refuser un statut null")
    void refuserStatutNull() {
        Preselection p = creerPreselection(null);
        assertThatThrownBy(() -> p.changerStatut(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Getters de la preselection")
    void getters() {
        Preselection p = creerPreselection(StatutCandidature.PRESELECTIONNEE);
        assertThat(p.getIdPreselection()).isEqualTo("PRE1");
        assertThat(p.getDatePreselection()).isEqualTo(LocalDate.of(2026, 10, 1));
        assertThat(p.getIdAgent()).isEqualTo("AG1");
        assertThat(p.getIdCandidature()).isEqualTo("CAND1");
    }

    @Test
    @DisplayName("Egalite basee sur l'identifiant")
    void egaliteParId() {
        assertThat(creerPreselection(null))
                .isEqualTo(new Preselection("PRE1", LocalDate.now(), StatutCandidature.REJETEE, "X", "Y"));
    }
}
