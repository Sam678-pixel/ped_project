package fonea.ped.domain.recrutement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SelectionTest {

    private Selection creerSelection(StatutCandidature statut) {
        return new Selection("SEL1", LocalDate.of(2026, 10, 15), statut, "PRE1");
    }

    @Test
    @DisplayName("Statut EN_COURS par defaut si null")
    void statutParDefaut() {
        assertThat(creerSelection(null).getStatutSelection())
                .isEqualTo(StatutCandidature.EN_COURS);
    }

    @Test
    @DisplayName("estValidee vrai uniquement pour SELECTIONNEE")
    void estValideeSelonStatut() {
        assertThat(creerSelection(StatutCandidature.SELECTIONNEE).estValidee()).isTrue();
        assertThat(creerSelection(StatutCandidature.EN_COURS).estValidee()).isFalse();
        assertThat(creerSelection(StatutCandidature.REJETEE).estValidee()).isFalse();
    }

    @Test
    @DisplayName("Changer le statut de la selection")
    void doitChangerStatut() {
        Selection s = creerSelection(null);
        s.changerStatut(StatutCandidature.SELECTIONNEE);
        assertThat(s.getStatutSelection()).isEqualTo(StatutCandidature.SELECTIONNEE);
        assertThat(s.estValidee()).isTrue();
    }

    @Test
    @DisplayName("Refuser un statut null")
    void refuserStatutNull() {
        Selection s = creerSelection(null);
        assertThatThrownBy(() -> s.changerStatut(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Confirmer une selection rejetee leve une exception")
    void refuserConfirmationSelectionRejetee() {
        Selection s = creerSelection(StatutCandidature.REJETEE);
        assertThatThrownBy(s::confirmer)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("Confirmer une selection en cours la valide")
    void confirmerDoitValiderSelection() {
        Selection s = creerSelection(StatutCandidature.EN_COURS);
        s.confirmer();
        assertThat(s.estValidee()).isTrue();
    }

    @Test
    @DisplayName("Getters et egalite par identifiant")
    void gettersEtEgalite() {
        Selection s = creerSelection(StatutCandidature.EN_COURS);
        assertThat(s.getIdSelection()).isEqualTo("SEL1");
        assertThat(s.getDateSelection()).isEqualTo(LocalDate.of(2026, 10, 15));
        assertThat(s.getIdPreselection()).isEqualTo("PRE1");
        assertThat(s).isEqualTo(new Selection("SEL1", LocalDate.now(), StatutCandidature.REJETEE, "AUTRE"));
    }
}
