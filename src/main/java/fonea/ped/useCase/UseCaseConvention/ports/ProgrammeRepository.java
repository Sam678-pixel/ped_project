package fonea.ped.useCase.UseCaseConvention.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.convention.model.Programme;
public interface ProgrammeRepository {
	
	Programme sauvegarder(Programme programme);

    Optional<Programme> trouverParId(String idProgramme);

    List<Programme> trouverTous();

    boolean existeParId(String idProgramme);

    String prochainId();
}
