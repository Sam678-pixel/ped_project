package fonea.ped.infrastructure.persistence.repositories;


import fonea.ped.domain.Offres.CritereCompetence;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.LiaisonRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.CritereCompetenceRepository;

import java.sql.*;
import java.util.*;

public class JdbcCritereCompetenceRepository implements CritereCompetenceRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO exige (idCritere, idCompetence) VALUES (?, ?)";
    private static final String SQL_FIND_CRIT =
        "SELECT * FROM exige WHERE idCritere = ?";
    private static final String SQL_FIND_COMP =
        "SELECT * FROM exige WHERE idCompetence = ?";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM exige WHERE idCritere = ? AND idCompetence = ?";
    private static final String SQL_DELETE =
        "DELETE FROM exige WHERE idCritere = ? AND idCompetence = ?";

    @Override public CritereCompetence sauvegarder(CritereCompetence l) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, l.getIdCritere());
            ps.setString(2, l.getIdCompetence());
            ps.executeUpdate();
            return l;
        } catch (SQLException e) { throw new RuntimeException("JDBC CritereCompetence save", e); }
    }

    @Override public List<CritereCompetence> trouverParCritere(String id) { return trouver(SQL_FIND_CRIT, id); }
    @Override public List<CritereCompetence> trouverParCompetence(String id) { return trouver(SQL_FIND_COMP, id); }
    private List<CritereCompetence> trouver(String sql, String p) {
        List<CritereCompetence> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(LiaisonRowMappers.CRITERE_COMPETENCE.map(rs));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC CritereCompetence find", e); }
    }

    @Override public boolean existe(String idCr, String idCo) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idCr); ps.setString(2, idCo);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC CritereCompetence exists", e); }
    }

    @Override public void supprimer(String idCr, String idCo) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setString(1, idCr); ps.setString(2, idCo);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC CritereCompetence delete", e); }
    }
}
