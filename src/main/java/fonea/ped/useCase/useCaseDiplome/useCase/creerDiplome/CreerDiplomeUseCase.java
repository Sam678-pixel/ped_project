package fonea.ped.useCase.useCaseDiplome.useCase.creerDiplome;

import java.time.LocalDate;

import fonea.ped.domain.diplome.Diplome;
import fonea.ped.useCase.useCaseDiplome.port.*;
import fonea.ped.domain.exceptions.DateInvalideException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreerDiplomeUseCase {
	
	private final DiplomeRepository diplomeRepository;

    public CreerDiplomeUseCase(DiplomeRepository diplomeRepository) {
        this.diplomeRepository = diplomeRepository;
    }

    public CreerDiplomeResponse executer(CreerDiplomeRequest request) {

        if (request.getNom() == null || request.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire.");
        }
        if (request.getPrenom() == null || request.getPrenom().isBlank()) {
            throw new IllegalArgumentException("Le prénom est obligatoire.");
        }
        if (request.getDateNaissance() == null) {
            throw new DateInvalideException("La date de naissance est obligatoire.");
        }
        if (request.getDateNaissance().isAfter(LocalDate.now())) {
            throw new DateInvalideException("La date de naissance ne peut pas être dans le futur.");
        }

        String nouvelId = diplomeRepository.prochainId();
        Diplome diplome = new Diplome(
                nouvelId,
                request.getNom(),
                request.getPrenom(),
                request.getDateNaissance(),
                request.getsexe(),
                request.getniveauDiplome(),
                request.getanneObtention(),
                request.getfiliere(),
                request.idUtilisateur()
        );
        
        /*
         * public Diplome(String idDiplome, String nom, String prenom,
                   LocalDate dateNaissance, String sexe, String niveauDiplome,
                   String anneeObtention, String filiere, String idUtilisateur) {
        
         */
        diplomeRepository.sauvegarder(diplome);

        return new CreerDiplomeResponse(
                diplome.getIdDiplome(),
                diplome.getNom(),
                diplome.getPrenom(),
                diplome.getDateNaissance(),
                diplome.calculerAge()
        );
    }
}
