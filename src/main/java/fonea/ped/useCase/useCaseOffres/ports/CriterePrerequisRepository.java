package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;

import fonea.ped.domain.Offres.CriterePrerequis;

public interface CriterePrerequisRepository {

    CriterePrerequis sauvegarder(CriterePrerequis liaison);

    List<CriterePrerequis> trouverParCritere(String idCritere);

    List<CriterePrerequis> trouverParPrerequis(String idPrerequis);

    boolean existe(String idCritere, String idPrerequis);

    void supprimer(String idCritere, String idPrerequis);
}
