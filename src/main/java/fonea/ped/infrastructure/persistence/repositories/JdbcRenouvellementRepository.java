package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.MiseEnStage.Renouvellement;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.MiseEnStageRowMappers;
import fonea.ped.useCase.useCaseMiseEnStage.ports.RenouvellementRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JdbcRenouvellementRepository implements RenouvellementRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Renouvellement (idRenouvellement, dateDebut, dateFin, idMiseStage)
        VALUES (?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            dateDebut = VALUES(dateDebut),
            dateFin = VALUES(dateFin),
            idMiseStage = VALUES(idMiseStage)
        """;
    private static final String SQL_FIND_ID = "SELECT * FROM Renouvellement WHERE idRenouvellement = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM Renouvellement ORDER BY dateDebut DESC";
    private static final String SQL_FIND_MES = "SELECT * FROM Renouvellement WHERE idMiseStage = ?";
    private static final String SQL_EXISTS_MES = "SELECT 1 FROM Renouvellement WHERE idMiseStage = ?";
    private static final String SQL_EXISTS = "SELECT 1 FROM Renouvellement WHERE idRenouvellement = ?";
    private static final String SQL_DELETE = "DELETE FROM Renouvellement WHERE idRenouvellement = ?";

    @Override public Renouvellement sauvegarder(Renouvellement r) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, r.getIdRenouvellement());
            if (r.getDateDebut() != null) ps.setDate(2, Date.valueOf(r.getDateDebut())); else ps.setNull(2, Types.DATE);
            if (r.getDateFin() != null) ps.setDate(3, Date.valueOf(r.getDateFin())); else ps.setNull(3, Types.DATE);
            ps.setString(4, r.getIdMiseStage());
            ps.executeUpdate();
            return r;
        } catch (SQLException e) { throw new RuntimeException("JDBC Renouvellement save", e); }
    }

    @Override public Optional<Renouvellement> trouverParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MiseEnStageRowMappers.RENOUVELLEMENT.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Renouvellement find", e); }
    }

    @Override public List<Renouvellement> trouverTous() {
        List<Renouvellement> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(MiseEnStageRowMappers.RENOUVELLEMENT.map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC Renouvellement findAll", e); }
    }

    @Override public Optional<Renouvellement> trouverParMiseEnStage(String idMiseStage) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_MES)) {
            ps.setString(1, idMiseStage);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MiseEnStageRowMappers.RENOUVELLEMENT.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Renouvellement findByMes", e); }
    }

    @Override public boolean existeParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC Renouvellement exists", e); }
    }

    @Override public boolean existeParMiseEnStage(String idMiseStage) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS_MES)) {
            ps.setString(1, idMiseStage);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC Renouvellement existsByMes", e); }
    }

    @Override public void supprimer(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC Renouvellement delete", e); }
    }

    @Override public String prochainId() {
        return "REN-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
