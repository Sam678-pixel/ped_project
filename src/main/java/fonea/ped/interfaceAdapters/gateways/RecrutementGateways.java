package fonea.ped.interfaceAdapters.gateways;

import fonea.ped.domain.recrutement.*;
import fonea.ped.infrastructure.persistence.repositories.*;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.DocumentRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.PreselectionRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.SelectionRepository;

import java.util.List;
import java.util.Optional;

public final class RecrutementGateways {

    private RecrutementGateways() {}

    public static class CandidatureGateway implements CandidatureRepository {
        private final CandidatureRepository delegate;
        public CandidatureGateway() { this(new JdbcCandidatureRepository()); }
        public CandidatureGateway(CandidatureRepository d) { this.delegate = d; }
        @Override public Candidature sauvegarder(Candidature c) { return delegate.sauvegarder(c); }
        @Override public Optional<Candidature> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Candidature> trouverToutes() { return delegate.trouverToutes(); }
        @Override public List<Candidature> trouverParPublication(String id) { return delegate.trouverParPublication(id); }
        @Override public List<Candidature> candidaturesParDiplome(String id) { return delegate.candidaturesParDiplome(id); }
        @Override public List<Candidature> trouverParOffre(String id) { return delegate.trouverParOffre(id); }
        @Override public boolean existeParDiplomeEtPublication(String d, String p) { return delegate.existeParDiplomeEtPublication(d, p); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    public static class DocumentGateway implements DocumentRepository {
        private final DocumentRepository delegate;
        public DocumentGateway() { this(new JdbcDocumentRepository()); }
        public DocumentGateway(DocumentRepository d) { this.delegate = d; }
        @Override public Document sauvegarder(Document d) { return delegate.sauvegarder(d); }
        @Override public Optional<Document> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Document> trouverParDiplome(String id) { return delegate.trouverParDiplome(id); }
        @Override public List<Document> trouverParType(String id) { return delegate.trouverParType(id); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    public static class PreselectionGateway implements PreselectionRepository {
        private final PreselectionRepository delegate;
        public PreselectionGateway() { this(new JdbcPreselectionRepository()); }
        public PreselectionGateway(PreselectionRepository d) { this.delegate = d; }
        @Override public Preselection sauvegarder(Preselection p) { return delegate.sauvegarder(p); }
        @Override public Optional<Preselection> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Preselection> trouverToutes() { return delegate.trouverToutes(); }
        @Override public List<Preselection> trouverParCandidature(String id) { return delegate.trouverParCandidature(id); }
        @Override public List<Preselection> trouverParAgent(String id) { return delegate.trouverParAgent(id); }
        @Override public boolean existeParCandidature(String id) { return delegate.existeParCandidature(id); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    public static class SelectionGateway implements SelectionRepository {
        private final SelectionRepository delegate;
        public SelectionGateway() { this(new JdbcSelectionRepository()); }
        public SelectionGateway(SelectionRepository d) { this.delegate = d; }
        @Override public Selection sauvegarder(Selection s) { return delegate.sauvegarder(s); }
        @Override public Optional<Selection> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Selection> trouverToutes() { return delegate.trouverToutes(); }
        @Override public List<Selection> trouverParPreselection(String id) { return delegate.trouverParPreselection(id); }
        @Override public boolean existeParPreselection(String id) { return delegate.existeParPreselection(id); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public void supprimer(String id) { delegate.supprimer(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }
}
