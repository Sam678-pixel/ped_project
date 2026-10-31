package fonea.ped.useCase.useCaseRecrutement.ports;

import java.util.List;

import fonea.ped.domain.Offres.PreselectionCritere;

public interface PreselectionCritereRepository {

    PreselectionCritere sauvegarder(PreselectionCritere liaison);

    List<PreselectionCritere> trouverParPreselection(String idPreselection);

    List<PreselectionCritere> trouverParCritere(String idCritere);

    boolean existe(String idPreselection, String idCritere);

    void supprimer(String idPreselection, String idCritere);
}
