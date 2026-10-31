package fonea.ped.useCase.useCaseOffres.UseCaseOffres.creerOffre;

import java.util.List;

import fonea.ped.domain.Offres.*;
/*import fonea.ped.domain.Offres.OffreCompetence;
import fonea.ped.domain.Offres.OffreCritere;
import fonea.ped.domain.Offres.OffrePrerequis;
import fonea.ped.domain.Offres.OffreQualification;
import fonea.ped.domain.Offres.OffresStages;
import fonea.ped.domain.Offres.StatutOffre;*/

import fonea.ped.useCase.useCaseOffres.ports.*;
/*
import fonea.ped.Offres.ports.CompetenceRepository;
import fonea.ped.Offres.ports.CritereRepository;
import fonea.ped.Offres.ports.DomaineRepository;
import fonea.ped.Offres.ports.MetierRepository;
import fonea.ped.Offres.ports.OffreCompetenceRepository;
import fonea.ped.Offres.ports.OffreCritereRepository;
import fonea.ped.Offres.ports.OffrePrerequisRepository;
import fonea.ped.Offres.ports.OffreQualificationRepository;
import fonea.ped.Offres.ports.OffreRepository;
import fonea.ped.Offres.ports.PosteRepository;
import fonea.ped.Offres.ports.PrerequisRepository;
import fonea.ped.Offres.ports.QualificationRepository;
import fonea.ped.Offres.ports.SecteurRepository;
import fonea.ped.convention.UseCaseConvention.ports.ConventionRepository;*/

import fonea.ped.domain.convention.model.Convention;
import fonea.ped.domain.convention.model.StatutConvention;
import fonea.ped.useCase.UseCaseConvention.ports.ConventionRepository;
import fonea.ped.useCase.useCaseEntreprise.port.EntrepriseRepository;


