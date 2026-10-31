package fonea.ped.infrastructure.persistence.repositories;

import java.sql.*;
import java.util.*;

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
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.LiaisonRowMappers;
import fonea.ped.useCase.LiaisonRepositories.CompetencePosteRepository;
import fonea.ped.useCase.LiaisonRepositories.CritereCompetenceRepository;
import fonea.ped.useCase.LiaisonRepositories.CriterePrerequisRepository;
import fonea.ped.useCase.LiaisonRepositories.CritereTechniqueLinguistiqueRepository;
import fonea.ped.useCase.LiaisonRepositories.DecrochageMotifRepository;
import fonea.ped.useCase.LiaisonRepositories.DiplomeCompetenceRepository;
import fonea.ped.useCase.LiaisonRepositories.EntrepriseSecteurRepository;
import fonea.ped.useCase.LiaisonRepositories.QualificationCompetenceRepository;
import fonea.ped.useCase.LiaisonRepositories.ResiliationMotifRepository;
import fonea.ped.useCase.useCaseSecurite.RolePermissionRepository;

/**
 * Regroupe les 10 implémentations JDBC des liaisons restantes.
 * Chaque classe interne implémente un port de LiaisonRepositories.
 */
public final class JdbcLiaisonsTransversesRepository {

    private JdbcLiaisonsTransversesRepository() {}

    // ==================== 1. RolePermission (accorder) ====================
    public static class JdbcRolePermissionRepository implements RolePermissionRepository {
        private static final String SAVE = "INSERT IGNORE INTO accorder (idRole, idPermission) VALUES (?, ?)";
        private static final String BY_ROLE = "SELECT * FROM accorder WHERE idRole = ?";
        private static final String BY_PERM = "SELECT * FROM accorder WHERE idPermission = ?";
        private static final String EXISTS = "SELECT 1 FROM accorder WHERE idRole = ? AND idPermission = ?";
        private static final String DEL = "DELETE FROM accorder WHERE idRole = ? AND idPermission = ?";

        @Override public RolePermission sauvegarder(RolePermission l) {
            try (Connection c = DatabaseConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(SAVE)) {
                ps.setString(1, l.getIdRole()); ps.setString(2, l.getIdPermission());
                ps.executeUpdate(); return l;
            } catch (SQLException e) { throw new RuntimeException("JDBC RolePermission save", e); }
        }
        @Override public List<RolePermission> trouverParRole(String id) {
            return find(BY_ROLE, id, LiaisonRowMappers.ROLE_PERMISSION);
        }
        @Override public List<RolePermission> trouverParPermission(String id) {
            return find(BY_PERM, id, LiaisonRowMappers.ROLE_PERMISSION);
        }
        @Override public boolean existe(String a, String b) { return exists(EXISTS, a, b); }
        @Override public void supprimer(String a, String b) { delete(DEL, a, b); }
    }

    // ==================== 2. EntrepriseSecteur (appartient_à) ====================
    public static class JdbcEntrepriseSecteurRepository implements EntrepriseSecteurRepository {
        private static final String SAVE = "INSERT IGNORE INTO appartient_a (idEntreprise, idSecteur) VALUES (?, ?)";
        private static final String BY_ENT = "SELECT * FROM appartient_a WHERE idEntreprise = ?";
        private static final String BY_SEC = "SELECT * FROM appartient_a WHERE idSecteur = ?";
        private static final String EXISTS = "SELECT 1 FROM appartient_a WHERE idEntreprise = ? AND idSecteur = ?";
        private static final String DEL = "DELETE FROM appartient_a WHERE idEntreprise = ? AND idSecteur = ?";

         public EntrepriseSecteur sauvegarder(EntrepriseSecteur l) {
            try (Connection c = DatabaseConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(SAVE)) {
                ps.setString(1, l.getIdEntreprise()); ps.setString(2, l.getIdSecteur());
                ps.executeUpdate(); return l;
            } catch (SQLException e) { throw new RuntimeException("JDBC EntrepriseSecteur save", e); }
        }
        @Override public List<EntrepriseSecteur> trouverParEntreprise(String id) {
            return find(BY_ENT, id, LiaisonRowMappers.ENTREPRISE_SECTEUR);
        }
        @Override public List<EntrepriseSecteur> trouverParSecteur(String id) {
            return find(BY_SEC, id, LiaisonRowMappers.ENTREPRISE_SECTEUR);
        }
        @Override public boolean existe(String a, String b) { return exists(EXISTS, a, b); }
         @Override public void supprimer(String a, String b) { delete(DEL, a, b); }
    }

