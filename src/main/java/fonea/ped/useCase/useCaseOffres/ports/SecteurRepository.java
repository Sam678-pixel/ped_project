package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.Offres.Secteur;

public interface SecteurRepository {
    Secteur sauvegarder(Secteur secteur);
    Optional<Secteur> trouverParId(String idSecteur);
    List<Secteur> trouverTous();
    boolean existeParId(String idSecteur);
    String prochainId();
}
