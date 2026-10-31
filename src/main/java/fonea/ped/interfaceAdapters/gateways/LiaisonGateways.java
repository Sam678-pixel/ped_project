package fonea.ped.interfaceAdapters.gateways;

import java.util.List;

import fonea.ped.domain.MiseEnStage.DecrochageMotif;
import fonea.ped.domain.Offres.CompetencePoste;
import fonea.ped.domain.Offres.CritereCompetence;
import fonea.ped.domain.Offres.CriterePrerequis;
import fonea.ped.domain.Offres.CritereTechniqueLinguistique;
import fonea.ped.domain.Offres.OffreCompetence;
import fonea.ped.domain.Offres.OffreCritere;
import fonea.ped.domain.Offres.OffrePrerequis;
import fonea.ped.domain.Offres.OffreQualification;
import fonea.ped.domain.Offres.PreselectionCritere;
import fonea.ped.domain.Offres.QualificationCompetence;
import fonea.ped.domain.convention.model.ResiliationMotif;
import fonea.ped.domain.diplome.DiplomeCompetence;
import fonea.ped.domain.entreprise.EntrepriseSecteur;
import fonea.ped.domain.recrutement.CandidatureDocument;
import fonea.ped.domain.securite.RolePermission;

import fonea.ped.infrastructure.persistence.repositories.JdbcCandidatureDocumentRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcCompetencePosteRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcCritereCompetenceRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcCriterePrerequisRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcCritereTechniqueLinguistiqueRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcDecrochageMotifRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcDiplomeCompetenceRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcEntrepriseSecteurRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcOffreCompetenceRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcOffreCritereRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcOffrePrerequisRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcOffreQualificationRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcPreselectionCritereRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcQualificationCompetenceRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcResiliationMotifRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcRolePermissionRepository;

import fonea.ped.useCase.UseCaseConvention.ports.ResiliationMotifRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.DecrochageMotifRepository;
import fonea.ped.useCase.useCaseOffres.ports.CompetencePosteRepository;
import fonea.ped.useCase.useCaseOffres.ports.CritereCompetenceRepository;
import fonea.ped.useCase.useCaseOffres.ports.CriterePrerequisRepository;
import fonea.ped.useCase.useCaseOffres.ports.CritereTechniqueLinguistiqueRepository;
import fonea.ped.useCase.useCaseOffres.ports.DiplomeCompetenceRepository;
import fonea.ped.useCase.useCaseOffres.ports.EntrepriseSecteurRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffreCompetenceRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffreCritereRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffrePrerequisRepository;
import fonea.ped.useCase.useCaseOffres.ports.OffreQualificationRepository;
import fonea.ped.useCase.useCaseOffres.ports.QualificationCompetenceRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureDocumentRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.PreselectionCritereRepository;
import fonea.ped.useCase.useCaseSecurite.RolePermissionRepository;

/**
 * Regroupe les gateways des liaisons (tables d'association) : ils implémentent
 * le port attendu par les cas d'usage et délèguent au repository JDBC.
 *
 * <p>Cette classe avait été générée avec des types d'un ancien paquet
 * {@code fonea.ped.domain.liaison} qui n'existe plus, et avec des méthodes
 * absentes des ports actuels : le projet ne compilait donc plus. Elle est
 * désormais alignée sur les ports réels.</p>
 */
public final class LiaisonGateways {

    private LiaisonGateways() {}

    // ==================== Offres ====================

    public static class OffreCritereGateway implements OffreCritereRepository {
        private final OffreCritereRepository delegate;

        public OffreCritereGateway() { this(new JdbcOffreCritereRepository()); }

