package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.Offres.PrerequisFormation;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RowMapper;
import fonea.ped.useCase.useCaseOffres.ports.PrerequisRepository;

/**
 * Implementation JDBC du port PrerequisRepository (table PrerequisFormation).
 * Le schema ne stocke que le libelle : il alimente libelleFormation et libelle.
 */
public class JdbcPrerequisRepository implements PrerequisRepository {

    private static final String SQL_SAVE = """
        INSERT INTO PrerequisFormation (idPrerequis, libelle)
        VALUES (?, ?)
        ON DUPLICATE KEY UPDATE libelle = VALUES(libelle)
        """;
    private static final String SQL_FIND_ID = """
        SELECT idPrerequis, libelle FROM PrerequisFormation WHERE idPrerequis = ?
        """;
    private static final String SQL_FIND_ALL = """
        SELECT idPrerequis, libelle FROM PrerequisFormation ORDER BY libelle
        """;
    private static final String SQL_EXISTS = """
        SELECT 1 FROM PrerequisFormation WHERE idPrerequis = ?
        """;

    private static final RowMapper<PrerequisFormation> MAPPER = rs -> {
        String libelle = rs.getString("libelle");
        return new PrerequisFormation(rs.getString("idPrerequis"), libelle, null, libelle);
    };

    @Override
    public PrerequisFormation sauvegarder(PrerequisFormation prerequis) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, prerequis.getIdPrerequis());
            ps.setString(2, prerequis.getLibelle() != null
                    ? prerequis.getLibelle()
                    : prerequis.getLibelleFormation());
            ps.executeUpdate();
            return prerequis;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder PrerequisFormation", e);
        }
    }

    @Override
    public Optional<PrerequisFormation> trouverParId(String idPrerequis) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, idPrerequis);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MAPPER.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId PrerequisFormation", e);
        }
    }

    @Override
    public List<PrerequisFormation> trouverTous() {
        List<PrerequisFormation> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(MAPPER.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverTous PrerequisFormation", e);
        }
    }

    @Override
    public boolean existeParId(String idPrerequis) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idPrerequis);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId PrerequisFormation", e);
        }
    }

    @Override
    public String prochainId() {
        return "PRE-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
