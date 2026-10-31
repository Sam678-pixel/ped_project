package fonea.ped.domain.securite;

import java.util.Objects;

/**
 * accorder = (#idRole, #idPermission)
 */
public class RolePermission {

    private String idRole;
    private String idPermission;

    public RolePermission(String idRole, String idPermission) {
        this.idRole = idRole;
        this.idPermission = idPermission;
    }

    public String getIdRole() { return idRole; }
    public String getIdPermission() { return idPermission; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return Objects.equals(idRole, that.idRole)
                && Objects.equals(idPermission, that.idPermission);
    }
    @Override public int hashCode() { return Objects.hash(idRole, idPermission); }
    @Override public String toString() {
        return "RolePermission{idRole='" + idRole +
                "', idPermission='" + idPermission + "'}";
    }
}
