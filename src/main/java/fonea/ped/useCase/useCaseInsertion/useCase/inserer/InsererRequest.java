package fonea.ped.useCase.useCaseInsertion.useCase.inserer;

import java.time.LocalDate;

public class InsererRequest {
	
	private final String idMiseStage;
    private final String idPoste;
    private final String idTypeContrat;

    // Infos du contrat
    private final LocalDate dateEmbauche;
    private final LocalDate dateDebutContrat;
    private final LocalDate dateFinContrat; // null si CDI
    private final Double salaire;

    public InsererRequest(String idMiseStage,
                          String idPoste,
                          String idTypeContrat,
                          LocalDate dateEmbauche,
                          LocalDate dateDebutContrat,
                          LocalDate dateFinContrat,
                          Double salaire) {
        this.idMiseStage = idMiseStage;
        this.idPoste = idPoste;
        this.idTypeContrat = idTypeContrat;
        this.dateEmbauche = dateEmbauche;
        this.dateDebutContrat = dateDebutContrat;
        this.dateFinContrat = dateFinContrat;
        this.salaire = salaire;
    }

    public String getIdMiseStage() { return idMiseStage; }
    public String getIdPoste() { return idPoste; }
    public String getIdTypeContrat() { return idTypeContrat; }
    public LocalDate getDateEmbauche() { return dateEmbauche; }
    public LocalDate getDateDebutContrat() { return dateDebutContrat; }
    public LocalDate getDateFinContrat() { return dateFinContrat; }
    public Double getSalaire() { return salaire; }
}
