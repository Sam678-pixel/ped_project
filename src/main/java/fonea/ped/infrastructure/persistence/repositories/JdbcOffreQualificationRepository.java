package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fonea.ped.domain.Offres.OffreQualification;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.OffreRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.OffreQualificationRepository;

/**
 * Implementation JDBC du port OffreQualificationRepository (table vise).
 */
public class JdbcOffreQualificationRepository implements OffreQualificationRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO vise (idOffre, idQualification) VALUES (?, ?)";
    private static final String SQL_FIND_OFFRE =
        "SELECT idOffre, idQualification FROM vise WHERE idOffre = ?";

    @Override
    public OffreQualification sauvegarder(OffreQualification liaison) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, liaison.getIdOffre());
            ps.setString(2, liaison.getIdQualification());
            ps.executeUpdate();
            return liaison;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder OffreQualification", e);
        }
    }

    @Override
    public List<OffreQualification> trouverParOffre(String idOffre) {
        List<OffreQualification> resultats = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_OFFRE)) {
            ps.setString(1, idOffre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultats.add(OffreRowMappers.OFFRE_QUALIFICATION.map(rs));
            }
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParOffre OffreQualification", e);
        }
    }

    @Override
    public String prochainId() {
        return "OQUA-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
