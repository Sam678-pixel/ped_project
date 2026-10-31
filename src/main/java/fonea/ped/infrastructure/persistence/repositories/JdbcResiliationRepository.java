package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.convention.model.Resiliation;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.ConventionRowMappers;
import fonea.ped.useCase.UseCaseConvention.ports.ResiliationRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JdbcResiliationRepository implements ResiliationRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Resiliation (idResiliation, dateResiliation, initiateur, idConvention)
        VALUES (?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            dateResiliation = VALUES(dateResiliation),
            initiateur = VALUES(initiateur),
            idConvention = VALUES(idConvention)
        """;
    private static final String SQL_FIND_ID =
        "SELECT * FROM Resiliation WHERE idResiliation = ?";
    private static final String SQL_FIND_BY_CONV =
        "SELECT * FROM Resiliation WHERE idConvention = ?";
    private static final String SQL_FIND_ALL =
        "SELECT * FROM Resiliation ORDER BY dateResiliation DESC";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM Resiliation WHERE idResiliation = ?";
    private static final String SQL_DELETE =
        "DELETE FROM Resiliation WHERE idResiliation = ?";

    @Override
    public Resiliation sauvegarder(Resiliation r) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, r.getIdResiliation());
            if (r.getDateResiliation() != null) ps.setDate(2, Date.valueOf(r.getDateResiliation()));
            else ps.setNull(2, Types.DATE);
            ps.setString(3, r.getInitiateur());
            ps.setString(4, r.getIdConvention());
            ps.executeUpdate();
            return r;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Resiliation", e);
        }
    }

    @Override
    public Optional<Resiliation> trouverParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(ConventionRowMappers.RESILIATION.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Resiliation", e);
        }
    }

    @Override
    public List<Resiliation> trouverParConvention(String idConvention) {
        List<Resiliation> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_CONV)) {
            ps.setString(1, idConvention);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(ConventionRowMappers.RESILIATION.map(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParConvention Resiliation", e);
        }
    }

    @Override
    public List<Resiliation> trouverToutes() {
        List<Resiliation> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(ConventionRowMappers.RESILIATION.map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverToutes Resiliation", e);
        }
    }

    @Override
    public boolean existeParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Resiliation", e);
        }
    }

    @Override
    public void supprimer(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC supprimer Resiliation", e);
        }
    }

    @Override
    public String prochainId() {
        return "RSL-" + UUID.randomUUID().toString().substring(0, 8);
    }
}