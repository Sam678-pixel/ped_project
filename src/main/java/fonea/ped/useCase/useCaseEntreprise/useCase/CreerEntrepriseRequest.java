package fonea.ped.useCase.useCaseEntreprise.useCase;

import java.util.List;

/**
 * Entrée du cas d'usage « créer une entreprise ».
 *
 * <p>L'ancienne signature {@code (idEntreprise, raisonSocial, adresse, niu,
 * idUtilisateur)} était appelée par le contrôleur avec
 * {@code (raisonSocial, adresse, telephone, email, idSecteur)} : les champs
 * étaient donc tous décalés. La signature est maintenant explicite.</p>
 */
public class CreerEntrepriseRequest {

    private final String raisonSocial;
    private final String adresse;
    private final String niu;
    private final String idUtilisateur;
    private final List<String> idsSecteurs;

    public CreerEntrepriseRequest(String raisonSocial,
                                  String adresse,
                                  String niu,
                                  String idUtilisateur,
                                  List<String> idsSecteurs) {
        this.raisonSocial = raisonSocial;
        this.adresse = adresse;
        this.niu = niu;
        this.idUtilisateur = idUtilisateur;
        this.idsSecteurs = idsSecteurs;
    }

    public String getRaisonSocial() { return raisonSocial; }
    public String getAdresse() { return adresse; }
    public String getNiu() { return niu; }
    public String getIdUtilisateur() { return idUtilisateur; }
    public List<String> getIdsSecteurs() { return idsSecteurs; }
}
