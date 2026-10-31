package fonea.ped.interfaceAdapters.controllers;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fonea.ped.domain.exceptions.CandidatureIntrouvableException;
import fonea.ped.domain.exceptions.PreselectionIntrouvableException;
import fonea.ped.domain.exceptions.SelectionIntrouvableException;
import fonea.ped.domain.recrutement.Candidature;
import fonea.ped.domain.recrutement.Preselection;
import fonea.ped.domain.recrutement.Selection;
import fonea.ped.infrastructure.securite.AgentFoneaCourant;
import fonea.ped.infrastructure.securite.UtilisateurCourant;
import fonea.ped.interfaceAdapters.controllers.dto.ApiDtos;
import fonea.ped.useCase.useCaseDiplome.useCase.postuler.PostulerRequest;
import fonea.ped.useCase.useCaseDiplome.useCase.postuler.PostulerResponse;
import fonea.ped.useCase.useCaseDiplome.useCase.postuler.PostulerUseCase;
import fonea.ped.useCase.useCaseMiseEnStage.service.ChaineRecrutement;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.PreselectionRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.SelectionRepository;
import fonea.ped.useCase.useCaseRecrutement.useCase.PreselectionnerRequest;
import fonea.ped.useCase.useCaseRecrutement.useCase.PreselectionnerResponse;
import fonea.ped.useCase.useCaseRecrutement.useCase.PreselectionnerUseCase;
import fonea.ped.useCase.useCaseRecrutement.useCase.selectionner.SelectionnerRequest;
import fonea.ped.useCase.useCaseRecrutement.useCase.selectionner.SelectionnerResponse;
import fonea.ped.useCase.useCaseRecrutement.useCase.selectionner.SelectionnerUseCase;

/**
 * API du recrutement : postuler (diplômé), présélectionner et sélectionner
 * (FONEA), et consultation filtrée des candidatures, présélections et
 * sélections.
 */
@RestController
@RequestMapping("/api")
public class RecrutementController {

    private final PostulerUseCase postulerUseCase;
    private final PreselectionnerUseCase preselectionnerUseCase;
    private final SelectionnerUseCase selectionnerUseCase;
    private final CandidatureRepository candidatureRepository;
    private final PreselectionRepository preselectionRepository;
    private final SelectionRepository selectionRepository;
    private final ChaineRecrutement chaineRecrutement;
    private final UtilisateurCourant utilisateurCourant;
    private final AgentFoneaCourant agentFoneaCourant;

    public RecrutementController(PostulerUseCase postulerUseCase,
                                 PreselectionnerUseCase preselectionnerUseCase,
                                 SelectionnerUseCase selectionnerUseCase,
                                 CandidatureRepository candidatureRepository,
                                 PreselectionRepository preselectionRepository,
                                 SelectionRepository selectionRepository,
                                 ChaineRecrutement chaineRecrutement,
                                 UtilisateurCourant utilisateurCourant,
                                 AgentFoneaCourant agentFoneaCourant) {
        this.postulerUseCase = postulerUseCase;
        this.preselectionnerUseCase = preselectionnerUseCase;
        this.selectionnerUseCase = selectionnerUseCase;
        this.candidatureRepository = candidatureRepository;
        this.preselectionRepository = preselectionRepository;
        this.selectionRepository = selectionRepository;
        this.chaineRecrutement = chaineRecrutement;
        this.utilisateurCourant = utilisateurCourant;
        this.agentFoneaCourant = agentFoneaCourant;
    }

    // ==================== Candidatures ====================

    @GetMapping("/candidatures")
    @PreAuthorize("hasAuthority('PERM_CANDIDATURE_LIRE')")
    public List<ApiDtos.CandidatureVue> listerCandidatures() {
        List<Candidature> candidatures;
        if (utilisateurCourant.voitTout()) {
            candidatures = candidatureRepository.trouverToutes();
        } else if (utilisateurCourant.aRole("ENTREPRISE")) {
            String idEntreprise = utilisateurCourant.idEntrepriseObligatoire();
            candidatures = candidatureRepository.trouverToutes().stream()
                    .filter(candidature -> chaineRecrutement
                            .entrepriseDeLaCandidature(candidature.getIdCandidature())
                            .map(idEntreprise::equals)
                            .orElse(false))
                    .toList();
        } else {
            candidatures = candidatureRepository.candidaturesParDiplome(
                    utilisateurCourant.idDiplomeObligatoire());
        }
        return candidatures.stream().map(RecrutementController::vue).toList();
    }

