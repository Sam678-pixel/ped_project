package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;

import fonea.ped.domain.Offres.OffreCompetence;

public interface OffreCompetenceRepository {
	 OffreCompetence sauvegarder(OffreCompetence liaison);
	    List<OffreCompetence> trouverParOffre(String idOffre);
	    String prochainId();
}
