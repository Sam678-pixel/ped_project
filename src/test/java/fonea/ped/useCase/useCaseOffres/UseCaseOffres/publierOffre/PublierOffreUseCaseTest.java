package fonea.ped.useCase.useCaseOffres.UseCaseOffres.publierOffre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fonea.ped.domain.Offres.OffresStages;
import fonea.ped.domain.Offres.Publication;
import fonea.ped.domain.Offres.StatutOffre;
import fonea.ped.domain.convention.model.Convention;
import fonea.ped.domain.convention.model.StatutConvention;
import fonea.ped.domain.convention.model.TypeConvention;
import fonea.ped.domain.exceptions.ConventionNonActiveException;
import fonea.ped.domain.exceptions.DateInvalideException;
import fonea.ped.domain.exceptions.OffreIntrouvableException;
import fonea.ped.useCase.UseCaseConvention.ports.ConventionRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffreRepository;
import fonea.ped.useCase.useCaseOffres.ports.PublicationRepository;

/**
 * Tests du cas d'usage « le FONEA publie une offre ».
 */
class PublierOffreUseCaseTest {

    private final OffreRepository offreRepository = mock(OffreRepository.class);
    private final PublicationRepository publicationRepository = mock(PublicationRepository.class);
    private final ConventionRepository conventionRepository = mock(ConventionRepository.class);
    private final PublierOffreUseCase useCase = new PublierOffreUseCase(
            offreRepository, publicationRepository, conventionRepository);

    private static OffresStages offre(StatutOffre statut) {
        return new OffresStages(
                "OFF-1", "Stage développeur", "Description", "Bujumbura",
                6, statut, 2, "POS-1", "DOM-1", "CNV-1");
    }

    private static Convention convention(StatutConvention statut) {
        return new Convention(
                "CNV-1", LocalDate.now().plusDays(10), 10.0, 5.0,
                statut, new TypeConvention("TYP_STAGE"), "ENT-1");
    }

    private void offreBrouillonAvecConvention(StatutConvention statutConvention) {
        when(offreRepository.trouverParId("OFF-1"))
                .thenReturn(Optional.of(offre(StatutOffre.BROUILLON)));
        when(conventionRepository.trouverParId("CNV-1"))
                .thenReturn(Optional.of(convention(statutConvention)));
    }

    @Test
    @DisplayName("Une offre en brouillon liée à une convention active est publiée")
    void publieUneOffreValide() {
        offreBrouillonAvecConvention(StatutConvention.ACTIVE);
        when(publicationRepository.trouverActiveParOffre("OFF-1")).thenReturn(Optional.empty());
        when(publicationRepository.prochainId()).thenReturn("PUB-1");

        LocalDate expiration = LocalDate.now().plusDays(30);
        PublierOffreResponse reponse = useCase.executer(
                new PublierOffreRequest("OFF-1", "AGT-1", expiration));

        assertThat(reponse.getIdPublication()).isEqualTo("PUB-1");
        assertThat(reponse.getIdOffre()).isEqualTo("OFF-1");
        assertThat(reponse.getStatutOffre()).isEqualTo(StatutOffre.PUBLIEE);
        assertThat(reponse.getDateExpiration()).isEqualTo(expiration);

        verify(offreRepository).sauvegarder(any(OffresStages.class));
        verify(publicationRepository).sauvegarder(any(Publication.class));
    }

    @Test
    @DisplayName("Une offre introuvable est signalée")
    void refuseUneOffreIntrouvable() {
        when(offreRepository.trouverParId("OFF-X")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executer(
                new PublierOffreRequest("OFF-X", "AGT-1", LocalDate.now().plusDays(1))))
                .isInstanceOf(OffreIntrouvableException.class);
    }

    @Test
    @DisplayName("Une convention non active empêche la publication")
    void refuseUneConventionNonActive() {
        offreBrouillonAvecConvention(StatutConvention.BROUILLON);

        assertThatThrownBy(() -> useCase.executer(
                new PublierOffreRequest("OFF-1", "AGT-1", LocalDate.now().plusDays(1))))
                .isInstanceOf(ConventionNonActiveException.class);

        verify(publicationRepository, never()).sauvegarder(any());
    }

    @Test
    @DisplayName("Une date d'expiration passée est refusée")
    void refuseUneDateExpirationPassee() {
        offreBrouillonAvecConvention(StatutConvention.ACTIVE);
        when(publicationRepository.trouverActiveParOffre("OFF-1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executer(
                new PublierOffreRequest("OFF-1", "AGT-1", LocalDate.now().minusDays(1))))
                .isInstanceOf(DateInvalideException.class);

        verify(publicationRepository, never()).sauvegarder(any());
    }

    @Test
    @DisplayName("Une offre déjà publiée ne peut pas être republiée")
    void refuseUneDoublePublication() {
        offreBrouillonAvecConvention(StatutConvention.ACTIVE);
        when(publicationRepository.trouverActiveParOffre("OFF-1")).thenReturn(Optional.of(
                new Publication("PUB-0", LocalDate.now(), LocalDate.now().plusDays(10),
                        "AGT-1", "OFF-1")));

        assertThatThrownBy(() -> useCase.executer(
                new PublierOffreRequest("OFF-1", "AGT-1", LocalDate.now().plusDays(30))))
                .isInstanceOf(IllegalStateException.class);

        verify(publicationRepository, never()).sauvegarder(any());
    }
}
