package fonea.ped.useCase.useCaseDiplome.useCase.postuler;

import java.time.LocalDate;
import java.util.List;

import fonea.ped.domain.recrutement.Document;
import fonea.ped.domain.recrutement.StatutCandidature;

public class PostulerResponse {
	private final String idCandidature;
    private final LocalDate dateDepot;
    private final StatutCandidature statutCandidature;
    private final String idDiplome;
    private final String idPublication;
    private final List<Document> idDocument;

    public PostulerResponse(String idCandidature,
                            LocalDate dateDepot,
                            StatutCandidature statutCandidature,
                            String idDiplome,
                            String idPublication,
                            List<Document> documents) {
        this.idCandidature = idCandidature;
        this.dateDepot = dateDepot;
        this.statutCandidature = statutCandidature;
        this.idDiplome = idDiplome;
        this.idPublication = idPublication;
        this.idDocument = documents;
    }

    public String getIdCandidature() { return idCandidature; }
    public LocalDate getDateDepot() { return dateDepot; }
    public StatutCandidature getStatutCandidature() { return statutCandidature; }
    public String getIdDiplome() { return idDiplome; }
    public String getIdPublication() { return idPublication; }
    public List<Document> getIdDocument() { return idDocument; }
}
