package fonea.ped.useCase.useCaseMiseEnStage.ports;

import java.util.List;

import fonea.ped.domain.MiseEnStage.DecrochageMotif;

public interface DecrochageMotifRepository {

    DecrochageMotif sauvegarder(DecrochageMotif liaison);

    List<DecrochageMotif> trouverParDecrochage(String idDecrochage);

    List<DecrochageMotif> trouverParMotif(String idMotifDecrochage);

    boolean existe(String idDecrochage, String idMotifDecrochage);

    void supprimer(String idDecrochage, String idMotifDecrochage);
}
