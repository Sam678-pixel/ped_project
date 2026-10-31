package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.Offres.Poste;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RowMapper;
import fonea.ped.useCase.useCaseOffres.ports.PosteRepository;

/**
 * Implementation JDBC du port PosteRepository (table Poste).
 */
public class JdbcPosteRepository implements PosteRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Poste (idPoste, libelle, idMetier)
        VALUES (?, ?, ?)
        ON DUPLICATE KEY UPDATE libelle = VALUES(libelle),
                                idMetier = VALUES(idMetier)
        """;
    private static final String SQL_FIND_ID = """
        SELECT idPoste, libelle, idMetier FROM Poste WHERE idPoste = ?
        """;
    private static final String SQL_FIND_ALL = """
        SELECT idPoste, libelle, idMetier FROM Poste ORDER BY libelle
        """;
    private static final String SQL_EXISTS = """
        SELECT 1 FROM Poste WHERE idPoste = ?
        """;

    private static final RowMapper<Poste> MAPPER = rs -> new Poste(
            rs.getString("idPoste"),
            rs.getString("libelle"),
            rs.getString("idMetier")
    );

    @Override
    public Poste sauvegarder(Poste poste) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, poste.getIdPoste());
            ps.setString(2, poste.getLibelle());
            ps.setString(3, poste.getIdMetier());
            ps.executeUpdate();
            return poste;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Poste", e);
        }
    }

    @Override
    public Optional<Poste> trouverParId(String idPoste) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, idPoste);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MAPPER.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Poste", e);
        }
    }

    @Override
    public List<Poste> trouverTous() {
        List<Poste> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(MAPPER.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverTous Poste", e);
        }
    }

    @Override
    public boolean existeParId(String idPoste) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idPoste);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Poste", e);
        }
    }

    @Override
    public String prochainId() {
        return "POST-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
