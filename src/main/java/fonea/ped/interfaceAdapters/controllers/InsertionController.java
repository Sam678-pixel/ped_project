package fonea.ped.interfaceAdapters.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fonea.ped.infrastructure.securite.UtilisateurCourant;
import fonea.ped.interfaceAdapters.controllers.dto.ApiDtos;
import fonea.ped.useCase.useCaseInsertion.useCase.inserer.InsererRequest;
import fonea.ped.useCase.useCaseInsertion.useCase.inserer.InsererResponse;
import fonea.ped.useCase.useCaseInsertion.useCase.inserer.InsererUseCase;
import fonea.ped.useCase.useCaseMiseEnStage.service.ChaineRecrutement;

/**
 * API d'insertion professionnelle : transformation d'une mise en stage en
 * embauche (contrat).
 */
@RestController
@RequestMapping("/api/insertions")
public class InsertionController {

    private final InsererUseCase insererUseCase;
    private final ChaineRecrutement chaineRecrutement;
    private final UtilisateurCourant utilisateurCourant;

    public InsertionController(InsererUseCase insererUseCase,
                               ChaineRecrutement chaineRecrutement,
                               UtilisateurCourant utilisateurCourant) {
        this.insererUseCase = insererUseCase;
        this.chaineRecrutement = chaineRecrutement;
        this.utilisateurCourant = utilisateurCourant;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERM_INSERTION_FAIRE')")
    public InsererResponse inserer(@RequestBody ApiDtos.Inserer corps) {
        // Seule l'entreprise qui emploie le stagiaire peut l'embaucher.
        String entrepriseProprietaire = chaineRecrutement
                .entrepriseDeLaMiseEnStage(corps.idMiseStage())
                .orElseThrow(() -> new IllegalStateException(
                        "Impossible de déterminer l'entreprise de la mise en stage "
                                + corps.idMiseStage() + "."));
        utilisateurCourant.exigerEntreprise(entrepriseProprietaire);

        InsererRequest requete = new InsererRequest(
                corps.idMiseStage(),
                corps.idPoste(),
                corps.idTypeContrat(),
                corps.dateEmbauche(),
                corps.dateDebutContrat(),
                corps.dateFinContrat(),
                corps.salaire());

        return insererUseCase.executer(requete);
    }
}
