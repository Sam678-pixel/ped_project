package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.MiseEnStage.MotifDecrochage;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.MotifDecrochageRowMapper;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MotifDecrochageRepository;

/**
 * Implementation JDBC du port MotifDecrochageRepository (table MotifDecrochage).
 * A ne pas confondre avec la liaison est_justifie_par (JdbcDecrochageMotifRepository).
 */
public class JdbcMotifDecrochageRepository implements MotifDecrochageRepository {

    private static final String SQL_SAVE = """
        INSERT INTO MotifDecrochage (idMotifDecrochage, libelle, typeMotif)
        VALUES (?, ?, ?)
        ON DUPLICATE KEY UPDATE libelle = VALUES(libelle),
                                typeMotif = VALUES(typeMotif)
        """;
    private static final String SQL_FIND_ID = """
        SELECT idMotifDecrochage, libelle, typeMotif FROM MotifDecrochage WHERE idMotifDecrochage = ?
        """;
    private static final String SQL_FIND_ALL = """
        SELECT idMotifDecrochage, libelle, typeMotif FROM MotifDecrochage ORDER BY libelle
        """;
    private static final String SQL_EXISTS = """
        SELECT 1 FROM MotifDecrochage WHERE idMotifDecrochage = ?
        """;

    private final MotifDecrochageRowMapper mapper = new MotifDecrochageRowMapper();

    @Override
    public MotifDecrochage sauvegarder(MotifDecrochage motif) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, motif.getIdMotifDecrochage());
            ps.setString(2, motif.getLibelle());
            ps.setString(3, motif.getTypeMotif());
            ps.executeUpdate();
            return motif;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder MotifDecrochage", e);
        }
    }

    @Override
    public Optional<MotifDecrochage> trouverParId(String idMotifDecrochage) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, idMotifDecrochage);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapper.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId MotifDecrochage", e);
        }
    }

    @Override
    public List<MotifDecrochage> trouverTous() {
        List<MotifDecrochage> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(mapper.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverTous MotifDecrochage", e);
        }
    }

    @Override
    public boolean existeParId(String idMotifDecrochage) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idMotifDecrochage);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId MotifDecrochage", e);
        }
    }

    @Override
    public String prochainId() {
        return "MDEC-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
