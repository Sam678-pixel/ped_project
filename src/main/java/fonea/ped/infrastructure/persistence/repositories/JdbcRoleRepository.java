package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.securite.Role;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.SecuriteRowMappers;
import fonea.ped.useCase.useCaseSecurite.RoleRepository;

import java.sql.*;
import java.util.*;

public class JdbcRoleRepository implements RoleRepository {

    private static final String SQL_SAVE = """
        INSERT INTO `Role` (idRole, codeRole, libelleRole)
        VALUES (?, ?, ?)
        ON DUPLICATE KEY UPDATE codeRole = VALUES(codeRole), libelleRole = VALUES(libelleRole)
        """;
    private static final String SQL_FIND_ID = "SELECT * FROM `Role` WHERE idRole = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM `Role` ORDER BY libelleRole";
    private static final String SQL_EXISTS = "SELECT 1 FROM `Role` WHERE idRole = ?";

    @Override
    public Role sauvegarder(Role r) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, r.getIdRole());
            ps.setString(2, r.getCodeRole());
            ps.setString(3, r.getLibelleRole());
            ps.executeUpdate();
            return r;
        } catch (SQLException e) { throw new RuntimeException("JDBC Role save", e); }
    }

    @Override
    public Optional<Role> trouverParId(String id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(SecuriteRowMappers.ROLE.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Role find", e); }
    }

    @Override
    public List<Role> trouverTous() {
        List<Role> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(SecuriteRowMappers.ROLE.map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC Role findAll", e); }
    }

    @Override
    public boolean existeParId(String id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC Role exists", e); }
    }

    @Override
    public String prochainId() {
        return "ROL-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
