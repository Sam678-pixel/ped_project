package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.insertion.Insertion;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.InsertionRowMappers;
import fonea.ped.useCase.useCaseInsertion.ports.InsertionRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JdbcInsertionRepository implements InsertionRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Insertion (idInsertion, DateEmbauche, idPoste, idMiseStage)
        VALUES (?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            DateEmbauche = VALUES(DateEmbauche),
            idPoste = VALUES(idPoste),
            idMiseStage = VALUES(idMiseStage)
        """;
    private static final String SQL_FIND_ID = "SELECT * FROM Insertion WHERE idInsertion = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM Insertion ORDER BY DateEmbauche DESC";
    private static final String SQL_FIND_MES = "SELECT * FROM Insertion WHERE idMiseStage = ?";
    private static final String SQL_EXISTS_MES = "SELECT 1 FROM Insertion WHERE idMiseStage = ?";
    private static final String SQL_EXISTS = "SELECT 1 FROM Insertion WHERE idInsertion = ?";
    private static final String SQL_DELETE = "DELETE FROM Insertion WHERE idInsertion = ?";

    @Override public Insertion sauvegarder(Insertion i) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, i.getIdInsertion());
            if (i.getDateEmbauche() != null) ps.setDate(2, Date.valueOf(i.getDateEmbauche()));
            else ps.setNull(2, Types.DATE);
            ps.setString(3, i.getIdPoste());
            ps.setString(4, i.getIdMiseStage());
            ps.executeUpdate();
            return i;
        } catch (SQLException e) { throw new RuntimeException("JDBC Insertion save", e); }
    }

    @Override public Optional<Insertion> trouverParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(InsertionRowMappers.INSERTION.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Insertion find", e); }
    }

    @Override public List<Insertion> trouverToutes() {
        List<Insertion> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(InsertionRowMappers.INSERTION.map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC Insertion findAll", e); }
    }

    @Override public Optional<Insertion> trouverParMiseEnStage(String idMiseStage) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_MES)) {
            ps.setString(1, idMiseStage);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(InsertionRowMappers.INSERTION.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Insertion findByMes", e); }
    }

    @Override public boolean existeParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC Insertion exists", e); }
    }

    @Override public boolean existeParMiseEnStage(String idMiseStage) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS_MES)) {
            ps.setString(1, idMiseStage);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC Insertion existsByMes", e); }
    }

    @Override public void supprimer(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC Insertion delete", e); }
    }

    @Override public String prochainId() {
        return "INS-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
