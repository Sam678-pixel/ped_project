package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.MiseEnStage.Decrochage;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.MiseEnStageRowMappers;
import fonea.ped.useCase.useCaseMiseEnStage.ports.DecrochageRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JdbcDecrochageRepository implements DecrochageRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Decrochage (idDecrochage, DateDecrochage, idMiseStage)
        VALUES (?, ?, ?)
        ON DUPLICATE KEY UPDATE
            DateDecrochage = VALUES(DateDecrochage),
            idMiseStage = VALUES(idMiseStage)
        """;
    private static final String SQL_FIND_ID = "SELECT * FROM Decrochage WHERE idDecrochage = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM Decrochage ORDER BY DateDecrochage DESC";
    private static final String SQL_FIND_MES = "SELECT * FROM Decrochage WHERE idMiseStage = ?";
    private static final String SQL_EXISTS_MES = "SELECT 1 FROM Decrochage WHERE idMiseStage = ?";
    private static final String SQL_EXISTS = "SELECT 1 FROM Decrochage WHERE idDecrochage = ?";
    private static final String SQL_DELETE = "DELETE FROM Decrochage WHERE idDecrochage = ?";

    @Override public Decrochage sauvegarder(Decrochage d) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, d.getIdDecrochage());
            if (d.getDateDecrochage() != null) ps.setDate(2, Date.valueOf(d.getDateDecrochage()));
            else ps.setNull(2, Types.DATE);
            ps.setString(3, d.getIdMiseStage());
            ps.executeUpdate();
            return d;
        } catch (SQLException e) { throw new RuntimeException("JDBC Decrochage save", e); }
    }

    @Override public Optional<Decrochage> trouverParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MiseEnStageRowMappers.DECROCHAGE.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Decrochage find", e); }
    }

    @Override public List<Decrochage> trouverTous() {
        List<Decrochage> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(MiseEnStageRowMappers.DECROCHAGE.map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC Decrochage findAll", e); }
    }

    @Override public Optional<Decrochage> trouverParMiseEnStage(String idMiseStage) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_MES)) {
            ps.setString(1, idMiseStage);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MiseEnStageRowMappers.DECROCHAGE.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Decrochage findByMes", e); }
    }

    @Override public boolean existeParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC Decrochage exists", e); }
    }

    @Override public boolean existeParMiseEnStage(String idMiseStage) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS_MES)) {
            ps.setString(1, idMiseStage);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC Decrochage existsByMes", e); }
    }

    @Override public void supprimer(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC Decrochage delete", e); }
    }

    @Override public String prochainId() {
        return "DEC-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
