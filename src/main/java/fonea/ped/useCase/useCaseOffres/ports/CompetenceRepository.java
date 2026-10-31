package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.Offres.Competence;

public interface CompetenceRepository {
    Competence sauvegarder(Competence competence);
    Optional<Competence> trouverParId(String idCompetence);
    List<Competence> trouverToutes();
    boolean existeParId(String idCompetence);
    String prochainId();

}
