package fonea.ped.domain.securite;

import java.util.Objects;

/**
 * Role = (idRole VARCHAR(50), codeRole VARCHAR(50), libelleRole VARCHAR(50))
 */
public class Role {

    private String idRole;
    private String codeRole;
    private String libelleRole;

    public Role(String idRole, String codeRole, String libelleRole) {
        if (codeRole == null || codeRole.isBlank()) {
            throw new IllegalArgumentException("Le code du rôle est obligatoire.");
        }
        if (libelleRole == null || libelleRole.isBlank()) {
            throw new IllegalArgumentException("Le libellé du rôle est obligatoire.");
        }
        this.idRole = idRole;
        this.codeRole = codeRole;
        this.libelleRole = libelleRole;
    }

    public void renommer(String libelleRole) {
        if (libelleRole == null || libelleRole.isBlank()) {
            throw new IllegalArgumentException("Le libellé ne peut pas être vide.");
        }
        this.libelleRole = libelleRole;
    }

    public String getIdRole() { return idRole; }
    public String getCodeRole() { return codeRole; }
    public String getLibelleRole() { return libelleRole; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(idRole, role.idRole);
    }
    @Override public int hashCode() { return Objects.hash(idRole); }
    @Override public String toString() {
        return "Role{idRole='" + idRole + "', codeRole='" + codeRole +
                "', libelleRole='" + libelleRole + "'}";
    }
}
