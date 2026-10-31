package fonea.ped.interfaceAdapters.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fonea.ped.domain.MiseEnStage.MotifDecrochage;
import fonea.ped.domain.Offres.Domaine;
import fonea.ped.domain.Offres.Secteur;
import fonea.ped.domain.convention.model.MotifResiliation;
import fonea.ped.domain.convention.model.Programme;
import fonea.ped.domain.insertion.TypeContrat;
import fonea.ped.domain.recrutement.TypeDocument;
import fonea.ped.useCase.UseCaseConvention.ports.MotifResiliationRepository;
import fonea.ped.useCase.UseCaseConvention.ports.ProgrammeRepository;
import fonea.ped.useCase.useCaseInsertion.ports.TypeContratRepository;
import fonea.ped.useCase.useCaseMiseEnStage.ports.MotifDecrochageRepository;
import fonea.ped.useCase.useCaseOffres.ports.DomaineRepository;
import fonea.ped.useCase.useCaseOffres.ports.SecteurRepository;
import fonea.ped.useCase.useCaseRecrutement.ports.TypeDocumentRepository;

/**
 * Consultation du référentiel (lecture seule).
 *
 * <p>Ces données sont indispensables au client pour construire les formulaires :
 * sans elles il devait deviner les identifiants de secteur, de domaine, de type
 * de contrat ou de type de document.</p>
 */
@RestController
@RequestMapping("/api/referentiel")
@PreAuthorize("hasAuthority('PERM_REFERENTIEL_LIRE')")
public class ReferentielController {

    private final SecteurRepository secteurRepository;
    private final DomaineRepository domaineRepository;
    private final TypeContratRepository typeContratRepository;
    private final TypeDocumentRepository typeDocumentRepository;
    private final MotifDecrochageRepository motifDecrochageRepository;
    private final MotifResiliationRepository motifResiliationRepository;
    private final ProgrammeRepository programmeRepository;

    public ReferentielController(SecteurRepository secteurRepository,
                                 DomaineRepository domaineRepository,
                                 TypeContratRepository typeContratRepository,
                                 TypeDocumentRepository typeDocumentRepository,
                                 MotifDecrochageRepository motifDecrochageRepository,
                                 MotifResiliationRepository motifResiliationRepository,
                                 ProgrammeRepository programmeRepository) {
        this.secteurRepository = secteurRepository;
        this.domaineRepository = domaineRepository;
        this.typeContratRepository = typeContratRepository;
        this.typeDocumentRepository = typeDocumentRepository;
        this.motifDecrochageRepository = motifDecrochageRepository;
        this.motifResiliationRepository = motifResiliationRepository;
        this.programmeRepository = programmeRepository;
    }

    @GetMapping("/secteurs")
    public List<Secteur> secteurs() {
        return secteurRepository.trouverTous();
    }

    @GetMapping("/domaines")
    public List<Domaine> domaines() {
        return domaineRepository.trouverTous();
    }

    @GetMapping("/types-contrat")
    public List<TypeContrat> typesContrat() {
        return typeContratRepository.trouverTous();
    }

    @GetMapping("/types-document")
    public List<TypeDocument> typesDocument() {
        return typeDocumentRepository.trouverTous();
    }

    @GetMapping("/motifs-decrochage")
    public List<MotifDecrochage> motifsDecrochage() {
        return motifDecrochageRepository.trouverTous();
    }

    @GetMapping("/motifs-resiliation")
    public List<MotifResiliation> motifsResiliation() {
        return motifResiliationRepository.trouverTous();
    }

    @GetMapping("/programmes")
    public List<Programme> programmes() {
        return programmeRepository.trouverTous();
    }
}
