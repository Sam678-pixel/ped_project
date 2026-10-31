package fonea.ped.infrastructure.persistence.repositories;

import java.sql.*;
import java.util.*;

import fonea.ped.domain.Offres.OffresStages;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.OffreRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.OffreRepository;

/**
 * Implémentation JDBC du port OffreRepository.
 * Format : repository + SQL regroupés, mapper séparé (OffreRowMappers).
 * SQL compatible MySQL uniquement.
 */
public class JdbcOffreRepository implements OffreRepository {

    // ============ SQL ============

    private static final String SQL_SAVE = """
        INSERT INTO OffresStage
            (idOffre, intitule, description, villeStage, dureeMois, StatutOffre, nbrePlaces, idPoste, idDomain, idConvention)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            intitule = VALUES(intitule),
            description = VALUES(description),
            villeStage = VALUES(villeStage),
            dureeMois = VALUES(dureeMois),
            StatutOffre = VALUES(StatutOffre),
            nbrePlaces = VALUES(nbrePlaces),
            idPoste = VALUES(idPoste),
            idDomain = VALUES(idDomain),
            idConvention = VALUES(idConvention)
        """;

    private static final String SQL_FIND_ID =
        "SELECT * FROM OffresStage WHERE idOffre = ?";

    private static final String SQL_FIND_ALL =
        "SELECT * FROM OffresStage ORDER BY intitule";

    private static final String SQL_FIND_BY_CONV =
        "SELECT * FROM OffresStage WHERE idConvention = ? ORDER BY intitule";

    private static final String SQL_FIND_PUBLIEES =
        "SELECT * FROM OffresStage WHERE StatutOffre = 'PUBLIEE'";

    private static final String SQL_EXISTS =
        "SELECT 1 FROM OffresStage WHERE idOffre = ?";

    private static final String SQL_DELETE =
        "DELETE FROM OffresStage WHERE idOffre = ?";

    // ============ MÉTHODES DU PORT ============

    @Override
    public OffresStages sauvegarder(OffresStages offre) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {

            ps.setString(1, offre.getIdOffre());
            ps.setString(2, offre.getIntitule());
            ps.setString(3, offre.getDescription());
            ps.setString(4, offre.getVilleStage());

            if (offre.getDureeMois() != null) ps.setInt(5, offre.getDureeMois());
            else ps.setNull(5, Types.INTEGER);

            ps.setString(6, offre.getStatutOffre().name());

            if (offre.getNbrePlaces() != null) ps.setInt(7, offre.getNbrePlaces());
            else ps.setNull(7, Types.INTEGER);

            ps.setString(8, offre.getIdPoste());
            ps.setString(9, offre.getIdDomaine());
            ps.setString(10, offre.getIdConvention());

            ps.executeUpdate();
            return offre;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Offre", e);
        }
    }

    @Override
    public Optional<OffresStages> trouverParId(String idOffre) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {

            ps.setString(1, idOffre);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.of(OffreRowMappers.OFFRE.map(rs))
                        : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Offre", e);
        }
    }

    @Override
    public List<OffresStages> trouverToutes() {
        List<OffresStages> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultats.add(OffreRowMappers.OFFRE.map(rs));
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverToutes Offre", e);
        }
    }

    @Override
    public List<OffresStages> trouverParConvention(String idConvention) {
        List<OffresStages> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_CONV)) {

            ps.setString(1, idConvention);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultats.add(OffreRowMappers.OFFRE.map(rs));
                }
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParConvention Offre", e);
        }
    }

    @Override
    public List<OffresStages>offrePubliees() {
        List<OffresStages> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_PUBLIEES);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultats.add(OffreRowMappers.OFFRE.map(rs));
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverPubliees Offre", e);
        }
    }

    @Override
    public boolean existeParId(String idOffre) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {

            ps.setString(1, idOffre);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Offre", e);
        }
    }

    @Override
    public void supprimer(String idOffre) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setString(1, idOffre);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC supprimer Offre", e);
        }
    }

    @Override
    public String prochainId() {
        return "OFF-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
