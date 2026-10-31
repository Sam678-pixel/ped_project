package fonea.ped.useCase.UseCaseConvention.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.convention.model.*;

public interface ConventionRepository {
	
	Convention sauvegarder(Convention convention);

    Optional<Convention> trouverParId(String idConvention);

    List<Convention> trouverToutes();

   // List<Convention> trouverActivesParEntreprise(String idEntreprise);
    
    List<Convention> trouverParEntreprise(String idEntreprise);
    
    List<Convention> trouverActives();

    boolean existeParId(String idConvention);
    
    void supprimer(String idConvention);

    String prochainId();
}
