package fonea.ped.useCase.useCaseDiplome.useCase.creerDiplome;

import java.time.LocalDate;

public class CreerDiplomeResponse {
	
	private final String idDiplome;
    private final String nom;
    private final String prenom;
    private final LocalDate dateNaissance;
    private final int age;

    public CreerDiplomeResponse(String idDiplome,
                                String nom,
                                String prenom,
                                LocalDate dateNaissance,
                                int age) {
        this.idDiplome = idDiplome;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.age = age;
    }

    public String getIdDiplome() { return idDiplome; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public int getAge() { return age; }
}
