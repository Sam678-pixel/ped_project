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

import fonea.ped.domain.Offres.OffresStages;
import fonea.ped.domain.Offres.StatutOffre;
import fonea.ped.domain.exceptions.OffreIntrouvableException;
import fonea.ped.infrastructure.securite.AgentFoneaCourant;
import fonea.ped.infrastructure.securite.UtilisateurCourant;
import fonea.ped.interfaceAdapters.controllers.dto.ApiDtos;
import fonea.ped.useCase.useCaseMiseEnStage.service.ChaineRecrutement;
import fonea.ped.useCase.useCaseOffres.UseCaseOffres.creerOffre.CreerOffreRequest;
import fonea.ped.useCase.useCaseOffres.UseCaseOffres.creerOffre.CreerOffreResponse;
import fonea.ped.useCase.useCaseOffres.UseCaseOffres.creerOffre.CreerOffreUseCase;
import fonea.ped.useCase.useCaseOffres.UseCaseOffres.publierOffre.PublierOffreRequest;
import fonea.ped.useCase.useCaseOffres.UseCaseOffres.publierOffre.PublierOffreResponse;
import fonea.ped.useCase.useCaseOffres.UseCaseOffres.publierOffre.PublierOffreUseCase;
import fonea.ped.useCase.useCaseOffres.ports.OffreRepository;

/**
 * API des offres de stage.
 *
 * <p>Séparation volontaire des responsabilités : l'entreprise <b>crée</b> une
 * offre (brouillon), le FONEA la <b>publie</b>. Sans cette séparation, une
 * entreprise pouvait publier elle-même une offre sans contrôle.</p>
 */
@RestController
@RequestMapping("/api/offres")
public class OffreController {

    private final CreerOffreUseCase creerOffreUseCase;
    private final PublierOffreUseCase publierOffreUseCase;
    private final OffreRepository offreRepository;
    private final ChaineRecrutement chaineRecrutement;
    private final AgentFoneaCourant agentFoneaCourant;
    private final UtilisateurCourant utilisateurCourant;

    public OffreController(CreerOffreUseCase creerOffreUseCase,
                           PublierOffreUseCase publierOffreUseCase,
                           OffreRepository offreRepository,
                           ChaineRecrutement chaineRecrutement,
                           AgentFoneaCourant agentFoneaCourant,
                           UtilisateurCourant utilisateurCourant) {
        this.creerOffreUseCase = creerOffreUseCase;
        this.publierOffreUseCase = publierOffreUseCase;
        this.offreRepository = offreRepository;
        this.chaineRecrutement = chaineRecrutement;
        this.agentFoneaCourant = agentFoneaCourant;
        this.utilisateurCourant = utilisateurCourant;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PERM_OFFRE_LIRE')")
    public List<ApiDtos.OffreVue> lister() {
        List<OffresStages> offres;
        if (utilisateurCourant.voitTout()) {
            offres = offreRepository.trouverToutes();
        } else if (utilisateurCourant.aRole("ENTREPRISE")) {
            String idEntreprise = utilisateurCourant.idEntrepriseObligatoire();
            offres = offreRepository.trouverToutes().stream()
                    .filter(offre -> chaineRecrutement.entrepriseDeLOffre(offre.getIdOffre())
                            .map(idEntreprise::equals)
                            .orElse(false))
                    .toList();
        } else {
            // Un diplômé ne voit que les offres effectivement publiées.
            offres = offreRepository.offrePubliees();
        }
        return offres.stream().map(OffreController::vue).toList();
    }

    @GetMapping("/{idOffre}")
    @PreAuthorize("hasAuthority('PERM_OFFRE_LIRE')")
    public ApiDtos.OffreVue consulter(@PathVariable String idOffre) {
        OffresStages offre = offreRepository.trouverParId(idOffre)
                .orElseThrow(() -> new OffreIntrouvableException(idOffre));

        if (!utilisateurCourant.voitTout()) {
            if (utilisateurCourant.aRole("ENTREPRISE")) {
                String proprietaire = chaineRecrutement.entrepriseDeLOffre(idOffre)
                        .orElseThrow(() -> new IllegalStateException(
                                "Impossible de déterminer l'entreprise de l'offre "
                                        + idOffre + "."));
                utilisateurCourant.exigerEntreprise(proprietaire);
            } else if (offre.getStatutOffre() != StatutOffre.PUBLIEE) {
                throw new AccessDeniedException("Cette offre n'est pas publiée.");
            }
        }
        return vue(offre);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERM_OFFRE_CREER')")
    public CreerOffreResponse creer(@RequestBody ApiDtos.CreerOffre corps) {
        String idEntreprise = corps.idEntreprise() != null
                ? corps.idEntreprise()
                : utilisateurCourant.idEntrepriseObligatoire();
        utilisateurCourant.exigerEntreprise(idEntreprise);

        CreerOffreRequest requete = new CreerOffreRequest(
                corps.intitule(),
                corps.description(),
                corps.villeStage(),
                corps.duree(),
                corps.nbrePlaces(),
                corps.idConvention(),
                corps.idDomaine(),
                corps.idPoste(),
                corps.idMetier(),
                corps.idSecteur(),
                idEntreprise,
                corps.idsCompetences(),
                corps.idsCriteres(),
                corps.idsPrerequis(),
                corps.idsQualifications());

        return creerOffreUseCase.executer(requete);
    }

    @PostMapping("/{idOffre}/publier")
    @PreAuthorize("hasAuthority('PERM_OFFRE_PUBLIER')")
    public PublierOffreResponse publier(@PathVariable String idOffre,
                                        @RequestBody ApiDtos.PublierOffre corps) {
        String idAgent = agentFoneaCourant.idObligatoire();

        PublierOffreRequest requete = new PublierOffreRequest(
                idOffre, idAgent, corps.dateExpiration());
        return publierOffreUseCase.executer(requete);
    }

    private static ApiDtos.OffreVue vue(OffresStages offre) {
        return new ApiDtos.OffreVue(
                offre.getIdOffre(),
                offre.getIntitule(),
                offre.getDescription(),
                offre.getVilleStage(),
                offre.getDureeMois(),
                offre.getStatutOffre() != null ? offre.getStatutOffre().name() : null,
                offre.getNbrePlaces(),
                offre.getIdPoste(),
                offre.getIdDomaine(),
                offre.getIdConvention());
    }
}
