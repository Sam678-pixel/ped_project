package fonea.ped.infrastructure.persistence.repositories;


import fonea.ped.domain.diplome.DiplomeCompetence;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.LiaisonRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.DiplomeCompetenceRepository;

import java.sql.*;
import java.util.*;

public class JdbcDiplomeCompetenceRepository implements DiplomeCompetenceRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO possede (`idDiplomé`, idCompetence) VALUES (?, ?)";
    private static final String SQL_FIND_DIP =
        "SELECT * FROM possede WHERE `idDiplomé` = ?";
    private static final String SQL_FIND_COMP =
        "SELECT * FROM possede WHERE idCompetence = ?";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM possede WHERE `idDiplomé` = ? AND idCompetence = ?";
    private static final String SQL_DELETE =
        "DELETE FROM possede WHERE `idDiplomé` = ? AND idCompetence = ?";

    @Override public DiplomeCompetence sauvegarder(DiplomeCompetence l) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, l.getIdDiplome());
            ps.setString(2, l.getIdCompetence());
            ps.executeUpdate();
            return l;
        } catch (SQLException e) { throw new RuntimeException("JDBC DiplomeCompetence save", e); }
    }

    @Override public List<DiplomeCompetence> trouverParDiplome(String id) { return trouver(SQL_FIND_DIP, id); }
    @Override public List<DiplomeCompetence> trouverParCompetence(String id) { return trouver(SQL_FIND_COMP, id); }
    private List<DiplomeCompetence> trouver(String sql, String p) {
        List<DiplomeCompetence> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(LiaisonRowMappers.DIPLOME_COMPETENCE.map(rs));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC DiplomeCompetence find", e); }
    }

    @Override public boolean existe(String idD, String idC) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idD); ps.setString(2, idC);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC DiplomeCompetence exists", e); }
    }

    @Override public void supprimer(String idD, String idC) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setString(1, idD); ps.setString(2, idC);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC DiplomeCompetence delete", e); }
    }
}
