package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.Offres.Qualification;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RowMapper;
import fonea.ped.useCase.useCaseOffres.ports.QualificationRepository;

/**
 * Implementation JDBC du port QualificationRepository (table Qualification).
 */
public class JdbcQualificationRepository implements QualificationRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Qualification (idQualification, libelle, idMetier)
        VALUES (?, ?, ?)
        ON DUPLICATE KEY UPDATE libelle = VALUES(libelle),
                                idMetier = VALUES(idMetier)
        """;
    private static final String SQL_FIND_ID = """
        SELECT idQualification, libelle, idMetier FROM Qualification WHERE idQualification = ?
        """;
    private static final String SQL_FIND_ALL = """
        SELECT idQualification, libelle, idMetier FROM Qualification ORDER BY libelle
        """;
    private static final String SQL_EXISTS = """
        SELECT 1 FROM Qualification WHERE idQualification = ?
        """;

    private static final RowMapper<Qualification> MAPPER = rs -> new Qualification(
            rs.getString("idQualification"),
            rs.getString("libelle"),
            rs.getString("idMetier")
    );

    @Override
    public Qualification sauvegarder(Qualification qualification) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, qualification.getIdQualification());
            ps.setString(2, qualification.getLibelle());
            ps.setString(3, qualification.getIdMetier());
            ps.executeUpdate();
            return qualification;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Qualification", e);
        }
    }

    @Override
    public Optional<Qualification> trouverParId(String idQualification) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, idQualification);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MAPPER.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Qualification", e);
        }
    }

    @Override
    public List<Qualification> trouverToutes() {
        List<Qualification> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(MAPPER.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverToutes Qualification", e);
        }
    }

    @Override
    public boolean existeParId(String idQualification) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idQualification);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Qualification", e);
        }
    }

    @Override
    public String prochainId() {
        return "QUAL-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
