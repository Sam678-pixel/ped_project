package fonea.ped.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fonea.ped.infrastructure.persistence.repositories.JdbcProgrammeRepository;
import fonea.ped.interfaceAdapters.gateways.ConventionGateways;
import fonea.ped.interfaceAdapters.gateways.DiplomeGateway;
import fonea.ped.interfaceAdapters.gateways.EntrepriseGateway;
import fonea.ped.interfaceAdapters.gateways.InsertionGateways;
import fonea.ped.interfaceAdapters.gateways.LiaisonGateways;
import fonea.ped.interfaceAdapters.gateways.MiseEnStageGateways;
import fonea.ped.interfaceAdapters.gateways.OffreGateways;
import fonea.ped.interfaceAdapters.gateways.RecrutementGateways;
import fonea.ped.interfaceAdapters.gateways.ReferentielGateways;
import fonea.ped.interfaceAdapters.gateways.SecuriteGateways;

import fonea.ped.useCase.UseCaseConvention.ports.ConventionRepository;
import fonea.ped.useCase.UseCaseConvention.ports.MotifResiliationRepository;
import fonea.ped.useCase.UseCaseConvention.ports.ProgrammeRepository;
import fonea.ped.useCase.UseCaseConvention.ports.ResiliationMotifRepository;
import fonea.ped.useCase.UseCaseConvention.ports.ResiliationRepository;
import fonea.ped.useCase.useCaseDiplome.port.DiplomeRepository;
import fonea.ped.useCase.useCaseEntreprise.port.EntrepriseRepository;
import fonea.ped.useCase.useCaseInsertion.ports.ContratRepository;
import fonea.ped.useCase.useCaseInsertion.ports.InsertionRepository;
import fonea.ped.useCase.useCaseInsertion.ports.TypeContratRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.DecrochageMotifRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.DecrochageRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MotifDecrochageRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.RenouvellementRepository;
import fonea.ped.useCase.useCaseOffres.ports.CompetencePosteRepository;
import fonea.ped.useCase.useCaseOffres.ports.CompetenceRepository;
import fonea.ped.useCase.useCaseOffres.ports.CritereCompetenceRepository;
import fonea.ped.useCase.useCaseOffres.ports.CriterePrerequisRepository;
import fonea.ped.useCase.useCaseOffres.ports.CritereRepository;
import fonea.ped.useCase.useCaseOffres.ports.CritereTechniqueLinguistiqueRepository;
import fonea.ped.useCase.useCaseOffres.ports.DiplomeCompetenceRepository;
import fonea.ped.useCase.useCaseOffres.ports.DomaineRepository;
import fonea.ped.useCase.useCaseOffres.ports.EntrepriseSecteurRepository;
import fonea.ped.useCase.useCaseOffres.ports.MetierRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffreCompetenceRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffreCritereRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffrePrerequisRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffreQualificationRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffreRepository;
import fonea.ped.useCase.useCaseOffres.ports.PosteRepository;
import fonea.ped.useCase.useCaseOffres.ports.PrerequisRepository;
import fonea.ped.useCase.useCaseOffres.ports.PublicationRepository;
import fonea.ped.useCase.useCaseOffres.ports.QualificationCompetenceRepository;
import fonea.ped.useCase.useCaseOffres.ports.QualificationRepository;
import fonea.ped.useCase.useCaseOffres.ports.SecteurRepository;
import fonea.ped.useCase.useCaseOffres.ports.TechniqueLinguistiqueRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureDocumentRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.DocumentRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.PreselectionCritereRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.PreselectionRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.SelectionRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.TypeDocumentRepository;
import fonea.ped.useCase.useCaseSecurite.AgentFoneaRepository;
import fonea.ped.useCase.useCaseSecurite.NotificationRepository;
import fonea.ped.useCase.useCaseSecurite.PermissionRepository;
import fonea.ped.useCase.useCaseSecurite.RolePermissionRepository;
import fonea.ped.useCase.useCaseSecurite.RoleRepository;
import fonea.ped.useCase.useCaseSecurite.UtilisateurRepository;

/**
 * Câblage des adaptateurs secondaires : chaque port est relié à son gateway,
 * lui-même déléguant au repository JDBC.
 *
 * <p>Avant cette classe, aucun gateway n'était un bean Spring : l'application
 * démarrait mais aucun use case n'était instanciable.</p>
 */
@Configuration(proxyBeanMethods = false)
public class GatewayConfiguration {

    // ==================== Référentiel ====================

    @Bean
    public SecteurRepository secteurRepository() {
        return new ReferentielGateways.SecteurGateway();
    }

    @Bean
    public DomaineRepository domaineRepository() {
        return new ReferentielGateways.DomaineGateway();
    }

    @Bean
    public MetierRepository metierRepository() {
        return new ReferentielGateways.MetierGateway();
    }

    @Bean
    public PosteRepository posteRepository() {
        return new ReferentielGateways.PosteGateway();
    }

    @Bean
    public CompetenceRepository competenceRepository() {
        return new ReferentielGateways.CompetenceGateway();
    }

    @Bean
    public CritereRepository critereRepository() {
        return new ReferentielGateways.CritereGateway();
    }

    @Bean
    public PrerequisRepository prerequisRepository() {
        return new ReferentielGateways.PrerequisGateway();
    }

    @Bean
    public QualificationRepository qualificationRepository() {
        return new ReferentielGateways.QualificationGateway();
    }

    @Bean
    public TechniqueLinguistiqueRepository techniqueLinguistiqueRepository() {
        return new ReferentielGateways.TechniqueLinguistiqueGateway();
    }

    @Bean
    public TypeContratRepository typeContratRepository() {
        return new ReferentielGateways.TypeContratGateway();
    }

