package fonea.ped.interfaceAdapters.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fonea.ped.domain.exceptions.ReferenceIntrouvableException;
import fonea.ped.interfaceAdapters.controllers.dto.ApiDtos;
import fonea.ped.useCase.useCaseEntreprise.useCase.CreerEntrepriseRequest;
import fonea.ped.useCase.useCaseEntreprise.useCase.CreerEntrepriseResponse;
import fonea.ped.useCase.useCaseEntreprise.useCase.CreerEntrepriseUseCase;
import fonea.ped.useCase.useCaseSecurite.UtilisateurRepository;

/**
 * API de création des entreprises (et de leur rattachement aux secteurs).
 */
@RestController
@RequestMapping("/api/entreprises")
public class EntrepriseController {

    private final CreerEntrepriseUseCase creerEntrepriseUseCase;
    private final UtilisateurRepository utilisateurRepository;

    public EntrepriseController(CreerEntrepriseUseCase creerEntrepriseUseCase,
                                UtilisateurRepository utilisateurRepository) {
        this.creerEntrepriseUseCase = creerEntrepriseUseCase;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERM_ENTREPRISE_CREER')")
    public CreerEntrepriseResponse creer(@RequestBody ApiDtos.CreerEntreprise corps) {
        if (corps.idUtilisateur() == null || corps.idUtilisateur().isBlank()) {
            throw new IllegalArgumentException(
                    "L'identifiant du compte utilisateur de l'entreprise est obligatoire.");
        }
        if (!utilisateurRepository.existeParId(corps.idUtilisateur())) {
            throw new ReferenceIntrouvableException("Utilisateur", corps.idUtilisateur());
        }

        CreerEntrepriseRequest requete = new CreerEntrepriseRequest(
                corps.raisonSocial(),
                corps.adresse(),
                corps.niu(),
                corps.idUtilisateur(),
                corps.idsSecteurs());

        return creerEntrepriseUseCase.executer(requete);
    }
}
