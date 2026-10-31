package fonea.ped.infrastructure.config;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fonea.ped.domain.MiseEnStage.MotifDecrochage;
import fonea.ped.domain.Offres.Competence;
import fonea.ped.domain.Offres.Critere;
import fonea.ped.domain.Offres.Domaine;
import fonea.ped.domain.Offres.Metier;
import fonea.ped.domain.Offres.Poste;
import fonea.ped.domain.Offres.PrerequisFormation;
import fonea.ped.domain.Offres.Qualification;
import fonea.ped.domain.Offres.Secteur;
import fonea.ped.domain.convention.model.MotifResiliation;
import fonea.ped.domain.convention.model.Programme;
import fonea.ped.domain.diplome.Diplome;
import fonea.ped.domain.entreprise.Entreprise;
import fonea.ped.domain.entreprise.EntrepriseSecteur;
import fonea.ped.domain.insertion.TypeContrat;
import fonea.ped.domain.recrutement.TypeDocument;
import fonea.ped.domain.securite.AgentFonea;
import fonea.ped.domain.securite.Permission;
import fonea.ped.domain.securite.Role;
import fonea.ped.domain.securite.RolePermission;
import fonea.ped.domain.securite.Utilisateur;
import fonea.ped.useCase.UseCaseConvention.ports.MotifResiliationRepository;
import fonea.ped.useCase.UseCaseConvention.ports.ProgrammeRepository;
import fonea.ped.useCase.useCaseDiplome.port.DiplomeRepository;
import fonea.ped.useCase.useCaseEntreprise.port.EntrepriseRepository;
import fonea.ped.useCase.useCaseInsertion.ports.TypeContratRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MotifDecrochageRepository;
import fonea.ped.useCase.useCaseOffres.ports.CompetenceRepository;
import fonea.ped.useCase.useCaseOffres.ports.CritereRepository;
import fonea.ped.useCase.useCaseOffres.ports.DomaineRepository;
import fonea.ped.useCase.useCaseOffres.ports.EntrepriseSecteurRepository;
import fonea.ped.useCase.useCaseOffres.ports.MetierRepository;
import fonea.ped.useCase.useCaseOffres.ports.PosteRepository;
import fonea.ped.useCase.useCaseOffres.ports.PrerequisRepository;
import fonea.ped.useCase.useCaseOffres.ports.QualificationRepository;
import fonea.ped.useCase.useCaseOffres.ports.SecteurRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.TypeDocumentRepository;
import fonea.ped.useCase.useCaseSecurite.AgentFoneaRepository;
import fonea.ped.useCase.useCaseSecurite.PermissionRepository;
import fonea.ped.useCase.useCaseSecurite.RolePermissionRepository;
import fonea.ped.useCase.useCaseSecurite.RoleRepository;
import fonea.ped.useCase.useCaseSecurite.UtilisateurRepository;

/**
 * Initialisation idempotente des données indispensables au démarrage :
 * rôles, permissions, compte administrateur, référentiel de base et, en
 * environnement de démonstration, trois comptes de test.
 *
 * <p>Sans ce jeu de données, l'API démarrait mais personne ne pouvait se
 * connecter (aucun utilisateur) et aucun formulaire n'était utilisable (aucun
 * secteur, domaine, type de contrat...).</p>
 *
 * <p>Toutes les écritures utilisent les upserts des repositories : relancer
 * l'application ne duplique rien.</p>
 */
@Component
public class InitialisationDonnees implements ApplicationRunner {

    private static final Logger JOURNAL = LoggerFactory.getLogger(InitialisationDonnees.class);

    // ==================== Rôles ====================

    private static final String ROLE_ADMIN = "ROL_ADMIN";
    private static final String ROLE_AGENT = "ROL_AGENT_FONEA";
    private static final String ROLE_ENTREPRISE = "ROL_ENTREPRISE";
    private static final String ROLE_DIPLOME = "ROL_DIPLOME";

    /** Code de permission → description. */
    private static final Map<String, String> PERMISSIONS = new LinkedHashMap<>();

