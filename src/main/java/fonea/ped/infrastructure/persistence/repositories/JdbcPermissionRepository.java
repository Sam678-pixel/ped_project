package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.securite.Permission;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.SecuriteRowMappers;
import fonea.ped.useCase.useCaseSecurite.PermissionRepository;

import java.sql.*;
import java.util.*;

public class JdbcPermissionRepository implements PermissionRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Permission (idPermission, codePermission, description)
        VALUES (?, ?, ?)
        ON DUPLICATE KEY UPDATE codePermission = VALUES(codePermission),
                                description = VALUES(description)
        """;
    private static final String SQL_FIND_ID = "SELECT * FROM Permission WHERE idPermission = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM Permission ORDER BY codePermission";

    @Override
    public Permission sauvegarder(Permission p) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, p.getIdPermission());
            ps.setString(2, p.getCodePermission());
            ps.setString(3, p.getDescription());
            ps.executeUpdate();
            return p;
        } catch (SQLException e) { throw new RuntimeException("JDBC Permission save", e); }
    }

    @Override
    public Optional<Permission> trouverParId(String id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(SecuriteRowMappers.PERMISSION.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Permission find", e); }
    }

    @Override
    public List<Permission> trouverTous() {
        List<Permission> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(SecuriteRowMappers.PERMISSION.map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC Permission findAll", e); }
    }

    @Override
    public String prochainId() {
        return "PRM-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
