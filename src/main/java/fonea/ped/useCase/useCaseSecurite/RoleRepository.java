package fonea.ped.useCase.useCaseSecurite;

import fonea.ped.domain.securite.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    Role sauvegarder(Role role);

    Optional<Role> trouverParId(String idRole);

    List<Role> trouverTous();

    boolean existeParId(String idRole);

    String prochainId();
}