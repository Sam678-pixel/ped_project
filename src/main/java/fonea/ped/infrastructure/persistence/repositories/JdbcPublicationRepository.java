package fonea.ped.infrastructure.persistence.repositories;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import fonea.ped.domain.Offres.Publication;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.OffreRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.PublicationRepository;

/**
 * Implémentation JDBC du port PublicationRepository.
 * Format : repository + SQL regroupés, mapper séparé (OffreRowMappers).
 * SQL compatible MySQL uniquement.
 */
public class JdbcPublicationRepository implements PublicationRepository {

    // ============ SQL ============

    private static final String SQL_SAVE = """
        INSERT INTO Publication (idPublication, datePublication, dateExpiration, idAgent, idOffre)
        VALUES (?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            datePublication = VALUES(datePublication),
            dateExpiration = VALUES(dateExpiration),
            idAgent = VALUES(idAgent),
            idOffre = VALUES(idOffre)
        """;

    private static final String SQL_FIND_ID =
        "SELECT * FROM Publication WHERE idPublication = ?";

    private static final String SQL_FIND_ALL =
        "SELECT * FROM Publication ORDER BY datePublication DESC";

    private static final String SQL_FIND_BY_OFFRE =
        "SELECT * FROM Publication WHERE idOffre = ? ORDER BY datePublication DESC";

    private static final String SQL_FIND_ACTIVE =
        "SELECT * FROM Publication WHERE idOffre = ? AND dateExpiration > ? ORDER BY dateExpiration DESC LIMIT 1";

    private static final String SQL_EXISTS =
        "SELECT 1 FROM Publication WHERE idPublication = ?";

    private static final String SQL_DELETE =
        "DELETE FROM Publication WHERE idPublication = ?";

    // ============ MÉTHODES DU PORT ============

    @Override
    public Publication sauvegarder(Publication publication) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {

            ps.setString(1, publication.getIdPublication());

            if (publication.getDatePublication() != null) {
                ps.setDate(2, Date.valueOf(publication.getDatePublication()));
            } else {
                ps.setNull(2, Types.DATE);
            }

            if (publication.getDateExpiration() != null) {
                ps.setDate(3, Date.valueOf(publication.getDateExpiration()));
            } else {
                ps.setNull(3, Types.DATE);
            }

            ps.setString(4, publication.getIdAgent());
            ps.setString(5, publication.getIdOffre());

            ps.executeUpdate();
            return publication;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Publication", e);
        }
    }

    @Override
    public Optional<Publication> trouverParId(String idPublication) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {

            ps.setString(1, idPublication);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.of(OffreRowMappers.PUBLICATION.map(rs))
                        : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Publication", e);
        }
    }

    @Override
    public List<Publication> trouverToutes() {
        List<Publication> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultats.add(OffreRowMappers.PUBLICATION.map(rs));
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverToutes Publication", e);
        }
    }

    @Override
    public List<Publication> trouverParOffre(String idOffre) {
        List<Publication> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_OFFRE)) {

            ps.setString(1, idOffre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultats.add(OffreRowMappers.PUBLICATION.map(rs));
                }
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParOffre Publication", e);
        }
    }

    @Override
    public Optional<Publication> trouverActiveParOffre(String idOffre) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ACTIVE)) {

            ps.setString(1, idOffre);
            ps.setDate(2, Date.valueOf(LocalDate.now()));

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.of(OffreRowMappers.PUBLICATION.map(rs))
                        : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverActiveParOffre Publication", e);
        }
    }

    @Override
    public boolean existeParId(String idPublication) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {

            ps.setString(1, idPublication);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Publication", e);
        }
    }

    @Override
    public void supprimer(String idPublication) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setString(1, idPublication);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC supprimer Publication", e);
        }
    }

    @Override
    public String prochainId() {
        return "PUB-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
