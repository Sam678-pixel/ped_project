package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.MiseEnStage.MiseEnStage;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.MiseEnStageRowMappers;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JdbcMiseEnStageRepository implements MiseEnStageRepository {

    private static final String SQL_SAVE = """
        INSERT INTO MiseEnStage (idMiseStage, dateDebut, dateFin, StatutStage, idSelection, idEntreprise)
        VALUES (?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            dateDebut = VALUES(dateDebut),
            dateFin = VALUES(dateFin),
            StatutStage = VALUES(StatutStage),
            idSelection = VALUES(idSelection),
            idEntreprise = VALUES(idEntreprise)
        """;
    private static final String SQL_FIND_ID = "SELECT * FROM MiseEnStage WHERE idMiseStage = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM MiseEnStage ORDER BY dateDebut DESC";
    private static final String SQL_FIND_SEL = "SELECT * FROM MiseEnStage WHERE idSelection = ?";
    private static final String SQL_FIND_ENT = "SELECT * FROM MiseEnStage WHERE idEntreprise = ?";
    private static final String SQL_EXISTS_SEL = "SELECT 1 FROM MiseEnStage WHERE idSelection = ?";
    private static final String SQL_EXISTS = "SELECT 1 FROM MiseEnStage WHERE idMiseStage = ?";
    private static final String SQL_DELETE = "DELETE FROM MiseEnStage WHERE idMiseStage = ?";

    @Override public MiseEnStage sauvegarder(MiseEnStage m) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, m.getIdMiseStage());
            if (m.getDateDebut() != null) ps.setDate(2, Date.valueOf(m.getDateDebut())); else ps.setNull(2, Types.DATE);
            if (m.getDateFin() != null) ps.setDate(3, Date.valueOf(m.getDateFin())); else ps.setNull(3, Types.DATE);
            ps.setString(4, m.getStatutStage().name());
            ps.setString(5, m.getIdSelection());
            ps.setString(6, m.getIdEntreprise());
            ps.executeUpdate();
            return m;
        } catch (SQLException e) { throw new RuntimeException("JDBC MiseEnStage save", e); }
    }

    @Override public Optional<MiseEnStage> trouverParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MiseEnStageRowMappers.MISE_EN_STAGE.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC MiseEnStage find", e); }
    }

    @Override public List<MiseEnStage> trouverToutes() {
        List<MiseEnStage> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(MiseEnStageRowMappers.MISE_EN_STAGE.map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC MiseEnStage findAll", e); }
    }

    @Override public Optional<MiseEnStage> trouverParSelection(String idSelection) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_SEL)) {
            ps.setString(1, idSelection);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MiseEnStageRowMappers.MISE_EN_STAGE.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC MiseEnStage findBySel", e); }
    }

    @Override public List<MiseEnStage> trouverParEntreprise(String idEntreprise) {
        List<MiseEnStage> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ENT)) {
            ps.setString(1, idEntreprise);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(MiseEnStageRowMappers.MISE_EN_STAGE.map(rs));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC MiseEnStage findByEnt", e); }
    }

    @Override public boolean existeParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC MiseEnStage exists", e); }
    }

    @Override public boolean existeParSelection(String idSelection) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS_SEL)) {
            ps.setString(1, idSelection);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC MiseEnStage existsBySel", e); }
    }

    @Override public void supprimer(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC MiseEnStage delete", e); }
    }

    @Override public String prochainId() {
        return "MES-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
