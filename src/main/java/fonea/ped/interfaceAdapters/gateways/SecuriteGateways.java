package fonea.ped.interfaceAdapters.gateways;

import fonea.ped.domain.securite.*;
import fonea.ped.infrastructure.persistence.repositories.*;
import fonea.ped.useCase.useCaseSecurite.RoleRepository;

import fonea.ped.useCase.useCaseSecurite.*;

import java.util.List;
import java.util.Optional;

public final class SecuriteGateways {

    private SecuriteGateways() {}

    // ============ Role ============
    public static class RoleGateway implements RoleRepository {
        private final RoleRepository delegate;
        public RoleGateway() { this(new JdbcRoleRepository()); }
        public RoleGateway(RoleRepository d) { this.delegate = d; }
        @Override public Role sauvegarder(Role r) { return delegate.sauvegarder(r); }
        @Override public Optional<Role> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Role> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ Permission ============
    public static class PermissionGateway implements PermissionRepository {
        private final PermissionRepository delegate;
        public PermissionGateway() { this(new JdbcPermissionRepository()); }
        public PermissionGateway(PermissionRepository d) { this.delegate = d; }
        @Override public Permission sauvegarder(Permission p) { return delegate.sauvegarder(p); }
        @Override public Optional<Permission> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Permission> trouverTous() { return delegate.trouverTous(); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ Utilisateur ============
    public static class UtilisateurGateway implements UtilisateurRepository {
        private final UtilisateurRepository delegate;
        public UtilisateurGateway() { this(new JdbcUtilisateurRepository()); }
        public UtilisateurGateway(UtilisateurRepository d) { this.delegate = d; }
        @Override public Utilisateur sauvegarder(Utilisateur u) { return delegate.sauvegarder(u); }
        @Override public Optional<Utilisateur> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public Optional<Utilisateur> trouverParEmail(String email) { return delegate.trouverParEmail(email); }
        @Override public List<Utilisateur> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ AgentFonea ============
    public static class AgentFoneaGateway implements AgentFoneaRepository {
        private final AgentFoneaRepository delegate;
        public AgentFoneaGateway() { this(new JdbcAgentFoneaRepository()); }
        public AgentFoneaGateway(AgentFoneaRepository d) { this.delegate = d; }
        @Override public AgentFonea sauvegarder(AgentFonea a) { return delegate.sauvegarder(a); }
        @Override public Optional<AgentFonea> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<AgentFonea> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ Notification ============
    public static class NotificationGateway implements NotificationRepository {
        private final NotificationRepository delegate;
        public NotificationGateway() { this(new JdbcNotificationRepository()); }
        public NotificationGateway(NotificationRepository d) { this.delegate = d; }
        @Override public Notification sauvegarder(Notification n) { return delegate.sauvegarder(n); }
        @Override public Optional<Notification> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Notification> trouverParUtilisateur(String idUser) { return delegate.trouverParUtilisateur(idUser); }
        @Override public List<Notification> trouverToutes() { return delegate.trouverToutes(); }
        @Override public long compterNonLues(String idUser) { return delegate.compterNonLues(idUser); }
        @Override public void marquerToutesLues(String idUser) { delegate.marquerToutesLues(idUser); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }
}
