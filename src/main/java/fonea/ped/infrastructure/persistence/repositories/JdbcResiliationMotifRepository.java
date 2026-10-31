package fonea.ped.infrastructure.persistence.repositories;


import fonea.ped.domain.convention.model.ResiliationMotif;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.LiaisonRowMappers;
import fonea.ped.useCase.UseCaseConvention.ports.ResiliationMotifRepository;

import java.sql.*;
import java.util.*;

public class JdbcResiliationMotifRepository implements ResiliationMotifRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO est_justifiee_par (idResiliation, idMotifResiliation) VALUES (?, ?)";
    private static final String SQL_FIND_RES =
        "SELECT * FROM est_justifiee_par WHERE idResiliation = ?";
    private static final String SQL_FIND_MOT =
        "SELECT * FROM est_justifiee_par WHERE idMotifResiliation = ?";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM est_justifiee_par WHERE idResiliation = ? AND idMotifResiliation = ?";
    private static final String SQL_DELETE =
        "DELETE FROM est_justifiee_par WHERE idResiliation = ? AND idMotifResiliation = ?";

    @Override public ResiliationMotif sauvegarder(ResiliationMotif l) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, l.getIdResiliation());
            ps.setString(2, l.getIdMotifResiliation());
            ps.executeUpdate();
            return l;
        } catch (SQLException e) { throw new RuntimeException("JDBC ResiliationMotif save", e); }
    }

    @Override public List<ResiliationMotif> trouverParResiliation(String id) { return trouver(SQL_FIND_RES, id); }
    @Override public List<ResiliationMotif> trouverParMotif(String id) { return trouver(SQL_FIND_MOT, id); }
    private List<ResiliationMotif> trouver(String sql, String p) {
        List<ResiliationMotif> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(LiaisonRowMappers.RESILIATION_MOTIF.map(rs));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC ResiliationMotif find", e); }
    }

    @Override public boolean existe(String idR, String idM) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idR); ps.setString(2, idM);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC ResiliationMotif exists", e); }
    }

    @Override public void supprimer(String idR, String idM) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setString(1, idR); ps.setString(2, idM);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC ResiliationMotif delete", e); }
    }
}