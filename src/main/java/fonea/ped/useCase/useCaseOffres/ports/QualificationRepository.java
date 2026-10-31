package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.Offres.Qualification;

public interface QualificationRepository {
	Qualification sauvegarder(Qualification qualification);
    Optional<Qualification> trouverParId(String idQualification);
    List<Qualification> trouverToutes();
    boolean existeParId(String idQualification);
    String prochainId();

}
