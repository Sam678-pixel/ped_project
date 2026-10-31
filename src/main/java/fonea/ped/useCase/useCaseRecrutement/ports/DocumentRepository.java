package fonea.ped.useCase.useCaseRecrutement.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.recrutement.Document;

public interface DocumentRepository {
	
	Document sauvegarder(Document document);

    Optional<Document> trouverParId(String idDocument);
    
    List<Document> trouverParDiplome(String idDiplome);
    
    List<Document> trouverParType(String idTypeDocument);
    
    boolean existeParId(String idDocument);
    
    
    void supprimer(String idDocument);

    String prochainId();
}
