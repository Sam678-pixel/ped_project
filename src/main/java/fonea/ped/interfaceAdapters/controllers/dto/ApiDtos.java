package fonea.ped.interfaceAdapters.controllers.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO d'entrée de l'API HTTP.
 *
 * <p>Les contrôleurs n'exposent jamais directement les objets du domaine : cela
 * évite de lier le contrat HTTP au modèle métier et empêche un client de forger
 * des identifiants ou des statuts (par exemple un statut {@code ACTIVE} sur une
 * convention qu'il crée).</p>
 */
public final class ApiDtos {

    private ApiDtos() {
    }

    // ==================== Authentification ====================

    public record Login(String email, String motDePasse) {
    }

    public record Jeton(String jeton,
                        String type,
                        long expirationSecondes,
                        String idUtilisateur,
                        String email,
                        String role) {
    }

    public record UtilisateurConnecte(String idUtilisateur,
                                      String email,
                                      String role,
                                      List<String> autorites) {
    }

    public record Erreur(int statut, String message) {
    }

    // ==================== Convention ====================

    public record CreerConvention(LocalDate dateEffet,
                                  Double tauxFonea,
                                  Double tauxEntreprise,
                                  String idTypeConvention,
                                  String idProgramme,
                                  String idEntreprise) {
    }

    public record ResilierConvention(String motif, String initiateur) {
    }

    // ==================== Offre ====================

    public record CreerOffre(String intitule,
                             String description,
                             String villeStage,
                             Integer duree,
                             Integer nbrePlaces,
                             String idConvention,
                             String idDomaine,
                             String idPoste,
                             String idMetier,
                             String idSecteur,
                             String idEntreprise,
                             List<String> idsCompetences,
                             List<String> idsCriteres,
                             List<String> idsPrerequis,
                             List<String> idsQualifications) {
    }

    public record PublierOffre(LocalDate dateExpiration) {
    }

    // ==================== Recrutement ====================

    public record Document(String idDocument, String cheminFichier, String idTypeDocument) {
    }

    public record Postuler(String idDiplome, String idOffre, List<Document> documents) {
    }

    public record Preselectionner(String idCandidature) {
    }

    public record Selectionner(String idPreselection) {
    }

    // ==================== Mise en stage ====================

    public record MettreEnStage(String idSelection,
                                LocalDate dateDebut,
                                LocalDate dateFinStage,
                                String idEntreprise) {
    }

    public record DeclarerDecrochage(String idMotifDecrochage) {
    }

    public record RenouvelerStage(LocalDate nouvelleDateDebut, LocalDate nouvelleDateFin) {
    }

    // ==================== Insertion ====================

    public record Inserer(String idMiseStage,
                          String idPoste,
                          String idTypeContrat,
                          LocalDate dateEmbauche,
                          LocalDate dateDebutContrat,
                          LocalDate dateFinContrat,
                          Double salaire) {
    }

    // ==================== Vues de consultation ====================

    public record ConventionVue(String idConvention,
                                LocalDate dateEffet,
                                Double tauxFonea,
                                Double tauxEntreprise,
                                String statutConvention,
                                String idTypeConvention,
                                String idEntreprise) {
    }

    public record OffreVue(String idOffre,
                           String intitule,
                           String description,
                           String villeStage,
                           Integer dureeMois,
                           String statutOffre,
                           Integer nbrePlaces,
                           String idPoste,
                           String idDomaine,
                           String idConvention) {
    }

    public record CandidatureVue(String idCandidature,
                                 LocalDate dateDepot,
                                 String statutCandidature,
                                 String idDiplome,
                                 String idPublication,
                                 List<Document> documents) {
    }

    public record PreselectionVue(String idPreselection,
                                  LocalDate datePreselection,
                                  String statutPreselection,
                                  String idAgent,
                                  String idCandidature) {
    }

    public record SelectionVue(String idSelection,
                               LocalDate dateSelection,
                               String statutSelection,
                               String idPreselection) {
    }

    public record MiseEnStageVue(String idMiseStage,
                                 LocalDate dateDebut,
                                 LocalDate dateFin,
                                 String statutStage,
                                 String idSelection,
                                 String idEntreprise) {
    }

    // ==================== Diplôme / Entreprise ====================

    public record CreerDiplome(String nom,
                               String prenom,
                               LocalDate dateNaissance,
                               String sexe,
                               String niveauDiplome,
                               String anneeObtention,
                               String filiere,
                               String idUtilisateur) {
    }

    public record CreerEntreprise(String raisonSocial,
                                  String adresse,
                                  String niu,
                                  String idUtilisateur,
                                  List<String> idsSecteurs) {
    }
}
