package fonea.ped.interfaceAdapters.gateways;

import fonea.ped.domain.convention.model.Convention;
import fonea.ped.domain.convention.model.Resiliation;
import fonea.ped.infrastructure.persistence.repositories.JdbcConventionRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcResiliationRepository;
import fonea.ped.useCase.UseCaseConvention.ports.ConventionRepository;
import fonea.ped.useCase.UseCaseConvention.ports.ResiliationRepository;


import java.util.List;
import java.util.Optional;

public final class ConventionGateways {

    private ConventionGateways() {}

    public static class ConventionGateway implements ConventionRepository {
        private final ConventionRepository delegate;
        public ConventionGateway() { this(new JdbcConventionRepository()); }
        public ConventionGateway(ConventionRepository d) { this.delegate = d; }
        @Override public Convention sauvegarder(Convention c) { return delegate.sauvegarder(c); }
        @Override public Optional<Convention> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Convention> trouverToutes() { return delegate.trouverToutes(); }
        @Override public List<Convention> trouverParEntreprise(String id) { return delegate.trouverParEntreprise(id); }
        @Override public List<Convention> trouverActives() { return delegate.trouverActives(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    public static class ResiliationGateway implements ResiliationRepository {
        private final ResiliationRepository delegate;
        public ResiliationGateway() { this(new JdbcResiliationRepository()); }
        public ResiliationGateway(ResiliationRepository d) { this.delegate = d; }
        @Override public Resiliation sauvegarder(Resiliation r) { return delegate.sauvegarder(r); }
        @Override public Optional<Resiliation> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Resiliation> trouverParConvention(String id) { return delegate.trouverParConvention(id); }
        @Override public List<Resiliation> trouverToutes() { return delegate.trouverToutes(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }
}
