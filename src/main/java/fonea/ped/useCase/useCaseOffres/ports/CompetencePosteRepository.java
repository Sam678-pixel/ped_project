package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;

import fonea.ped.domain.Offres.CompetencePoste;

public interface CompetencePosteRepository {

    CompetencePoste sauvegarder(CompetencePoste liaison);

    List<CompetencePoste> trouverParCompetence(String idCompetence);

    List<CompetencePoste> trouverParPoste(String idPoste);

    boolean existe(String idCompetence, String idPoste);

    void supprimer(String idCompetence, String idPoste);
}
