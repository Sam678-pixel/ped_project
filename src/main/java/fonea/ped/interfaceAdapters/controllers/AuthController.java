package fonea.ped.interfaceAdapters.controllers;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fonea.ped.domain.exceptions.ReferenceIntrouvableException;
import fonea.ped.domain.securite.Utilisateur;
import fonea.ped.infrastructure.securite.JwtService;
import fonea.ped.infrastructure.securite.UtilisateurDetailsService;
import fonea.ped.interfaceAdapters.controllers.dto.ApiDtos.Jeton;
import fonea.ped.interfaceAdapters.controllers.dto.ApiDtos.Login;
import fonea.ped.interfaceAdapters.controllers.dto.ApiDtos.UtilisateurConnecte;
import fonea.ped.useCase.useCaseSecurite.UtilisateurRepository;

/**
 * Point d'entrée d'authentification : échange email + mot de passe contre un
 * jeton JWT, puis permet de consulter l'identité courante.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurDetailsService utilisateurDetailsService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
                          UtilisateurRepository utilisateurRepository,
                          UtilisateurDetailsService utilisateurDetailsService,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurDetailsService = utilisateurDetailsService;
        this.jwtService = jwtService;
    }

    /**
     * Authentifie un utilisateur et renvoie un jeton porteur.
     *
     * <p>L'identifiant accepté est l'email (ou l'idUtilisateur) ; la réponse
     * contient aussi le rôle afin que le client puisse adapter son affichage.</p>
     */
    @PostMapping("/login")
    public Jeton login(@RequestBody Login demande) {
        if (demande == null || demande.email() == null || demande.motDePasse() == null) {
            throw new IllegalArgumentException("L'email et le mot de passe sont obligatoires.");
        }

        Authentication authentification = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        demande.email().trim(), demande.motDePasse()));

        Utilisateur utilisateur = utilisateurRepository
                .trouverParId(authentification.getName())
                .orElseThrow(() -> new ReferenceIntrouvableException(
                        "Utilisateur", authentification.getName()));

        String codeRole = utilisateurDetailsService.codeRole(utilisateur);
        String jeton = jwtService.generer(
                utilisateur.getIdUtilisateur(), utilisateur.getEmail(), codeRole);

        return new Jeton(
                jeton,
                "Bearer",
                jwtService.getExpirationSecondes(),
                utilisateur.getIdUtilisateur(),
                utilisateur.getEmail(),
                codeRole);
    }

    /** Identité et autorités de l'appelant authentifié. */
    @GetMapping("/moi")
    public UtilisateurConnecte moi(Authentication authentification) {
        Utilisateur utilisateur = utilisateurRepository
                .trouverParId(authentification.getName())
                .orElseThrow(() -> new ReferenceIntrouvableException(
                        "Utilisateur", authentification.getName()));

        List<String> autorites = authentification.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .sorted()
                .toList();

        return new UtilisateurConnecte(
                utilisateur.getIdUtilisateur(),
                utilisateur.getEmail(),
                utilisateurDetailsService.codeRole(utilisateur),
                autorites);
    }
}
