package fonea.ped.domain.diplome;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Diplome {

    private String idDiplome;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;
    private String niveauDiplome;
    private String anneeObtention;
    private String filiere;
    private String idUtilisateur;

    public Diplome(String idDiplome, String nom, String prenom,
                   LocalDate dateNaissance, String sexe, String niveauDiplome,
                   String anneeObtention, String filiere, String idUtilisateur) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire.");
        }
        if (prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException("Le prénom est obligatoire.");
        }
        this.idDiplome = idDiplome;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.niveauDiplome = niveauDiplome;
        this.anneeObtention = anneeObtention;
        this.filiere = filiere;
        this.idUtilisateur = idUtilisateur;
    }

    public void mettreAJourInformations(String nom, String prenom, LocalDate dateNaissance) {
        if (nom != null && !nom.isBlank()) this.nom = nom;
        if (prenom != null && !prenom.isBlank()) this.prenom = prenom;
        if (dateNaissance != null) this.dateNaissance = dateNaissance;
    }

    public int calculerAge() {
        if (dateNaissance == null) {
            throw new IllegalStateException("La date de naissance n'est pas définie.");
        }
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    public String getIdDiplome() { return idDiplome; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public String getSexe() { return sexe; }
    public String getNiveauDiplome() { return niveauDiplome; }
    public String getAnneeObtention() { return anneeObtention; }
    public String getFiliere() { return filiere; }
    public String getIdUtilisateur() { return idUtilisateur; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diplome diplome = (Diplome) o;
        return Objects.equals(idDiplome, diplome.idDiplome);
    }
    @Override public int hashCode() { return Objects.hash(idDiplome); }
    @Override public String toString() {
        return "Diplome{idDiplome='" + idDiplome + "', nom='" + nom +
                "', prenom='" + prenom + "', niveauDiplome='" + niveauDiplome + "'}";
    }
}
