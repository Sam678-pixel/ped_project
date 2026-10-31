package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fonea.ped.domain.Offres.OffreCompetence;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.OffreRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.OffreCompetenceRepository;

/**
 * Implementation JDBC du port OffreCompetenceRepository (table offre_competence).
 */
public class JdbcOffreCompetenceRepository implements OffreCompetenceRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO offre_competence (idOffre, idCompetence) VALUES (?, ?)";
    private static final String SQL_FIND_OFFRE =
        "SELECT idOffre, idCompetence FROM offre_competence WHERE idOffre = ?";

    @Override
    public OffreCompetence sauvegarder(OffreCompetence liaison) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, liaison.getIdOffre());
            ps.setString(2, liaison.getIdCompetence());
            ps.executeUpdate();
            return liaison;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder OffreCompetence", e);
        }
    }

    @Override
    public List<OffreCompetence> trouverParOffre(String idOffre) {
        List<OffreCompetence> resultats = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_OFFRE)) {
            ps.setString(1, idOffre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultats.add(OffreRowMappers.OFFRE_COMPETENCE.map(rs));
            }
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParOffre OffreCompetence", e);
        }
    }

    @Override
    public String prochainId() {
        return "OCOM-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
