package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.convention.model.MotifResiliation;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RowMapper;
import fonea.ped.useCase.UseCaseConvention.ports.MotifResiliationRepository;

/**
 * Implementation JDBC du port MotifResiliationRepository (table MotifResiliation).
 * A ne pas confondre avec la liaison est_justifiee_par (JdbcResiliationMotifRepository).
 */
public class JdbcMotifResiliationRepository implements MotifResiliationRepository {

    private static final String SQL_SAVE = """
        INSERT INTO MotifResiliation (idMotifResiliation, libelle)
        VALUES (?, ?)
        ON DUPLICATE KEY UPDATE libelle = VALUES(libelle)
        """;
    private static final String SQL_FIND_ID = """
        SELECT idMotifResiliation, libelle FROM MotifResiliation WHERE idMotifResiliation = ?
        """;
    private static final String SQL_FIND_ALL = """
        SELECT idMotifResiliation, libelle FROM MotifResiliation ORDER BY libelle
        """;
    private static final String SQL_EXISTS = """
        SELECT 1 FROM MotifResiliation WHERE idMotifResiliation = ?
        """;

    private static final RowMapper<MotifResiliation> MAPPER = rs ->
            new MotifResiliation(rs.getString("idMotifResiliation"), rs.getString("libelle"));

    @Override
    public MotifResiliation sauvegarder(MotifResiliation motif) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, motif.getIdMotifResiliation());
            ps.setString(2, motif.getLibelle());
            ps.executeUpdate();
            return motif;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder MotifResiliation", e);
        }
    }

    @Override
    public Optional<MotifResiliation> trouverParId(String idMotifResiliation) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, idMotifResiliation);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MAPPER.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId MotifResiliation", e);
        }
    }

    @Override
    public List<MotifResiliation> trouverTous() {
        List<MotifResiliation> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(MAPPER.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverTous MotifResiliation", e);
        }
    }

    @Override
    public boolean existeParId(String idMotifResiliation) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idMotifResiliation);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId MotifResiliation", e);
        }
    }

    @Override
    public String prochainId() {
        return "MRES-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
