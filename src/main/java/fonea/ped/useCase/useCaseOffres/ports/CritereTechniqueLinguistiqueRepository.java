package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;

import fonea.ped.domain.Offres.CritereTechniqueLinguistique;

public interface CritereTechniqueLinguistiqueRepository {

    CritereTechniqueLinguistique sauvegarder(CritereTechniqueLinguistique liaison);

    List<CritereTechniqueLinguistique> trouverParCritere(String idCritere);

    List<CritereTechniqueLinguistique> trouverParTechniqueLinguistique(String idTechniqueLinguistique);

    boolean existe(String idCritere, String idTechniqueLinguistique);

    void supprimer(String idCritere, String idTechniqueLinguistique);
}
