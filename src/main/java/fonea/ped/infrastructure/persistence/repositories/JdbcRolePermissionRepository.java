package fonea.ped.infrastructure.persistence.repositories;



import java.sql.*;
import java.util.*;

import fonea.ped.domain.securite.RolePermission;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.LiaisonRowMappers;
import fonea.ped.useCase.useCaseSecurite.RolePermissionRepository;

public class JdbcRolePermissionRepository implements RolePermissionRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO accorder (idRole, idPermission) VALUES (?, ?)";
    private static final String SQL_FIND_ROLE =
        "SELECT * FROM accorder WHERE idRole = ?";
    private static final String SQL_FIND_PERM =
        "SELECT * FROM accorder WHERE idPermission = ?";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM accorder WHERE idRole = ? AND idPermission = ?";
    private static final String SQL_DELETE =
        "DELETE FROM accorder WHERE idRole = ? AND idPermission = ?";

    @Override public RolePermission sauvegarder(RolePermission l) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, l.getIdRole());
            ps.setString(2, l.getIdPermission());
            ps.executeUpdate();
            return l;
        } catch (SQLException e) { throw new RuntimeException("JDBC RolePermission save", e); }
    }

    @Override public List<RolePermission> trouverParRole(String idRole) {
        return trouver(SQL_FIND_ROLE, idRole);
    }
    @Override public List<RolePermission> trouverParPermission(String idPermission) {
        return trouver(SQL_FIND_PERM, idPermission);
    }
    private List<RolePermission> trouver(String sql, String p) {
        List<RolePermission> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(LiaisonRowMappers.ROLE_PERMISSION.map(rs));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC RolePermission find", e); }
    }

    @Override public boolean existe(String idR, String idP) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idR); ps.setString(2, idP);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC RolePermission exists", e); }
    }

    @Override public void supprimer(String idR, String idP) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setString(1, idR); ps.setString(2, idP);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC RolePermission delete", e); }
    }
}
