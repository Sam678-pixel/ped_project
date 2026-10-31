package fonea.ped.domain.insertion;

import java.time.LocalDate;
import java.util.Objects;

public class Insertion {

    private String idInsertion;
    private LocalDate dateEmbauche;
    private String idPoste;
    private String idMiseStage;

    public Insertion(String idInsertion, LocalDate dateEmbauche,
                     String idPoste, String idMiseStage) {
        this.idInsertion = idInsertion;
        this.dateEmbauche = dateEmbauche;
        this.idPoste = idPoste;
        this.idMiseStage = idMiseStage;
    }

    public void enregistrerEmbauche(LocalDate dateEmbauche) {
        if (dateEmbauche == null) {
            throw new IllegalArgumentException("La date d'embauche ne peut pas être null.");
        }
        this.dateEmbauche = dateEmbauche;
    }

    public void associerPoste(String idPoste) {
        if (idPoste == null || idPoste.isBlank()) {
            throw new IllegalArgumentException("L'identifiant du poste est obligatoire.");
        }
        this.idPoste = idPoste;
    }

    public String getIdInsertion() { return idInsertion; }
    public LocalDate getDateEmbauche() { return dateEmbauche; }
    public String getIdPoste() { return idPoste; }
    public String getIdMiseStage() { return idMiseStage; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Insertion that = (Insertion) o;
        return Objects.equals(idInsertion, that.idInsertion);
    }
    @Override public int hashCode() { return Objects.hash(idInsertion); }
    @Override public String toString() {
        return "Insertion{idInsertion='" + idInsertion +
                "', dateEmbauche=" + dateEmbauche + "}";
    }

	
}
