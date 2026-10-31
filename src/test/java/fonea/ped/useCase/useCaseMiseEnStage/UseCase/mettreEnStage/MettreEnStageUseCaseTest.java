package fonea.ped.useCase.useCaseMiseEnStage.UseCase.mettreEnStage;

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
import fonea.ped.domain.exceptions.CandidatureNonSelectionneeException;
import fonea.ped.domain.exceptions.DatesStageInvalidesException;
import fonea.ped.domain.exceptions.SelectionIntrouvableException;
import fonea.ped.domain.recrutement.Selection;
import fonea.ped.domain.recrutement.StatutCandidature;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;
import fonea.ped.useCase.useCaseMiseEnStage.service.ChaineRecrutement;
import fonea.ped.useCase.useCaseRecrutement.ports.SelectionRepository;

/**
 * Tests du cas d'usage « l'entreprise met un candidat sélectionné en stage ».
 */
class MettreEnStageUseCaseTest {

    private final SelectionRepository selectionRepository = mock(SelectionRepository.class);
    private final MiseEnStageRepository miseEnStageRepository = mock(MiseEnStageRepository.class);
    private final ChaineRecrutement chaineRecrutement = mock(ChaineRecrutement.class);

    private final MettreEnStageUseCase useCase = new MettreEnStageUseCase(
            selectionRepository, miseEnStageRepository, chaineRecrutement);

    private static Selection selection(StatutCandidature statut) {
        return new Selection("SEL-1", LocalDate.now(), statut, "PRE-1");
    }

    private void preparerSelection(StatutCandidature statut) {
        when(selectionRepository.trouverParId("SEL-1"))
                .thenReturn(Optional.of(selection(statut)));
        when(chaineRecrutement.entrepriseDeLaSelection("SEL-1"))
                .thenReturn(Optional.of("ENT-1"));
        when(miseEnStageRepository.existeParSelection("SEL-1")).thenReturn(false);
        when(miseEnStageRepository.prochainId()).thenReturn("MES-1");
    }

    private static MettreEnStageRequest requete(String idEntreprise) {
        return new MettreEnStageRequest(
                "SEL-1",
                LocalDate.now().plusDays(15),
                LocalDate.now().plusMonths(6),
                idEntreprise);
    }

    @Test
    @DisplayName("Une sélection validée donne une mise en stage en attente")
    void creeUneMiseEnStage() {
        preparerSelection(StatutCandidature.SELECTIONNEE);

        MettreEnStageResponse reponse = useCase.executer(requete("ENT-1"));

        assertThat(reponse.getIdMiseStage()).isEqualTo("MES-1");
        assertThat(reponse.getStatutStage()).isEqualTo(StatutStage.EN_ATTENTE);
        assertThat(reponse.getIdSelection()).isEqualTo("SEL-1");

        ArgumentCaptor<MiseEnStage> captor = ArgumentCaptor.forClass(MiseEnStage.class);
        verify(miseEnStageRepository).sauvegarder(captor.capture());
        assertThat(captor.getValue().getIdEntreprise()).isEqualTo("ENT-1");
    }

    @Test
    @DisplayName("Une sélection introuvable est signalée")
    void refuseUneSelectionIntrouvable() {
        when(selectionRepository.trouverParId("SEL-1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executer(requete("ENT-1")))
                .isInstanceOf(SelectionIntrouvableException.class);
    }

    @Test
    @DisplayName("Une sélection non validée ne peut pas être mise en stage")
    void refuseUneSelectionNonValidee() {
        preparerSelection(StatutCandidature.PRESELECTIONNEE);

        assertThatThrownBy(() -> useCase.executer(requete("ENT-1")))
                .isInstanceOf(CandidatureNonSelectionneeException.class);

        verify(miseEnStageRepository, never()).sauvegarder(any());
    }

    @Test
    @DisplayName("Une autre entreprise ne peut pas placer le candidat en stage")
    void refuseUneEntrepriseNonProprietaire() {
        preparerSelection(StatutCandidature.SELECTIONNEE);

        assertThatThrownBy(() -> useCase.executer(requete("ENT-AUTRE")))
                .isInstanceOf(IllegalArgumentException.class);

        verify(miseEnStageRepository, never()).sauvegarder(any());
    }

    @Test
    @DisplayName("Des dates de stage incohérentes sont refusées")
    void refuseDesDatesIncoherentes() {
        preparerSelection(StatutCandidature.SELECTIONNEE);

        MettreEnStageRequest requete = new MettreEnStageRequest(
                "SEL-1",
                LocalDate.now().plusMonths(6),
                LocalDate.now().plusDays(15),
                "ENT-1");

        assertThatThrownBy(() -> useCase.executer(requete))
                .isInstanceOf(DatesStageInvalidesException.class);

        verify(miseEnStageRepository, never()).sauvegarder(any());
    }
}
