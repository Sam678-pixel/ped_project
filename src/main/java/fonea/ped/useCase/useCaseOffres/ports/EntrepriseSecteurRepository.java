package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;

import fonea.ped.domain.entreprise.EntrepriseSecteur;

public interface EntrepriseSecteurRepository {

    EntrepriseSecteur sauvegarder(EntrepriseSecteur liaison);

    List<EntrepriseSecteur> trouverParEntreprise(String idEntreprise);

    List<EntrepriseSecteur> trouverParSecteur(String idSecteur);

    boolean existe(String idEntreprise, String idSecteur);

    void supprimer(String idEntreprise, String idSecteur);
}
