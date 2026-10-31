package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.Offres.Poste;

public interface PosteRepository {
    Poste sauvegarder(Poste poste);
    Optional<Poste> trouverParId(String idPoste);
    List<Poste> trouverTous();
    boolean existeParId(String idPoste);
    String prochainId();
}
