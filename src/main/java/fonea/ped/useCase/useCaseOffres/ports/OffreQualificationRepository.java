package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;

import fonea.ped.domain.Offres.OffreQualification;

public interface OffreQualificationRepository {
	
	OffreQualification sauvegarder(OffreQualification liaison);
    List<OffreQualification> trouverParOffre(String idOffre);
    String prochainId();
}