    static {
        PERMISSIONS.put("ADMIN_TOTAL", "Accès total à toutes les opérations");
        PERMISSIONS.put("CONVENTION_CREER", "Créer une convention");
        PERMISSIONS.put("CONVENTION_VALIDER", "Valider (signer) une convention");
        PERMISSIONS.put("CONVENTION_RESILIER", "Résilier une convention");
        PERMISSIONS.put("CONVENTION_LIRE", "Consulter les conventions");
        PERMISSIONS.put("OFFRE_CREER", "Créer une offre de stage");
        PERMISSIONS.put("OFFRE_PUBLIER", "Publier une offre de stage");
        PERMISSIONS.put("OFFRE_LIRE", "Consulter les offres de stage");
        PERMISSIONS.put("CANDIDATURE_DEPOSER", "Déposer une candidature");
        PERMISSIONS.put("CANDIDATURE_LIRE", "Consulter les candidatures");
        PERMISSIONS.put("PRESELECTION_FAIRE", "Présélectionner une candidature");
        PERMISSIONS.put("SELECTION_FAIRE", "Sélectionner un candidat");
        PERMISSIONS.put("STAGE_GERER", "Mettre en stage, déclarer un décrochage, renouveler");
        PERMISSIONS.put("STAGE_LIRE", "Consulter les mises en stage");
        PERMISSIONS.put("INSERTION_FAIRE", "Transformer un stage en embauche");
        PERMISSIONS.put("REFERENTIEL_LIRE", "Consulter le référentiel");
        PERMISSIONS.put("REFERENTIEL_GERER", "Administrer le référentiel");
        PERMISSIONS.put("DIPLOME_CREER", "Créer un profil diplômé");
        PERMISSIONS.put("ENTREPRISE_CREER", "Créer une entreprise");
    }

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AgentFoneaRepository agentFoneaRepository;
    private final PasswordEncoder passwordEncoder;

    private final SecteurRepository secteurRepository;
    private final DomaineRepository domaineRepository;
    private final MetierRepository metierRepository;
    private final PosteRepository posteRepository;
    private final CompetenceRepository competenceRepository;
    private final CritereRepository critereRepository;
    private final PrerequisRepository prerequisRepository;
    private final QualificationRepository qualificationRepository;
    private final TypeContratRepository typeContratRepository;
    private final TypeDocumentRepository typeDocumentRepository;
    private final MotifDecrochageRepository motifDecrochageRepository;
    private final MotifResiliationRepository motifResiliationRepository;
    private final ProgrammeRepository programmeRepository;

    private final EntrepriseRepository entrepriseRepository;
    private final EntrepriseSecteurRepository entrepriseSecteurRepository;
    private final DiplomeRepository diplomeRepository;

    @Value("${ped.securite.admin.email:admin@fonea.bi}")
    private String adminEmail;

    @Value("${ped.securite.admin.mot-de-passe:Admin@2026}")
    private String adminMotDePasse;

    @Value("${ped.securite.demo:true}")
    private boolean demoActive;

    public InitialisationDonnees(RoleRepository roleRepository,
                                 PermissionRepository permissionRepository,
                                 RolePermissionRepository rolePermissionRepository,
                                 UtilisateurRepository utilisateurRepository,
                                 AgentFoneaRepository agentFoneaRepository,
                                 PasswordEncoder passwordEncoder,
                                 SecteurRepository secteurRepository,
                                 DomaineRepository domaineRepository,
                                 MetierRepository metierRepository,
                                 PosteRepository posteRepository,
                                 CompetenceRepository competenceRepository,
                                 CritereRepository critereRepository,
                                 PrerequisRepository prerequisRepository,
                                 QualificationRepository qualificationRepository,
                                 TypeContratRepository typeContratRepository,
                                 TypeDocumentRepository typeDocumentRepository,
                                 MotifDecrochageRepository motifDecrochageRepository,
                                 MotifResiliationRepository motifResiliationRepository,
                                 ProgrammeRepository programmeRepository,
                                 EntrepriseRepository entrepriseRepository,
                                 EntrepriseSecteurRepository entrepriseSecteurRepository,
                                 DiplomeRepository diplomeRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.agentFoneaRepository = agentFoneaRepository;
        this.passwordEncoder = passwordEncoder;
        this.secteurRepository = secteurRepository;
        this.domaineRepository = domaineRepository;
        this.metierRepository = metierRepository;
        this.posteRepository = posteRepository;
        this.competenceRepository = competenceRepository;
        this.critereRepository = critereRepository;
        this.prerequisRepository = prerequisRepository;
        this.qualificationRepository = qualificationRepository;
        this.typeContratRepository = typeContratRepository;
        this.typeDocumentRepository = typeDocumentRepository;
        this.motifDecrochageRepository = motifDecrochageRepository;
        this.motifResiliationRepository = motifResiliationRepository;
        this.programmeRepository = programmeRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseSecteurRepository = entrepriseSecteurRepository;
        this.diplomeRepository = diplomeRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        initialiserRolesEtPermissions();
        String idAdmin = initialiserAdministrateur();
        initialiserReferentiel();
        if (demoActive) {
            initialiserDemonstration(idAdmin);
        }
        JOURNAL.info("Initialisation des données terminée (admin={}).", adminEmail);
    }

