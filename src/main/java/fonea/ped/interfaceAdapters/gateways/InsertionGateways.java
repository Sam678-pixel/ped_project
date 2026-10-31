package fonea.ped.interfaceAdapters.gateways;

import fonea.ped.domain.insertion.Contrat;
import fonea.ped.domain.insertion.Insertion;
import fonea.ped.infrastructure.persistence.repositories.JdbcContratRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcInsertionRepository;
import fonea.ped.useCase.useCaseInsertion.ports.ContratRepository;
//import fonea.ped.useCase.useCaseInsertion.ports.InsertionPosteRepository;
import fonea.ped.useCase.useCaseInsertion.ports.InsertionRepository;

import java.util.List;
import java.util.Optional;

public final class InsertionGateways {

    private InsertionGateways() {}

    public static class InsertionGateway implements InsertionRepository/*, InsertionPosteRepository */{
        private final InsertionRepository delegate;
        public InsertionGateway() { this(new JdbcInsertionRepository()); }
        public InsertionGateway(InsertionRepository d) { this.delegate = d; }
        @Override public Insertion sauvegarder(Insertion i) { return delegate.sauvegarder(i); }
        @Override public Optional<Insertion> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Insertion> trouverToutes() { return delegate.trouverToutes(); }
        @Override public Optional trouverParMiseEnStage(String id) { return delegate.trouverParMiseEnStage(id); }
       // @Override public List<Insertion> trouverParPoste(String id) { return delegate.trouverParPoste(id); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public boolean existeParMiseEnStage(String id) { return delegate.existeParMiseEnStage(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    public static class ContratGateway implements ContratRepository {
        private final ContratRepository delegate;
        public ContratGateway() { this(new JdbcContratRepository()); }
        public ContratGateway(ContratRepository d) { this.delegate = d; }
        @Override public Contrat sauvegarder(Contrat c) { return delegate.sauvegarder(c); }
        @Override public Optional<Contrat> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Contrat> trouverTous() { return delegate.trouverTous(); }
        @Override public Optional<Contrat> trouverParInsertion(String id) { return delegate.trouverParInsertion(id); }
        //@Override public List<Contrat> trouverParTypeContrat(String id) { return delegate.trouverParTypeContrat(id); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }
}
