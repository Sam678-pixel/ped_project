package fonea.ped.infrastructure.persistence.repositories;


import fonea.ped.domain.Offres.CriterePrerequis;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.LiaisonRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.CriterePrerequisRepository;

import java.sql.*;
import java.util.*;

public class JdbcCriterePrerequisRepository implements CriterePrerequisRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO requiert_prerequis (idCritere, idPrerequis) VALUES (?, ?)";
    private static final String SQL_FIND_CRIT =
        "SELECT * FROM requiert_prerequis WHERE idCritere = ?";
    private static final String SQL_FIND_PRE =
        "SELECT * FROM requiert_prerequis WHERE idPrerequis = ?";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM requiert_prerequis WHERE idCritere = ? AND idPrerequis = ?";
    private static final String SQL_DELETE =
        "DELETE FROM requiert_prerequis WHERE idCritere = ? AND idPrerequis = ?";

    @Override public CriterePrerequis sauvegarder(CriterePrerequis l) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, l.getIdCritere());
            ps.setString(2, l.getIdPrerequis());
            ps.executeUpdate();
            return l;
        } catch (SQLException e) { throw new RuntimeException("JDBC CriterePrerequis save", e); }
    }

    @Override public List<CriterePrerequis> trouverParCritere(String id) { return trouver(SQL_FIND_CRIT, id); }
    @Override public List<CriterePrerequis> trouverParPrerequis(String id) { return trouver(SQL_FIND_PRE, id); }
    private List<CriterePrerequis> trouver(String sql, String p) {
        List<CriterePrerequis> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(LiaisonRowMappers.CRITERE_PREREQUIS.map(rs));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC CriterePrerequis find", e); }
    }

    @Override public boolean existe(String idCr, String idP) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idCr); ps.setString(2, idP);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC CriterePrerequis exists", e); }
    }

    @Override public void supprimer(String idCr, String idP) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setString(1, idCr); ps.setString(2, idP);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC CriterePrerequis delete", e); }
    }
}
