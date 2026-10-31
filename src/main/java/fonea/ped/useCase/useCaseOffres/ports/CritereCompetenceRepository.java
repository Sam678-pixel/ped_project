package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;

import fonea.ped.domain.Offres.CritereCompetence;

public interface CritereCompetenceRepository {

    CritereCompetence sauvegarder(CritereCompetence liaison);

    List<CritereCompetence> trouverParCritere(String idCritere);

    List<CritereCompetence> trouverParCompetence(String idCompetence);

    boolean existe(String idCritere, String idCompetence);

    void supprimer(String idCritere, String idCompetence);
}
