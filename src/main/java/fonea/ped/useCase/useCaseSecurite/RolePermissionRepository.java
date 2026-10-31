package fonea.ped.useCase.useCaseSecurite;

import java.util.List;

import fonea.ped.domain.securite.RolePermission;

public interface RolePermissionRepository {

    RolePermission sauvegarder(RolePermission liaison);

    List<RolePermission> trouverParRole(String idRole);

    List<RolePermission> trouverParPermission(String idPermission);

    boolean existe(String idRole, String idPermission);

    void supprimer(String idRole, String idPermission);
}
