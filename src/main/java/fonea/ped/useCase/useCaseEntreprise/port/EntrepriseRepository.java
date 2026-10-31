package fonea.ped.useCase.useCaseEntreprise.port;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.entreprise.Entreprise;

public interface EntrepriseRepository {
	
	Entreprise sauvegarder(Entreprise entreprise);

    Optional<Entreprise> trouverParId(String idEntreprise);

    List<Entreprise> trouverToutes();

    boolean existeParId(String idEntreprise);

    String prochainId();
}
