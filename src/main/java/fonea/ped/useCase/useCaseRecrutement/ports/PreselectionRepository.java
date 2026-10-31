package fonea.ped.useCase.useCaseRecrutement.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.recrutement.Preselection;

public interface PreselectionRepository {
	
	Preselection sauvegarder(Preselection preselection);

    Optional<Preselection> trouverParId(String idPreselection);

    List<Preselection> trouverToutes();

    List<Preselection> trouverParCandidature(String idCandidature);
    
    boolean existeParId(String idPreselection);

    boolean existeParCandidature(String idCandidature);
    
    List<Preselection> trouverParAgent(String idAgent);
    
    void supprimer(String idSelection);

    String prochainId();
}
