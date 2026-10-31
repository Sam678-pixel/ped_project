package fonea.ped.interfaceAdapters.gateways;

import fonea.ped.domain.entreprise.Entreprise;
import fonea.ped.infrastructure.persistence.repositories.JdbcEntrepriseRepository;
import fonea.ped.useCase.useCaseEntreprise.port.EntrepriseRepository;

import java.util.List;
import java.util.Optional;

public class EntrepriseGateway implements EntrepriseRepository {

    private final EntrepriseRepository delegate;

    public EntrepriseGateway() { this(new JdbcEntrepriseRepository()); }
    public EntrepriseGateway(EntrepriseRepository d) { this.delegate = d; }

    @Override public Entreprise sauvegarder(Entreprise e) { return delegate.sauvegarder(e); }
    @Override public Optional<Entreprise> trouverParId(String id) { return delegate.trouverParId(id); }
    @Override public List<Entreprise> trouverToutes() { return delegate.trouverToutes(); }
    @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
    @Override public String prochainId() { return delegate.prochainId(); }
}
