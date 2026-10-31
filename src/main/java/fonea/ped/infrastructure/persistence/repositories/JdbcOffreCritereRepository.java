package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fonea.ped.domain.Offres.OffreCritere;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.OffreRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.OffreCritereRepository;

/**
 * Implementation JDBC du port OffreCritereRepository (table est_soumise_a).
 */
public class JdbcOffreCritereRepository implements OffreCritereRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO est_soumise_a (idOffre, idCritere) VALUES (?, ?)";
    private static final String SQL_FIND_OFFRE =
        "SELECT idOffre, idCritere FROM est_soumise_a WHERE idOffre = ?";
    private static final String SQL_FIND_CRITERE =
        "SELECT idOffre, idCritere FROM est_soumise_a WHERE idCritere = ?";

    @Override
    public OffreCritere sauvegarder(OffreCritere liaison) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, liaison.getIdOffre());
            ps.setString(2, liaison.getIdCritere());
            ps.executeUpdate();
            return liaison;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder OffreCritere", e);
        }
    }

    @Override
    public List<OffreCritere> trouverParOffre(String idOffre) {
        return trouver(SQL_FIND_OFFRE, idOffre);
    }

    @Override
    public List<OffreCritere> trouverParCritere(String idCritere) {
        return trouver(SQL_FIND_CRITERE, idCritere);
    }

    private List<OffreCritere> trouver(String sql, String param) {
        List<OffreCritere> resultats = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultats.add(OffreRowMappers.OFFRE_CRITERE.map(rs));
            }
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouver OffreCritere", e);
        }
    }

    @Override
    public String prochainId() {
        return "OCRI-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
