package fonea.ped.useCase.useCaseSecurite;

import fonea.ped.domain.securite.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository {

    Permission sauvegarder(Permission permission);

    Optional<Permission> trouverParId(String idPermission);

    List<Permission> trouverTous();

    //boolean existeParId(String idPermission);

    String prochainId();
}
