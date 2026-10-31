package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.Offres.Metier;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RowMapper;
import fonea.ped.useCase.useCaseOffres.ports.MetierRepository;

/**
 * Implementation JDBC du port MetierRepository (table Metier).
 */
public class JdbcMetierRepository implements MetierRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Metier (idMetier, libelle, idSecteur)
        VALUES (?, ?, ?)
        ON DUPLICATE KEY UPDATE libelle = VALUES(libelle),
                                idSecteur = VALUES(idSecteur)
        """;
    private static final String SQL_FIND_ID = """
        SELECT idMetier, libelle, idSecteur FROM Metier WHERE idMetier = ?
        """;
    private static final String SQL_FIND_ALL = """
        SELECT idMetier, libelle, idSecteur FROM Metier ORDER BY libelle
        """;
    private static final String SQL_EXISTS = """
        SELECT 1 FROM Metier WHERE idMetier = ?
        """;

    private static final RowMapper<Metier> MAPPER = rs -> new Metier(
            rs.getString("idMetier"),
            rs.getString("libelle"),
            rs.getString("idSecteur")
    );

    @Override
    public Metier sauvegarder(Metier metier) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, metier.getIdMetier());
            ps.setString(2, metier.getLibelle());
            ps.setString(3, metier.getIdSecteur());
            ps.executeUpdate();
            return metier;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Metier", e);
        }
    }

    @Override
    public Optional<Metier> trouverParId(String idMetier) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, idMetier);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MAPPER.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Metier", e);
        }
    }

    @Override
    public List<Metier> trouverTous() {
        List<Metier> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(MAPPER.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverTous Metier", e);
        }
    }

    @Override
    public boolean existeParId(String idMetier) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idMetier);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Metier", e);
        }
    }

    @Override
    public String prochainId() {
        return "MET-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
