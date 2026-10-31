package fonea.ped.useCase.UseCaseConvention.useCase.creerConvention;

import java.time.LocalDate;


import fonea.ped.useCase.UseCaseConvention.ports.ConventionRepository;
import fonea.ped.useCase.UseCaseConvention.ports.ProgrammeRepository;
import fonea.ped.domain.convention.model.Convention;
import fonea.ped.domain.convention.model.Programme;
import fonea.ped.domain.convention.model.StatutConvention;
import fonea.ped.useCase.useCaseEntreprise.port.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreerConventionUseCase {
	
	private final ConventionRepository conventionRepository;
    private final ProgrammeRepository programmeRepository;
    private final EntrepriseRepository entrepriseRepository;

    public CreerConventionUseCase(ConventionRepository conventionRepository,
                                  ProgrammeRepository programmeRepository,
                                  EntrepriseRepository entrepriseRepository) {
        this.conventionRepository = conventionRepository;
        this.programmeRepository = programmeRepository;
        this.entrepriseRepository = entrepriseRepository;
    }

    public CreerConventionResponse executer(CreerConventionRequest request) {

        // 1. Validation des données d'entrée
        if (request.getDateEffet() == null) {
            throw new IllegalArgumentException("La date d'effet est obligatoire.");
        }
        if (request.getDateEffet().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date d'effet ne peut pas être dans le passé.");
        }
        if (request.getTauxFonea() == null || request.getTauxFonea() < 0) {
            throw new IllegalArgumentException("Le taux FONEA doit être positif.");
        }
        if (request.getTauxEntreprise() == null || request.getTauxEntreprise() < 0) {
            throw new IllegalArgumentException("Le taux Entreprise doit être positif.");
        }

        // 2. Vérifier l'existence du programme
        Programme programme = programmeRepository.trouverParId(request.getIdProgramme())
                .orElseThrow(() -> new IllegalStateException(
                        "Le programme avec l'id " + request.getIdProgramme() + " n'existe pas."));

        // 3. Vérifier l'existence de l'entreprise
        boolean entrepriseExiste = entrepriseRepository.existeParId(request.getIdEntreprise());
        if (!entrepriseExiste) {
            throw new IllegalStateException(
                    "L'entreprise avec l'id " + request.getIdEntreprise() + " n'existe pas.");
        }

        // 4. Création de l'entité métier
        String nouvelId = conventionRepository.prochainId();
        Convention convention = new Convention(
                nouvelId,
                request.getDateEffet(),
                request.getTauxFonea(),
                request.getTauxEntreprise(),
                StatutConvention.BROUILLON,
                request.getTypeConvention(),
                request.getIdEntreprise()
        );
        
        /*
             public Convention(String idConvention, LocalDate dateEffet, Double tauxFonea,Double tauxEntreprise,
                      StatutConvention statutConvention, String typeConvention, String idEntreprise)
         */

        // 5. Persistance via le port
        conventionRepository.sauvegarder(convention);

        // 6. Construction de la réponse
        return new CreerConventionResponse(
                convention.getIdConvention(),
                convention.getDateEffet(),
                convention.getTauxFonea(),
                convention.getTauxEntreprise(),
                convention.getStatutConvention(),
                programme.getIdProgramme(),
                request.getIdEntreprise()
        );
    }
}
