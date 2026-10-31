package fonea.ped.insertion.model;

import java.time.LocalDate;
import java.util.Objects;

public class Insertion {

    private String idInsertion;
    private LocalDate dateEmbauche;
    private String idTypeContrat;
    private String idPoste;
    private String idMiseStage;

    public Insertion(String idInsertion,
                     LocalDate dateEmbauche,
                     String idTypeContrat,
                     String idPoste,
                     String idMiseStage) {
        this.idInsertion = idInsertion;
        this.dateEmbauche = dateEmbauche;
        this.idTypeContrat = idTypeContrat;
        this.idPoste = idPoste;
        this.idMiseStage = idMiseStage;
    }

    // ----- Méthodes métier -----

    /**
     * Enregistre la date d'embauche.
     */
    public void enregistrerEmbauche(LocalDate dateEmbauche) {
        if (dateEmbauche == null) {
            throw new IllegalArgumentException("La date d'embauche ne peut pas être null.");
        }
        this.dateEmbauche = dateEmbauche;
    }

    /**
     * Associe un type de contrat à l'insertion.
     */
    public void associerContrat(String idTypeContrat) {
        if (idTypeContrat == null) {
            throw new IllegalArgumentException("L'identifiant du type de contrat ne peut pas être null.");
        }
        this.idTypeContrat = idTypeContrat;
    }

    /**
     * Associe un poste à l'insertion.
     */
    public void associerPoste(String idPoste) {
        if (idPoste == null) {
            throw new IllegalArgumentException("L'identifiant du poste ne peut pas être null.");
        }
        this.idPoste = idPoste;
    }

    // ----- Getters -----

    public String getIdInsertion() {
        return idInsertion;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public String getIdTypeContrat() {
        return idTypeContrat;
    }

    public String getIdPoste() {
        return idPoste;
    }

    public String getIdMiseStage() {
        return idMiseStage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Insertion insertion = (Insertion) o;
        return Objects.equals(idInsertion, insertion.idInsertion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInsertion);
    }

    @Override
    public String toString() {
        return "Insertion{idInsertion=" + idInsertion +
                ", dateEmbauche=" + dateEmbauche +
                ", idTypeContrat=" + idTypeContrat +
                ", idPoste=" + idPoste +
                ", idMiseStage=" + idMiseStage + "}";
    }
}