    // ==================== Sécurité ====================

    private void initialiserRolesEtPermissions() {
        roleRepository.sauvegarder(new Role(ROLE_ADMIN, "ADMIN", "Administrateur"));
        roleRepository.sauvegarder(new Role(ROLE_AGENT, "AGENT_FONEA", "Agent FONEA"));
        roleRepository.sauvegarder(new Role(ROLE_ENTREPRISE, "ENTREPRISE", "Entreprise"));
        roleRepository.sauvegarder(new Role(ROLE_DIPLOME, "DIPLOME", "Diplômé"));

        for (Map.Entry<String, String> entree : PERMISSIONS.entrySet()) {
            permissionRepository.sauvegarder(new Permission(
                    idPermission(entree.getKey()), entree.getKey(), entree.getValue()));
        }

        // L'administrateur reçoit toutes les permissions.
        for (String code : PERMISSIONS.keySet()) {
            accorder(ROLE_ADMIN, code);
        }

        // FONEA : valide les conventions, publie les offres, présélectionne et sélectionne.
        for (String code : List.of(
                "CONVENTION_VALIDER", "CONVENTION_RESILIER", "CONVENTION_LIRE",
                "OFFRE_CREER", "OFFRE_PUBLIER", "OFFRE_LIRE",
                "CANDIDATURE_LIRE", "PRESELECTION_FAIRE", "SELECTION_FAIRE",
                "STAGE_LIRE", "REFERENTIEL_LIRE", "REFERENTIEL_GERER",
                "DIPLOME_CREER", "ENTREPRISE_CREER")) {
            accorder(ROLE_AGENT, code);
        }

        // Entreprise : signe la convention, crée les offres, gère ses stages et embauche.
        for (String code : List.of(
                "CONVENTION_CREER", "CONVENTION_LIRE",
                "OFFRE_CREER", "OFFRE_LIRE", "CANDIDATURE_LIRE",
                "STAGE_GERER", "STAGE_LIRE", "INSERTION_FAIRE",
                "REFERENTIEL_LIRE")) {
            accorder(ROLE_ENTREPRISE, code);
        }

        // Diplômé : postule et consulte les offres.
        for (String code : List.of(
                "CANDIDATURE_DEPOSER", "CANDIDATURE_LIRE",
                "OFFRE_LIRE", "REFERENTIEL_LIRE")) {
            accorder(ROLE_DIPLOME, code);
        }
    }

    private void accorder(String idRole, String codePermission) {
        rolePermissionRepository.sauvegarder(
                new RolePermission(idRole, idPermission(codePermission)));
    }

    private static String idPermission(String code) {
        return "PRM_" + code;
    }

