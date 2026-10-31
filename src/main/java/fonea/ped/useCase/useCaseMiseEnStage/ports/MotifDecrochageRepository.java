package fonea.ped.useCase.useCaseMiseEnStage.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.MiseEnStage.MotifDecrochage;

/**
 * Port de sortie pour le referentiel des motifs de decrochage.
 * (Table MotifDecrochage, distincte de la liaison est_justifie_par.)
 */
public interface MotifDecrochageRepository {

    MotifDecrochage sauvegarder(MotifDecrochage motif);

    Optional<MotifDecrochage> trouverParId(String idMotifDecrochage);

    List<MotifDecrochage> trouverTous();

    boolean existeParId(String idMotifDecrochage);

    String prochainId();
}
