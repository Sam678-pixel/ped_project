package fonea.ped.infrastructure.persistence.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.insertion.TypeContrat;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.TypeContratRowMapper;
import fonea.ped.useCase.useCaseInsertion.ports.TypeContratRepository;

public class JdbcTypeContratRepository implements TypeContratRepository {

    private static final String SQL_SAVE = """
        INSERT INTO TypeContrat (idTypeContrat, libelle)
        VALUES (?, ?)
        ON DUPLICATE KEY UPDATE libelle = VALUES(libelle)
        """;
    private static final String SQL_FIND_ID = """
        SELECT idTypeContrat, libelle FROM TypeContrat WHERE idTypeContrat = ?
        """;
    private static final String SQL_FIND_ALL = """
        SELECT idTypeContrat, libelle FROM TypeContrat ORDER BY libelle
        """;
    private static final String SQL_EXISTS = """
        SELECT 1 FROM TypeContrat WHERE idTypeContrat = ?
        """;

    private final TypeContratRowMapper mapper = new TypeContratRowMapper();

    @Override
    public TypeContrat sauvegarder(TypeContrat tc) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, tc.getIdTypeContrat());
            ps.setString(2, tc.getLibelle());
            ps.executeUpdate();
            return tc;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder TypeContrat", e);
        }
    }

    @Override
    public Optional<TypeContrat> trouverParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapper.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId TypeContrat", e);
        }
    }

    @Override
    public List<TypeContrat> trouverTous() {
        List<TypeContrat> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(mapper.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverTous TypeContrat", e);
        }
    }

    @Override
    public boolean existeParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId TypeContrat", e);
        }
    }

    @Override
    public String prochainId() {
        return "TCT-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
