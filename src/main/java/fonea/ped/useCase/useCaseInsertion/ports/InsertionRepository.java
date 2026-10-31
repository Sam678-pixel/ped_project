package fonea.ped.useCase.useCaseInsertion.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.insertion.Insertion;

public interface InsertionRepository {
	
	Insertion sauvegarder(Insertion insertion);

    Optional<Insertion> trouverParId(String idInsertion);

    List<Insertion> trouverToutes();
    
    //public List<Insertion> trouverParPoste (String id);

    Optional<Insertion> trouverParMiseEnStage(String idMiseStage);

    boolean existeParMiseEnStage(String idMiseStage);
    
    public boolean existeParId(String id);
    
    public void supprimer(String id);

    String prochainId();
}
