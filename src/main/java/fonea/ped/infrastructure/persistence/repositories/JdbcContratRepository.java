package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.insertion.Contrat;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.InsertionRowMappers;
import fonea.ped.useCase.useCaseInsertion.ports.ContratRepository;
import fonea.ped.useCase.useCaseInsertion.ports.ContratInsertionRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JdbcContratRepository implements ContratRepository,ContratInsertionRepository  {

    private static final String SQL_SAVE = """
        INSERT INTO Contrat (idContrat, dateDebut, dateFin, Salaire, idTypeContrat, idInsertion)
        VALUES (?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            dateDebut = VALUES(dateDebut),
            dateFin = VALUES(dateFin),
            Salaire = VALUES(Salaire),
            idTypeContrat = VALUES(idTypeContrat),
            idInsertion = VALUES(idInsertion)
        """;
    private static final String SQL_FIND_ID = "SELECT * FROM Contrat WHERE idContrat = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM Contrat ORDER BY dateDebut DESC";
    private static final String SQL_FIND_INS = "SELECT * FROM Contrat WHERE idInsertion = ?";
    private static final String SQL_EXISTS_INS = "SELECT 1 FROM Contrat WHERE idInsertion = ?";
    private static final String SQL_EXISTS = "SELECT 1 FROM Contrat WHERE idContrat = ?";
    private static final String SQL_DELETE = "DELETE FROM Contrat WHERE idContrat = ?";

    @Override public Contrat sauvegarder(Contrat c) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, c.getIdContrat());
            if (c.getDateDebut() != null) ps.setDate(2, Date.valueOf(c.getDateDebut())); else ps.setNull(2, Types.DATE);
            if (c.getDateFin() != null) ps.setDate(3, Date.valueOf(c.getDateFin())); else ps.setNull(3, Types.DATE);
            if (c.getSalaire() != null) ps.setDouble(4, c.getSalaire()); else ps.setNull(4, Types.DECIMAL);
            ps.setString(5, c.getIdTypeContrat());
            ps.setString(6, c.getIdInsertion());
            ps.executeUpdate();
            return c;
        } catch (SQLException e) { throw new RuntimeException("JDBC Contrat save", e); }
    }

    @Override public Optional<Contrat> trouverParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(InsertionRowMappers.CONTRAT.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Contrat find", e); }
    }

    @Override public List<Contrat> trouverTous() {
        List<Contrat> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(InsertionRowMappers.CONTRAT.map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC Contrat findAll", e); }
    }

    @Override public Optional<Contrat> trouverParInsertion(String idInsertion) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_INS)) {
            ps.setString(1, idInsertion);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(InsertionRowMappers.CONTRAT.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Contrat findByIns", e); }
    }

    @Override public boolean existeParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC Contrat exists", e); }
    }

    @Override public boolean existeParInsertion(String idInsertion) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS_INS)) {
            ps.setString(1, idInsertion);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC Contrat existsByIns", e); }
    }

    @Override public void supprimer(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC Contrat delete", e); }
    }

    @Override public String prochainId() {
        return "CTR-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
