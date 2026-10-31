package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fonea.ped.domain.Offres.PreselectionCritere;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RowMapper;
import fonea.ped.useCase.useCaseRecrutement.ports.PreselectionCritereRepository;

/**
 * Implementation JDBC du port PreselectionCritereRepository (table evaluer).
 */
public class JdbcPreselectionCritereRepository implements PreselectionCritereRepository {

    private static final String SQL_SAVE = """
        INSERT INTO evaluer (idPreselection, idCritere, estSatisfait)
        VALUES (?, ?, ?)
        ON DUPLICATE KEY UPDATE estSatisfait = VALUES(estSatisfait)
        """;
    private static final String SQL_FIND_PRESELECTION =
        "SELECT idPreselection, idCritere, estSatisfait FROM evaluer WHERE idPreselection = ?";
    private static final String SQL_FIND_CRITERE =
        "SELECT idPreselection, idCritere, estSatisfait FROM evaluer WHERE idCritere = ?";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM evaluer WHERE idPreselection = ? AND idCritere = ?";
    private static final String SQL_DELETE =
        "DELETE FROM evaluer WHERE idPreselection = ? AND idCritere = ?";

    private static final RowMapper<PreselectionCritere> MAPPER = rs -> new PreselectionCritere(
            rs.getString("idPreselection"),
            rs.getString("idCritere"),
            rs.getString("estSatisfait")
    );

    @Override
    public PreselectionCritere sauvegarder(PreselectionCritere liaison) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, liaison.getIdPreselection());
            ps.setString(2, liaison.getIdCritere());
            ps.setString(3, liaison.getEstSatisfait());
            ps.executeUpdate();
            return liaison;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder PreselectionCritere", e);
        }
    }

    @Override
    public List<PreselectionCritere> trouverParPreselection(String idPreselection) {
        return trouver(SQL_FIND_PRESELECTION, idPreselection);
    }

    @Override
    public List<PreselectionCritere> trouverParCritere(String idCritere) {
        return trouver(SQL_FIND_CRITERE, idCritere);
    }

    private List<PreselectionCritere> trouver(String sql, String param) {
        List<PreselectionCritere> resultats = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultats.add(MAPPER.map(rs));
            }
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouver PreselectionCritere", e);
        }
    }

    @Override
    public boolean existe(String idPreselection, String idCritere) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idPreselection);
            ps.setString(2, idCritere);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existe PreselectionCritere", e);
        }
    }

    @Override
    public void supprimer(String idPreselection, String idCritere) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setString(1, idPreselection);
            ps.setString(2, idCritere);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC supprimer PreselectionCritere", e);
        }
    }
}
