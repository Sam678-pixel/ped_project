package fonea.ped.interfaceAdapters.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fonea.ped.domain.MiseEnStage.MiseEnStage;
import fonea.ped.domain.MiseEnStage.MotifDecrochage;
import fonea.ped.domain.exceptions.MiseEnStageIntrouvableException;
import fonea.ped.domain.exceptions.ReferenceIntrouvableException;
import fonea.ped.infrastructure.securite.UtilisateurCourant;
import fonea.ped.interfaceAdapters.controllers.dto.ApiDtos;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.declarerDecrochage.DeclarerDecrochageRequest;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.declarerDecrochage.DeclarerDecrochageResponse;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.declarerDecrochage.DeclarerDecrochageUseCase;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.demarrerStage.DemarrerStageRequest;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.demarrerStage.DemarrerStageResponse;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.demarrerStage.DemarrerStageUseCase;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.mettreEnStage.MettreEnStageRequest;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.mettreEnStage.MettreEnStageResponse;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.mettreEnStage.MettreEnStageUseCase;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.renouvelerstage.RenouvelerStageRequest;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.renouvelerstage.RenouvelerStageResponse;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.renouvelerstage.RenouvelerStageUseCase;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.terminerStage.TerminerStageRequest;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.terminerStage.TerminerStageResponse;
import fonea.ped.useCase.useCaseMiseEnStage.UseCase.terminerStage.TerminerStageUseCase;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MotifDecrochageRepository;
import fonea.ped.useCase.useCaseMiseEnStage.service.ChaineRecrutement;

/**
 * API de gestion des stages : mise en stage (entreprise), démarrage, clôture,
 * déclaration de décrochage, renouvellement et consultation.
 *
 * <p>Chaque opération vérifie que l'entreprise connectée est bien propriétaire
 * du stage concerné (TBAC) ; un administrateur ou un agent FONEA passe
 * outre.</p>
 */
@RestController
@RequestMapping("/api/mises-en-stage")
public class MiseEnStageController {

    private final MettreEnStageUseCase mettreEnStageUseCase;
    private final DemarrerStageUseCase demarrerStageUseCase;
    private final TerminerStageUseCase terminerStageUseCase;
    private final DeclarerDecrochageUseCase declarerDecrochageUseCase;
    private final RenouvelerStageUseCase renouvelerStageUseCase;
    private final MiseEnStageRepository miseEnStageRepository;
    private final MotifDecrochageRepository motifDecrochageRepository;
    private final ChaineRecrutement chaineRecrutement;
    private final UtilisateurCourant utilisateurCourant;

