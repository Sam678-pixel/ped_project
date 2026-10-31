package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.Offres.OffresStages;

public interface OffreRepository {
	
	OffresStages sauvegarder(OffresStages offre);

    Optional<OffresStages> trouverParId(String idOffre);

    List<OffresStages> trouverToutes();

    public List<OffresStages> trouverParConvention(String idConvention);

    boolean existeParId(String idOffre);

    String prochainId();
    
    public List<OffresStages>offrePubliees();
    
    void supprimer(String idOffre);
}
