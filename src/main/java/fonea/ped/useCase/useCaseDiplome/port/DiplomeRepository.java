package fonea.ped.useCase.useCaseDiplome.port;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.diplome.Diplome;

public interface DiplomeRepository {
	
	Diplome sauvegarder(Diplome diplome);

    Optional<Diplome> trouverParId(String idDiplome);

    List<Diplome> trouverTous();

    boolean existeParId(String idDiplome);

    String prochainId();
}
