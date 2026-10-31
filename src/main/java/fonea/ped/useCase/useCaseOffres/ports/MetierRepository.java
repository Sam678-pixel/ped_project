package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.Offres.Metier;

public interface MetierRepository {
    Metier sauvegarder(Metier metier);
    
    Optional<Metier> trouverParId(String idMetier);
    
    List<Metier> trouverTous();
    
    boolean existeParId(String idMetier);
    String prochainId();
}