    // ==================== 3. DiplomeCompetence (possede) ====================
    public static class JdbcDiplomeCompetenceRepository implements DiplomeCompetenceRepository {
        private static final String SAVE = "INSERT IGNORE INTO possede (idDiplomé, idCompetence) VALUES (?, ?)";
        private static final String BY_DIP = "SELECT * FROM possede WHERE idDiplomé = ?";
        private static final String BY_COMP = "SELECT * FROM possede WHERE idCompetence = ?";
        private static final String EXISTS = "SELECT 1 FROM possede WHERE idDiplomé = ? AND idCompetence = ?";
        private static final String DEL = "DELETE FROM possede WHERE idDiplomé = ? AND idCompetence = ?";

        @Override public DiplomeCompetence sauvegarder(DiplomeCompetence l) {
            try (Connection c = DatabaseConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(SAVE)) {
                ps.setString(1, l.getIdDiplome()); ps.setString(2, l.getIdCompetence());
                ps.executeUpdate(); return l;
            } catch (SQLException e) { throw new RuntimeException("JDBC DiplomeCompetence save", e); }
        }
        @Override public List<DiplomeCompetence> trouverParDiplome(String id) {
            return find(BY_DIP, id, LiaisonRowMappers.DIPLOME_COMPETENCE);
        }
        @Override public List<DiplomeCompetence> trouverParCompetence(String id) {
            return find(BY_COMP, id, LiaisonRowMappers.DIPLOME_COMPETENCE);
        }
        @Override public boolean existe(String a, String b) { return exists(EXISTS, a, b); }
        @Override public void supprimer(String a, String b) { delete(DEL, a, b); }
    }

    // ==================== 4. CritereCompetence (exige) ====================
    public static class JdbcCritereCompetenceRepository implements CritereCompetenceRepository {
        private static final String SAVE = "INSERT IGNORE INTO exige (idCritere, idCompetence) VALUES (?, ?)";
        private static final String BY_CRIT = "SELECT * FROM exige WHERE idCritere = ?";
        private static final String BY_COMP = "SELECT * FROM exige WHERE idCompetence = ?";
        private static final String EXISTS = "SELECT 1 FROM exige WHERE idCritere = ? AND idCompetence = ?";
        private static final String DEL = "DELETE FROM exige WHERE idCritere = ? AND idCompetence = ?";

        @Override public CritereCompetence sauvegarder(CritereCompetence l) {
            try (Connection c = DatabaseConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(SAVE)) {
                ps.setString(1, l.getIdCritere()); ps.setString(2, l.getIdCompetence());
                ps.executeUpdate(); return l;
            } catch (SQLException e) { throw new RuntimeException("JDBC CritereCompetence save", e); }
        }
        @Override public List<CritereCompetence> trouverParCritere(String id) {
            return find(BY_CRIT, id, LiaisonRowMappers.CRITERE_COMPETENCE);
        }
        @Override public List<CritereCompetence> trouverParCompetence(String id) {
            return find(BY_COMP, id, LiaisonRowMappers.CRITERE_COMPETENCE);
        }
        @Override public boolean existe(String a, String b) { return exists(EXISTS, a, b); }
        @Override public void supprimer(String a, String b) { delete(DEL, a, b); }
    }

    // ==================== 5. CompetencePoste (necessite) ====================
    public static class JdbcCompetencePosteRepository implements CompetencePosteRepository {
        private static final String SAVE = "INSERT IGNORE INTO necessite (idCompetence, idPoste) VALUES (?, ?)";
        private static final String BY_COMP = "SELECT * FROM necessite WHERE idCompetence = ?";
        private static final String BY_POSTE = "SELECT * FROM necessite WHERE idPoste = ?";
        private static final String EXISTS = "SELECT 1 FROM necessite WHERE idCompetence = ? AND idPoste = ?";
        private static final String DEL = "DELETE FROM necessite WHERE idCompetence = ? AND idPoste = ?";

        @Override public CompetencePoste sauvegarder(CompetencePoste l) {
            try (Connection c = DatabaseConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(SAVE)) {
                ps.setString(1, l.getIdCompetence()); ps.setString(2, l.getIdPoste());
                ps.executeUpdate(); return l;
            } catch (SQLException e) { throw new RuntimeException("JDBC CompetencePoste save", e); }
        }
        @Override public List<CompetencePoste> trouverParCompetence(String id) {
            return find(BY_COMP, id, LiaisonRowMappers.COMPETENCE_POSTE);
        }
        @Override public List<CompetencePoste> trouverParPoste(String id) {
            return find(BY_POSTE, id, LiaisonRowMappers.COMPETENCE_POSTE);
        }
        @Override public boolean existe(String a, String b) { return exists(EXISTS, a, b); }
        @Override public void supprimer(String a, String b) { delete(DEL, a, b); }
    }