    private String initialiserAdministrateur() {
        Optional<Utilisateur> existant = utilisateurRepository.trouverParEmail(adminEmail);
        if (existant.isPresent()) {
            Utilisateur admin = existant.get();
            if (!ROLE_ADMIN.equals(admin.getIdRole())) {
                admin.changerRole(ROLE_ADMIN);
                utilisateurRepository.sauvegarder(admin);
            }
            creerAgentSiAbsent("AGT_ADMIN", "FONEA", "Administrateur",
                    "Administrateur", admin.getIdUtilisateur());
            return admin.getIdUtilisateur();
        }

        String idAdmin = utilisateurRepository.existeParId("USR_ADMIN")
                ? utilisateurRepository.prochainId()
                : "USR_ADMIN";
        Utilisateur admin = new Utilisateur(
                idAdmin,
                adminEmail,
                passwordEncoder.encode(adminMotDePasse),
                null,
                ROLE_ADMIN);
        utilisateurRepository.sauvegarder(admin);
        creerAgentSiAbsent("AGT_ADMIN", "FONEA", "Administrateur",
                "Administrateur", idAdmin);
        return idAdmin;
    }

    private void creerAgentSiAbsent(String idAgent, String nom, String prenom,
                                    String fonction, String idUtilisateur) {
        if (!agentFoneaRepository.existeParId(idAgent)) {
            agentFoneaRepository.sauvegarder(
                    new AgentFonea(idAgent, nom, prenom, fonction, idUtilisateur));
        }
    }

    // ==================== Référentiel ====================

    private void initialiserReferentiel() {
        // Secteurs
        secteurRepository.sauvegarder(new Secteur("SEC_INFO", "Informatique"));
        secteurRepository.sauvegarder(new Secteur("SEC_FIN", "Finance"));
        secteurRepository.sauvegarder(new Secteur("SEC_SANTE", "Santé"));
        secteurRepository.sauvegarder(new Secteur("SEC_EDU", "Éducation"));
        secteurRepository.sauvegarder(new Secteur("SEC_AGRI", "Agriculture"));

        // Domaines
        domaineRepository.sauvegarder(new Domaine("DOM_DEV", "Développement logiciel"));
        domaineRepository.sauvegarder(new Domaine("DOM_DATA", "Data et analyse"));
        domaineRepository.sauvegarder(new Domaine("DOM_RESEAU", "Réseaux et sécurité"));
        domaineRepository.sauvegarder(new Domaine("DOM_GESTION", "Gestion et comptabilité"));

        // Métiers et postes
        metierRepository.sauvegarder(new Metier("MET_DEV", "Développeur", "SEC_INFO"));
        metierRepository.sauvegarder(new Metier("MET_COMPTA", "Comptable", "SEC_FIN"));
        posteRepository.sauvegarder(new Poste("POS_DEV_JUNIOR", "Développeur junior", "MET_DEV"));
        posteRepository.sauvegarder(new Poste("POS_ASSISTANT_COMPTA",
                "Assistant comptable", "MET_COMPTA"));

        // Compétences, critères, prérequis, qualifications
        competenceRepository.sauvegarder(new Competence("COMP_JAVA", "Java"));
        competenceRepository.sauvegarder(new Competence("COMP_SQL", "SQL"));
        competenceRepository.sauvegarder(new Competence("COMP_COMM", "Communication"));

        critereRepository.sauvegarder(new Critere("CRIT_JUNIOR", 18, 30, "Licence"));
        critereRepository.sauvegarder(new Critere("CRIT_SENIOR", 25, 45, "Master"));

        prerequisRepository.sauvegarder(new PrerequisFormation(
                "PREQ_BAC", "Baccalauréat", null, "Baccalauréat"));
        prerequisRepository.sauvegarder(new PrerequisFormation(
                "PREQ_FR", "Français courant", null, "Français courant"));

        qualificationRepository.sauvegarder(
                new Qualification("QUAL_DEV", "Développeur junior", "MET_DEV"));
        qualificationRepository.sauvegarder(
                new Qualification("QUAL_COMPTA", "Comptable junior", "MET_COMPTA"));

        // Types de contrat
        typeContratRepository.sauvegarder(new TypeContrat("TC_CDI", "CDI"));
        typeContratRepository.sauvegarder(new TypeContrat("TC_CDD", "CDD"));
        typeContratRepository.sauvegarder(new TypeContrat("TC_STAGE", "Stage"));

        // Types de document : les identifiants CV et IDENTITE sont attendus par
        // Candidature.sontDocumentsComplets().
        typeDocumentRepository.sauvegarder(new TypeDocument("CV", "Curriculum vitae"));
        typeDocumentRepository.sauvegarder(new TypeDocument("IDENTITE", "Pièce d'identité"));
        typeDocumentRepository.sauvegarder(new TypeDocument("DIPLOME", "Diplôme"));
        typeDocumentRepository.sauvegarder(new TypeDocument("LETTRE", "Lettre de motivation"));

        // Motifs
        motifDecrochageRepository.sauvegarder(
                new MotifDecrochage("MD_ABANDON", "Abandon du stage", "VOLONTAIRE"));
        motifDecrochageRepository.sauvegarder(
                new MotifDecrochage("MD_MALADIE", "Raison médicale", "INVOLONTAIRE"));
        motifDecrochageRepository.sauvegarder(
                new MotifDecrochage("MD_INCOMPATIBILITE", "Incompatibilité", "INVOLONTAIRE"));

        motifResiliationRepository.sauvegarder(
                new MotifResiliation("MR_FIN", "Fin de programme"));
        motifResiliationRepository.sauvegarder(
                new MotifResiliation("MR_MANQUEMENT", "Manquement aux obligations"));
        motifResiliationRepository.sauvegarder(
                new MotifResiliation("MR_COMMUN", "Accord commun"));

        // Programmes
        programmeRepository.sauvegarder(new Programme("PROG_LICENCE", "Licence", "DIPLOME"));
        programmeRepository.sauvegarder(new Programme("PROG_MASTER", "Master", "DIPLOME"));
        programmeRepository.sauvegarder(new Programme("PROG_BTS", "BTS", "DIPLOME"));
    }

