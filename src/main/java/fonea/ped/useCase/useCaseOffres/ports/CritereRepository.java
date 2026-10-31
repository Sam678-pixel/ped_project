package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.Offres.Critere;

public interface CritereRepository {
	Critere sauvegarder(Critere critere);
    Optional<Critere> trouverParId(String idCritere);
    List<Critere> trouverTous();
    boolean existeParId(String idCritere);
    String prochainId();
}
