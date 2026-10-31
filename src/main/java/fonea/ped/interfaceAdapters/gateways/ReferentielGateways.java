package fonea.ped.interfaceAdapters.gateways;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.MiseEnStage.MotifDecrochage;
import fonea.ped.domain.Offres.Competence;
import fonea.ped.domain.Offres.Critere;
import fonea.ped.domain.Offres.Domaine;
import fonea.ped.domain.Offres.Metier;
import fonea.ped.domain.Offres.Poste;
import fonea.ped.domain.Offres.PrerequisFormation;
import fonea.ped.domain.Offres.Qualification;
import fonea.ped.domain.Offres.Secteur;
import fonea.ped.domain.Offres.TechniqueLinguistique;
import fonea.ped.domain.convention.model.MotifResiliation;
import fonea.ped.domain.insertion.TypeContrat;
import fonea.ped.domain.recrutement.TypeDocument;
import fonea.ped.infrastructure.persistence.repositories.JdbcCompetenceRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcCritereRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcDomaineRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcMetierRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcMotifDecrochageRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcMotifResiliationRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcPosteRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcPrerequisRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcQualificationRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcSecteurRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcTechniqueLinguistiqueRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcTypeContratRepository;
import fonea.ped.infrastructure.persistence.repositories.JdbcTypeDocumentRepository;
import fonea.ped.useCase.UseCaseConvention.ports.MotifResiliationRepository;
import fonea.ped.useCase.useCaseInsertion.ports.TypeContratRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MotifDecrochageRepository;
import fonea.ped.useCase.useCaseOffres.ports.CompetenceRepository;
import fonea.ped.useCase.useCaseOffres.ports.CritereRepository;
import fonea.ped.useCase.useCaseOffres.ports.DomaineRepository;
import fonea.ped.useCase.useCaseOffres.ports.MetierRepository;
import fonea.ped.useCase.useCaseOffres.ports.PosteRepository;
import fonea.ped.useCase.useCaseOffres.ports.PrerequisRepository;
import fonea.ped.useCase.useCaseOffres.ports.QualificationRepository;
import fonea.ped.useCase.useCaseOffres.ports.SecteurRepository;
import fonea.ped.useCase.useCaseOffres.ports.TechniqueLinguistiqueRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.TypeDocumentRepository;

/**
 * Regroupe les 13 Gateways du module Référentiel.
 * Chacune délègue au repository JDBC par défaut.
 */
public final class ReferentielGateways {

    private ReferentielGateways() {}

