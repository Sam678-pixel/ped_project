package fonea.ped.diplome.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Diplome {
	 private String idDiplome;
	    private String nom;
	    private String prenom;
	    private LocalDate dateNaissance;

	    // Constructeur par défaut
	    public Diplome() {
	    }
	    
	    // Constructeur avec paramètres
	    public Diplome(String idDiplome, String nom, String prenom, LocalDate dateNaissance) {
	        this.idDiplome = idDiplome;
	        this.nom = nom;
	        this.prenom = prenom;
	        this.dateNaissance = dateNaissance;
	    }

	    // Getters et Setters
	    public String getIdDiplome() { 
	    	return idDiplome;
	    }
	    
	    public void setIdDiplome(String idDiplome) {
	    	this.idDiplome = idDiplome;
	    }

	    public String getNom() {
	        return nom;
	    }

	    public void setNom(String nom) {
	        this.nom = nom;
	    }

	    public String getPrenom() {
	        return prenom;
	    }

	    public void setPrenom(String prenom) {
	        this.prenom = prenom;
	    }

	    public LocalDate getDateNaissance() {
	        return dateNaissance;
	    }

	    public void setDateNaissance(LocalDate dateNaissance) {
	        this.dateNaissance = dateNaissance;
	    }

	    /**
	     * Met à jour les informations personnelles du diplômé
	     */
	    public void mettreAJourInformations(String nom, String prenom, LocalDate dateNaissance) {
	        if (nom != null && !nom.isBlank()) {
	            this.nom = nom;
	        }
	        if (prenom != null && !prenom.isBlank()) {
	            this.prenom = prenom;
	        }
	        if (dateNaissance != null) {
	            this.dateNaissance = dateNaissance;
	        }
	    }

	    /**
	     * Calcule l'âge du diplômé à partir de sa date de naissance
	     */
	    public int calculerAge() {
	        if (dateNaissance == null) {
	            throw new IllegalStateException("La date de naissance n'est pas définie.");
	        }
	        return Period.between(dateNaissance, LocalDate.now()).getYears();
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Diplome diplome = (Diplome) o;
	        return Objects.equals(idDiplome, diplome.idDiplome);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(idDiplome);
	    }

	    @Override
	    public String toString() {
	        return "Diplome{" +
	                "idDiplome=" + idDiplome +
	                ", nom='" + nom + '\'' +
	                ", prenom='" + prenom + '\'' +
	                ", dateNaissance=" + dateNaissance +
	                '}';
	    }
	}
