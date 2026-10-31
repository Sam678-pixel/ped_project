package fonea.ped.useCase.useCaseEntreprise.useCase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fonea.ped.domain.entreprise.Entreprise;
import fonea.ped.domain.entreprise.EntrepriseSecteur;
import fonea.ped.domain.exceptions.ReferenceIntrouvableException;
import fonea.ped.useCase.useCaseEntreprise.port.EntrepriseRepository;
import fonea.ped.useCase.useCaseOffres.ports.EntrepriseSecteurRepository;
import fonea.ped.useCase.useCaseOffres.ports.SecteurRepository;

/**
 * Cas d'usage « créer une entreprise » (et la rattacher à ses secteurs).
 *
 * <p>Le {@code SecteurRepository} était injecté mais jamais utilisé, et les
 * liaisons {@code appartient_a} n'étaient jamais écrites.</p>
 */
@Service
@Transactional
public class CreerEntrepriseUseCase {

    private final EntrepriseRepository entrepriseRepository;
    private final SecteurRepository secteurRepository;
    private final EntrepriseSecteurRepository entrepriseSecteurRepository;

    public CreerEntrepriseUseCase(EntrepriseRepository entrepriseRepository,
                                  SecteurRepository secteurRepository,
                                  EntrepriseSecteurRepository entrepriseSecteurRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.secteurRepository = secteurRepository;
        this.entrepriseSecteurRepository = entrepriseSecteurRepository;
    }

    public CreerEntrepriseResponse executer(CreerEntrepriseRequest request) {

        if (request.getRaisonSocial() == null || request.getRaisonSocial().isBlank()) {
            throw new IllegalArgumentException("La raison sociale est obligatoire.");
        }

        List<String> idsSecteurs = request.getIdsSecteurs();
        if (idsSecteurs != null) {
            for (String idSecteur : idsSecteurs) {
                if (idSecteur == null || !secteurRepository.existeParId(idSecteur)) {
                    throw new ReferenceIntrouvableException("Secteur", idSecteur);
                }
            }
        }

        String nouvelId = entrepriseRepository.prochainId();
        Entreprise entreprise = new Entreprise(
                nouvelId,
                request.getRaisonSocial(),
                request.getAdresse(),
                request.getNiu(),
                request.getIdUtilisateur()
        );
        entrepriseRepository.sauvegarder(entreprise);

        if (idsSecteurs != null) {
            for (String idSecteur : idsSecteurs) {
                entrepriseSecteurRepository.sauvegarder(
                        new EntrepriseSecteur(entreprise.getIdEntreprise(), idSecteur));
            }
        }

        return new CreerEntrepriseResponse(
                entreprise.getIdEntreprise(),
                entreprise.getRaisonSociale(),
                entreprise.getAdresse(),
                entreprise.getNiu(),
                entreprise.getIdUtilisateur()
        );
    }
}
