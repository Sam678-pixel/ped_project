package fonea.ped.useCase.useCaseDiplome.useCase.creerDiplome;

import java.time.LocalDate;

//import fonea.ped.domain.diplome.Diplome;

public class CreerDiplomeRequest {
	
	private final String nom;
    private final String prenom;
    private final LocalDate dateNaissance;
    private final String sexe;
    private final String niveauDiplome;
    private final String anneObtention;
    private final String filiere;
    private final String idUtilisateur;
    

    public CreerDiplomeRequest(String nom, String prenom, LocalDate dateNaissance, String sexe,
    		                   String niveauDiplome,String anneObtention, String filiere, String idUtilisateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe= sexe;
        this.niveauDiplome=niveauDiplome;
        this.anneObtention=anneObtention;
        this.filiere= filiere;
        this.idUtilisateur=idUtilisateur;
    }
    
    

    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public String getsexe() {return sexe;}
    public String getniveauDiplome() {return niveauDiplome;}
    public String getanneObtention() {return anneObtention;}
    public String getfiliere() {return filiere;}
    public String idUtilisateur() {return idUtilisateur;}
    	
    };

