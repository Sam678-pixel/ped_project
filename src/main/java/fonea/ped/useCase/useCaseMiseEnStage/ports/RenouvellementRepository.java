package fonea.ped.useCase.useCaseMiseEnStage.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.MiseEnStage.Renouvellement;

public interface RenouvellementRepository {
	
	Renouvellement sauvegarder(Renouvellement renouvellement);

    Optional<Renouvellement> trouverParId(String idRenouvellement);

    List<Renouvellement> trouverTous();

    Optional<Renouvellement> trouverParMiseEnStage(String idMiseStage);

    boolean existeParMiseEnStage(String idMiseStage);
    
    public boolean existeParId(String id);
    
    public void supprimer(String id);

    String prochainId();
}