        public OffreCritereGateway(OffreCritereRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public OffreCritere sauvegarder(OffreCritere liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<OffreCritere> trouverParOffre(String idOffre) {
            return delegate.trouverParOffre(idOffre);
        }

        @Override
        public List<OffreCritere> trouverParCritere(String idCritere) {
            return delegate.trouverParCritere(idCritere);
        }

        @Override
        public String prochainId() {
            return delegate.prochainId();
        }
    }

    public static class OffreCompetenceGateway implements OffreCompetenceRepository {
        private final OffreCompetenceRepository delegate;

        public OffreCompetenceGateway() { this(new JdbcOffreCompetenceRepository()); }

        public OffreCompetenceGateway(OffreCompetenceRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public OffreCompetence sauvegarder(OffreCompetence liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<OffreCompetence> trouverParOffre(String idOffre) {
            return delegate.trouverParOffre(idOffre);
        }

        @Override
        public String prochainId() {
            return delegate.prochainId();
        }
    }

    public static class OffrePrerequisGateway implements OffrePrerequisRepository {
        private final OffrePrerequisRepository delegate;

        public OffrePrerequisGateway() { this(new JdbcOffrePrerequisRepository()); }

        public OffrePrerequisGateway(OffrePrerequisRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public OffrePrerequis sauvegarder(OffrePrerequis liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<OffrePrerequis> trouverParOffre(String idOffre) {
            return delegate.trouverParOffre(idOffre);
        }

        @Override
        public String prochainId() {
            return delegate.prochainId();
        }
    }

    public static class OffreQualificationGateway implements OffreQualificationRepository {
        private final OffreQualificationRepository delegate;

        public OffreQualificationGateway() { this(new JdbcOffreQualificationRepository()); }

        public OffreQualificationGateway(OffreQualificationRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public OffreQualification sauvegarder(OffreQualification liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<OffreQualification> trouverParOffre(String idOffre) {
            return delegate.trouverParOffre(idOffre);
        }

        @Override
        public String prochainId() {
            return delegate.prochainId();
        }
    }

    public static class CompetencePosteGateway implements CompetencePosteRepository {
        private final CompetencePosteRepository delegate;

        public CompetencePosteGateway() { this(new JdbcCompetencePosteRepository()); }

        public CompetencePosteGateway(CompetencePosteRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public CompetencePoste sauvegarder(CompetencePoste liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<CompetencePoste> trouverParCompetence(String idCompetence) {
            return delegate.trouverParCompetence(idCompetence);
        }

        @Override
        public List<CompetencePoste> trouverParPoste(String idPoste) {
            return delegate.trouverParPoste(idPoste);
        }

        @Override
        public boolean existe(String idCompetence, String idPoste) {
            return delegate.existe(idCompetence, idPoste);
        }

        @Override
        public void supprimer(String idCompetence, String idPoste) {
            delegate.supprimer(idCompetence, idPoste);
        }
    }

    public static class QualificationCompetenceGateway implements QualificationCompetenceRepository {
        private final QualificationCompetenceRepository delegate;

        public QualificationCompetenceGateway() {
            this(new JdbcQualificationCompetenceRepository());
        }

        public QualificationCompetenceGateway(QualificationCompetenceRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public QualificationCompetence sauvegarder(QualificationCompetence liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<QualificationCompetence> trouverParQualification(String idQualification) {
            return delegate.trouverParQualification(idQualification);
        }

        @Override
        public List<QualificationCompetence> trouverParCompetence(String idCompetence) {
            return delegate.trouverParCompetence(idCompetence);
        }

        @Override
        public boolean existe(String idQualification, String idCompetence) {
            return delegate.existe(idQualification, idCompetence);
        }

        @Override
        public void supprimer(String idQualification, String idCompetence) {
            delegate.supprimer(idQualification, idCompetence);
        }
    }

    public static class CritereCompetenceGateway implements CritereCompetenceRepository {
        private final CritereCompetenceRepository delegate;

        public CritereCompetenceGateway() { this(new JdbcCritereCompetenceRepository()); }

        public CritereCompetenceGateway(CritereCompetenceRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public CritereCompetence sauvegarder(CritereCompetence liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<CritereCompetence> trouverParCritere(String idCritere) {
            return delegate.trouverParCritere(idCritere);
        }

        @Override
        public List<CritereCompetence> trouverParCompetence(String idCompetence) {
            return delegate.trouverParCompetence(idCompetence);
        }

        @Override
        public boolean existe(String idCritere, String idCompetence) {
            return delegate.existe(idCritere, idCompetence);
        }

        @Override
        public void supprimer(String idCritere, String idCompetence) {
            delegate.supprimer(idCritere, idCompetence);
        }
    }

    public static class CriterePrerequisGateway implements CriterePrerequisRepository {
        private final CriterePrerequisRepository delegate;

        public CriterePrerequisGateway() { this(new JdbcCriterePrerequisRepository()); }

        public CriterePrerequisGateway(CriterePrerequisRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public CriterePrerequis sauvegarder(CriterePrerequis liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<CriterePrerequis> trouverParCritere(String idCritere) {
            return delegate.trouverParCritere(idCritere);
        }

        @Override
        public List<CriterePrerequis> trouverParPrerequis(String idPrerequis) {
            return delegate.trouverParPrerequis(idPrerequis);
        }

        @Override
        public boolean existe(String idCritere, String idPrerequis) {
            return delegate.existe(idCritere, idPrerequis);
        }

        @Override
        public void supprimer(String idCritere, String idPrerequis) {
            delegate.supprimer(idCritere, idPrerequis);
        }
    }

    public static class CritereTechniqueLinguistiqueGateway
            implements CritereTechniqueLinguistiqueRepository {

        private final CritereTechniqueLinguistiqueRepository delegate;

        public CritereTechniqueLinguistiqueGateway() {
            this(new JdbcCritereTechniqueLinguistiqueRepository());
        }

        public CritereTechniqueLinguistiqueGateway(CritereTechniqueLinguistiqueRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public CritereTechniqueLinguistique sauvegarder(CritereTechniqueLinguistique liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<CritereTechniqueLinguistique> trouverParCritere(String idCritere) {
            return delegate.trouverParCritere(idCritere);
        }

        @Override
        public List<CritereTechniqueLinguistique> trouverParTechniqueLinguistique(
                String idTechniqueLinguistique) {
            return delegate.trouverParTechniqueLinguistique(idTechniqueLinguistique);
        }

        @Override
        public boolean existe(String idCritere, String idTechniqueLinguistique) {
            return delegate.existe(idCritere, idTechniqueLinguistique);
        }

        @Override
        public void supprimer(String idCritere, String idTechniqueLinguistique) {
            delegate.supprimer(idCritere, idTechniqueLinguistique);
        }
    }

    // ==================== Recrutement ====================

    public static class CandidatureDocumentGateway implements CandidatureDocumentRepository {
        private final CandidatureDocumentRepository delegate;

        public CandidatureDocumentGateway() { this(new JdbcCandidatureDocumentRepository()); }

        public CandidatureDocumentGateway(CandidatureDocumentRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public CandidatureDocument sauvegarder(CandidatureDocument liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<CandidatureDocument> trouverParCandidature(String idCandidature) {
            return delegate.trouverParCandidature(idCandidature);
        }

        @Override
        public List<CandidatureDocument> trouverParDocument(String idDocument) {
            return delegate.trouverParDocument(idDocument);
        }

        @Override
        public boolean existe(String idCandidature, String idDocument) {
            return delegate.existe(idCandidature, idDocument);
        }

        @Override
        public void supprimer(String idCandidature, String idDocument) {
            delegate.supprimer(idCandidature, idDocument);
        }
    }

    public static class PreselectionCritereGateway implements PreselectionCritereRepository {
        private final PreselectionCritereRepository delegate;

        public PreselectionCritereGateway() { this(new JdbcPreselectionCritereRepository()); }

        public PreselectionCritereGateway(PreselectionCritereRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public PreselectionCritere sauvegarder(PreselectionCritere liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<PreselectionCritere> trouverParPreselection(String idPreselection) {
            return delegate.trouverParPreselection(idPreselection);
        }

        @Override
        public List<PreselectionCritere> trouverParCritere(String idCritere) {
            return delegate.trouverParCritere(idCritere);
        }

        @Override
        public boolean existe(String idPreselection, String idCritere) {
            return delegate.existe(idPreselection, idCritere);
        }

        @Override
        public void supprimer(String idPreselection, String idCritere) {
            delegate.supprimer(idPreselection, idCritere);
        }
    }

    // ==================== Transverses ====================

    public static class RolePermissionGateway implements RolePermissionRepository {
        private final RolePermissionRepository delegate;

        public RolePermissionGateway() { this(new JdbcRolePermissionRepository()); }

        public RolePermissionGateway(RolePermissionRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public RolePermission sauvegarder(RolePermission liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<RolePermission> trouverParRole(String idRole) {
            return delegate.trouverParRole(idRole);
        }

        @Override
        public List<RolePermission> trouverParPermission(String idPermission) {
            return delegate.trouverParPermission(idPermission);
        }

        @Override
        public boolean existe(String idRole, String idPermission) {
            return delegate.existe(idRole, idPermission);
        }

        @Override
        public void supprimer(String idRole, String idPermission) {
            delegate.supprimer(idRole, idPermission);
        }
    }

    public static class EntrepriseSecteurGateway implements EntrepriseSecteurRepository {
        private final EntrepriseSecteurRepository delegate;

        public EntrepriseSecteurGateway() { this(new JdbcEntrepriseSecteurRepository()); }

        public EntrepriseSecteurGateway(EntrepriseSecteurRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public EntrepriseSecteur sauvegarder(EntrepriseSecteur liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<EntrepriseSecteur> trouverParEntreprise(String idEntreprise) {
            return delegate.trouverParEntreprise(idEntreprise);
        }

        @Override
        public List<EntrepriseSecteur> trouverParSecteur(String idSecteur) {
            return delegate.trouverParSecteur(idSecteur);
        }

        @Override
        public boolean existe(String idEntreprise, String idSecteur) {
            return delegate.existe(idEntreprise, idSecteur);
        }

        @Override
        public void supprimer(String idEntreprise, String idSecteur) {
            delegate.supprimer(idEntreprise, idSecteur);
        }
    }

    public static class DiplomeCompetenceGateway implements DiplomeCompetenceRepository {
        private final DiplomeCompetenceRepository delegate;

        public DiplomeCompetenceGateway() { this(new JdbcDiplomeCompetenceRepository()); }

        public DiplomeCompetenceGateway(DiplomeCompetenceRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public DiplomeCompetence sauvegarder(DiplomeCompetence liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<DiplomeCompetence> trouverParDiplome(String idDiplome) {
            return delegate.trouverParDiplome(idDiplome);
        }

        @Override
        public List<DiplomeCompetence> trouverParCompetence(String idCompetence) {
            return delegate.trouverParCompetence(idCompetence);
        }

        @Override
        public boolean existe(String idDiplome, String idCompetence) {
            return delegate.existe(idDiplome, idCompetence);
        }

        @Override
        public void supprimer(String idDiplome, String idCompetence) {
            delegate.supprimer(idDiplome, idCompetence);
        }
    }

    public static class ResiliationMotifGateway implements ResiliationMotifRepository {
        private final ResiliationMotifRepository delegate;

        public ResiliationMotifGateway() { this(new JdbcResiliationMotifRepository()); }

        public ResiliationMotifGateway(ResiliationMotifRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public ResiliationMotif sauvegarder(ResiliationMotif liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<ResiliationMotif> trouverParResiliation(String idResiliation) {
            return delegate.trouverParResiliation(idResiliation);
        }

        @Override
        public List<ResiliationMotif> trouverParMotif(String idMotifResiliation) {
            return delegate.trouverParMotif(idMotifResiliation);
        }

        @Override
        public boolean existe(String idResiliation, String idMotifResiliation) {
            return delegate.existe(idResiliation, idMotifResiliation);
        }

        @Override
        public void supprimer(String idResiliation, String idMotifResiliation) {
            delegate.supprimer(idResiliation, idMotifResiliation);
        }
    }

    public static class DecrochageMotifGateway implements DecrochageMotifRepository {
        private final DecrochageMotifRepository delegate;

        public DecrochageMotifGateway() { this(new JdbcDecrochageMotifRepository()); }

        public DecrochageMotifGateway(DecrochageMotifRepository delegate) {
            this.delegate = delegate;
        }

        @Override
        public DecrochageMotif sauvegarder(DecrochageMotif liaison) {
            return delegate.sauvegarder(liaison);
        }

        @Override
        public List<DecrochageMotif> trouverParDecrochage(String idDecrochage) {
            return delegate.trouverParDecrochage(idDecrochage);
        }

        @Override
        public List<DecrochageMotif> trouverParMotif(String idMotifDecrochage) {
            return delegate.trouverParMotif(idMotifDecrochage);
        }

        @Override
        public boolean existe(String idDecrochage, String idMotifDecrochage) {
            return delegate.existe(idDecrochage, idMotifDecrochage);
        }

        @Override
        public void supprimer(String idDecrochage, String idMotifDecrochage) {
            delegate.supprimer(idDecrochage, idMotifDecrochage);
        }
    }
}
