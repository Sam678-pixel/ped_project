package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.Offres.PrerequisFormation;

public interface PrerequisRepository {
	PrerequisFormation sauvegarder(PrerequisFormation prerequis);
    Optional<PrerequisFormation> trouverParId(String idPrerequis);
    List<PrerequisFormation> trouverTous();
    boolean existeParId(String idPrerequis);
    String prochainId();
}
