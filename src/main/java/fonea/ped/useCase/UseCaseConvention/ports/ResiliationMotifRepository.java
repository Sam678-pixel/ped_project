package fonea.ped.useCase.UseCaseConvention.ports;

import java.util.List;

import fonea.ped.domain.convention.model.ResiliationMotif;

public interface ResiliationMotifRepository {

    ResiliationMotif sauvegarder(ResiliationMotif liaison);

    List<ResiliationMotif> trouverParResiliation(String idResiliation);

    List<ResiliationMotif> trouverParMotif(String idMotifResiliation);

    boolean existe(String idResiliation, String idMotifResiliation);

    void supprimer(String idResiliation, String idMotifResiliation);
}