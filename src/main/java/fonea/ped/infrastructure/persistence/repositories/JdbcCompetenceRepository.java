package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.Offres.Competence;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RowMapper;
import fonea.ped.useCase.useCaseOffres.ports.CompetenceRepository;

/**
 * Implementation JDBC du port CompetenceRepository (table Competence).
 */
public class JdbcCompetenceRepository implements CompetenceRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Competence (idCompetence, libelle)
        VALUES (?, ?)
        ON DUPLICATE KEY UPDATE libelle = VALUES(libelle)
        """;
    private static final String SQL_FIND_ID = """
        SELECT idCompetence, libelle FROM Competence WHERE idCompetence = ?
        """;
    private static final String SQL_FIND_ALL = """
        SELECT idCompetence, libelle FROM Competence ORDER BY libelle
        """;
    private static final String SQL_EXISTS = """
        SELECT 1 FROM Competence WHERE idCompetence = ?
        """;

    private static final RowMapper<Competence> MAPPER = rs ->
            new Competence(rs.getString("idCompetence"), rs.getString("libelle"));

    @Override
    public Competence sauvegarder(Competence competence) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, competence.getIdCompetence());
            ps.setString(2, competence.getLibelle());
            ps.executeUpdate();
            return competence;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Competence", e);
        }
    }

    @Override
    public Optional<Competence> trouverParId(String idCompetence) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, idCompetence);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MAPPER.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Competence", e);
        }
    }

    @Override
    public List<Competence> trouverToutes() {
        List<Competence> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(MAPPER.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverToutes Competence", e);
        }
    }

    @Override
    public boolean existeParId(String idCompetence) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idCompetence);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Competence", e);
        }
    }

    @Override
    public String prochainId() {
        return "COMP-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
