package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.Offres.TechniqueLinguistique;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RowMapper;
import fonea.ped.useCase.useCaseOffres.ports.TechniqueLinguistiqueRepository;

/**
 * Implementation JDBC du port TechniqueLinguistiqueRepository (table TechniqueLinguistique).
 */
public class JdbcTechniqueLinguistiqueRepository implements TechniqueLinguistiqueRepository {

    private static final String SQL_SAVE = """
        INSERT INTO TechniqueLinguistique (idTechLing, libelle)
        VALUES (?, ?)
        ON DUPLICATE KEY UPDATE libelle = VALUES(libelle)
        """;
    private static final String SQL_FIND_ID = """
        SELECT idTechLing, libelle FROM TechniqueLinguistique WHERE idTechLing = ?
        """;
    private static final String SQL_FIND_ALL = """
        SELECT idTechLing, libelle FROM TechniqueLinguistique ORDER BY libelle
        """;
    private static final String SQL_EXISTS = """
        SELECT 1 FROM TechniqueLinguistique WHERE idTechLing = ?
        """;

    private static final RowMapper<TechniqueLinguistique> MAPPER = rs ->
            new TechniqueLinguistique(rs.getString("idTechLing"), rs.getString("libelle"));

    @Override
    public TechniqueLinguistique sauvegarder(TechniqueLinguistique technique) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, technique.getIdTechniqueLinguistique());
            ps.setString(2, technique.getLibelle());
            ps.executeUpdate();
            return technique;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder TechniqueLinguistique", e);
        }
    }

    @Override
    public Optional<TechniqueLinguistique> trouverParId(String idTechniqueLinguistique) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, idTechniqueLinguistique);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MAPPER.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId TechniqueLinguistique", e);
        }
    }

    @Override
    public List<TechniqueLinguistique> trouverTous() {
        List<TechniqueLinguistique> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(MAPPER.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverTous TechniqueLinguistique", e);
        }
    }

    @Override
    public boolean existeParId(String idTechniqueLinguistique) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idTechniqueLinguistique);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId TechniqueLinguistique", e);
        }
    }

    @Override
    public String prochainId() {
        return "TL-" + UUID.randomUUID().toString().substring(0, 8);
    }
}

