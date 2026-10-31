package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.recrutement.Preselection;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RecrutementRowMappers;
import fonea.ped.useCase.useCaseRecrutement.ports.PreselectionRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Implémentation JDBC du port PreselectionRepository.
 * SQL MySQL uniquement.
 */
public class JdbcPreselectionRepository implements PreselectionRepository {

    // ============ SQL ============

    private static final String SQL_SAVE = """
        INSERT INTO Preselection (idPreselection, datePreselection, StatutPreselection, idAgent, idCandidature)
        VALUES (?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            datePreselection = VALUES(datePreselection),
            StatutPreselection = VALUES(StatutPreselection),
            idAgent = VALUES(idAgent),
            idCandidature = VALUES(idCandidature)
        """;

    private static final String SQL_FIND_ID =
        "SELECT * FROM Preselection WHERE idPreselection = ?";

    private static final String SQL_FIND_ALL =
        "SELECT * FROM Preselection ORDER BY datePreselection DESC";

    private static final String SQL_FIND_BY_CANDIDATURE =
        "SELECT * FROM Preselection WHERE idCandidature = ?";

    private static final String SQL_FIND_BY_AGENT =
        "SELECT * FROM Preselection WHERE idAgent = ?";

    private static final String SQL_EXISTS_CAND =
        "SELECT 1 FROM Preselection WHERE idCandidature = ?";

    private static final String SQL_EXISTS =
        "SELECT 1 FROM Preselection WHERE idPreselection = ?";

    private static final String SQL_DELETE =
        "DELETE FROM Preselection WHERE idPreselection = ?";

    // ============ MÉTHODES DU PORT ============

    @Override
    public Preselection sauvegarder(Preselection preselection) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {

            ps.setString(1, preselection.getIdPreselection());

            if (preselection.getDatePreselection() != null) {
                ps.setDate(2, Date.valueOf(preselection.getDatePreselection()));
            } else {
                ps.setNull(2, Types.DATE);
            }

            ps.setString(3, preselection.getStatutPreselection().name());
            ps.setString(4, preselection.getIdAgent());
            ps.setString(5, preselection.getIdCandidature());

            ps.executeUpdate();
            return preselection;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Preselection", e);
        }
    }

    @Override
    public Optional<Preselection> trouverParId(String idPreselection) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {

            ps.setString(1, idPreselection);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.of(RecrutementRowMappers.PRESELECTION.map(rs))
                        : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Preselection", e);
        }
    }

    @Override
    public List<Preselection> trouverToutes() {
        List<Preselection> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultats.add(RecrutementRowMappers.PRESELECTION.map(rs));
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverToutes Preselection", e);
        }
    }

    @Override
    public List<Preselection> trouverParCandidature(String idCandidature) {
        return trouverListe(SQL_FIND_BY_CANDIDATURE, idCandidature);
    }

    @Override
    public List<Preselection> trouverParAgent(String idAgent) {
        return trouverListe(SQL_FIND_BY_AGENT, idAgent);
    }

    private List<Preselection> trouverListe(String sql, String param) {
        List<Preselection> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultats.add(RecrutementRowMappers.PRESELECTION.map(rs));
                }
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC find Preselection", e);
        }
    }

    @Override
    public boolean existeParCandidature(String idCandidature) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS_CAND)) {

            ps.setString(1, idCandidature);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParCandidature Preselection", e);
        }
    }

    @Override
    public boolean existeParId(String idPreselection) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {

            ps.setString(1, idPreselection);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Preselection", e);
        }
    }

    @Override
    public void supprimer(String idPreselection) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setString(1, idPreselection);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC supprimer Preselection", e);
        }
    }

    @Override
    public String prochainId() {
        return "PRE-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