    // ==================== Démonstration ====================

    /**
     * Crée trois comptes de test et leurs profils, pour pouvoir dérouler le
     * flux complet sans écrire de SQL. Désactivable avec
     * {@code ped.securite.demo=false}.
     */
    private void initialiserDemonstration(String idAdmin) {
        String idAgent = creerUtilisateurSiAbsent(
                "USR_AGENT", "agent@fonea.bi", "Agent@2026", ROLE_AGENT);
        creerAgentSiAbsent("AGT_DEMO", "Ndayishimiye", "Claude",
                "Agent de placement", idAgent);

        String idEntreprise = creerUtilisateurSiAbsent(
                "USR_ENTREPRISE", "entreprise@demo.bi", "Entreprise@2026", ROLE_ENTREPRISE);
        if (!entrepriseRepository.existeParId("ENT_DEMO")) {
            entrepriseRepository.sauvegarder(new Entreprise(
                    "ENT_DEMO",
                    "Entreprise Démo SARL",
                    "Avenue de l'Indépendance, Bujumbura",
                    "NIU-DEMO-0001",
                    idEntreprise));
            entrepriseSecteurRepository.sauvegarder(
                    new EntrepriseSecteur("ENT_DEMO", "SEC_INFO"));
        }

        String idDiplome = creerUtilisateurSiAbsent(
                "USR_DIPLOME", "diplome@demo.bi", "Diplome@2026", ROLE_DIPLOME);
        if (!diplomeRepository.existeParId("DIP_DEMO")) {
            diplomeRepository.sauvegarder(new Diplome(
                    "DIP_DEMO",
                    "Nkurunziza",
                    "Alice",
                    LocalDate.of(1998, 5, 12),
                    "F",
                    "Licence",
                    "2024",
                    "Informatique",
                    idDiplome));
        }

        JOURNAL.info("Comptes de démonstration prêts : agent@fonea.bi, "
                + "entreprise@demo.bi, diplome@demo.bi (admin={}).", idAdmin);
    }

    private String creerUtilisateurSiAbsent(String idParDefaut, String email,
                                            String motDePasse, String idRole) {
        Optional<Utilisateur> existant = utilisateurRepository.trouverParEmail(email);
        if (existant.isPresent()) {
            return existant.get().getIdUtilisateur();
        }
        String id = utilisateurRepository.existeParId(idParDefaut)
                ? utilisateurRepository.prochainId()
                : idParDefaut;
        utilisateurRepository.sauvegarder(new Utilisateur(
                id, email, passwordEncoder.encode(motDePasse), null, idRole));
        return id;
    }
}