    public MiseEnStageController(MettreEnStageUseCase mettreEnStageUseCase,
                                 DemarrerStageUseCase demarrerStageUseCase,
                                 TerminerStageUseCase terminerStageUseCase,
                                 DeclarerDecrochageUseCase declarerDecrochageUseCase,
                                 RenouvelerStageUseCase renouvelerStageUseCase,
                                 MiseEnStageRepository miseEnStageRepository,
                                 MotifDecrochageRepository motifDecrochageRepository,
                                 ChaineRecrutement chaineRecrutement,
                                 UtilisateurCourant utilisateurCourant) {
        this.mettreEnStageUseCase = mettreEnStageUseCase;
        this.demarrerStageUseCase = demarrerStageUseCase;
        this.terminerStageUseCase = terminerStageUseCase;
        this.declarerDecrochageUseCase = declarerDecrochageUseCase;
        this.renouvelerStageUseCase = renouvelerStageUseCase;
        this.miseEnStageRepository = miseEnStageRepository;
        this.motifDecrochageRepository = motifDecrochageRepository;
        this.chaineRecrutement = chaineRecrutement;
        this.utilisateurCourant = utilisateurCourant;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PERM_STAGE_LIRE')")
    public List<ApiDtos.MiseEnStageVue> lister() {
        List<MiseEnStage> stages = utilisateurCourant.voitTout()
                ? miseEnStageRepository.trouverToutes()
                : miseEnStageRepository.trouverParEntreprise(
                        utilisateurCourant.idEntrepriseObligatoire());
        return stages.stream().map(MiseEnStageController::vue).toList();
    }

    @GetMapping("/{idMiseStage}")
    @PreAuthorize("hasAuthority('PERM_STAGE_LIRE')")
    public ApiDtos.MiseEnStageVue consulter(@PathVariable String idMiseStage) {
        MiseEnStage miseEnStage = miseEnStageRepository.trouverParId(idMiseStage)
                .orElseThrow(() -> new MiseEnStageIntrouvableException(idMiseStage));
        utilisateurCourant.exigerEntreprise(miseEnStage.getIdEntreprise());
        return vue(miseEnStage);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERM_STAGE_GERER')")
    public MettreEnStageResponse mettreEnStage(@RequestBody ApiDtos.MettreEnStage corps) {
        String idEntreprise = corps.idEntreprise() != null && !corps.idEntreprise().isBlank()
                ? corps.idEntreprise()
                : utilisateurCourant.idEntrepriseObligatoire();
        utilisateurCourant.exigerEntreprise(idEntreprise);

        MettreEnStageRequest requete = new MettreEnStageRequest(
                corps.idSelection(),
                corps.dateDebut(),
                corps.dateFinStage(),
                idEntreprise);

        return mettreEnStageUseCase.executer(requete);
    }

    @PostMapping("/{idMiseStage}/demarrer")
    @PreAuthorize("hasAuthority('PERM_STAGE_GERER')")
    public DemarrerStageResponse demarrer(@PathVariable String idMiseStage) {
        utilisateurCourant.exigerEntreprise(entrepriseProprietaire(idMiseStage));
        return demarrerStageUseCase.executer(new DemarrerStageRequest(idMiseStage));
    }

    @PostMapping("/{idMiseStage}/terminer")
    @PreAuthorize("hasAuthority('PERM_STAGE_GERER')")
    public TerminerStageResponse terminer(@PathVariable String idMiseStage) {
        utilisateurCourant.exigerEntreprise(entrepriseProprietaire(idMiseStage));
        return terminerStageUseCase.executer(new TerminerStageRequest(idMiseStage));
    }

    @PostMapping("/{idMiseStage}/decrochage")
    @PreAuthorize("hasAuthority('PERM_STAGE_GERER')")
    public DeclarerDecrochageResponse declarerDecrochage(
            @PathVariable String idMiseStage,
            @RequestBody ApiDtos.DeclarerDecrochage corps) {

        String entrepriseProprietaire = entrepriseProprietaire(idMiseStage);
        utilisateurCourant.exigerEntreprise(entrepriseProprietaire);

        MotifDecrochage motif = motifDecrochageRepository
                .trouverParId(corps.idMotifDecrochage())
                .orElseThrow(() -> new ReferenceIntrouvableException(
                        "Motif de décrochage", corps.idMotifDecrochage()));

        DeclarerDecrochageRequest requete = new DeclarerDecrochageRequest(
                idMiseStage, motif, entrepriseProprietaire);
        return declarerDecrochageUseCase.executer(requete);
    }

    @PostMapping("/{idMiseStage}/renouvellement")
    @PreAuthorize("hasAuthority('PERM_STAGE_GERER')")
    public RenouvelerStageResponse renouveler(
            @PathVariable String idMiseStage,
            @RequestBody ApiDtos.RenouvelerStage corps) {

        String entrepriseProprietaire = entrepriseProprietaire(idMiseStage);
        utilisateurCourant.exigerEntreprise(entrepriseProprietaire);

        RenouvelerStageRequest requete = new RenouvelerStageRequest(
                idMiseStage, corps.nouvelleDateDebut(), corps.nouvelleDateFin());
        return renouvelerStageUseCase.executer(requete);
    }

    private String entrepriseProprietaire(String idMiseStage) {
        return chaineRecrutement.entrepriseDeLaMiseEnStage(idMiseStage)
                .orElseThrow(() -> new IllegalStateException(
                        "Impossible de déterminer l'entreprise de la mise en stage "
                                + idMiseStage + "."));
    }

    private static ApiDtos.MiseEnStageVue vue(MiseEnStage miseEnStage) {
        return new ApiDtos.MiseEnStageVue(
                miseEnStage.getIdMiseStage(),
                miseEnStage.getDateDebut(),
                miseEnStage.getDateFin(),
                miseEnStage.getStatutStage() != null
                        ? miseEnStage.getStatutStage().name() : null,
                miseEnStage.getIdSelection(),
                miseEnStage.getIdEntreprise());
    }
}
