package fonea.ped.useCase.useCaseInsertion.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.insertion.TypeContrat;

public interface TypeContratRepository {
	
	TypeContrat sauvegarder(TypeContrat typeContrat);

    Optional<TypeContrat> trouverParId(String idTypeContrat);

    List<TypeContrat> trouverTous();

    boolean existeParId(String idTypeContrat);

    String prochainId();
}
