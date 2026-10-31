package fonea.ped.useCase.useCaseSecurite;

import fonea.ped.domain.securite.AgentFonea;

import java.util.List;
import java.util.Optional;

public interface AgentFoneaRepository {

    AgentFonea sauvegarder(AgentFonea agent);

    Optional<AgentFonea> trouverParId(String idAgent);

    List<AgentFonea> trouverTous();

    boolean existeParId(String idAgent);

    String prochainId();
}
