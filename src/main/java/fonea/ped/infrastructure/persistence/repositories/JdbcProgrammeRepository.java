package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.convention.model.Programme;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.useCase.UseCaseConvention.ports.ProgrammeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implémentation JDBC du port ProgrammeRepository.
 *
 * <p>Ce repository manquait : {@code CreerConventionUseCase} dépendait d'un port
 * sans aucune implémentation, ce qui rendait le câblage Spring impossible.</p>
 */
public class JdbcProgrammeRepository implements ProgrammeRepository {

    private static final String SQL_SAVE = """
            INSERT INTO Programme (idProgramme, libelle, typeProgramme)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE
                libelle = VALUES(libelle),
                typeProgramme = VALUES(typeProgramme)
            """;

    private static final String SQL_FIND_ID =
            "SELECT idProgramme, libelle, typeProgramme FROM Programme WHERE idProgramme = ?";

    private static final String SQL_FIND_ALL =
            "SELECT idProgramme, libelle, typeProgramme FROM Programme ORDER BY libelle";

    private static final String SQL_EXISTS =
            "SELECT 1 FROM Programme WHERE idProgramme = ?";

    @Override
    public Programme sauvegarder(Programme programme) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, programme.getIdProgramme());
            ps.setString(2, programme.getLibelle());
            ps.setString(3, programme.getTypeProgramme());
            ps.executeUpdate();
            return programme;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Programme", e);
        }
    }

    @Override
    public Optional<Programme> trouverParId(String idProgramme) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, idProgramme);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapper(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Programme", e);
        }
    }

    @Override
    public List<Programme> trouverTous() {
        List<Programme> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(mapper(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverTous Programme", e);
        }
    }

    @Override
    public boolean existeParId(String idProgramme) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idProgramme);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Programme", e);
        }
    }

    @Override
    public String prochainId() {
        return "PROG-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private Programme mapper(ResultSet rs) throws SQLException {
        return new Programme(
                rs.getString("idProgramme"),
                rs.getString("libelle"),
                rs.getString("typeProgramme"));
    }
}
