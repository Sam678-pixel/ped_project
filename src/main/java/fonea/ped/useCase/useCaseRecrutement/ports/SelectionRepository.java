package fonea.ped.useCase.useCaseRecrutement.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.recrutement.Selection;

public interface SelectionRepository {
	
	Selection sauvegarder(Selection selection);

    Optional<Selection> trouverParId(String idSelection);

    List<Selection> trouverToutes();

    List<Selection> trouverParPreselection(String idPreselection);

    boolean existeParPreselection(String idPreselection);
    
    boolean existeParId(String idSelection);
    
    void supprimer(String idSelection);

    String prochainId();
}
