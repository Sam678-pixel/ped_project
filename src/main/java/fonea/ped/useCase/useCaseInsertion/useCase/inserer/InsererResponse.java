package fonea.ped.useCase.useCaseInsertion.useCase.inserer;

import java.time.LocalDate;

import fonea.ped.domain.insertion.StatutContrat;

public class InsererResponse {
	
	private final String idInsertion;
    private final LocalDate dateEmbauche;
    private final String idPoste;
    private final String idTypeContrat;
    private final String idMiseStage;

    private final String idContrat;
    private final LocalDate dateDebutContrat;
    private final LocalDate dateFinContrat;
    private final Double salaire;
    private final StatutContrat statutContrat;

    public InsererResponse(String idInsertion,
                           LocalDate dateEmbauche,
                           String idPoste,
                           String idTypeContrat,
                           String idMiseStage,
                           String idContrat,
                           LocalDate dateDebutContrat,
                           LocalDate dateFinContrat,
                           Double salaire,
                           StatutContrat statutContrat) {
        this.idInsertion = idInsertion;
        this.dateEmbauche = dateEmbauche;
        this.idPoste = idPoste;
        this.idTypeContrat = idTypeContrat;
        this.idMiseStage = idMiseStage;
        this.idContrat = idContrat;
        this.dateDebutContrat = dateDebutContrat;
        this.dateFinContrat = dateFinContrat;
        this.salaire = salaire;
        this.statutContrat = statutContrat;
    }

    public String getIdInsertion() { return idInsertion; }
    public LocalDate getDateEmbauche() { return dateEmbauche; }
    public String getIdPoste() { return idPoste; }
    public String getIdTypeContrat() { return idTypeContrat; }
    public String getIdMiseStage() { return idMiseStage; }
    public String getIdContrat() { return idContrat; }
    public LocalDate getDateDebutContrat() { return dateDebutContrat; }
    public LocalDate getDateFinContrat() { return dateFinContrat; }
    public Double getSalaire() { return salaire; }
    public StatutContrat getStatutContrat() { return statutContrat; }
}
