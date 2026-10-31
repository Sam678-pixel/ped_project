package fonea.ped.domain.recrutement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CandidatureTest {

    private Document creerDocument(String id, String typeDocument) {
        return new Document(id, "/uploads/" + id + ".pdf", "D1", typeDocument);
    }

    private Candidature creerCandidature(String id, StatutCandidature statut, List<Document> documents) {
        return new Candidature(id, LocalDate.now(), statut, "D1", "PUB1", documents);
    }

    @Test
    @DisplayName("Creer une candidature avec statut EN_COURS par defaut")
    void doitAvoirStatutEnCoursParDefaut() {
        Candidature c = creerCandidature("CAN1", null, List.of(creerDocument("DOC1", "CV")));
        assertThat(c.getStatutCandidature()).isEqualTo(StatutCandidature.EN_COURS);
    }

    @Test
    @DisplayName("Deposer une candidature")
    void doitDeposerCandidature() {
        Candidature c = new Candidature("CAN2", null, StatutCandidature.EN_COURS, "D1", "PUB1",
                List.of(creerDocument("DOC2", "CV")));
        c.deposer();
        assertThat(c.getDateDepot()).isEqualTo(LocalDate.now());
        assertThat(c.getStatutCandidature()).isEqualTo(StatutCandidature.EN_COURS);
    }

    @Test
    @DisplayName("Changer le statut vers PRESELECTIONNEE")
    void doitPasserAPreselectionnee() {
        Candidature c = creerCandidature("CAN3", StatutCandidature.EN_COURS,
                List.of(creerDocument("DOC3", "CV")));
        c.changerStatut(StatutCandidature.PRESELECTIONNEE);
        assertThat(c.getStatutCandidature()).isEqualTo(StatutCandidature.PRESELECTIONNEE);
    }

    @Test
    @DisplayName("Changer le statut vers SELECTIONNEE")
    void doitPasserASelectionnee() {
        Candidature c = creerCandidature("CAN4", StatutCandidature.PRESELECTIONNEE,
                List.of(creerDocument("DOC4", "CV")));
        c.changerStatut(StatutCandidature.SELECTIONNEE);
        assertThat(c.getStatutCandidature()).isEqualTo(StatutCandidature.SELECTIONNEE);
    }

    @Test
    @DisplayName("Rejeter une candidature")
    void doitRejeter() {
        Candidature c = creerCandidature("CAN5", StatutCandidature.EN_COURS,
                List.of(creerDocument("DOC5", "CV")));
        c.rejeter();
        assertThat(c.getStatutCandidature()).isEqualTo(StatutCandidature.REJETEE);
    }

    @Test
    @DisplayName("Refuser un statut null")
    void nePasChangerStatutNull() {
        Candidature c = creerCandidature("CAN6", StatutCandidature.EN_COURS,
                List.of(creerDocument("DOC6", "CV")));
        assertThatThrownBy(() -> c.changerStatut(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Une candidature sans document n'est pas complete")
    void documentsIncompletsSiVide() {
        Candidature c = creerCandidature("CAN7", StatutCandidature.EN_COURS, Collections.emptyList());
        assertThat(c.sontDocumentsComplets()).isFalse();
    }

    @Test
    @DisplayName("Documents complets avec un CV et une piece d'identite")
    void documentsCompletsAvecCvEtIdentite() {
        Candidature c = creerCandidature("CAN8", StatutCandidature.EN_COURS,
                List.of(creerDocument("DOC7", "CV"), creerDocument("DOC8", "IDENTITE")));
        assertThat(c.sontDocumentsComplets()).isTrue();
    }

    @Test
    @DisplayName("Documents incomplets s'il manque la piece d'identite")
    void documentsIncompletsSansIdentite() {
        Candidature c = creerCandidature("CAN9", StatutCandidature.EN_COURS,
                List.of(creerDocument("DOC9", "CV")));
        assertThat(c.sontDocumentsComplets()).isFalse();
    }

    @Test
    @DisplayName("Ajouter un document")
    void doitAjouterDocument() {
        Candidature c = creerCandidature("CAN10", StatutCandidature.EN_COURS, new ArrayList<>());
        Document doc = creerDocument("DOC10", "CV");
        c.ajouterDocument(doc);
        assertThat(c.getDocuments()).hasSize(1).first().isEqualTo(doc);
    }

    @Test
    @DisplayName("Ne pas ajouter deux fois le meme document")
    void nePasAjouterDocumentEnDouble() {
        Candidature c = creerCandidature("CAN11", StatutCandidature.EN_COURS, new ArrayList<>());
        Document doc = creerDocument("DOC11", "CV");
        c.ajouterDocument(doc);
        c.ajouterDocument(creerDocument("DOC11", "CV"));
        assertThat(c.getDocuments()).hasSize(1);
    }

    @Test
    @DisplayName("Refuser un document null")
    void refuserDocumentNull() {
        Candidature c = creerCandidature("CAN12", StatutCandidature.EN_COURS, new ArrayList<>());
        assertThatThrownBy(() -> c.ajouterDocument(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Supprimer un document par son identifiant")
    void doitSupprimerDocument() {
        Candidature c = creerCandidature("CAN13", StatutCandidature.EN_COURS, new ArrayList<>());
        c.ajouterDocument(creerDocument("DOC12", "CV"));
        c.ajouterDocument(creerDocument("DOC13", "IDENTITE"));
        c.supprimerDocument("DOC12");
        assertThat(c.getDocuments()).hasSize(1);
        assertThat(c.getDocuments().get(0).getIdDocument()).isEqualTo("DOC13");
    }

    @Test
    @DisplayName("Equals et hashCode sur idCandidature")
    void equalsHashCode() {
        List<Document> docs = List.of(creerDocument("DOC14", "CV"));
        Candidature c1 = new Candidature("CAN14", LocalDate.now(), StatutCandidature.EN_COURS, "D1", "PUB1", docs);
        Candidature c2 = new Candidature("CAN14", LocalDate.now(), StatutCandidature.PRESELECTIONNEE, "D2", "PUB2", docs);
        Candidature c3 = new Candidature("CAN15", LocalDate.now(), StatutCandidature.EN_COURS, "D1", "PUB1", docs);
        assertThat(c1).isEqualTo(c2);
        assertThat(c1.hashCode()).isEqualTo(c2.hashCode());
        assertThat(c1).isNotEqualTo(c3);
    }
}
