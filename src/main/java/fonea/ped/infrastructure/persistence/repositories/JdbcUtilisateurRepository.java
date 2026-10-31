package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.securite.Utilisateur;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.SecuriteRowMappers;
import fonea.ped.useCase.useCaseSecurite.UtilisateurRepository;

public class JdbcUtilisateurRepository implements UtilisateurRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Utilisateur (idUtilisateur, email, `password`, derniereConnexion, telephone, idRole)
        VALUES (?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE email = VALUES(email), `password` = VALUES(`password`),
                                derniereConnexion = VALUES(derniereConnexion), telephone = VALUES(telephone),
                                idRole = VALUES(idRole)
        """;
    private static final String SQL_FIND_ID = "SELECT * FROM Utilisateur WHERE idUtilisateur = ?";
    private static final String SQL_FIND_EMAIL = "SELECT * FROM Utilisateur WHERE email = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM Utilisateur ORDER BY email";
    private static final String SQL_EXISTS = "SELECT 1 FROM Utilisateur WHERE idUtilisateur = ?";

    @Override
    public Utilisateur sauvegarder(Utilisateur u) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, u.getIdUtilisateur());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            if (u.getDerniereConnexion() != null)
                ps.setTimestamp(4, Timestamp.valueOf(u.getDerniereConnexion()));
            else ps.setNull(4, Types.TIMESTAMP);
            ps.setString(5, u.getTelephone());
            ps.setString(6, u.getIdRole());
            ps.executeUpdate();
            return u;
        } catch (SQLException e) { throw new RuntimeException("JDBC Utilisateur save", e); }
    }

    @Override
    public Optional<Utilisateur> trouverParId(String id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(SecuriteRowMappers.UTILISATEUR.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Utilisateur find", e); }
    }

    @Override
    public Optional<Utilisateur> trouverParEmail(String email) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(SecuriteRowMappers.UTILISATEUR.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Utilisateur findEmail", e); }
    }

    @Override
    public List<Utilisateur> trouverTous() {
        List<Utilisateur> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(SecuriteRowMappers.UTILISATEUR.map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC Utilisateur findAll", e); }
    }

    @Override
    public boolean existeParId(String id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC Utilisateur exists", e); }
    }

    @Override
    public String prochainId() {
        return "USR-" + UUID.randomUUID().toString().substring(0, 8);
    }
}