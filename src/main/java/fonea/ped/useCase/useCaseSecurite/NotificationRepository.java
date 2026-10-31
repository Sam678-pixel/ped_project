package fonea.ped.useCase.useCaseSecurite;

import fonea.ped.domain.securite.Notification;

import java.util.List;
import java.util.Optional;

/**
 * Port de sortie pour la persistance des Notifications.
 * Implémenté par la couche infrastructure (JDBC, InMemory, etc.).
 */
public interface NotificationRepository {

    /**
     * Sauvegarde une notification (création ou mise à jour).
     */
    Notification sauvegarder(Notification notification);

    /**
     * Trouve une notification par son identifiant.
     */
    Optional<Notification> trouverParId(String idNotification);

    /**
     * Liste les notifications d'un utilisateur (les plus récentes d'abord).
     */
    List<Notification> trouverParUtilisateur(String idUtilisateur);

    /**
     * Liste toutes les notifications (utile pour l'administration).
     */
    List<Notification> trouverToutes();

    /**
     * Compte les notifications non lues d'un utilisateur.
     */
    long compterNonLues(String idUtilisateur);

    /**
     * Marque toutes les notifications d'un utilisateur comme lues.
     */
    void marquerToutesLues(String idUtilisateur);

    /**
     * Supprime une notification par son identifiant.
     */
    void supprimer(String idNotification);

    /**
     * Génère le prochain identifiant de notification.
     */
    String prochainId();
}