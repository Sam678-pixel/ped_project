package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;

import fonea.ped.domain.Offres.OffreCritere;

public interface OffreCritereRepository {
	OffreCritere sauvegarder(OffreCritere liaison);
    List<OffreCritere> trouverParOffre(String idOffre);
    String prochainId();
	List<OffreCritere> trouverParCritere(String id);
}
