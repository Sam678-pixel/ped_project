package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;

import fonea.ped.domain.Offres.OffrePrerequis;

public interface OffrePrerequisRepository {
	OffrePrerequis sauvegarder(OffrePrerequis liaison);
    List<OffrePrerequis> trouverParOffre(String idOffre);
    String prochainId();
}
