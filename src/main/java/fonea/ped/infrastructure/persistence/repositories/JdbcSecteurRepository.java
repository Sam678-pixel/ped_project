package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.Offres.Secteur;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.SecteurRowMapper;
import fonea.ped.useCase.useCaseOffres.ports.SecteurRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implémentation JDBC du port SecteurRepository.
 * Aucune dépendance ORM, SQL écrit à la main.
 */
public class JdbcSecteurRepository implements SecteurRepository {

    private final SecteurRowMapper mapper = new SecteurRowMapper();

    @Override
    public Secteur sauvegarder(Secteur secteur) {
        String sql = """
            INSERT INTO Secteur (idSecteur, libelle)
            VALUES (?, ?)
            ON DUPLICATE KEY UPDATE libelle = VALUES(libelle)
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, secteur.getIdSecteur());
            ps.setString(2, secteur.getLibelle());
            ps.executeUpdate();
            return secteur;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Secteur", e);
        }
    }

    @Override
    public Optional<Secteur> trouverParId(String idSecteur) {
        String sql = "SELECT idSecteur, libelle FROM Secteur WHERE idSecteur = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idSecteur);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapper.map(rs));
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Secteur", e);
        }
    }

    @Override
    public List<Secteur> trouverTous() {
        String sql = "SELECT idSecteur, libelle FROM Secteur ORDER BY libelle";
        List<Secteur> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(mapper.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverTous Secteur", e);
        }
    }

    @Override
    public boolean existeParId(String idSecteur) {
        String sql = "SELECT 1 FROM Secteur WHERE idSecteur = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idSecteur);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Secteur", e);
        }
    }

    @Override
    public String prochainId() {
        return "SECT-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
