package fonea.ped.useCase.useCaseMiseEnStage.UseCase.terminerStage;

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
import org.mockito.ArgumentCaptor;

import fonea.ped.domain.MiseEnStage.MiseEnStage;
import fonea.ped.domain.MiseEnStage.StatutStage;
import fonea.ped.domain.exceptions.MiseEnStageIntrouvableException;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;

/**
 * Tests du cas d'usage « terminer un stage ».
 */
class TerminerStageUseCaseTest {

    private final MiseEnStageRepository miseEnStageRepository =
            mock(MiseEnStageRepository.class);

    private final TerminerStageUseCase useCase =
            new TerminerStageUseCase(miseEnStageRepository);

    private static MiseEnStage stage(StatutStage statut) {
        return new MiseEnStage("MES-1", LocalDate.of(2026, 9, 1),
                LocalDate.of(2026, 12, 1), statut, "SEL-1", "ENT-1");
    }

    @Test
    @DisplayName("Un stage en cours passe en termine et est persiste")
    void termineUnStageEnCours() {
        MiseEnStage miseEnStage = stage(StatutStage.EN_COURS);
        when(miseEnStageRepository.trouverParId("MES-1"))
                .thenReturn(Optional.of(miseEnStage));

        TerminerStageResponse reponse =
                useCase.executer(new TerminerStageRequest("MES-1"));

        assertThat(reponse.getStatutStage()).isEqualTo(StatutStage.TERMINE);
        assertThat(reponse.getDateFin()).isEqualTo(LocalDate.now());

        ArgumentCaptor<MiseEnStage> captor = ArgumentCaptor.forClass(MiseEnStage.class);
        verify(miseEnStageRepository).sauvegarder(captor.capture());
        assertThat(captor.getValue().getStatutStage()).isEqualTo(StatutStage.TERMINE);
    }

    @Test
    @DisplayName("Un stage renouvele peut etre termine")
    void termineUnStageRenouvele() {
        when(miseEnStageRepository.trouverParId("MES-1"))
                .thenReturn(Optional.of(stage(StatutStage.RENOUVELE)));

        TerminerStageResponse reponse =
                useCase.executer(new TerminerStageRequest("MES-1"));

        assertThat(reponse.getStatutStage()).isEqualTo(StatutStage.TERMINE);
        verify(miseEnStageRepository).sauvegarder(any());
    }

    @Test
    @DisplayName("Un stage en attente ne peut pas etre termine")
    void refuseUnStageEnAttente() {
        when(miseEnStageRepository.trouverParId("MES-1"))
                .thenReturn(Optional.of(stage(StatutStage.EN_ATTENTE)));

        assertThatThrownBy(() -> useCase.executer(new TerminerStageRequest("MES-1")))
                .isInstanceOf(IllegalStateException.class);

        verify(miseEnStageRepository, never()).sauvegarder(any());
    }

    @Test
    @DisplayName("Un stage introuvable est signale")
    void refuseUnStageIntrouvable() {
        when(miseEnStageRepository.trouverParId("MES-404"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executer(new TerminerStageRequest("MES-404")))
                .isInstanceOf(MiseEnStageIntrouvableException.class);

        verify(miseEnStageRepository, never()).sauvegarder(any());
    }
}
