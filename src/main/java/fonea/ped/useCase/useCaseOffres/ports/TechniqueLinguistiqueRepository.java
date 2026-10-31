package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.Offres.TechniqueLinguistique;

public interface TechniqueLinguistiqueRepository {

    TechniqueLinguistique sauvegarder(TechniqueLinguistique technique);

    Optional<TechniqueLinguistique> trouverParId(String idTechniqueLinguistique);

    List<TechniqueLinguistique> trouverTous();

    boolean existeParId(String idTechniqueLinguistique);

    String prochainId();
}