    @GetMapping("/candidatures/{idCandidature}")
    @PreAuthorize("hasAuthority('PERM_CANDIDATURE_LIRE')")
    public ApiDtos.CandidatureVue consulterCandidature(@PathVariable String idCandidature) {
        Candidature candidature = candidatureRepository.trouverParId(idCandidature)
                .orElseThrow(() -> new CandidatureIntrouvableException(idCandidature));

        if (!utilisateurCourant.voitTout()) {
            if (utilisateurCourant.aRole("ENTREPRISE")) {
                utilisateurCourant.exigerEntreprise(
                        entrepriseProprietaireCandidature(idCandidature));
            } else {
                utilisateurCourant.exigerDiplome(candidature.getIdDiplome());
            }
        }
        return vue(candidature);
    }

    // ==================== Présélections ====================

    @GetMapping("/preselections")
    @PreAuthorize("hasAuthority('PERM_PRESELECTION_FAIRE')")
    public List<ApiDtos.PreselectionVue> listerPreselections() {
        List<Preselection> preselections;
        if (utilisateurCourant.voitTout()) {
            preselections = preselectionRepository.trouverToutes();
        } else {
            String idEntreprise = utilisateurCourant.idEntrepriseObligatoire();
            preselections = preselectionRepository.trouverToutes().stream()
                    .filter(preselection -> chaineRecrutement
                            .entrepriseDeLaPreselection(preselection.getIdPreselection())
                            .map(idEntreprise::equals)
                            .orElse(false))
                    .toList();
        }
        return preselections.stream().map(RecrutementController::vue).toList();
    }

    @GetMapping("/preselections/{idPreselection}")
    @PreAuthorize("hasAuthority('PERM_PRESELECTION_FAIRE')")
    public ApiDtos.PreselectionVue consulterPreselection(
            @PathVariable String idPreselection) {
        Preselection preselection = preselectionRepository.trouverParId(idPreselection)
                .orElseThrow(() -> new PreselectionIntrouvableException(idPreselection));

        if (!utilisateurCourant.voitTout()) {
            if (!utilisateurCourant.aRole("ENTREPRISE")) {
                throw new AccessDeniedException(
                        "Vous ne pouvez pas consulter cette présélection.");
            }
            utilisateurCourant.exigerEntreprise(
                    chaineRecrutement.entrepriseDeLaPreselection(idPreselection)
                            .orElseThrow(() -> new IllegalStateException(
                                    "Impossible de déterminer l'entreprise de la présélection "
                                            + idPreselection + ".")));
        }
        return vue(preselection);
    }

    // ==================== Sélections ====================

    @GetMapping("/selections")
    @PreAuthorize("hasAuthority('PERM_SELECTION_FAIRE')")
    public List<ApiDtos.SelectionVue> listerSelections() {
        List<Selection> selections;
        if (utilisateurCourant.voitTout()) {
            selections = selectionRepository.trouverToutes();
        } else {
            String idEntreprise = utilisateurCourant.idEntrepriseObligatoire();
            selections = selectionRepository.trouverToutes().stream()
                    .filter(selection -> chaineRecrutement
                            .entrepriseDeLaSelection(selection.getIdSelection())
                            .map(idEntreprise::equals)
                            .orElse(false))
                    .toList();
        }
        return selections.stream().map(RecrutementController::vue).toList();
    }

