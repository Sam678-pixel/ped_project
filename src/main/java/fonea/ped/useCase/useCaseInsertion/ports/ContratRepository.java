package fonea.ped.useCase.useCaseInsertion.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.insertion.Contrat;

public interface ContratRepository {
	
	Contrat sauvegarder(Contrat contrat);

    Optional<Contrat> trouverParId(String idContrat);

    List<Contrat> trouverTous();
    
    //public List<Contrat> trouverParTypeContrat(String id);
    
    public boolean existeParId(String id);
    
    //public boolean existeParInsertion(String idInsertion);
    
    public void supprimer(String id);

    Optional<Contrat> trouverParInsertion(String idInsertion);

    String prochainId();
	
}
