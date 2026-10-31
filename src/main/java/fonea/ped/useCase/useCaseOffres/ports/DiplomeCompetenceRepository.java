package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;

import fonea.ped.domain.diplome.DiplomeCompetence;

public interface DiplomeCompetenceRepository {

    DiplomeCompetence sauvegarder(DiplomeCompetence liaison);

    List<DiplomeCompetence> trouverParDiplome(String idDiplome);

    List<DiplomeCompetence> trouverParCompetence(String idCompetence);

    boolean existe(String idDiplome, String idCompetence);

    void supprimer(String idDiplome, String idCompetence);
}
