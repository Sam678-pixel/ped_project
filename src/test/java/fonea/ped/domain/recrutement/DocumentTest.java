package fonea.ped.domain.recrutement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    @DisplayName("Creer un document")
    void doitCreerDocument() {
        Document d = new Document("DOC1", "/uploads/cv.pdf", "D1", "TD1");
        assertThat(d.getIdDocument()).isEqualTo("DOC1");
        assertThat(d.getCheminFichier()).isEqualTo("/uploads/cv.pdf");
        assertThat(d.getIdDiplome()).isEqualTo("D1");
        assertThat(d.getIdTypeDocument()).isEqualTo("TD1");
    }

    @Test
    @DisplayName("Ne pas creer un document sans chemin de fichier")
    void nePasCreerSansChemin() {
        assertThatThrownBy(() -> new Document("DOC2", null, "D1", "TD1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("chemin");
        assertThatThrownBy(() -> new Document("DOC3", "   ", "D1", "TD1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("chemin");
    }

    @Test
    @DisplayName("Equals et hashCode sur idDocument")
    void equalsHashCode() {
        Document d1 = new Document("DOC4", "/a.pdf", "D1", "TD1");
        Document d2 = new Document("DOC4", "/b.pdf", "D2", "TD2");
        Document d3 = new Document("DOC5", "/a.pdf", "D1", "TD1");
        assertThat(d1).isEqualTo(d2);
        assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
        assertThat(d1).isNotEqualTo(d3);
    }
}
