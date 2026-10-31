package fonea.ped.useCase.UseCaseConvention.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.convention.model.Resiliation;

public interface ResiliationRepository {
	
	Resiliation sauvegarder(Resiliation resiliation);

    Optional<Resiliation> trouverParId(String idResiliation);

    List<Resiliation> trouverParConvention(String idConvention);

    List<Resiliation> trouverToutes();

    boolean existeParId(String idResiliation);

    void supprimer(String idResiliation);

    String prochainId();
}
