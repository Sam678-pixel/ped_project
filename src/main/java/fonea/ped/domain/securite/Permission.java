package fonea.ped.domain.securite;

import java.util.Objects;

/**
 * Permission = (idPermission VARCHAR(50), codePermission VARCHAR(50), description TEXT)
 */
public class Permission {

    private String idPermission;
    private String codePermission;
    private String description;

    public Permission(String idPermission, String codePermission, String description) {
        if (codePermission == null || codePermission.isBlank()) {
            throw new IllegalArgumentException("Le code de la permission est obligatoire.");
        }
        this.idPermission = idPermission;
        this.codePermission = codePermission;
        this.description = description;
    }

    public String getIdPermission() { return idPermission; }
    public String getCodePermission() { return codePermission; }
    public String getDescription() { return description; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(idPermission, that.idPermission);
    }
    @Override public int hashCode() { return Objects.hash(idPermission); }
    @Override public String toString() {
        return "Permission{idPermission='" + idPermission +
                "', codePermission='" + codePermission + "'}";
    }
}
