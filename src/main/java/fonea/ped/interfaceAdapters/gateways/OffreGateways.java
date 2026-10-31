package fonea.ped.interfaceAdapters.gateways;

import fonea.ped.domain.Offres.OffresStages;
import fonea.ped.domain.Offres.Publication;
import fonea.ped.useCase.useCaseOffres.ports.OffreRepository;
import fonea.ped.useCase.useCaseOffres.ports.PublicationRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcOffreRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcPublicationRepository;

import java.util.List;
import java.util.Optional;

public final class OffreGateways {

    private OffreGateways() {}

    public static class OffreGateway implements OffreRepository {
        private final OffreRepository delegate;
        public OffreGateway() { this(new JdbcOffreRepository()); }
        public OffreGateway(OffreRepository d) { this.delegate = d; }
        @Override public OffresStages sauvegarder(OffresStages o) { return delegate.sauvegarder(o); }
        @Override public Optional<OffresStages> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<OffresStages> trouverToutes() { return delegate.trouverToutes(); }
        @Override public List<OffresStages> trouverParConvention(String id) { return delegate.trouverParConvention(id); }
        @Override public List<OffresStages> offrePubliees() { return delegate.offrePubliees(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    public static class PublicationGateway implements PublicationRepository {
        private final PublicationRepository delegate;
        public PublicationGateway() { this(new JdbcPublicationRepository()); }
        public PublicationGateway(PublicationRepository d) { this.delegate = d; }
        @Override public Publication sauvegarder(Publication p) { return delegate.sauvegarder(p); }
        @Override public Optional<Publication> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Publication> trouverParOffre(String id) { return delegate.trouverParOffre(id); }
        @Override public Optional<Publication> trouverActiveParOffre(String id) { return delegate.trouverActiveParOffre(id); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
        @ Override public List<Publication> trouverToutes() { return delegate.trouverToutes();}
    }
}
