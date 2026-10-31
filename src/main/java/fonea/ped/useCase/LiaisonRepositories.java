package fonea.ped.useCase;

import java.util.List;

import fonea.ped.domain.MiseEnStage.DecrochageMotif;
import fonea.ped.domain.Offres.CompetencePoste;
import fonea.ped.domain.Offres.CritereCompetence;
import fonea.ped.domain.Offres.CriterePrerequis;
import fonea.ped.domain.Offres.CritereTechniqueLinguistique;
import fonea.ped.domain.Offres.QualificationCompetence;
import fonea.ped.domain.convention.model.ResiliationMotif;
import fonea.ped.domain.diplome.DiplomeCompetence;
import fonea.ped.domain.entreprise.EntrepriseSecteur;
import fonea.ped.domain.securite.RolePermission;

public final class LiaisonRepositories {

    private LiaisonRepositories() {}

    // ========== RolePermission (accorder) ==========
    public interface RolePermissionRepository {
        RolePermission sauvegarder(RolePermission liaison);
        List<RolePermission> trouverParRole(String idRole);
        List<RolePermission> trouverParPermission(String idPermission);
        boolean existe(String idRole, String idPermission);
        void supprimer(String idRole, String idPermission);
    }

    // ========== EntrepriseSecteur (appartient_à) ==========
    public interface EntrepriseSecteurRepository {
        EntrepriseSecteur sauvegarder(EntrepriseSecteur liaison);
        List<EntrepriseSecteur> trouverParEntreprise(String idEntreprise);
        List<EntrepriseSecteur> trouverParSecteur(String idSecteur);
        boolean existe(String idEntreprise, String idSecteur);
        void supprimer(String idEntreprise, String idSecteur);
    }

    // ========== DiplomeCompetence (possede) ==========
    public interface DiplomeCompetenceRepository {
        DiplomeCompetence sauvegarder(DiplomeCompetence liaison);
        List<DiplomeCompetence> trouverParDiplome(String idDiplome);
        List<DiplomeCompetence> trouverParCompetence(String idCompetence);
        boolean existe(String idDiplome, String idCompetence);
        void supprimer(String idDiplome, String idCompetence);
    }

    // ========== CritereCompetence (exige) ==========
    public interface CritereCompetenceRepository {
        CritereCompetence sauvegarder(CritereCompetence liaison);
        List<CritereCompetence> trouverParCritere(String idCritere);
        List<CritereCompetence> trouverParCompetence(String idCompetence);
        boolean existe(String idCritere, String idCompetence);
        void supprimer(String idCritere, String idCompetence);
    }

    // ========== CompetencePoste (necessite) ==========
    public interface CompetencePosteRepository {
        CompetencePoste sauvegarder(CompetencePoste liaison);
        List<CompetencePoste> trouverParCompetence(String idCompetence);
        List<CompetencePoste> trouverParPoste(String idPoste);
        boolean existe(String idCompetence, String idPoste);
        void supprimer(String idCompetence, String idPoste);
    }

    // ========== QualificationCompetence (developpe) ==========
    public interface QualificationCompetenceRepository {
        QualificationCompetence sauvegarder(QualificationCompetence liaison);
        List<QualificationCompetence> trouverParQualification(String idQualification);
        List<QualificationCompetence> trouverParCompetence(String idCompetence);
        boolean existe(String idQualification, String idCompetence);
        void supprimer(String idQualification, String idCompetence);
    }

    // ========== CriterePrerequis (requiert_prerequis) ==========
    public interface CriterePrerequisRepository {
        CriterePrerequis sauvegarder(CriterePrerequis liaison);
        List<CriterePrerequis> trouverParCritere(String idCritere);
        List<CriterePrerequis> trouverParPrerequis(String idPrerequis);
        boolean existe(String idCritere, String idPrerequis);
        void supprimer(String idCritere, String idPrerequis);
    }

    // ========== CritereTechniqueLinguistique (requiert_technique) ==========
    public interface CritereTechniqueLinguistiqueRepository {
        CritereTechniqueLinguistique sauvegarder(CritereTechniqueLinguistique liaison);
        List<CritereTechniqueLinguistique> trouverParCritere(String idCritere);
        List<CritereTechniqueLinguistique> trouverParTechniqueLinguistique(String idTechniqueLinguistique);
        boolean existe(String idCritere, String idTechniqueLinguistique);
        void supprimer(String idCritere, String idTechniqueLinguistique);
    }

    // ========== ResiliationMotif (est_justifiee_par) ==========
    public interface ResiliationMotifRepository {
        ResiliationMotif sauvegarder(ResiliationMotif liaison);
        List<ResiliationMotif> trouverParResiliation(String idResiliation);
        List<ResiliationMotif> trouverParMotif(String idMotifResiliation);
        boolean existe(String idResiliation, String idMotifResiliation);
        void supprimer(String idResiliation, String idMotifResiliation);
    }

    // ========== DecrochageMotif (est_justifie_par) ==========
    public interface DecrochageMotifRepository {
        DecrochageMotif sauvegarder(DecrochageMotif liaison);
        List<DecrochageMotif> trouverParDecrochage(String idDecrochage);
        List<DecrochageMotif> trouverParMotif(String idMotifDecrochage);
        boolean existe(String idDecrochage, String idMotifDecrochage);
        void supprimer(String idDecrochage, String idMotifDecrochage);
    }
}