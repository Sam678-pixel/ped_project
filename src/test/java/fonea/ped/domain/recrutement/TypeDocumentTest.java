package fonea.ped.domain.recrutement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TypeDocumentTest {

    @Test
    @DisplayName("Le libelle est obligatoire")
    void libelleObligatoire() {
        assertThatThrownBy(() -> new TypeDocument("TD1", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Renommer un type de document")
    void doitRenommer() {
        TypeDocument t = new TypeDocument("TD1", "CV");
        t.renommer("Curriculum Vitae");
        assertThat(t.getLibelle()).isEqualTo("Curriculum Vitae");
        assertThat(t.getIdTypeDocument()).isEqualTo("TD1");
    }

    @Test
    @DisplayName("Interdire un renommage vide")
    void refuserRenommageVide() {
        TypeDocument t = new TypeDocument("TD1", "CV");
        assertThatThrownBy(() -> t.renommer(" "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Egalite basee sur l'identifiant")
    void egaliteParId() {
        assertThat(new TypeDocument("TD1", "CV")).isEqualTo(new TypeDocument("TD1", "Autre"));
    }
}
