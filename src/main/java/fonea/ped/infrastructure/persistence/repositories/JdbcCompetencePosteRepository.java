package fonea.ped.infrastructure.persistence.repositories;


import fonea.ped.domain.Offres.CompetencePoste;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.LiaisonRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.CompetencePosteRepository;

import java.sql.*;
import java.util.*;

public class JdbcCompetencePosteRepository implements CompetencePosteRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO necessite (idCompetence, idPoste) VALUES (?, ?)";
    private static final String SQL_FIND_COMP =
        "SELECT * FROM necessite WHERE idCompetence = ?";
    private static final String SQL_FIND_POST =
        "SELECT * FROM necessite WHERE idPoste = ?";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM necessite WHERE idCompetence = ? AND idPoste = ?";
    private static final String SQL_DELETE =
        "DELETE FROM necessite WHERE idCompetence = ? AND idPoste = ?";

    @Override public CompetencePoste sauvegarder(CompetencePoste l) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, l.getIdCompetence());
            ps.setString(2, l.getIdPoste());
            ps.executeUpdate();
            return l;
        } catch (SQLException e) { throw new RuntimeException("JDBC CompetencePoste save", e); }
    }

    @Override public List<CompetencePoste> trouverParCompetence(String id) { return trouver(SQL_FIND_COMP, id); }
    @Override public List<CompetencePoste> trouverParPoste(String id) { return trouver(SQL_FIND_POST, id); }
    private List<CompetencePoste> trouver(String sql, String p) {
        List<CompetencePoste> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(LiaisonRowMappers.COMPETENCE_POSTE.map(rs));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC CompetencePoste find", e); }
    }

    @Override public boolean existe(String idC, String idP) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idC); ps.setString(2, idP);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC CompetencePoste exists", e); }
    }

    @Override public void supprimer(String idC, String idP) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setString(1, idC); ps.setString(2, idP);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC CompetencePoste delete", e); }
    }
}
