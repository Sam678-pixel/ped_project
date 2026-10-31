package fonea.ped.domain.Offres;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OffresStagesTest {

    private OffresStages creerOffre(Integer places) {
        return new OffresStages("O1", "Stage dev", "Description", "Kigali", 6,
                StatutOffre.BROUILLON, places, "P1", "D1", "C1");
    }

    @Test
    @DisplayName("Statut BROUILLON et 0 place par defaut si null")
    void doitAvoirValeursParDefaut() {
        OffresStages o = new OffresStages("O2", "Stage", "Desc", "Kigali", 3,
                null, null, "P1", "D1", "C1");
        assertThat(o.getStatutOffre()).isEqualTo(StatutOffre.BROUILLON);
        assertThat(o.getNbrePlaces()).isZero();
    }

    @Test
    @DisplayName("Publier une offre non cloturee")
    void doitPublierOffre() {
        OffresStages o = creerOffre(2);
        o.publier();
        assertThat(o.getStatutOffre()).isEqualTo(StatutOffre.PUBLIEE);
    }

    @Test
    @DisplayName("Ne pas publier une offre cloturee")
    void neDoitPasPublierOffreCloturee() {
        OffresStages o = creerOffre(2);
        o.cloturer();
        assertThatThrownBy(o::publier)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("clôturée");
    }

    @Test
    @DisplayName("Cloturer une offre")
    void doitCloturerOffre() {
        OffresStages o = creerOffre(2);
        o.publier();
        o.cloturer();
        assertThat(o.getStatutOffre()).isEqualTo(StatutOffre.CLOTUREE);
    }

    @Test
    @DisplayName("Changer de statut avec une valeur non nulle")
    void doitChangerStatut() {
        OffresStages o = creerOffre(1);
        o.changerStatut(StatutOffre.ANNULEE);
        assertThat(o.getStatutOffre()).isEqualTo(StatutOffre.ANNULEE);
    }

    @Test
    @DisplayName("Refuser un statut null")
    void neDoitPasAccepterStatutNull() {
        OffresStages o = creerOffre(1);
        assertThatThrownBy(() -> o.changerStatut(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Disponibilite et completion selon le nombre de places")
    void doitGererLesPlaces() {
        OffresStages o = creerOffre(2);
        assertThat(o.verifierDisponibilite()).isTrue();
        assertThat(o.estComplete()).isFalse();

        o.decrementerPlaces();
        assertThat(o.getNbrePlaces()).isEqualTo(1);

        o.decrementerPlaces();
        assertThat(o.verifierDisponibilite()).isFalse();
        assertThat(o.estComplete()).isTrue();
    }

    @Test
    @DisplayName("Decrementer sans place disponible leve une exception")
    void neDoitPasDecrementerSansPlace() {
        OffresStages o = creerOffre(0);
        assertThatThrownBy(o::decrementerPlaces)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("place");
    }

    @Test
    @DisplayName("Getters retournent les valeurs du constructeur")
    void gettersDoiventFonctionner() {
        OffresStages o = creerOffre(4);
        assertThat(o.getIdOffre()).isEqualTo("O1");
        assertThat(o.getIntitule()).isEqualTo("Stage dev");
        assertThat(o.getDescription()).isEqualTo("Description");
        assertThat(o.getVilleStage()).isEqualTo("Kigali");
        assertThat(o.getDureeMois()).isEqualTo(6);
        assertThat(o.getNbrePlaces()).isEqualTo(4);
        assertThat(o.getIdPoste()).isEqualTo("P1");
        assertThat(o.getIdDomaine()).isEqualTo("D1");
        assertThat(o.getIdConvention()).isEqualTo("C1");
    }

    @Test
    @DisplayName("Egalite basee sur l'identifiant de l'offre")
    void egaliteBaseeSurId() {
        OffresStages a = creerOffre(1);
        OffresStages b = new OffresStages("O1", "Autre", "Autre", "Autre", 1,
                StatutOffre.PUBLIEE, 1, "P9", "D9", "C9");
        OffresStages c = new OffresStages("O2", "Stage dev", "Description", "Kigali", 6,
                StatutOffre.BROUILLON, 1, "P1", "D1", "C1");
        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
        assertThat(a).isNotEqualTo(c);
    }
}
