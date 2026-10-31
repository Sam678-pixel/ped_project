package fonea.ped.useCase.useCaseMiseEnStage.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.MiseEnStage.Decrochage;

public interface DecrochageRepository {
	
	Decrochage sauvegarder(Decrochage decrochage);

    Optional<Decrochage> trouverParId(String idDecrochage);

    List<Decrochage> trouverTous();

    Optional<Decrochage> trouverParMiseEnStage(String idMiseStage);

    boolean existeParMiseEnStage(String idMiseStage);
    
    public boolean existeParId(String id);
    
    public void supprimer(String id);

    String prochainId();
}
