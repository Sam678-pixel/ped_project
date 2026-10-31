package fonea.ped.infrastructure.persistence.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.Offres.Domaine;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.DomaineRowMapper;
import fonea.ped.useCase.useCaseOffres.ports.DomaineRepository;

public class JdbcDomaineRepository implements DomaineRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Domaine (idDomain, libelle)
        VALUES (?, ?)
        ON DUPLICATE KEY UPDATE libelle = VALUES(libelle)
        """;
    private static final String SQL_FIND_ID = """
        SELECT idDomain, libelle FROM Domaine WHERE idDomain = ?
        """;
    private static final String SQL_FIND_ALL = """
        SELECT idDomain, libelle FROM Domaine ORDER BY libelle
        """;
    private static final String SQL_EXISTS = """
        SELECT 1 FROM Domaine WHERE idDomain = ?
        """;

    private final DomaineRowMapper mapper = new DomaineRowMapper();

    @Override
    public Domaine sauvegarder(Domaine domaine) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, domaine.getIdDomaine());
            ps.setString(2, domaine.getLibelle());
            ps.executeUpdate();
            return domaine;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Domaine", e);
        }
    }

    @Override
    public Optional<Domaine> trouverParId(String idDomaine) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, idDomaine);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapper.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Domaine", e);
        }
    }

    @Override
    public List<Domaine> trouverTous() {
        List<Domaine> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(mapper.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverTous Domaine", e);
        }
    }

    @Override
    public boolean existeParId(String idDomaine) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idDomaine);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Domaine", e);
        }
    }

    @Override
    public String prochainId() {
        return "DOM-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
