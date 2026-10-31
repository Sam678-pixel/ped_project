package fonea.ped.infrastructure.persistence.mappers;

import fonea.ped.domain.securite.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Regroupe tous les RowMappers du module Sécurité.
 */
public final class SecuriteRowMappers {

    private SecuriteRowMappers() {}

    public static final RowMapper<Role> ROLE = rs ->
            new Role(
                    rs.getString("idRole"),
                    rs.getString("codeRole"),
                    rs.getString("libelleRole")
            );

    public static final RowMapper<Permission> PERMISSION = rs ->
            new Permission(
                    rs.getString("idPermission"),
                    rs.getString("codePermission"),
                    rs.getString("description")
            );

    public static final RowMapper<Utilisateur> UTILISATEUR = rs ->
            new Utilisateur(
                    rs.getString("idUtilisateur"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("telephone"),
                    rs.getString("idRole")
            );

    public static final RowMapper<AgentFonea> AGENT = rs ->
            new AgentFonea(
                    rs.getString("idAgent"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("fonction"),
                    rs.getString("idUtilisateur")
            );

    public static final RowMapper<Notification> NOTIFICATION = rs ->
            new Notification(
                    rs.getString("idNotification"),
                    rs.getString("typeNotification"),
                    rs.getString("message"),
                    rs.getString("idUtilisateur")
            );

    public static final RowMapper<RolePermission> ROLE_PERMISSION = rs ->
            new RolePermission(
                    rs.getString("idRole"),
                    rs.getString("idPermission")
            );
}