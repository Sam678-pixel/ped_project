package fonea.ped.infrastructure.persistence.repositories;


import fonea.ped.domain.Offres.CritereTechniqueLinguistique;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.LiaisonRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.CritereTechniqueLinguistiqueRepository;

import java.sql.*;
import java.util.*;

public class JdbcCritereTechniqueLinguistiqueRepository implements CritereTechniqueLinguistiqueRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO requiert_technique (idCritere, idTechLing) VALUES (?, ?)";
    private static final String SQL_FIND_CRIT =
        "SELECT * FROM requiert_technique WHERE idCritere = ?";
    private static final String SQL_FIND_TECH =
        "SELECT * FROM requiert_technique WHERE idTechLing = ?";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM requiert_technique WHERE idCritere = ? AND idTechLing = ?";
    private static final String SQL_DELETE =
        "DELETE FROM requiert_technique WHERE idCritere = ? AND idTechLing = ?";

    @Override public CritereTechniqueLinguistique sauvegarder(CritereTechniqueLinguistique l) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, l.getIdCritere());
            ps.setString(2, l.getIdTechniqueLinguistique());
            ps.executeUpdate();
            return l;
        } catch (SQLException e) { throw new RuntimeException("JDBC CritereTechniqueLinguistique save", e); }
    }

    @Override public List<CritereTechniqueLinguistique> trouverParCritere(String id) { return trouver(SQL_FIND_CRIT, id); }
    @Override public List<CritereTechniqueLinguistique> trouverParTechniqueLinguistique(String id) { return trouver(SQL_FIND_TECH, id); }
    private List<CritereTechniqueLinguistique> trouver(String sql, String p) {
        List<CritereTechniqueLinguistique> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(LiaisonRowMappers.CRITERE_TECHNIQUE_LINGUISTIQUE.map(rs));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC CritereTechniqueLinguistique find", e); }
    }

    @Override public boolean existe(String idCr, String idT) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idCr); ps.setString(2, idT);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC CritereTechniqueLinguistique exists", e); }
    }

    @Override public void supprimer(String idCr, String idT) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setString(1, idCr); ps.setString(2, idT);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC CritereTechniqueLinguistique delete", e); }
    }
}
