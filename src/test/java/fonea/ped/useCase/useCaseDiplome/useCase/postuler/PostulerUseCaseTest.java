package fonea.ped.useCase.useCaseDiplome.useCase.postuler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fonea.ped.domain.Offres.OffresStages;
import fonea.ped.domain.Offres.Publication;
import fonea.ped.domain.Offres.StatutOffre;
import fonea.ped.domain.exceptions.CandidatureDejaExistanteException;
import fonea.ped.domain.exceptions.DocumentsIncompletsException;
import fonea.ped.domain.exceptions.OffreNonPublieeException;
import fonea.ped.domain.recrutement.Candidature;
import fonea.ped.domain.recrutement.Document;
import fonea.ped.domain.recrutement.StatutCandidature;
import fonea.ped.useCase.useCaseDiplome.port.DiplomeRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffreRepository;
import fonea.ped.useCase.useCaseOffres.ports.PublicationRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureDocumentRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.DocumentRepository;

/**
 * Tests du cas d'usage « un diplômé postule à une offre publiée ».
 */
class PostulerUseCaseTest {

    private final CandidatureRepository candidatureRepository = mock(CandidatureRepository.class);
    private final OffreRepository offreRepository = mock(OffreRepository.class);
    private final PublicationRepository publicationRepository = mock(PublicationRepository.class);
    private final DiplomeRepository diplomeRepository = mock(DiplomeRepository.class);
    private final DocumentRepository documentRepository = mock(DocumentRepository.class);
    private final CandidatureDocumentRepository candidatureDocumentRepository =
            mock(CandidatureDocumentRepository.class);

    private final PostulerUseCase useCase = new PostulerUseCase(
            candidatureRepository, offreRepository, publicationRepository,
            diplomeRepository, documentRepository, candidatureDocumentRepository);

    private static final List<Document> DOCUMENTS_COMPLETS = List.of(
            new Document("DOC-CV", "cv.pdf", null, "CV"),
            new Document("DOC-ID", "identite.pdf", null, "IDENTITE"));

    @BeforeEach
    void preparerUneOffrePubliee() {
        when(diplomeRepository.existeParId("DIP-1")).thenReturn(true);
        when(offreRepository.trouverParId("OFF-1")).thenReturn(Optional.of(
                new OffresStages("OFF-1", "Stage", "Description", "Bujumbura",
                        6, StatutOffre.PUBLIEE, 2, "POS-1", "DOM-1", "CNV-1")));
        when(publicationRepository.trouverActiveParOffre("OFF-1")).thenReturn(Optional.of(
                new Publication("PUB-1", LocalDate.now(), LocalDate.now().plusDays(30),
                        "AGT-1", "OFF-1")));
        when(candidatureRepository.existeParDiplomeEtPublication("DIP-1", "PUB-1"))
                .thenReturn(false);
        when(candidatureRepository.prochainId()).thenReturn("CND-1");
    }

    @Test
    @DisplayName("Une candidature complète est créée et ses documents rattachés")
    void creeUneCandidatureComplete() {
        PostulerResponse reponse = useCase.executer(
                new PostulerRequest("DIP-1", "OFF-1", DOCUMENTS_COMPLETS));

        assertThat(reponse.getIdCandidature()).isEqualTo("CND-1");
        assertThat(reponse.getStatutCandidature()).isEqualTo(StatutCandidature.EN_COURS);
        assertThat(reponse.getIdDiplome()).isEqualTo("DIP-1");
        assertThat(reponse.getIdPublication()).isEqualTo("PUB-1");
        assertThat(reponse.getIdDocument()).hasSize(2);

        verify(documentRepository, times(2)).sauvegarder(any(Document.class));
        verify(candidatureRepository).sauvegarder(any(Candidature.class));
        verify(candidatureDocumentRepository, times(2)).sauvegarder(any());
    }

    @Test
    @DisplayName("Sans pièce d'identité, la candidature est refusée")
    void refuseDesDocumentsIncomplets() {
        List<Document> incomplets = List.of(
                new Document("DOC-CV", "cv.pdf", null, "CV"));

        assertThatThrownBy(() -> useCase.executer(
                new PostulerRequest("DIP-1", "OFF-1", incomplets)))
                .isInstanceOf(DocumentsIncompletsException.class);

        verify(candidatureRepository, never()).sauvegarder(any());
    }

    @Test
    @DisplayName("Une offre non publiée ne peut pas recevoir de candidature")
    void refuseUneOffreNonPubliee() {
        when(offreRepository.trouverParId("OFF-1")).thenReturn(Optional.of(
                new OffresStages("OFF-1", "Stage", "Description", "Bujumbura",
                        6, StatutOffre.BROUILLON, 2, "POS-1", "DOM-1", "CNV-1")));

        assertThatThrownBy(() -> useCase.executer(
                new PostulerRequest("DIP-1", "OFF-1", DOCUMENTS_COMPLETS)))
                .isInstanceOf(OffreNonPublieeException.class);
    }

    @Test
    @DisplayName("Un diplômé ne peut pas postuler deux fois à la même publication")
    void refuseUneCandidatureEnDouble() {
        when(candidatureRepository.existeParDiplomeEtPublication(anyString(), anyString()))
                .thenReturn(true);

        assertThatThrownBy(() -> useCase.executer(
                new PostulerRequest("DIP-1", "OFF-1", DOCUMENTS_COMPLETS)))
                .isInstanceOf(CandidatureDejaExistanteException.class);

        verify(candidatureRepository, never()).sauvegarder(any());
    }
}
