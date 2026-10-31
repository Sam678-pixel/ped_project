package fonea.ped.useCase.useCaseMiseEnStage.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.MiseEnStage.MiseEnStage;

public interface MiseEnStageRepository {
	
	MiseEnStage sauvegarder(MiseEnStage miseEnStage);

    Optional<MiseEnStage> trouverParId(String idMiseStage);

    List<MiseEnStage> trouverToutes();
    
    public List<MiseEnStage> trouverParEntreprise(String idEntreprise);

    Optional<MiseEnStage> trouverParSelection(String idSelection);

    boolean existeParSelection(String idSelection);
    
    public boolean existeParId(String id) ;
    
    public void supprimer(String id);

    String prochainId();
}