    // ============ Secteur ============
    public static class SecteurGateway implements SecteurRepository {
        private final SecteurRepository delegate;
        public SecteurGateway() { this(new JdbcSecteurRepository()); }
        public SecteurGateway(SecteurRepository d) { this.delegate = d; }
        @Override public Secteur sauvegarder(Secteur s) { return delegate.sauvegarder(s); }
        @Override public Optional<Secteur> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Secteur> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ Domaine ============
    public static class DomaineGateway implements DomaineRepository {
        private final DomaineRepository delegate;
        public DomaineGateway() { this(new JdbcDomaineRepository()); }
        public DomaineGateway(DomaineRepository d) { this.delegate = d; }
        @Override public Domaine sauvegarder(Domaine d) { return delegate.sauvegarder(d); }
        @Override public Optional<Domaine> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Domaine> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ TypeContrat ============
    public static class TypeContratGateway implements TypeContratRepository {
        private final TypeContratRepository delegate;
        public TypeContratGateway() { this(new JdbcTypeContratRepository()); }
        public TypeContratGateway(TypeContratRepository d) { this.delegate = d; }
        @Override public TypeContrat sauvegarder(TypeContrat t) { return delegate.sauvegarder(t); }
        @Override public Optional<TypeContrat> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<TypeContrat> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ MotifDecrochage ============
    public static class MotifDecrochageGateway implements MotifDecrochageRepository {
        private final MotifDecrochageRepository delegate;
        public MotifDecrochageGateway() { this(new JdbcMotifDecrochageRepository()); }
        public MotifDecrochageGateway(MotifDecrochageRepository d) { this.delegate = d; }
        @Override public MotifDecrochage sauvegarder(MotifDecrochage m) { return delegate.sauvegarder(m); }
        @Override public Optional<MotifDecrochage> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<MotifDecrochage> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ MotifResiliation ============
    public static class MotifResiliationGateway implements MotifResiliationRepository {
        private final MotifResiliationRepository delegate;
        public MotifResiliationGateway() { this(new JdbcMotifResiliationRepository()); }
        public MotifResiliationGateway(MotifResiliationRepository d) { this.delegate = d; }
        @Override public MotifResiliation sauvegarder(MotifResiliation m) { return delegate.sauvegarder(m); }
        @Override public Optional<MotifResiliation> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<MotifResiliation> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ TypeDocument ============
    public static class TypeDocumentGateway implements TypeDocumentRepository {
        private final TypeDocumentRepository delegate;
        public TypeDocumentGateway() { this(new JdbcTypeDocumentRepository()); }
        public TypeDocumentGateway(TypeDocumentRepository d) { this.delegate = d; }
        @Override public TypeDocument sauvegarder(TypeDocument t) { return delegate.sauvegarder(t); }
        @Override public Optional<TypeDocument> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<TypeDocument> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ TechniqueLinguistique ============
    public static class TechniqueLinguistiqueGateway implements TechniqueLinguistiqueRepository {
        private final TechniqueLinguistiqueRepository delegate;
        public TechniqueLinguistiqueGateway() { this(new JdbcTechniqueLinguistiqueRepository()); }
        public TechniqueLinguistiqueGateway(TechniqueLinguistiqueRepository d) { this.delegate = d; }
        @Override public TechniqueLinguistique sauvegarder(TechniqueLinguistique t) { return delegate.sauvegarder(t); }
        @Override public Optional<TechniqueLinguistique> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<TechniqueLinguistique> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ PrerequisFormation ============
    public static class PrerequisGateway implements PrerequisRepository {
        private final PrerequisRepository delegate;
        public PrerequisGateway() { this(new JdbcPrerequisRepository()); }
        public PrerequisGateway(PrerequisRepository d) { this.delegate = d; }
        @Override public PrerequisFormation sauvegarder(PrerequisFormation p) { return delegate.sauvegarder(p); }
        @Override public Optional<PrerequisFormation> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<PrerequisFormation> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ Competence ============
    public static class CompetenceGateway implements CompetenceRepository {
        private final CompetenceRepository delegate;
        public CompetenceGateway() { this(new JdbcCompetenceRepository()); }
        public CompetenceGateway(CompetenceRepository d) { this.delegate = d; }
        @Override public Competence sauvegarder(Competence c) { return delegate.sauvegarder(c); }
        @Override public Optional<Competence> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Competence> trouverToutes() { return delegate.trouverToutes(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ Metier ============
    public static class MetierGateway implements MetierRepository {
        private final MetierRepository delegate;
        public MetierGateway() { this(new JdbcMetierRepository()); }
        public MetierGateway(MetierRepository d) { this.delegate = d; }
        @Override public Metier sauvegarder(Metier m) { return delegate.sauvegarder(m); }
        @Override public Optional<Metier> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Metier> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ Qualification ============
    public static class QualificationGateway implements QualificationRepository {
        private final QualificationRepository delegate;
        public QualificationGateway() { this(new JdbcQualificationRepository()); }
        public QualificationGateway(QualificationRepository d) { this.delegate = d; }
        @Override public Qualification sauvegarder(Qualification q) { return delegate.sauvegarder(q); }
        @Override public Optional<Qualification> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Qualification> trouverToutes() { return delegate.trouverToutes(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ Poste ============
    public static class PosteGateway implements PosteRepository {
        private final PosteRepository delegate;
        public PosteGateway() { this(new JdbcPosteRepository()); }
        public PosteGateway(PosteRepository d) { this.delegate = d; }
        @Override public Poste sauvegarder(Poste p) { return delegate.sauvegarder(p); }
        @Override public Optional<Poste> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Poste> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }

    // ============ Critere ============
    public static class CritereGateway implements CritereRepository {
        private final CritereRepository delegate;
        public CritereGateway() { this(new JdbcCritereRepository()); }
        public CritereGateway(CritereRepository d) { this.delegate = d; }
        @Override public Critere sauvegarder(Critere c) { return delegate.sauvegarder(c); }
        @Override public Optional<Critere> trouverParId(String id) { return delegate.trouverParId(id); }
        @Override public List<Critere> trouverTous() { return delegate.trouverTous(); }
        @Override public boolean existeParId(String id) { return delegate.existeParId(id); }
        @Override public String prochainId() { return delegate.prochainId(); }
    }
}
