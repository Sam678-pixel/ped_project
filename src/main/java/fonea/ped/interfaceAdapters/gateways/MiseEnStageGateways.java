package fonea.ped.interfaceAdapters.gateways;

import fonea.ped.domain.MiseEnStage.*;
import fonea.ped.infrastructure.persistence.repositories.*;
import fonea.ped.useCase.useCaseMiseEnStage.ports.DecrochageRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MiseEnStageRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.RenouvellementRepository;

import java.util.List;
import java.util.Optional;

public final class MiseEnStageGateways {

    private MiseEnStageGateways() {}

    public static class MiseEnStageGateway implements MiseEnStageRepository {
        private final MiseEnStageRepository delegate;
        public MiseEnStageGateway() { this(new JdbcMiseEnStageRepository()); }
        public MiseEnStageGateway(MiseEnStageRepository d) { this.delegate = d; }
        @Override public MiseEnStage sauvegarder(MiseEnStage m) { return delegate.sauvegarder(m); }
        @Override public Optional<MiseEnStage> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<MiseEnStage> trouverToutes() { return delegate.trouverToutes(); }
        @Override public Optional trouverParSelection(String id) { return delegate.trouverParSelection(id); }
        @Override public List<MiseEnStage> trouverParEntreprise(String id) { return delegate.trouverParEntreprise(id); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public boolean existeParSelection(String id) { return delegate.existeParSelection(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    public static class DecrochageGateway implements DecrochageRepository {
        private final DecrochageRepository delegate;
        public DecrochageGateway() { this(new JdbcDecrochageRepository()); }
        public DecrochageGateway(DecrochageRepository d) { this.delegate = d; }
        @Override public Decrochage sauvegarder(Decrochage d) { return delegate.sauvegarder(d); }
        @Override public Optional<Decrochage> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Decrochage> trouverTous() { return delegate.trouverTous(); }
        @Override public Optional<Decrochage> trouverParMiseEnStage(String id) { return delegate.trouverParMiseEnStage(id); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public boolean existeParMiseEnStage(String id) { return delegate.existeParMiseEnStage(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    public static class RenouvellementGateway implements RenouvellementRepository {
        private final RenouvellementRepository delegate;
        public RenouvellementGateway() { this(new JdbcRenouvellementRepository()); }
        public RenouvellementGateway(RenouvellementRepository d) { this.delegate = d; }
        @Override public Renouvellement sauvegarder(Renouvellement r) { return delegate.sauvegarder(r); }
        @Override public Optional<Renouvellement> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Renouvellement> trouverTous() { return delegate.trouverTous(); }
        @Override public Optional trouverParMiseEnStage(String id) { return delegate.trouverParMiseEnStage(id); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public boolean existeParMiseEnStage(String id) { return delegate.existeParMiseEnStage(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }
}
