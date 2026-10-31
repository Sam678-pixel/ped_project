package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fonea.ped.domain.Offres.OffrePrerequis;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.OffreRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.OffrePrerequisRepository;

/**
 * Implementation JDBC du port OffrePrerequisRepository (table offre_prerequis).
 */
public class JdbcOffrePrerequisRepository implements OffrePrerequisRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO offre_prerequis (idOffre, idPrerequis) VALUES (?, ?)";
    private static final String SQL_FIND_OFFRE =
        "SELECT idOffre, idPrerequis FROM offre_prerequis WHERE idOffre = ?";

    @Override
    public OffrePrerequis sauvegarder(OffrePrerequis liaison) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, liaison.getIdOffre());
            ps.setString(2, liaison.getIdPrerequis());
            ps.executeUpdate();
            return liaison;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder OffrePrerequis", e);
        }
    }

    @Override
    public List<OffrePrerequis> trouverParOffre(String idOffre) {
        List<OffrePrerequis> resultats = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_OFFRE)) {
            ps.setString(1, idOffre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultats.add(OffreRowMappers.OFFRE_PREREQUIS.map(rs));
            }
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParOffre OffrePrerequis", e);
        }
    }

    @Override
    public String prochainId() {
        return "OPRE-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
