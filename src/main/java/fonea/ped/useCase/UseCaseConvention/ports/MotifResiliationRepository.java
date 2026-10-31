package fonea.ped.useCase.UseCaseConvention.ports;


import java.util.List;
import java.util.Optional;

import fonea.ped.domain.convention.model.MotifResiliation;

public interface MotifResiliationRepository {

    MotifResiliation sauvegarder(MotifResiliation motif);

    Optional<MotifResiliation> trouverParId(String idMotifResiliation);

    List<MotifResiliation> trouverTous();

    boolean existeParId(String idMotifResiliation);

    String prochainId();
}
