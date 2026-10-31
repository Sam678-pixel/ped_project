package fonea.ped.domain.exceptions;

/**
 * Racine de toutes les exceptions métier.
 *
 * <p>Le statut HTTP est déduit du nom de l'exception afin de ne pas dupliquer
 * la classification dans chaque sous-classe :</p>
 * <ul>
 *   <li>{@code *IntrouvableException} → 404</li>
 *   <li>{@code *Deja*}, {@code *Impossible*}, {@code *NonActive*},
 *       {@code *NonPubliee*}, {@code *Incomplete*}… → 409 (conflit d'état)</li>
 *   <li>le reste → 400 (requête invalide)</li>
 * </ul>
 */
public class DomaineException extends RuntimeException {

    public DomaineException(String message) {
        super(message);
    }

    public DomaineException(String message, Throwable cause) {
        super(message, cause);
    }

    /** Statut HTTP à renvoyer au client. */
    public int statutHttp() {
        String nom = getClass().getSimpleName();
        if (nom.contains("Introuvable")) {
            return 404;
        }
        if (nom.contains("Deja")
                || nom.contains("Impossible")
                || nom.contains("NonActive")
                || nom.contains("NonPubliee")
                || nom.contains("NonPreselectionnable")
                || nom.contains("NonPreselectionnee")
                || nom.contains("NonSelectionnee")
                || nom.contains("Incomplete")
                || nom.contains("Incomplet")
                || nom.contains("Depasse")) {
            return 409;
        }
        return 400;
    }
}
