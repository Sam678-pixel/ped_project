package fonea.ped.infrastructure.persistence.repositories;


import fonea.ped.domain.Offres.QualificationCompetence;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.LiaisonRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.QualificationCompetenceRepository;

import java.sql.*;
import java.util.*;

public class JdbcQualificationCompetenceRepository implements QualificationCompetenceRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO developpe (idQualification, idCompetence) VALUES (?, ?)";
    private static final String SQL_FIND_QUAL =
        "SELECT * FROM developpe WHERE idQualification = ?";
    private static final String SQL_FIND_COMP =
        "SELECT * FROM developpe WHERE idCompetence = ?";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM developpe WHERE idQualification = ? AND idCompetence = ?";
    private static final String SQL_DELETE =
        "DELETE FROM developpe WHERE idQualification = ? AND idCompetence = ?";

    @Override public QualificationCompetence sauvegarder(QualificationCompetence l) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, l.getIdQualification());
            ps.setString(2, l.getIdCompetence());
            ps.executeUpdate();
            return l;
        } catch (SQLException e) { throw new RuntimeException("JDBC QualificationCompetence save", e); }
    }

    @Override public List<QualificationCompetence> trouverParQualification(String id) { return trouver(SQL_FIND_QUAL, id); }
    @Override public List<QualificationCompetence> trouverParCompetence(String id) { return trouver(SQL_FIND_COMP, id); }
    private List<QualificationCompetence> trouver(String sql, String p) {
        List<QualificationCompetence> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(LiaisonRowMappers.QUALIFICATION_COMPETENCE.map(rs));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC QualificationCompetence find", e); }
    }

    @Override public boolean existe(String idQ, String idC) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idQ); ps.setString(2, idC);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC QualificationCompetence exists", e); }
    }

    @Override public void supprimer(String idQ, String idC) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setString(1, idQ); ps.setString(2, idC);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC QualificationCompetence delete", e); }
    }
}
