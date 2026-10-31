package fonea.ped.useCase.useCaseRecrutement.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.recrutement.Candidature;

public interface CandidatureRepository {
	
	Candidature sauvegarder(Candidature candidature);

    Optional<Candidature> trouverParId(String idCandidature);

    List<Candidature> trouverToutes();
    
    List<Candidature> trouverParPublication(String idPublication);
    
    List<Candidature> candidaturesParDiplome(String idDiplome);

    List<Candidature> trouverParOffre(String idPublication);

    boolean existeParDiplomeEtPublication(String idDiplome, String idPublication);
    
    boolean existeParId(String idCandidature);
    
    void supprimer(String idCandidature);

    String prochainId();
}
