package fonea.ped.interfaceAdapters.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fonea.ped.domain.convention.model.Convention;
import fonea.ped.domain.convention.model.StatutConvention;
import fonea.ped.domain.convention.model.TypeConvention;
import fonea.ped.domain.exceptions.ConventionIntrouvableException;
import fonea.ped.infrastructure.securite.UtilisateurCourant;
import fonea.ped.interfaceAdapters.controllers.dto.ApiDtos;
import fonea.ped.useCase.UseCaseConvention.ports.ConventionRepository;
import fonea.ped.useCase.UseCaseConvention.useCase.creerConvention.CreerConventionRequest;
import fonea.ped.useCase.UseCaseConvention.useCase.creerConvention.CreerConventionResponse;
import fonea.ped.useCase.UseCaseConvention.useCase.creerConvention.CreerConventionUseCase;
import fonea.ped.useCase.UseCaseConvention.useCase.resilierConvention.ResilierConventionRequest;
import fonea.ped.useCase.UseCaseConvention.useCase.resilierConvention.ResilierConventionResponse;
import fonea.ped.useCase.UseCaseConvention.useCase.resilierConvention.ResilierConventionUseCase;
import fonea.ped.useCase.UseCaseConvention.useCase.validerConvention.ValiderConventionRequest;
import fonea.ped.useCase.UseCaseConvention.useCase.validerConvention.ValiderConventionResponse;
import fonea.ped.useCase.UseCaseConvention.useCase.validerConvention.ValiderConventionUseCase;

/**
 * API du cycle de vie des conventions :
 * création par l'entreprise → validation (signature) par le FONEA →
 * résiliation éventuelle.
 */
@RestController
@RequestMapping("/api/conventions")
public class ConventionController {

    private final CreerConventionUseCase creerConventionUseCase;
    private final ValiderConventionUseCase validerConventionUseCase;
    private final ResilierConventionUseCase resilierConventionUseCase;
    private final ConventionRepository conventionRepository;
    private final UtilisateurCourant utilisateurCourant;

    public ConventionController(CreerConventionUseCase creerConventionUseCase,
                                ValiderConventionUseCase validerConventionUseCase,
                                ResilierConventionUseCase resilierConventionUseCase,
                                ConventionRepository conventionRepository,
                                UtilisateurCourant utilisateurCourant) {
        this.creerConventionUseCase = creerConventionUseCase;
        this.validerConventionUseCase = validerConventionUseCase;
        this.resilierConventionUseCase = resilierConventionUseCase;
        this.conventionRepository = conventionRepository;
        this.utilisateurCourant = utilisateurCourant;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PERM_CONVENTION_LIRE')")
    public List<ApiDtos.ConventionVue> lister() {
        List<Convention> conventions = utilisateurCourant.voitTout()
                ? conventionRepository.trouverToutes()
                : conventionRepository.trouverParEntreprise(
                        utilisateurCourant.idEntrepriseObligatoire());
        return conventions.stream().map(ConventionController::vue).toList();
    }

    @GetMapping("/{idConvention}")
    @PreAuthorize("hasAuthority('PERM_CONVENTION_LIRE')")
    public ApiDtos.ConventionVue consulter(@PathVariable String idConvention) {
        Convention convention = conventionRepository.trouverParId(idConvention)
                .orElseThrow(() -> new ConventionIntrouvableException(idConvention));
        utilisateurCourant.exigerEntreprise(convention.getIdEntreprise());
        return vue(convention);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERM_CONVENTION_CREER')")
    public CreerConventionResponse creer(@RequestBody ApiDtos.CreerConvention corps) {
        String idEntreprise = corps.idEntreprise() != null
                ? corps.idEntreprise()
                : utilisateurCourant.idEntrepriseObligatoire();
        utilisateurCourant.exigerEntreprise(idEntreprise);

        // Le statut n'est jamais choisi par le client : toute convention naît
        // en BROUILLON et doit être validée par le FONEA.
        CreerConventionRequest requete = new CreerConventionRequest(
                corps.dateEffet(),
                corps.tauxFonea(),
                corps.tauxEntreprise(),
                StatutConvention.BROUILLON,
                idEntreprise,
                new TypeConvention(corps.idTypeConvention()),
                corps.idProgramme());

        return creerConventionUseCase.executer(requete);
    }

    @PostMapping("/{idConvention}/valider")
    @PreAuthorize("hasAuthority('PERM_CONVENTION_VALIDER')")
    public ValiderConventionResponse valider(@PathVariable String idConvention) {
        return validerConventionUseCase.executer(new ValiderConventionRequest(idConvention));
    }

    @PostMapping("/{idConvention}/resilier")
    @PreAuthorize("hasAuthority('PERM_CONVENTION_RESILIER')")
    public ResilierConventionResponse resilier(@PathVariable String idConvention,
                                               @RequestBody ApiDtos.ResilierConvention corps) {
        Convention convention = conventionRepository.trouverParId(idConvention)
                .orElseThrow(() -> new ConventionIntrouvableException(idConvention));
        utilisateurCourant.exigerEntreprise(convention.getIdEntreprise());

        String initiateur = corps.initiateur() != null && !corps.initiateur().isBlank()
                ? corps.initiateur()
                : utilisateurCourant.id();

        ResilierConventionRequest requete = new ResilierConventionRequest(
                idConvention, corps.motif(), initiateur);
        return resilierConventionUseCase.executer(requete);
    }

    private static ApiDtos.ConventionVue vue(Convention convention) {
        return new ApiDtos.ConventionVue(
                convention.getIdConvention(),
                convention.getDateEffet(),
                convention.getTauxFonea(),
                convention.getTauxEntreprise(),
                convention.getStatutConvention() != null
                        ? convention.getStatutConvention().name() : null,
                convention.getTypeConvention() != null
                        ? convention.getTypeConvention().getIdTypeConvention() : null,
                convention.getIdEntreprise());
    }
}
