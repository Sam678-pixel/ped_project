-- ============================================================
-- SCHÉMA FONEA/PED — Version alignée sur le MLD
-- Compatible PostgreSQL / H2
-- ============================================================

-- ==================== RÉFÉRENTIELS ====================
CREATE TABLE IF NOT EXISTS Secteur (
    idSecteur VARCHAR(50) PRIMARY KEY,
    libelle   VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Domaine (
    idDomain VARCHAR(50) PRIMARY KEY,
    libelle  VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS TypeContrat (
    idTypeContrat VARCHAR(50) PRIMARY KEY,
    libelle       VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS MotifDecrochage (
    idMotifDecrochage VARCHAR(50) PRIMARY KEY,
    libelle           VARCHAR(50) NOT NULL,
    typeMotif         VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS MotifResiliation (
    idMotifResiliation VARCHAR(50) PRIMARY KEY,
    libelle            VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS TypeDocument (
    idTypedocument VARCHAR(50) PRIMARY KEY,
    libelle        VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS TechniqueLinguistique (
    idTechLing VARCHAR(50) PRIMARY KEY,
    libelle    VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS PrerequisFormation (
    idPrerequis VARCHAR(50) PRIMARY KEY,
    libelle     VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Competence (
    idCompetence VARCHAR(50) PRIMARY KEY,
    libelle      VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Metier (
    idMetier  VARCHAR(50) PRIMARY KEY,
    libelle   VARCHAR(50) NOT NULL,
    idSecteur VARCHAR(50),
    FOREIGN KEY (idSecteur) REFERENCES Secteur(idSecteur)
);

CREATE TABLE IF NOT EXISTS Qualification (
    idQualification VARCHAR(50) PRIMARY KEY,
    libelle         VARCHAR(50) NOT NULL,
    idMetier        VARCHAR(50),
    FOREIGN KEY (idMetier) REFERENCES Metier(idMetier)
);

CREATE TABLE IF NOT EXISTS Poste (
    idPoste  VARCHAR(50) PRIMARY KEY,
    libelle  VARCHAR(50) NOT NULL,
    idMetier VARCHAR(50),
    FOREIGN KEY (idMetier) REFERENCES Metier(idMetier)
);

CREATE TABLE IF NOT EXISTS Critere (
    idCritere      VARCHAR(50) PRIMARY KEY,
    ageMin         INT,
    ageMax         INT,
    NiveauDiplome  VARCHAR(50)
);

-- ==================== SÉCURITÉ ====================
CREATE TABLE IF NOT EXISTS Role (
    idRole      VARCHAR(50) PRIMARY KEY,
    codeRole    VARCHAR(50),
    libelleRole VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Permission (
    idPermission   VARCHAR(50) PRIMARY KEY,
    codePermission VARCHAR(50),
    description    TEXT
);

CREATE TABLE IF NOT EXISTS Utilisateur (
    idUtilisateur      VARCHAR(50) PRIMARY KEY,
    email              VARCHAR(50),
    password           VARCHAR(100),
    derniereConnexion  TIMESTAMP,
    telephone          VARCHAR(50),
    idRole             VARCHAR(50),
    FOREIGN KEY (idRole) REFERENCES Role(idRole)
);

CREATE TABLE IF NOT EXISTS AgentFonea (
    idAgent       VARCHAR(50) PRIMARY KEY,
    nom           VARCHAR(50),
    prenom        VARCHAR(50),
    fonction      VARCHAR(50),
    idUtilisateur VARCHAR(50),
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(idUtilisateur)
);

CREATE TABLE IF NOT EXISTS Notification (
    idNotification   VARCHAR(50) PRIMARY KEY,
    typeNotification VARCHAR(50),
    message          TEXT,
    dateEnvoi        TIMESTAMP,
    lue              BOOLEAN,
    idUtilisateur    VARCHAR(50),
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(idUtilisateur)
);

-- ==================== ENTREPRISE ====================
CREATE TABLE IF NOT EXISTS Entreprise (
    idEntreprise  VARCHAR(50) PRIMARY KEY,
    raisonSociale VARCHAR(50),
    adresse       VARCHAR(50),
    niu           VARCHAR(50),
    idUtilisateur VARCHAR(50),
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(idUtilisateur)
);

-- ==================== DIPLÔMÉ ====================
CREATE TABLE IF NOT EXISTS `Diplomé` (
    `idDiplomé`     VARCHAR(50) PRIMARY KEY,
    nom             VARCHAR(50),
    prenom          VARCHAR(20),
    dateNaissance   DATE,
    sexe            VARCHAR(10),
    niveauDiplome   VARCHAR(50),
    `annéeOptention` VARCHAR(50),
    Filiere         VARCHAR(50),
    idUtilisateur   VARCHAR(50),
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(idUtilisateur)
);

-- ==================== CONVENTION ====================
CREATE TABLE IF NOT EXISTS Convention (
    idConvention    VARCHAR(50) PRIMARY KEY,
    dateEffet       DATE,
    tauxFonea       DECIMAL(15,2),
    tauxEntreprise       DECIMAL(15,2),
    SatutConvention VARCHAR(50),
    TypeConvention  VARCHAR(50),
    idEntreprise    VARCHAR(50),
    FOREIGN KEY (idEntreprise) REFERENCES Entreprise(idEntreprise)
);

CREATE TABLE IF NOT EXISTS Resiliation (
    idResiliation   VARCHAR(50) PRIMARY KEY,
    dateResiliation DATE,
    initiateur      VARCHAR(50),
    idConvention    VARCHAR(50),
    FOREIGN KEY (idConvention) REFERENCES Convention(idConvention)
);

-- ==================== OFFRES ====================
CREATE TABLE IF NOT EXISTS OffresStage (
    idOffre       VARCHAR(50) PRIMARY KEY,
    intitule      VARCHAR(100),
    description   TEXT,
    villeStage    VARCHAR(50),
    dureeMois     INT,
    StatutOffre   VARCHAR(50),
    nbrePlaces    INT,
    idPoste       VARCHAR(50),
    idDomain      VARCHAR(50),
    idConvention  VARCHAR(50),
    FOREIGN KEY (idPoste) REFERENCES Poste(idPoste),
    FOREIGN KEY (idDomain) REFERENCES Domaine(idDomain),
    FOREIGN KEY (idConvention) REFERENCES Convention(idConvention)
);

CREATE TABLE IF NOT EXISTS Publication (
    idPublication  VARCHAR(50) PRIMARY KEY,
    datePublication DATE,
    dateExpiration  DATE,
    idAgent         VARCHAR(50),
    idOffre         VARCHAR(50),
    FOREIGN KEY (idAgent) REFERENCES AgentFonea(idAgent),
    FOREIGN KEY (idOffre) REFERENCES OffresStage(idOffre)
);

-- ==================== RECRUTEMENT ====================
CREATE TABLE IF NOT EXISTS Candidature (
    idCandidature      VARCHAR(50) PRIMARY KEY,
    dateDepot          DATE,
    StatutCandidature  VARCHAR(50),
    `idDiplomé`        VARCHAR(50),
    idPublication      VARCHAR(50),
    FOREIGN KEY (`idDiplomé`) REFERENCES `Diplomé`(`idDiplomé`),
    FOREIGN KEY (idPublication) REFERENCES Publication(idPublication)
);

CREATE TABLE IF NOT EXISTS Document (
    idDocument     VARCHAR(50) PRIMARY KEY,
    cheminFichier  VARCHAR(255),
    `idDiplomé`    VARCHAR(50),
    idTypedocument VARCHAR(50),
    FOREIGN KEY (`idDiplomé`) REFERENCES `Diplomé`(`idDiplomé`),
    FOREIGN KEY (idTypedocument) REFERENCES TypeDocument(idTypedocument)
);

CREATE TABLE IF NOT EXISTS Preselection (
    idPreselection     VARCHAR(50) PRIMARY KEY,
    datePreselection   DATE,
    StatutPreselection VARCHAR(50),
    idAgent            VARCHAR(50),
    idCandidature      VARCHAR(50),
    FOREIGN KEY (idAgent) REFERENCES AgentFonea(idAgent),
    FOREIGN KEY (idCandidature) REFERENCES Candidature(idCandidature)
);

CREATE TABLE IF NOT EXISTS Selection (
    idSelection     VARCHAR(50) PRIMARY KEY,
    dateSelection   DATE,
    StatutSelection VARCHAR(50),
    idPreselection  VARCHAR(50),
    FOREIGN KEY (idPreselection) REFERENCES Preselection(idPreselection)
);

-- ==================== MISE EN STAGE ====================
CREATE TABLE IF NOT EXISTS MiseEnStage (
    idMiseStage   VARCHAR(50) PRIMARY KEY,
    dateDebut     DATE,
    dateFin       DATE,
    StatutStage   VARCHAR(50),
    idSelection   VARCHAR(50),
    idEntreprise  VARCHAR(50),
    FOREIGN KEY (idSelection) REFERENCES Selection(idSelection),
    FOREIGN KEY (idEntreprise) REFERENCES Entreprise(idEntreprise)
);

CREATE TABLE IF NOT EXISTS Decrochage (
    idDecrochage   VARCHAR(50) PRIMARY KEY,
    DateDecrochage DATE,
    idMiseStage    VARCHAR(50),
    FOREIGN KEY (idMiseStage) REFERENCES MiseEnStage(idMiseStage)
);

CREATE TABLE IF NOT EXISTS Renouvellement (
    idRenouvellement VARCHAR(50) PRIMARY KEY,
    dateDebut        DATE,
    dateFin          DATE,
    idMiseStage      VARCHAR(50),
    FOREIGN KEY (idMiseStage) REFERENCES MiseEnStage(idMiseStage)
);

-- ==================== INSERTION ====================
CREATE TABLE IF NOT EXISTS Insertion (
    idInsertion   VARCHAR(50) PRIMARY KEY,
    DateEmbauche  DATE,
    idPoste       VARCHAR(50),
    idMiseStage   VARCHAR(50),
    FOREIGN KEY (idPoste) REFERENCES Poste(idPoste),
    FOREIGN KEY (idMiseStage) REFERENCES MiseEnStage(idMiseStage)
);

CREATE TABLE IF NOT EXISTS Contrat (
    idContrat     VARCHAR(50) PRIMARY KEY,
    dateDebut     DATE,
    dateFin       DATE,
    Salaire       DECIMAL(12,2),
    idTypeContrat VARCHAR(50),
    idInsertion   VARCHAR(50),
    FOREIGN KEY (idTypeContrat) REFERENCES TypeContrat(idTypeContrat),
    FOREIGN KEY (idInsertion) REFERENCES Insertion(idInsertion)
);

-- ==================== LIAISONS ====================
CREATE TABLE IF NOT EXISTS piece_a_joindre (
    idCandidature VARCHAR(50),
    idDocument    VARCHAR(50),
    PRIMARY KEY (idCandidature, idDocument),
    FOREIGN KEY (idCandidature) REFERENCES Candidature(idCandidature),
    FOREIGN KEY (idDocument) REFERENCES Document(idDocument)
);

CREATE TABLE IF NOT EXISTS accorder (
    idRole       VARCHAR(50),
    idPermission VARCHAR(50),
    PRIMARY KEY (idRole, idPermission),
    FOREIGN KEY (idRole) REFERENCES Role(idRole),
    FOREIGN KEY (idPermission) REFERENCES Permission(idPermission)
);

CREATE TABLE IF NOT EXISTS appartient_a (
    idEntreprise VARCHAR(50),
    idSecteur    VARCHAR(50),
    PRIMARY KEY (idEntreprise, idSecteur),
    FOREIGN KEY (idEntreprise) REFERENCES Entreprise(idEntreprise),
    FOREIGN KEY (idSecteur) REFERENCES Secteur(idSecteur)
);

CREATE TABLE IF NOT EXISTS est_soumise_a (
    idOffre   VARCHAR(50),
    idCritere VARCHAR(50),
    PRIMARY KEY (idOffre, idCritere),
    FOREIGN KEY (idOffre) REFERENCES OffresStage(idOffre),
    FOREIGN KEY (idCritere) REFERENCES Critere(idCritere)
);

CREATE TABLE IF NOT EXISTS offre_competence (
    idOffre      VARCHAR(50),
    idCompetence VARCHAR(50),
    PRIMARY KEY (idOffre, idCompetence),
    FOREIGN KEY (idOffre) REFERENCES OffresStage(idOffre),
    FOREIGN KEY (idCompetence) REFERENCES Competence(idCompetence)
);

CREATE TABLE IF NOT EXISTS offre_prerequis (
    idOffre     VARCHAR(50),
    idPrerequis  VARCHAR(50),
    PRIMARY KEY (idOffre, idPrerequis),
    FOREIGN KEY (idOffre) REFERENCES OffresStage(idOffre),
    FOREIGN KEY (idPrerequis) REFERENCES PrerequisFormation(idPrerequis)
);

CREATE TABLE IF NOT EXISTS requiert_technique (
    idCritere  VARCHAR(50),
    idTechLing VARCHAR(50),
    PRIMARY KEY (idCritere, idTechLing),
    FOREIGN KEY (idCritere) REFERENCES Critere(idCritere),
    FOREIGN KEY (idTechLing) REFERENCES TechniqueLinguistique(idTechLing)
);

CREATE TABLE IF NOT EXISTS requiert_prerequis (
    idCritere   VARCHAR(50),
    idPrerequis VARCHAR(50),
    PRIMARY KEY (idCritere, idPrerequis),
    FOREIGN KEY (idCritere) REFERENCES Critere(idCritere),
    FOREIGN KEY (idPrerequis) REFERENCES PrerequisFormation(idPrerequis)
);

CREATE TABLE IF NOT EXISTS evaluer (
    idPreselection VARCHAR(50),
    idCritere      VARCHAR(50),
    estSatisfait   VARCHAR(50),
    PRIMARY KEY (idPreselection, idCritere),
    FOREIGN KEY (idPreselection) REFERENCES Preselection(idPreselection),
    FOREIGN KEY (idCritere) REFERENCES Critere(idCritere)
);

CREATE TABLE IF NOT EXISTS vise (
    idOffre         VARCHAR(50),
    idQualification VARCHAR(50),
    PRIMARY KEY (idOffre, idQualification),
    FOREIGN KEY (idOffre) REFERENCES OffresStage(idOffre),
    FOREIGN KEY (idQualification) REFERENCES Qualification(idQualification)
);

CREATE TABLE IF NOT EXISTS developpe (
    idQualification VARCHAR(50),
    idCompetence    VARCHAR(50),
    PRIMARY KEY (idQualification, idCompetence),
    FOREIGN KEY (idQualification) REFERENCES Qualification(idQualification),
    FOREIGN KEY (idCompetence) REFERENCES Competence(idCompetence)
);

CREATE TABLE IF NOT EXISTS exige (
    idCritere    VARCHAR(50),
    idCompetence VARCHAR(50),
    PRIMARY KEY (idCritere, idCompetence),
    FOREIGN KEY (idCritere) REFERENCES Critere(idCritere),
    FOREIGN KEY (idCompetence) REFERENCES Competence(idCompetence)
);

CREATE TABLE IF NOT EXISTS necessite (
    idCompetence VARCHAR(50),
    idPoste      VARCHAR(50),
    PRIMARY KEY (idCompetence, idPoste),
    FOREIGN KEY (idCompetence) REFERENCES Competence(idCompetence),
    FOREIGN KEY (idPoste) REFERENCES Poste(idPoste)
);

CREATE TABLE IF NOT EXISTS est_justifiee_par (
    idResiliation      VARCHAR(50),
    idMotifResiliation VARCHAR(50),
    PRIMARY KEY (idResiliation, idMotifResiliation),
    FOREIGN KEY (idResiliation) REFERENCES Resiliation(idResiliation),
    FOREIGN KEY (idMotifResiliation) REFERENCES MotifResiliation(idMotifResiliation)
);

CREATE TABLE IF NOT EXISTS est_justifie_par (
    idDecrochage      VARCHAR(50),
    idMotifDecrochage VARCHAR(50),
    PRIMARY KEY (idDecrochage, idMotifDecrochage),
    FOREIGN KEY (idDecrochage) REFERENCES Decrochage(idDecrochage),
    FOREIGN KEY (idMotifDecrochage) REFERENCES MotifDecrochage(idMotifDecrochage)
);

CREATE TABLE IF NOT EXISTS possede (
    `idDiplomé`  VARCHAR(50),
    idCompetence VARCHAR(50),
    PRIMARY KEY (`idDiplomé`, idCompetence),
    FOREIGN KEY (`idDiplomé`) REFERENCES `Diplomé`(`idDiplomé`),
    FOREIGN KEY (idCompetence) REFERENCES Competence(idCompetence)
);
-- Référentiel utilisé par CreerConventionUseCase (table absente du MLD initial).
CREATE TABLE IF NOT EXISTS Programme (
    idProgramme   VARCHAR(50) PRIMARY KEY,
    libelle       VARCHAR(100),
    typeProgramme VARCHAR(50)
);

-- ==================== MIGRATIONS ====================
-- Le hash BCrypt fait 60 caractères : la colonne doit être élargie.
-- MODIFY est idempotent, l'instruction peut être rejouée à chaque démarrage.
ALTER TABLE Utilisateur MODIFY COLUMN password VARCHAR(100);

