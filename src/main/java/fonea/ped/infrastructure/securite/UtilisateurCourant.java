package fonea.ped.infrastructure.securite;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import fonea.ped.domain.entreprise.Entreprise;
import fonea.ped.domain.exceptions.DiplomeIntrouvableException;
import fonea.ped.domain.diplome.Diplome;
import fonea.ped.useCase.useCaseDiplome.port.DiplomeRepository;
import fonea.ped.useCase.useCaseEntreprise.port.EntrepriseRepository;

/**
 * Contexte de l'appelant : qui est connecté, à quelle entreprise il est
 * rattaché, et quelles permissions il possède.
 *
 * <p>C'est la brique « contexte » du TBAC : une permission seule ne suffit pas,
 * il faut aussi que la ressource appartienne à l'appelant.</p>
 */
@Component
public class UtilisateurCourant {

    private final EntrepriseRepository entrepriseRepository;
    private final DiplomeRepository diplomeRepository;

    public UtilisateurCourant(EntrepriseRepository entrepriseRepository,
                              DiplomeRepository diplomeRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.diplomeRepository = diplomeRepository;
    }

    public String id() {
        Authentication authentification = SecurityContextHolder.getContext().getAuthentication();
        if (authentification == null || !authentification.isAuthenticated()) {
            throw new IllegalStateException("Aucun utilisateur authentifié.");
        }
        return authentification.getName();
    }

    public boolean possede(String permission) {
        Authentication authentification = SecurityContextHolder.getContext().getAuthentication();
        if (authentification == null) {
            return false;
        }
        String autorite = "PERM_" + permission;
        return authentification.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(autorite::equals);
    }

    public boolean estAdmin() {
        return possede("ADMIN_TOTAL");
    }

    /** Un agent FONEA voit l'ensemble des ressources, sans filtrage par entreprise. */
    public boolean estAgentFonea() {
        return aRole("AGENT_FONEA");
    }

    /** Vrai si l'appelant voit toutes les ressources (administrateur ou agent FONEA). */
    public boolean voitTout() {
        return estAdmin() || estAgentFonea();
    }

    public boolean aRole(String codeRole) {
        Authentication authentification = SecurityContextHolder.getContext().getAuthentication();
        if (authentification == null) {
            return false;
        }
        String autorite = "ROLE_" + codeRole;
        return authentification.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(autorite::equals);
    }

    /** Entreprise rattachée au compte connecté (un utilisateur ENTREPRISE). */
    public Optional<String> idEntreprise() {
        String idUtilisateur = id();
        return entrepriseRepository.trouverToutes().stream()
                .filter(entreprise -> idUtilisateur.equals(entreprise.getIdUtilisateur()))
                .map(Entreprise::getIdEntreprise)
                .findFirst();
    }

    public String idEntrepriseObligatoire() {
        return idEntreprise().orElseThrow(() -> new IllegalStateException(
                "Aucune entreprise n'est rattachée au compte " + id() + "."));
    }

    /** Profil diplômé rattaché au compte connecté (un utilisateur DIPLOME). */
    public Optional<String> idDiplome() {
        String idUtilisateur = id();
        return diplomeRepository.trouverTous().stream()
                .filter(diplome -> idUtilisateur.equals(diplome.getIdUtilisateur()))
                .map(Diplome::getIdDiplome)
                .findFirst();
    }

    public String idDiplomeObligatoire() {
        return idDiplome().orElseThrow(() -> new IllegalStateException(
                "Aucun profil diplômé n'est rattaché au compte " + id() + "."));
    }

    /**
     * Vérifie que l'appelant agit bien sur son entreprise.
     *
     * <p>Un administrateur ou un agent FONEA (non rattaché à une entreprise)
     * peut agir sur n'importe quelle entreprise ; un compte ENTREPRISE est
     * strictement limité à la sienne.</p>
     */
    public void exigerEntreprise(String idEntreprise) {
        if (estAdmin() || estAgentFonea()) {
            return;
        }
        Optional<String> sienne = idEntreprise();
        if (sienne.isEmpty()) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "Aucune entreprise n'est rattachée à votre compte.");
        }
        if (!sienne.get().equals(idEntreprise)) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "Vous n'êtes pas rattaché à l'entreprise " + idEntreprise + ".");
        }
    }

    /**
     * Vérifie que le diplômé visé est bien le compte connecté.
     *
     * <p>Même logique que {@link #exigerEntreprise(String)} : un compte
     * {@code DIPLOME} est limité à son propre profil ; un agent FONEA ou un
     * administrateur peut agir pour n'importe quel diplômé.</p>
     */
    public void exigerDiplome(String idDiplome) {
        if (estAdmin() || estAgentFonea()) {
            return;
        }
        diplomeRepository.trouverParId(idDiplome)
                .orElseThrow(() -> new DiplomeIntrouvableException(idDiplome));

        Optional<Diplome> sien = diplomeRepository.trouverTous().stream()
                .filter(candidat -> id().equals(candidat.getIdUtilisateur()))
                .findFirst();

        if (sien.isEmpty()) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "Aucun profil diplômé n'est rattaché à votre compte.");
        }
        if (!sien.get().getIdDiplome().equals(idDiplome)) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "Ce diplômé n'est pas rattaché à votre compte.");
        }
    }
}
