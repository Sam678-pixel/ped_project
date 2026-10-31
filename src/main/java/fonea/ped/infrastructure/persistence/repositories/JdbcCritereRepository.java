package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.Offres.Critere;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RowMapper;
import fonea.ped.useCase.useCaseOffres.ports.CritereRepository;

/**
 * Implementation JDBC du port CritereRepository (table Critere).
 */
public class JdbcCritereRepository implements CritereRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Critere (idCritere, ageMin, ageMax, NiveauDiplome)
        VALUES (?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE ageMin = VALUES(ageMin),
                                ageMax = VALUES(ageMax),
                                NiveauDiplome = VALUES(NiveauDiplome)
        """;
    private static final String SQL_FIND_ID = """
        SELECT idCritere, ageMin, ageMax, NiveauDiplome FROM Critere WHERE idCritere = ?
        """;
    private static final String SQL_FIND_ALL = """
        SELECT idCritere, ageMin, ageMax, NiveauDiplome FROM Critere ORDER BY idCritere
        """;
    private static final String SQL_EXISTS = """
        SELECT 1 FROM Critere WHERE idCritere = ?
        """;

    private static final RowMapper<Critere> MAPPER = rs -> new Critere(
            rs.getString("idCritere"),
            rs.getObject("ageMin") != null ? rs.getInt("ageMin") : null,
            rs.getObject("ageMax") != null ? rs.getInt("ageMax") : null,
            rs.getString("NiveauDiplome")
    );

    @Override
    public Critere sauvegarder(Critere critere) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, critere.getIdCritere());
            if (critere.getAgeMin() != null) ps.setInt(2, critere.getAgeMin()); else ps.setNull(2, java.sql.Types.INTEGER);
            if (critere.getAgeMax() != null) ps.setInt(3, critere.getAgeMax()); else ps.setNull(3, java.sql.Types.INTEGER);
            ps.setString(4, critere.getNiveauDiplome());
            ps.executeUpdate();
            return critere;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Critere", e);
        }
    }

    @Override
    public Optional<Critere> trouverParId(String idCritere) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, idCritere);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MAPPER.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Critere", e);
        }
    }

    @Override
    public List<Critere> trouverTous() {
        List<Critere> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(MAPPER.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverTous Critere", e);
        }
    }

    @Override
    public boolean existeParId(String idCritere) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idCritere);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Critere", e);
        }
    }

    @Override
    public String prochainId() {
        return "CRIT-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
