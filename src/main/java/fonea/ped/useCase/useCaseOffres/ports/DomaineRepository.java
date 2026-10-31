package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.Offres.Domaine;

public interface DomaineRepository {
    Domaine sauvegarder(Domaine domaine);
    Optional<Domaine> trouverParId(String idDomaine);
    List<Domaine> trouverTous();
    boolean existeParId(String idDomaine);
    String prochainId();
}
