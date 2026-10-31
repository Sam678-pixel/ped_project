package fonea.ped.useCase.useCaseRecrutement.ports;

import java.util.List;
import java.util.Optional;

import fonea.ped.domain.recrutement.TypeDocument;

public interface TypeDocumentRepository {

    TypeDocument sauvegarder(TypeDocument typeDocument);

    Optional<TypeDocument> trouverParId(String idTypeDocument);

    List<TypeDocument> trouverTous();

    boolean existeParId(String idTypeDocument);

    String prochainId();
}
