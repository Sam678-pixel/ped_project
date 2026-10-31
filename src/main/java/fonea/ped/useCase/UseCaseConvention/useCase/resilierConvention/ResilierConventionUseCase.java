package fonea.ped.useCase.UseCaseConvention.useCase.resilierConvention;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fonea.ped.domain.Offres.Competence;
import fonea.ped.domain.Offres.OffresStages;
import fonea.ped.domain.Offres.StatutOffre;
import fonea.ped.useCase.useCaseOffres.ports.*;

import fonea.ped.useCase.UseCaseConvention.ports.ConventionRepository;
import fonea.ped.useCase.UseCaseConvention.ports.ResiliationRepository;
import fonea.ped.domain.convention.model.*;
import fonea.ped.domain.exceptions.ConventionDejaResilieeException;

import fonea.ped.domain.exceptions.ConventionIntrouvableException;
import fonea.ped.domain.exceptions.ConventionNonActiveException;
import fonea.ped.domain.exceptions.MotifObligatoireException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResilierConventionUseCase {
	
	private final ConventionRepository conventionRepository;
    private final ResiliationRepository resiliationRepository;
    private final OffreRepository offreRepository;

    public ResilierConventionUseCase(ConventionRepository conventionRepository,
                                     ResiliationRepository resiliationRepository,
                                     OffreRepository offreRepository) {
        this.conventionRepository = conventionRepository;
        this.resiliationRepository = resiliationRepository;
        this.offreRepository = offreRepository;
    }

    public ResilierConventionResponse executer(ResilierConventionRequest request) {

        // 1. Vérifier existence de la convention
        Convention convention = conventionRepository.trouverParId(request.getIdConvention())
                .orElseThrow(() -> new ConventionIntrouvableException(request.getIdConvention()));

        // 2. Vérifier que la convention est ACTIVE
        if (convention.getStatutConvention() == StatutConvention.RESILIEE) {
            throw new ConventionDejaResilieeException(convention.getIdConvention());
        }
        if (convention.getStatutConvention() != StatutConvention.ACTIVE) {
            throw new ConventionNonActiveException(
                    convention.getIdConvention(),
                    convention.getStatutConvention().name()
            );
        }

        // 3. Vérifier le motif
        if (request.getMotif() == null || request.getMotif().isBlank()) {
            throw new MotifObligatoireException();
        }
        if (request.getInitiateur() == null || request.getInitiateur().isBlank()) {
            throw new MotifObligatoireException(); // ou une InitiateurObligatoireException
        }

        // 4. Annuler toutes les offres actives liées à la convention
        List<OffresStages> offresActives = offreRepository.trouverParConvention(request.getIdConvention());
        List<String> idsOffresAnnulees = new ArrayList<>();
        for (OffresStages offre : offresActives) {
            offre.changerStatut(StatutOffre.ANNULEE);
            offreRepository.sauvegarder(offre);
            idsOffresAnnulees.add(offre.getIdOffre());
        }

        // 5. Créer la Resiliation
        String idResiliation = resiliationRepository.prochainId();
        Resiliation resiliation = new Resiliation(
                idResiliation,
                LocalDate.now(),
                request.getInitiateur(),
                convention.getIdConvention()
                );
       
        resiliationRepository.sauvegarder(resiliation);

        // 6. Mettre à jour la convention
        convention.resilier(idResiliation, request.getMotif(), request.getInitiateur());
        conventionRepository.sauvegarder(convention);

        // 7. Réponse
        return new ResilierConventionResponse(
                resiliation.getIdResiliation(),
                convention.getIdConvention(),
                resiliation.getDateResiliation(),
                resiliation.getInitiateur(),
                resiliation.getMotifResiliation(),
                idsOffresAnnulees
                /*
                 * (                  String idResiliation, LocalDate dateResiliation,
                       String initiateur, String idConvention,MotifResiliation motifResiliation)
                 */
        );
    }
}
