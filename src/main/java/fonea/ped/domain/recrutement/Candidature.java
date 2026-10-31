package fonea.ped.domain.recrutement;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Candidature {

    private String idCandidature;
    private LocalDate dateDepot;
    private StatutCandidature statutCandidature;
    private String  idDiplome;
    private String idPublication;
    private  List<Document> documents;

    public Candidature(String idCandidature, LocalDate dateDepot,
                       StatutCandidature statutCandidature, String idDiplome, String idPublication, List<Document> documents) {
        this.idCandidature = idCandidature;
        this.dateDepot = dateDepot;
        this.statutCandidature = statutCandidature != null ? statutCandidature : StatutCandidature.EN_COURS;
        this.idDiplome = idDiplome;
        this.idPublication = idPublication;
        this.documents= documents;
    }

    public void deposer() {
        this.dateDepot = LocalDate.now();
        this.statutCandidature = StatutCandidature.EN_COURS;
    }

    public void changerStatut(StatutCandidature nouveauStatut) {
        if (nouveauStatut == null ) {
            throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
        }
        this.statutCandidature = nouveauStatut;
    }

    public void rejeter() { this.statutCandidature = StatutCandidature.REJETEE; }
    //public void retirer() {
       // if ("SELECTIONNEE".equals(this.statutCandidature)) {
            //throw new IllegalStateException("Impossible de retirer une candidature déjà sélectionnée.");
        //}
        //this.statutCandidature = "RETIREE";
    //}

    public String getIdCandidature() { return idCandidature; }
    public LocalDate getDateDepot() { return dateDepot; }
    public StatutCandidature getStatutCandidature() { return statutCandidature; }
    public String getIdDiplome() { return idDiplome; }
    public String getIdPublication() { return idPublication; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidature that = (Candidature) o;
        return Objects.equals(idCandidature, that.idCandidature);
    }
    @Override public int hashCode() { return Objects.hash(idCandidature); }
    @Override public String toString() {
        return "Candidature{idCandidature='" + idCandidature +
                "', statut='" + statutCandidature + "'}";
    }
    
    public void ajouterDocument(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Le document ne peut pas être null.");
        }
        // Exemple de règle métier : éviter les doublons par identifiant
        boolean existeDeja = this.documents.stream()
                .anyMatch(doc -> doc.getIdDocument().equals(document.getIdDocument()));
        
        if (!existeDeja) {
            this.documents.add(document);
        }
    }

    /**
     * Supprime un document par son objet ou son identifiant.
     */
    public void supprimerDocument(String documentId) {
        this.documents.removeIf(doc -> doc.getIdDocument().equals(documentId));
    }
    
    public boolean sontDocumentsComplets() {
        if (this.documents == null || this.documents.isEmpty()) {
            return false;
        }

        // Exemple de règle métier : vérifier la présence de types de documents précis
        boolean aUnCv = this.documents.stream()
                .anyMatch(doc -> "CV".equalsIgnoreCase(doc.getIdTypeDocument()));
                
        boolean aUnePieceIdentite = this.documents.stream()
                .anyMatch(doc -> "IDENTITE".equalsIgnoreCase(doc.getIdTypeDocument()));

        // La candidature est complète si elle a les deux (ou selon vos propres règles)
        return aUnCv && aUnePieceIdentite;
    }

	public List<Document> getDocuments() {
		return documents;
	}
}