    // ==================== 6. QualificationCompetence (developpe) ====================
    public static class JdbcQualificationCompetenceRepository implements QualificationCompetenceRepository {
        private static final String SAVE = "INSERT IGNORE INTO developpe (idQualification, idCompetence) VALUES (?, ?)";
        private static final String BY_QUAL = "SELECT * FROM developpe WHERE idQualification = ?";
        private static final String BY_COMP = "SELECT * FROM developpe WHERE idCompetence = ?";
        private static final String EXISTS = "SELECT 1 FROM developpe WHERE idQualification = ? AND idCompetence = ?";
        private static final String DEL = "DELETE FROM developpe WHERE idQualification = ? AND idCompetence = ?";

        @Override public QualificationCompetence sauvegarder(QualificationCompetence l) {
            try (Connection c = DatabaseConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(SAVE)) {
                ps.setString(1, l.getIdQualification()); ps.setString(2, l.getIdCompetence());
                ps.executeUpdate(); return l;
            } catch (SQLException e) { throw new RuntimeException("JDBC QualificationCompetence save", e); }
        }
        @Override public List<QualificationCompetence> trouverParQualification(String id) {
            return find(BY_QUAL, id, LiaisonRowMappers.QUALIFICATION_COMPETENCE);
        }
        @Override public List<QualificationCompetence> trouverParCompetence(String id) {
            return find(BY_COMP, id, LiaisonRowMappers.QUALIFICATION_COMPETENCE);
        }
        @Override public boolean existe(String a, String b) { return exists(EXISTS, a, b); }
        @Override public void supprimer(String a, String b) { delete(DEL, a, b); }
    }

    // ==================== 7. CriterePrerequis (requiert_prerequis) ====================
    public static class JdbcCriterePrerequisRepository implements CriterePrerequisRepository {
        private static final String SAVE = "INSERT IGNORE INTO requiert_prerequis (idCritere, idPrerequis) VALUES (?, ?)";
        private static final String BY_CRIT = "SELECT * FROM requiert_prerequis WHERE idCritere = ?";
        private static final String BY_PRE = "SELECT * FROM requiert_prerequis WHERE idPrerequis = ?";
        private static final String EXISTS = "SELECT 1 FROM requiert_prerequis WHERE idCritere = ? AND idPrerequis = ?";
        private static final String DEL = "DELETE FROM requiert_prerequis WHERE idCritere = ? AND idPrerequis = ?";

        @Override public CriterePrerequis sauvegarder(CriterePrerequis l) {
            try (Connection c = DatabaseConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(SAVE)) {
                ps.setString(1, l.getIdCritere()); ps.setString(2, l.getIdPrerequis());
                ps.executeUpdate(); return l;
            } catch (SQLException e) { throw new RuntimeException("JDBC CriterePrerequis save", e); }
        }
        @Override public List<CriterePrerequis> trouverParCritere(String id) {
            return find(BY_CRIT, id, LiaisonRowMappers.CRITERE_PREREQUIS);
        }
        @Override public List<CriterePrerequis> trouverParPrerequis(String id) {
            return find(BY_PRE, id, LiaisonRowMappers.CRITERE_PREREQUIS);
        }
        @Override public boolean existe(String a, String b) { return exists(EXISTS, a, b); }
        @Override public void supprimer(String a, String b) { delete(DEL, a, b); }
    }

    // ==================== 8. CritereTechniqueLinguistique (requiert_technique) ====================
    public static class JdbcCritereTechniqueLinguistiqueRepository implements CritereTechniqueLinguistiqueRepository {
        private static final String SAVE = "INSERT IGNORE INTO requiert_technique (idCritere, idTechLing) VALUES (?, ?)";
        private static final String BY_CRIT = "SELECT * FROM requiert_technique WHERE idCritere = ?";
        private static final String BY_TL = "SELECT * FROM requiert_technique WHERE idTechLing = ?";
        private static final String EXISTS = "SELECT 1 FROM requiert_technique WHERE idCritere = ? AND idTechLing = ?";
        private static final String DEL = "DELETE FROM requiert_technique WHERE idCritere = ? AND idTechLing = ?";

