package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.Offres.Publication;

public interface PublicationRepository {
	
	Publication sauvegarder(Publication publication);

    Optional<Publication> trouverParId(String idPublication);

    List<Publication> trouverParOffre(String idOffre);
    
    List<Publication> trouverToutes();

    Optional<Publication> trouverActiveParOffre(String idOffre);

    String prochainId();
    
    boolean existeParId(String idPublication);
    
    void supprimer(String idPublication);
}
