package fonea.ped.useCase.UseCaseConvention.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.convention.model.Motif;


public interface MotifRepository {
	Motif sauvegarder(Motif motif);

    Optional<Motif> trouverParId(String idMotif);

    List<Motif> trouverTous();

    boolean existeParId(String idMotif);

    String prochainId();
}
