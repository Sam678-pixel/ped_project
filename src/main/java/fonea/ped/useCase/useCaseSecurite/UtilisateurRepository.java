package fonea.ped.useCase.useCaseSecurite;

import fonea.ped.domain.securite.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository {

    Utilisateur sauvegarder(Utilisateur utilisateur);

    Optional<Utilisateur> trouverParId(String idUtilisateur);

    Optional<Utilisateur> trouverParEmail(String email);

    List<Utilisateur> trouverTous();

    boolean existeParId(String idUtilisateur);

    String prochainId();
}