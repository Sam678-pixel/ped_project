package fonea.ped.interfaceAdapters.controllers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fonea.ped.domain.exceptions.ReferenceIntrouvableException;
import fonea.ped.infrastructure.securite.UtilisateurCourant;
import fonea.ped.interfaceAdapters.controllers.dto.ApiDtos;
import fonea.ped.useCase.useCaseDiplome.useCase.creerDiplome.CreerDiplomeRequest;
import fonea.ped.useCase.useCaseDiplome.useCase.creerDiplome.CreerDiplomeResponse;
import fonea.ped.useCase.useCaseDiplome.useCase.creerDiplome.CreerDiplomeUseCase;
import fonea.ped.useCase.useCaseSecurite.UtilisateurRepository;

/**
 * API de création des profils diplômés.
 *
 * <p>Le profil est toujours rattaché à un compte {@code Utilisateur} existant :
 * sans ce contrôle, la clé étrangère échouait en 500 au lieu d'un 404 lisible.</p>
 */
@RestController
@RequestMapping("/api/diplomes")
public class DiplomeController {

    private final CreerDiplomeUseCase creerDiplomeUseCase;
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurCourant utilisateurCourant;

    public DiplomeController(CreerDiplomeUseCase creerDiplomeUseCase,
                             UtilisateurRepository utilisateurRepository,
                             UtilisateurCourant utilisateurCourant) {
        this.creerDiplomeUseCase = creerDiplomeUseCase;
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurCourant = utilisateurCourant;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERM_DIPLOME_CREER')")
    public CreerDiplomeResponse creer(@RequestBody ApiDtos.CreerDiplome corps) {
        String idUtilisateur = corps.idUtilisateur() != null && !corps.idUtilisateur().isBlank()
                ? corps.idUtilisateur()
                : utilisateurCourant.id();

        // Un compte diplômé ne crée que son propre profil.
        if (utilisateurCourant.aRole("DIPLOME")
                && !utilisateurCourant.estAdmin()
                && !idUtilisateur.equals(utilisateurCourant.id())) {
            throw new AccessDeniedException(
                    "Un diplômé ne peut créer que son propre profil.");
        }

        if (!utilisateurRepository.existeParId(idUtilisateur)) {
            throw new ReferenceIntrouvableException("Utilisateur", idUtilisateur);
        }

        CreerDiplomeRequest requete = new CreerDiplomeRequest(
                corps.nom(),
                corps.prenom(),
                corps.dateNaissance(),
                corps.sexe(),
                corps.niveauDiplome(),
                corps.anneeObtention(),
                corps.filiere(),
                idUtilisateur);

        return creerDiplomeUseCase.executer(requete);
    }
}
