package fonea.ped.infrastructure.securite;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import fonea.ped.domain.securite.AgentFonea;
import fonea.ped.useCase.useCaseSecurite.AgentFoneaRepository;

/**
 * Résout la fiche {@code AgentFonea} du compte connecté.
 *
 * <p>Les tables {@code Publication} et {@code Preselection} référencent
 * {@code AgentFonea.idAgent} (et non {@code Utilisateur.idUtilisateur}) : il
 * faut donc traduire l'identité de l'appelant avant d'écrire ces lignes.</p>
 */
@Component
public class AgentFoneaCourant {

    private final AgentFoneaRepository agentFoneaRepository;
    private final UtilisateurCourant utilisateurCourant;

    public AgentFoneaCourant(AgentFoneaRepository agentFoneaRepository,
                             UtilisateurCourant utilisateurCourant) {
        this.agentFoneaRepository = agentFoneaRepository;
        this.utilisateurCourant = utilisateurCourant;
    }

    public Optional<String> id() {
        String idUtilisateur = utilisateurCourant.id();
        return agentFoneaRepository.trouverTous().stream()
                .filter(agent -> idUtilisateur.equals(agent.getIdUtilisateur()))
                .map(AgentFonea::getIdAgent)
                .findFirst();
    }

    public String idObligatoire() {
        return id().orElseThrow(() -> new AccessDeniedException(
                "Seul un agent FONEA peut effectuer cette opération."));
    }
}
