package fonea.ped.useCase.useCaseDiplome.useCase.postuler;

import java.util.List;

import fonea.ped.domain.recrutement.Document;

/**
 * Entrée du cas d'usage « postuler ».
 *
 * <p>L'ancienne version exigeait une {@code Candidature} complète en paramètre
 * (et plantait en NPE si elle était nulle) : c'est désormais le use case qui
 * crée la candidature.</p>
 */
public class PostulerRequest {

    private final String idDiplome;
    private final String idOffre;
    private final List<Document> documents;

    public PostulerRequest(String idDiplome, String idOffre, List<Document> documents) {
        this.idDiplome = idDiplome;
        this.idOffre = idOffre;
        this.documents = documents;
    }

    public String getIdDiplome() { return idDiplome; }
    public String getIdOffre() { return idOffre; }
    public List<Document> getDocuments() { return documents; }
}
