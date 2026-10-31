package fonea.ped.useCase.useCaseRecrutement.ports;

import java.util.List;

import fonea.ped.domain.recrutement.CandidatureDocument;

public interface CandidatureDocumentRepository {

    CandidatureDocument sauvegarder(CandidatureDocument liaison);

    List<CandidatureDocument> trouverParCandidature(String idCandidature);

    List<CandidatureDocument> trouverParDocument(String idDocument);

    boolean existe(String idCandidature, String idDocument);

    void supprimer(String idCandidature, String idDocument);
}