    @Bean
    public TypeDocumentRepository typeDocumentRepository() {
        return new ReferentielGateways.TypeDocumentGateway();
    }

    @Bean
    public MotifDecrochageRepository motifDecrochageRepository() {
        return new ReferentielGateways.MotifDecrochageGateway();
    }

    @Bean
    public MotifResiliationRepository motifResiliationRepository() {
        return new ReferentielGateways.MotifResiliationGateway();
    }

    @Bean
    public ProgrammeRepository programmeRepository() {
        return new JdbcProgrammeRepository();
    }

    // ==================== Offres ====================

    @Bean
    public OffreRepository offreRepository() {
        return new OffreGateways.OffreGateway();
    }

    @Bean
    public PublicationRepository publicationRepository() {
        return new OffreGateways.PublicationGateway();
    }

    @Bean
    public OffreCompetenceRepository offreCompetenceRepository() {
        return new LiaisonGateways.OffreCompetenceGateway();
    }

    @Bean
    public OffreCritereRepository offreCritereRepository() {
        return new LiaisonGateways.OffreCritereGateway();
    }

    @Bean
    public OffrePrerequisRepository offrePrerequisRepository() {
        return new LiaisonGateways.OffrePrerequisGateway();
    }

    @Bean
    public OffreQualificationRepository offreQualificationRepository() {
        return new LiaisonGateways.OffreQualificationGateway();
    }

    @Bean
    public CompetencePosteRepository competencePosteRepository() {
        return new LiaisonGateways.CompetencePosteGateway();
    }

    @Bean
    public QualificationCompetenceRepository qualificationCompetenceRepository() {
        return new LiaisonGateways.QualificationCompetenceGateway();
    }

    @Bean
    public CritereCompetenceRepository critereCompetenceRepository() {
        return new LiaisonGateways.CritereCompetenceGateway();
    }

    @Bean
    public CriterePrerequisRepository criterePrerequisRepository() {
        return new LiaisonGateways.CriterePrerequisGateway();
    }

    @Bean
    public CritereTechniqueLinguistiqueRepository critereTechniqueLinguistiqueRepository() {
        return new LiaisonGateways.CritereTechniqueLinguistiqueGateway();
    }

    // ==================== Recrutement ====================

    @Bean
    public CandidatureRepository candidatureRepository() {
        return new RecrutementGateways.CandidatureGateway();
    }

    @Bean
    public DocumentRepository documentRepository() {
        return new RecrutementGateways.DocumentGateway();
    }

    @Bean
    public PreselectionRepository preselectionRepository() {
        return new RecrutementGateways.PreselectionGateway();
    }

    @Bean
    public SelectionRepository selectionRepository() {
        return new RecrutementGateways.SelectionGateway();
    }

    @Bean
    public CandidatureDocumentRepository candidatureDocumentRepository() {
        return new LiaisonGateways.CandidatureDocumentGateway();
    }

    @Bean
    public PreselectionCritereRepository preselectionCritereRepository() {
        return new LiaisonGateways.PreselectionCritereGateway();
    }

    // ==================== Mise en stage ====================

    @Bean
    public MiseEnStageRepository miseEnStageRepository() {
        return new MiseEnStageGateways.MiseEnStageGateway();
    }

    @Bean
    public DecrochageRepository decrochageRepository() {
        return new MiseEnStageGateways.DecrochageGateway();
    }

    @Bean
    public RenouvellementRepository renouvellementRepository() {
        return new MiseEnStageGateways.RenouvellementGateway();
    }

    @Bean
    public DecrochageMotifRepository decrochageMotifRepository() {
        return new LiaisonGateways.DecrochageMotifGateway();
    }

    // ==================== Insertion ====================

    @Bean
    public InsertionRepository insertionRepository() {
        return new InsertionGateways.InsertionGateway();
    }

    @Bean
    public ContratRepository contratRepository() {
        return new InsertionGateways.ContratGateway();
    }

    // ==================== Convention ====================

    @Bean
    public ConventionRepository conventionRepository() {
        return new ConventionGateways.ConventionGateway();
    }

    @Bean
    public ResiliationRepository resiliationRepository() {
        return new ConventionGateways.ResiliationGateway();
    }

    @Bean
    public ResiliationMotifRepository resiliationMotifRepository() {
        return new LiaisonGateways.ResiliationMotifGateway();
    }

    // ==================== Diplôme / Entreprise ====================

    @Bean
    public DiplomeRepository diplomeRepository() {
        return new DiplomeGateway();
    }

    @Bean
    public EntrepriseRepository entrepriseRepository() {
        return new EntrepriseGateway();
    }

    @Bean
    public EntrepriseSecteurRepository entrepriseSecteurRepository() {
        return new LiaisonGateways.EntrepriseSecteurGateway();
    }

    @Bean
    public DiplomeCompetenceRepository diplomeCompetenceRepository() {
        return new LiaisonGateways.DiplomeCompetenceGateway();
    }

    // ==================== Sécurité ====================

    @Bean
    public UtilisateurRepository utilisateurRepository() {
        return new SecuriteGateways.UtilisateurGateway();
    }

    @Bean
    public RoleRepository roleRepository() {
        return new SecuriteGateways.RoleGateway();
    }

    @Bean
    public PermissionRepository permissionRepository() {
        return new SecuriteGateways.PermissionGateway();
    }

    @Bean
    public RolePermissionRepository rolePermissionRepository() {
        return new LiaisonGateways.RolePermissionGateway();
    }

    @Bean
    public AgentFoneaRepository agentFoneaRepository() {
        return new SecuriteGateways.AgentFoneaGateway();
    }

    @Bean
    public NotificationRepository notificationRepository() {
        return new SecuriteGateways.NotificationGateway();
    }
}
