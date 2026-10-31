package fonea.ped.useCase.UseCaseConvention.useCase.validerConvention;

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

import fonea.ped.domain.convention.model.Convention;
import fonea.ped.domain.convention.model.StatutConvention;
import fonea.ped.domain.convention.model.TypeConvention;
import fonea.ped.domain.exceptions.ConventionDejaResilieeException;
import fonea.ped.domain.exceptions.ConventionIntrouvableException;
import fonea.ped.useCase.UseCaseConvention.ports.ConventionRepository;

/**
 * Tests du cas d'usage « le FONEA valide une convention ».
 */
class ValiderConventionUseCaseTest {

    private final ConventionRepository conventionRepository = mock(ConventionRepository.class);
    private final ValiderConventionUseCase useCase =
            new ValiderConventionUseCase(conventionRepository);

    private static Convention convention(StatutConvention statut) {
        return new Convention(
                "CNV-1",
                LocalDate.now().plusDays(10),
                10.0,
                5.0,
                statut,
                new TypeConvention("TYP_STAGE"),
                "ENT-1");
    }

    @Test
    @DisplayName("Une convention en brouillon passe à ACTIVE et est persistée")
    void valideUneConventionEnBrouillon() {
        Convention convention = convention(StatutConvention.BROUILLON);
        when(conventionRepository.trouverParId("CNV-1")).thenReturn(Optional.of(convention));

        ValiderConventionResponse reponse =
                useCase.executer(new ValiderConventionRequest("CNV-1"));

        assertThat(reponse.getIdConvention()).isEqualTo("CNV-1");
        assertThat(reponse.getStatut()).isEqualTo(StatutConvention.ACTIVE);
        assertThat(convention.getStatutConvention()).isEqualTo(StatutConvention.ACTIVE);
        verify(conventionRepository).sauvegarder(convention);
    }

    @Test
    @DisplayName("Une convention introuvable est signalée")
    void refuseUneConventionIntrouvable() {
        when(conventionRepository.trouverParId("CNV-X")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executer(new ValiderConventionRequest("CNV-X")))
                .isInstanceOf(ConventionIntrouvableException.class);
    }

    @Test
    @DisplayName("Une convention résiliée ne peut plus être validée")
    void refuseUneConventionResiliee() {
        when(conventionRepository.trouverParId("CNV-1"))
                .thenReturn(Optional.of(convention(StatutConvention.RESILIEE)));

        assertThatThrownBy(() -> useCase.executer(new ValiderConventionRequest("CNV-1")))
                .isInstanceOf(ConventionDejaResilieeException.class);

        verify(conventionRepository, never()).sauvegarder(any());
    }

    @Test
    @DisplayName("Valider une convention déjà active est idempotent")
    void neReecritPasUneConventionDejaActive() {
        when(conventionRepository.trouverParId("CNV-1"))
                .thenReturn(Optional.of(convention(StatutConvention.ACTIVE)));

        ValiderConventionResponse reponse =
                useCase.executer(new ValiderConventionRequest("CNV-1"));

        assertThat(reponse.getStatut()).isEqualTo(StatutConvention.ACTIVE);
        verify(conventionRepository, never()).sauvegarder(any());
    }
}
