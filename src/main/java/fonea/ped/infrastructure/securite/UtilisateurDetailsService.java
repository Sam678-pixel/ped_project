package fonea.ped.infrastructure.securite;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fonea.ped.domain.securite.Permission;
import fonea.ped.domain.securite.Role;
import fonea.ped.domain.securite.RolePermission;
import fonea.ped.domain.securite.Utilisateur;
import fonea.ped.useCase.useCaseSecurite.PermissionRepository;
import fonea.ped.useCase.useCaseSecurite.RolePermissionRepository;
import fonea.ped.useCase.useCaseSecurite.RoleRepository;
import fonea.ped.useCase.useCaseSecurite.UtilisateurRepository;

/**
 * Charge un utilisateur et ses autorisations depuis la base.
 *
 * <p>Le modèle {@code Role → Permission} existait déjà en base mais n'était
 * relié à rien : chaque permission est exposée comme autorité Spring
 * {@code PERM_<CODE>} et chaque rôle comme {@code ROLE_<CODE>}.</p>
 */
@Service
public class UtilisateurDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;

    public UtilisateurDetailsService(UtilisateurRepository utilisateurRepository,
                                     RoleRepository roleRepository,
                                     RolePermissionRepository rolePermissionRepository,
                                     PermissionRepository permissionRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifiant) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.trouverParId(identifiant)
                .or(() -> utilisateurRepository.trouverParEmail(identifiant))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utilisateur inconnu : " + identifiant));

        return User.withUsername(utilisateur.getIdUtilisateur())
                .password(utilisateur.getPassword())
                .authorities(authorities(utilisateur))
                .build();
    }

    public List<GrantedAuthority> authorities(Utilisateur utilisateur) {
        List<GrantedAuthority> autorites = new ArrayList<>();
        if (utilisateur.getIdRole() == null) {
            return autorites;
        }
        Role role = roleRepository.trouverParId(utilisateur.getIdRole()).orElse(null);
        if (role == null) {
            return autorites;
        }
        autorites.add(new SimpleGrantedAuthority("ROLE_" + role.getCodeRole()));
        for (RolePermission liaison : rolePermissionRepository.trouverParRole(role.getIdRole())) {
            Permission permission = permissionRepository
                    .trouverParId(liaison.getIdPermission())
                    .orElse(null);
            if (permission != null) {
                autorites.add(new SimpleGrantedAuthority("PERM_" + permission.getCodePermission()));
            }
        }
        return autorites;
    }

    public String codeRole(Utilisateur utilisateur) {
        if (utilisateur.getIdRole() == null) {
            return null;
        }
        return roleRepository.trouverParId(utilisateur.getIdRole())
                .map(Role::getCodeRole)
                .orElse(null);
    }
}