import fonea.ped.domain.exceptions.AucunCritereAssocieException;
import fonea.ped.domain.exceptions.AucuneCompetenceAssocieeException;
import fonea.ped.domain.exceptions.ConventionIntrouvableException;
import fonea.ped.domain.exceptions.ConventionNonActiveException;
import fonea.ped.domain.exceptions.DateInvalideException;
import fonea.ped.domain.exceptions.EntrepriseIntrouvableException;
import fonea.ped.domain.exceptions.NombrePlacesInvalideException;
import fonea.ped.domain.exceptions.ReferenceIntrouvableException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreerOffreUseCase {
	 private final OffreRepository offreRepository;
	    private final ConventionRepository conventionRepository;
	    private final EntrepriseRepository entrepriseRepository;
	    private final DomaineRepository domaineRepository;
	    private final PosteRepository posteRepository;
	    private final MetierRepository metierRepository;
	    private final SecteurRepository secteurRepository;
	    private final QualificationRepository qualificationRepository;
	    private final CompetenceRepository competenceRepository;
	    private final PrerequisRepository prerequisRepository;
	    private final CritereRepository critereRepository;

	    // Ports de liaison (Option B)
	    private final OffreCompetenceRepository offreCompetenceRepository;
	    private final OffreCritereRepository offreCritereRepository;
	    private final OffrePrerequisRepository offrePrerequisRepository;
	    private final OffreQualificationRepository offreQualificationRepository;

	    public CreerOffreUseCase(OffreRepository offreRepository,
	                             ConventionRepository conventionRepository,
	                             EntrepriseRepository entrepriseRepository,
	                             DomaineRepository domaineRepository,
	                             PosteRepository posteRepository,
	                             MetierRepository metierRepository,
	                             SecteurRepository secteurRepository,
	                             QualificationRepository qualificationRepository,
	                             CompetenceRepository competenceRepository,
	                             PrerequisRepository prerequisRepository,
	                             CritereRepository critereRepository,
	                             OffreCompetenceRepository offreCompetenceRepository,
	                             OffreCritereRepository offreCritereRepository,
	                             OffrePrerequisRepository offrePrerequisRepository,
	                             OffreQualificationRepository offreQualificationRepository) {
	        this.offreRepository = offreRepository;
	        this.conventionRepository = conventionRepository;
	        this.entrepriseRepository = entrepriseRepository;
	        this.domaineRepository = domaineRepository;
	        this.posteRepository = posteRepository;
	        this.metierRepository = metierRepository;
	        this.secteurRepository = secteurRepository;
	        this.qualificationRepository = qualificationRepository;
	        this.competenceRepository = competenceRepository;
	        this.prerequisRepository = prerequisRepository;
	        this.critereRepository = critereRepository;
	        this.offreCompetenceRepository = offreCompetenceRepository;
	        this.offreCritereRepository = offreCritereRepository;
	        this.offrePrerequisRepository = offrePrerequisRepository;
	        this.offreQualificationRepository = offreQualificationRepository;
	    }

	    public CreerOffreResponse executer(CreerOffreRequest request) {

	        // 1. Validations simples
	        if (request.getIntitule() == null || request.getIntitule().isBlank()) {
	            throw new IllegalArgumentException("L'intitulé de l'offre est obligatoire.");
	        }
	        if (request.getNbrePlaces() == null || request.getNbrePlaces() <= 0) {
	            throw new NombrePlacesInvalideException();
	        }

	        // 2. Vérifier la convention
	        Convention convention = conventionRepository.trouverParId(request.getIdConvention())
	                .orElseThrow(() -> new ConventionIntrouvableException(request.getIdConvention()));

	        if (convention.getStatutConvention() != StatutConvention.ACTIVE) {
	            throw new ConventionNonActiveException(
	                    convention.getIdConvention(),
	                    convention.getStatutConvention().name()
	            );
	        }

	        // 2 bis. La convention doit appartenir à l'entreprise qui publie l'offre.
	        if (convention.getIdEntreprise() == null
	                || !convention.getIdEntreprise().equals(request.getIdEntreprise())) {
	            throw new IllegalArgumentException(
	                    "La convention " + convention.getIdConvention()
	                            + " n'appartient pas à l'entreprise " + request.getIdEntreprise() + ".");
	        }

	        // 3. Vérifier les références obligatoires
	        if (!entrepriseRepository.existeParId(request.getIdEntreprise())) {
	            throw new EntrepriseIntrouvableException(request.getIdEntreprise());
	        }
	        if (!domaineRepository.existeParId(request.getIdDomaine())) {
	            throw new ReferenceIntrouvableException("Domaine", request.getIdDomaine());
	        }
	        if (!posteRepository.existeParId(request.getIdPoste())) {
	            throw new ReferenceIntrouvableException("Poste", request.getIdPoste());
	        }
	        if (!metierRepository.existeParId(request.getIdMetier())) {
	            throw new ReferenceIntrouvableException("Métier", request.getIdMetier());
	        }
	        if (!secteurRepository.existeParId(request.getIdSecteur())) {
	            throw new ReferenceIntrouvableException("Secteur", request.getIdSecteur());
	        }

	        // 4. Vérifier les listes obligatoires
	        if (request.getIdsCompetences() == null || request.getIdsCompetences().isEmpty()) {
	            throw new AucuneCompetenceAssocieeException();
	        }
	        if (request.getIdsCriteres() == null || request.getIdsCriteres().isEmpty()) {
	            throw new AucunCritereAssocieException();
	        }
	        if (request.getIdsPrerequis() == null || request.getIdsPrerequis().isEmpty()) {
	            throw new AucunCritereAssocieException();
	        }
	        if (request.getIdsQualifications() == null || request.getIdsQualifications().isEmpty()) {
	            throw new ReferenceIntrouvableException("Qualification", null);
	        }

	        // 5. Vérifier l'existence de chaque élément des listes
	        verifierExistenceListe(request.getIdsCompetences(), competenceRepository::existeParId, "Compétence");
	        verifierExistenceListe(request.getIdsCriteres(), critereRepository::existeParId, "Critère");
	        verifierExistenceListe(request.getIdsPrerequis(), prerequisRepository::existeParId, "Prérequis");
	        verifierExistenceListe(request.getIdsQualifications(), qualificationRepository::existeParId, "Qualification");

	        // 6. Création de l'offre
	        String nouvelId = offreRepository.prochainId();
	        OffresStages offre = new OffresStages(
	                nouvelId,
	                request.getIntitule(),
	                request.getDescription(),
	                request.getVilleStage(),
	                request.getDuree(),
	                StatutOffre.BROUILLON,
	                request.getNbrePlaces(),
	                request.getIdPoste(),
	                request.getIdDomaine(),
	                request.getIdConvention()
	        );
	        offreRepository.sauvegarder(offre);

	        // 7. Création des liaisons (Option B)
	        for (String idCompetence : request.getIdsCompetences()) {
	            OffreCompetence liaison = new OffreCompetence(
	                    offreCompetenceRepository.prochainId(),
	                    offre.getIdOffre(),
	                    idCompetence
	            );
	            offreCompetenceRepository.sauvegarder(liaison);
	        }
	        for (String idCritere : request.getIdsCriteres()) {
	            OffreCritere liaison = new OffreCritere(
	                    offreCritereRepository.prochainId(),
	                    offre.getIdOffre(),
	                    idCritere
	            );
	            offreCritereRepository.sauvegarder(liaison);
	        }
	        for (String idPrerequis : request.getIdsPrerequis()) {
	            OffrePrerequis liaison = new OffrePrerequis(
	                    offrePrerequisRepository.prochainId(),
	                    offre.getIdOffre(),
	                    idPrerequis
	            );
	            offrePrerequisRepository.sauvegarder(liaison);
	        }
	        for (String idQualification : request.getIdsQualifications()) {
	            OffreQualification liaison = new OffreQualification(
	                    offreQualificationRepository.prochainId(),
	                    offre.getIdOffre(),
	                    idQualification
	            );
	            offreQualificationRepository.sauvegarder(liaison);
	        }

	        // 8. Réponse
	        return new CreerOffreResponse(
	                offre.getIdOffre(),
	                offre.getIntitule(),
	                offre.getVilleStage(),
	                offre.getDureeMois(),
	                offre.getNbrePlaces(),
	                offre.getStatutOffre(),
	                request.getIdConvention(),
	                request.getIdDomaine(),
	                request.getIdPoste(),
	                request.getIdMetier(),
	                request.getIdSecteur(),
	                request.getIdEntreprise(),
	                request.getIdsCompetences(),
	                request.getIdsCriteres(),
	                request.getIdsPrerequis(),
	                request.getIdsQualifications()
	        );
	    }

	    private void verifierExistenceListe(List<String> ids,
	                                        java.util.function.Predicate<String> existeFn,
	                                        String type) {
	        for (String id : ids) {
	            if (id == null || !existeFn.test(id)) {
	                throw new ReferenceIntrouvableException(type, id);
	            }
	        }
	    }
}
