package fonea.ped.interfaceAdapters.gateways;


import fonea.ped.domain.diplome.Diplome;
import fonea.ped.infrastructure.persistence.repositories.JdbcDiplomeRepository;
import fonea.ped.useCase.useCaseDiplome.port.DiplomeRepository;

import java.util.List;
import java.util.Optional;

public class DiplomeGateway implements DiplomeRepository {

    private final DiplomeRepository delegate;

    public DiplomeGateway() { this(new JdbcDiplomeRepository()); }
    public DiplomeGateway(DiplomeRepository d) { this.delegate = d; }

    @Override public Diplome sauvegarder(Diplome d) { return delegate.sauvegarder(d); }
    @Override public Optional<Diplome> trouverParId(String id) { return delegate.trouverParId(id); }
    @Override public List<Diplome> trouverTous() { return delegate.trouverTous(); }
    @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
    @Override public String prochainId() { return delegate.prochainId(); }
}