    @GetMapping("/selections/{idSelection}")
    @PreAuthorize("hasAuthority('PERM_SELECTION_FAIRE')")
    public ApiDtos.SelectionVue consulterSelection(@PathVariable String idSelection) {
        Selection selection = selectionRepository.trouverParId(idSelection)
                .orElseThrow(() -> new SelectionIntrouvableException(idSelection));

        if (!utilisateurCourant.voitTout()) {
            if (!utilisateurCourant.aRole("ENTREPRISE")) {
                throw new AccessDeniedException(
                        "Vous ne pouvez pas consulter cette sélection.");
            }
            utilisateurCourant.exigerEntreprise(
                    chaineRecrutement.entrepriseDeLaSelection(idSelection)
                            .orElseThrow(() -> new IllegalStateException(
                                    "Impossible de déterminer l'entreprise de la sélection "
                                            + idSelection + ".")));
        }
        return vue(selection);
    }

    // ==================== Écritures ====================

    @PostMapping("/candidatures")
    @PreAuthorize("hasAuthority('PERM_CANDIDATURE_DEPOSER')")
    public PostulerResponse postuler(@RequestBody ApiDtos.Postuler corps) {
        // Un diplômé ne peut postuler que pour son propre profil.
        utilisateurCourant.exigerDiplome(corps.idDiplome());

        List<fonea.ped.domain.recrutement.Document> documents = corps.documents() == null
                ? List.of()
                : corps.documents().stream()
                        .map(document -> new fonea.ped.domain.recrutement.Document(
                                document.idDocument(),
                                document.cheminFichier(),
                                null,
                                document.idTypeDocument()))
                        .toList();

        PostulerRequest requete = new PostulerRequest(
                corps.idDiplome(), corps.idOffre(), documents);
        return postulerUseCase.executer(requete);
    }

    @PostMapping("/candidatures/{idCandidature}/preselectionner")
    @PreAuthorize("hasAuthority('PERM_PRESELECTION_FAIRE')")
    public PreselectionnerResponse preselectionner(@PathVariable String idCandidature) {
        PreselectionnerRequest requete = new PreselectionnerRequest(
                idCandidature, agentFoneaCourant.idObligatoire());
        return preselectionnerUseCase.executer(requete);
    }

    @PostMapping("/preselections/{idPreselection}/selectionner")
    @PreAuthorize("hasAuthority('PERM_SELECTION_FAIRE')")
    public SelectionnerResponse selectionner(@PathVariable String idPreselection) {
        return selectionnerUseCase.executer(new SelectionnerRequest(idPreselection));
    }

    // ==================== Helpers ====================

    private String entrepriseProprietaireCandidature(String idCandidature) {
        return chaineRecrutement.entrepriseDeLaCandidature(idCandidature)
                .orElseThrow(() -> new IllegalStateException(
                        "Impossible de déterminer l'entreprise de la candidature "
                                + idCandidature + "."));
    }

    private static ApiDtos.CandidatureVue vue(Candidature candidature) {
        List<ApiDtos.Document> documents = candidature.getDocuments() == null
                ? List.of()
                : candidature.getDocuments().stream()
                        .map(document -> new ApiDtos.Document(
                                document.getIdDocument(),
                                document.getCheminFichier(),
                                document.getIdTypeDocument()))
                        .toList();
        return new ApiDtos.CandidatureVue(
                candidature.getIdCandidature(),
                candidature.getDateDepot(),
                candidature.getStatutCandidature() != null
                        ? candidature.getStatutCandidature().name() : null,
                candidature.getIdDiplome(),
                candidature.getIdPublication(),
                documents);
    }

    private static ApiDtos.PreselectionVue vue(Preselection preselection) {
        return new ApiDtos.PreselectionVue(
                preselection.getIdPreselection(),
                preselection.getDatePreselection(),
                preselection.getStatutPreselection() != null
                        ? preselection.getStatutPreselection().name() : null,
                preselection.getIdAgent(),
                preselection.getIdCandidature());
    }

    private static ApiDtos.SelectionVue vue(Selection selection) {
        return new ApiDtos.SelectionVue(
                selection.getIdSelection(),
                selection.getDateSelection(),
                selection.getStatutSelection() != null
                        ? selection.getStatutSelection().name() : null,
                selection.getIdPreselection());
    }
}