        @Override public CritereTechniqueLinguistique sauvegarder(CritereTechniqueLinguistique l) {
            try (Connection c = DatabaseConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(SAVE)) {
                ps.setString(1, l.getIdCritere()); ps.setString(2, l.getIdTechniqueLinguistique());
                ps.executeUpdate(); return l;
            } catch (SQLException e) { throw new RuntimeException("JDBC CritereTechniqueLinguistique save", e); }
        }
        @Override public List<CritereTechniqueLinguistique> trouverParCritere(String id) {
            return find(BY_CRIT, id, LiaisonRowMappers.CRITERE_TECHNIQUE_LINGUISTIQUE);
        }
        @Override public List<CritereTechniqueLinguistique> trouverParTechniqueLinguistique(String id) {
            return find(BY_TL, id, LiaisonRowMappers.CRITERE_TECHNIQUE_LINGUISTIQUE);
        }
        @Override public boolean existe(String a, String b) { return exists(EXISTS, a, b); }
        @Override public void supprimer(String a, String b) { delete(DEL, a, b); }
    }

    // ==================== 9. ResiliationMotif (est_justifiee_par) ====================
    public static class JdbcResiliationMotifRepository implements ResiliationMotifRepository {
        private static final String SAVE = "INSERT IGNORE INTO est_justifiee_par (idResiliation, idMotifResiliation) VALUES (?, ?)";
        private static final String BY_RES = "SELECT * FROM est_justifiee_par WHERE idResiliation = ?";
        private static final String BY_MOT = "SELECT * FROM est_justifiee_par WHERE idMotifResiliation = ?";
        private static final String EXISTS = "SELECT 1 FROM est_justifiee_par WHERE idResiliation = ? AND idMotifResiliation = ?";
        private static final String DEL = "DELETE FROM est_justifiee_par WHERE idResiliation = ? AND idMotifResiliation = ?";

        @Override public ResiliationMotif sauvegarder(ResiliationMotif l) {
            try (Connection c = DatabaseConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(SAVE)) {
                ps.setString(1, l.getIdResiliation()); ps.setString(2, l.getIdMotifResiliation());
                ps.executeUpdate(); return l;
            } catch (SQLException e) { throw new RuntimeException("JDBC ResiliationMotif save", e); }
        }
        @Override public List<ResiliationMotif> trouverParResiliation(String id) {
            return find(BY_RES, id, LiaisonRowMappers.RESILIATION_MOTIF);
        }
        @Override public List<ResiliationMotif> trouverParMotif(String id) {
            return find(BY_MOT, id, LiaisonRowMappers.RESILIATION_MOTIF);
        }
        @Override public boolean existe(String a, String b) { return exists(EXISTS, a, b); }
        @Override public void supprimer(String a, String b) { delete(DEL, a, b); }
    }

    // ==================== 10. DecrochageMotif (est_justifie_par) ====================
    public static class JdbcDecrochageMotifRepository implements DecrochageMotifRepository {
        private static final String SAVE = "INSERT IGNORE INTO est_justifie_par (idDecrochage, idMotifDecrochage) VALUES (?, ?)";
        private static final String BY_DEC = "SELECT * FROM est_justifie_par WHERE idDecrochage = ?";
        private static final String BY_MOT = "SELECT * FROM est_justifie_par WHERE idMotifDecrochage = ?";
        private static final String EXISTS = "SELECT 1 FROM est_justifie_par WHERE idDecrochage = ? AND idMotifDecrochage = ?";
        private static final String DEL = "DELETE FROM est_justifie_par WHERE idDecrochage = ? AND idMotifDecrochage = ?";

        @Override public DecrochageMotif sauvegarder(DecrochageMotif l) {
            try (Connection c = DatabaseConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(SAVE)) {
                ps.setString(1, l.getIdDecrochage()); ps.setString(2, l.getIdMotifDecrochage());
                ps.executeUpdate(); return l;
            } catch (SQLException e) { throw new RuntimeException("JDBC DecrochageMotif save", e); }
        }
        @Override public List<DecrochageMotif> trouverParDecrochage(String id) {
            return find(BY_DEC, id, LiaisonRowMappers.DECROCHAGE_MOTIF);
        }
        @Override public List<DecrochageMotif> trouverParMotif(String id) {
            return find(BY_MOT, id, LiaisonRowMappers.DECROCHAGE_MOTIF);
        }
        @Override public boolean existe(String a, String b) { return exists(EXISTS, a, b); }
        @Override public void supprimer(String a, String b) { delete(DEL, a, b); }
    }

    // ==================== HELPERS GÉNÉRIQUES ====================

    private static <T> List<T> find(String sql, String param, fonea.ped.infrastructure.persistence.mappers.RowMapper<T> mapper) {
        List<T> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapper.map(rs));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC liaison find", e); }
    }

    private static boolean exists(String sql, String a, String b) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a); ps.setString(2, b);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC liaison exists", e); }
    }

    private static void delete(String sql, String a, String b) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a); ps.setString(2, b);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC liaison delete", e); }
    }
}
