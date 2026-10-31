package fonea.ped.useCase.useCaseMiseEnStage.UseCase.demarrerStage;

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
 * Tests du cas d'usage « démarrer un stage ».
 */
class DemarrerStageUseCaseTest {

    private final MiseEnStageRepository miseEnStageRepository =
            mock(MiseEnStageRepository.class);

    private final DemarrerStageUseCase useCase =
            new DemarrerStageUseCase(miseEnStageRepository);

    private static MiseEnStage stage(StatutStage statut) {
        return new MiseEnStage("MES-1", LocalDate.of(2026, 9, 1),
                LocalDate.of(2026, 12, 1), statut, "SEL-1", "ENT-1");
    }

    @Test
    @DisplayName("Un stage en attente passe en cours et est persiste")
    void demarreUnStageEnAttente() {
        MiseEnStage miseEnStage = stage(StatutStage.EN_ATTENTE);
        when(miseEnStageRepository.trouverParId("MES-1"))
                .thenReturn(Optional.of(miseEnStage));

        DemarrerStageResponse reponse =
                useCase.executer(new DemarrerStageRequest("MES-1"));

        assertThat(reponse.getIdMiseStage()).isEqualTo("MES-1");
        assertThat(reponse.getStatutStage()).isEqualTo(StatutStage.EN_COURS);
        assertThat(reponse.getDateDebut()).isEqualTo(LocalDate.of(2026, 9, 1));

        ArgumentCaptor<MiseEnStage> captor = ArgumentCaptor.forClass(MiseEnStage.class);
        verify(miseEnStageRepository).sauvegarder(captor.capture());
        assertThat(captor.getValue().getStatutStage()).isEqualTo(StatutStage.EN_COURS);
    }

    @Test
    @DisplayName("Un stage introuvable est signale")
    void refuseUnStageIntrouvable() {
        when(miseEnStageRepository.trouverParId("MES-404"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executer(new DemarrerStageRequest("MES-404")))
                .isInstanceOf(MiseEnStageIntrouvableException.class);

        verify(miseEnStageRepository, never()).sauvegarder(any());
    }

    @Test
    @DisplayName("Un stage deja en cours ne peut pas etre redemarre")
    void refuseUnStageDejaEnCours() {
        when(miseEnStageRepository.trouverParId("MES-1"))
                .thenReturn(Optional.of(stage(StatutStage.EN_COURS)));

        assertThatThrownBy(() -> useCase.executer(new DemarrerStageRequest("MES-1")))
                .isInstanceOf(IllegalStateException.class);

        verify(miseEnStageRepository, never()).sauvegarder(any());
    }

    @Test
    @DisplayName("Un stage termine ne peut pas etre redemarre")
    void refuseUnStageTermine() {
        when(miseEnStageRepository.trouverParId("MES-1"))
                .thenReturn(Optional.of(stage(StatutStage.TERMINE)));

        assertThatThrownBy(() -> useCase.executer(new DemarrerStageRequest("MES-1")))
                .isInstanceOf(IllegalStateException.class);

        verify(miseEnStageRepository, never()).sauvegarder(any());
    }
}
