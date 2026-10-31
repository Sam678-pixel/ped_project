package fonea.ped.useCase.useCaseRecrutement.useCase;

import java.time.LocalDate;

import fonea.ped.domain.exceptions.CandidatureIncompleteException;
import fonea.ped.domain.exceptions.CandidatureIntrouvableException;
import fonea.ped.domain.exceptions.CandidatureNonPreselectionnableException;
import fonea.ped.domain.exceptions.PreselectionDejaExistanteException;

import fonea.ped.domain.recrutement.StatutCandidature;
import fonea.ped.domain.recrutement.Candidature;
import fonea.ped.domain.recrutement.Preselection;

import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.PreselectionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PreselectionnerUseCase {
	
	private final CandidatureRepository candidatureRepository;
    private final PreselectionRepository preselectionRepository;

    public PreselectionnerUseCase(CandidatureRepository candidatureRepository,
                                  PreselectionRepository preselectionRepository) {
        this.candidatureRepository = candidatureRepository;
        this.preselectionRepository = preselectionRepository;
    }

    public PreselectionnerResponse executer(PreselectionnerRequest request) {

        // 1. Vérifier l'existence de la candidature
        Candidature candidature = candidatureRepository.trouverParId(request.getIdCandidature())
                .orElseThrow(() -> new CandidatureIntrouvableException(request.getIdCandidature()));

        // 2. Vérifier que la candidature est complète
        if (!candidature.sontDocumentsComplets()) {
            throw new CandidatureIncompleteException(candidature.getIdCandidature());
        }

        // 3. Vérifier le statut de la candidature
        StatutCandidature statut = candidature.getStatutCandidature();
        if (statut != StatutCandidature.EN_COURS) {
            throw new CandidatureNonPreselectionnableException(
                    candidature.getIdCandidature(), statut.name());
        }

        // 4. Vérifier qu'aucune présélection n'existe déjà
        if (preselectionRepository.existeParCandidature(candidature.getIdCandidature())) {
            throw new PreselectionDejaExistanteException(candidature.getIdCandidature());
        }

        // 5. Création de la présélection
        String nouvelId = preselectionRepository.prochainId();
        Preselection preselection = new Preselection(
                nouvelId,
                LocalDate.now(),
                StatutCandidature.PRESELECTIONNEE,
                request.getIdAgent(),
                candidature.getIdCandidature()
        );
        /*
         *     public Preselection(String idPreselection, LocalDate datePreselection,
                        String statutPreselection, String idAgent, String idCandidature)
         */
        preselectionRepository.sauvegarder(preselection);

        // 6. Mise à jour du statut de la candidature
        candidature.changerStatut(StatutCandidature.PRESELECTIONNEE);
        candidatureRepository.sauvegarder(candidature);

        // 7. Réponse
        return new PreselectionnerResponse(
                preselection.getIdPreselection(),
                preselection.getDatePreselection(),
                preselection.getStatut(),
                preselection.getIdCandidature()
        );
    }
}
