package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.securite.Notification;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.SecuriteRowMappers;
import fonea.ped.useCase.useCaseSecurite.NotificationRepository;

import java.sql.*;
import java.util.*;

public class JdbcNotificationRepository implements NotificationRepository {

    // ============ SQL ============

    private static final String SQL_SAVE = """
        INSERT INTO Notification
            (idNotification, typeNotification, message, dateEnvoi, lue, idUtilisateur)
        VALUES (?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE typeNotification = VALUES(typeNotification), message = VALUES(message),
                                dateEnvoi = VALUES(dateEnvoi), lue = VALUES(lue),
                                idUtilisateur = VALUES(idUtilisateur)
        """;

    private static final String SQL_FIND_ID = """
        SELECT idNotification, typeNotification, message, dateEnvoi, lue, idUtilisateur
        FROM Notification
        WHERE idNotification = ?
        """;

    private static final String SQL_FIND_BY_USER = """
        SELECT idNotification, typeNotification, message, dateEnvoi, lue, idUtilisateur
        FROM Notification
        WHERE idUtilisateur = ?
        ORDER BY dateEnvoi DESC
        """;

    private static final String SQL_FIND_ALL = """
        SELECT idNotification, typeNotification, message, dateEnvoi, lue, idUtilisateur
        FROM Notification
        ORDER BY dateEnvoi DESC
        """;

    private static final String SQL_COUNT_UNREAD = """
        SELECT COUNT(*) FROM Notification
        WHERE idUtilisateur = ? AND lue = 0
        """;

    private static final String SQL_MARK_ALL_READ = """
        UPDATE Notification SET lue = 1 WHERE idUtilisateur = ?
        """;

    private static final String SQL_DELETE = """
        DELETE FROM Notification WHERE idNotification = ?
        """;

    // ============ MÉTHODES DU PORT ============

    @Override
    public Notification sauvegarder(Notification notification) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {

            ps.setString(1, notification.getIdNotification());
            ps.setString(2, notification.getTypeNotification());
            ps.setString(3, notification.getMessage());

            if (notification.getDateEnvoi() != null) {
                ps.setTimestamp(4, Timestamp.valueOf(notification.getDateEnvoi()));
            } else {
                ps.setNull(4, Types.TIMESTAMP);
            }

            ps.setBoolean(5, notification.isLue());
            ps.setString(6, notification.getIdUtilisateur());

            ps.executeUpdate();
            return notification;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Notification", e);
        }
    }

    @Override
    public Optional<Notification> trouverParId(String idNotification) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {

            ps.setString(1, idNotification);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.of(SecuriteRowMappers.NOTIFICATION.map(rs))
                        : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Notification", e);
        }
    }

    @Override
    public List<Notification> trouverParUtilisateur(String idUtilisateur) {
        List<Notification> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_USER)) {

            ps.setString(1, idUtilisateur);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultats.add(SecuriteRowMappers.NOTIFICATION.map(rs));
                }
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParUtilisateur Notification", e);
        }
    }

    @Override
    public List<Notification> trouverToutes() {
        List<Notification> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultats.add(SecuriteRowMappers.NOTIFICATION.map(rs));
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverToutes Notification", e);
        }
    }

    @Override
    public long compterNonLues(String idUtilisateur) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_COUNT_UNREAD)) {

            ps.setString(1, idUtilisateur);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getLong(1) : 0L;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC compterNonLues Notification", e);
        }
    }

    @Override
    public void marquerToutesLues(String idUtilisateur) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_MARK_ALL_READ)) {

            ps.setString(1, idUtilisateur);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC marquerToutesLues Notification", e);
        }
    }

    @Override
    public void supprimer(String idNotification) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setString(1, idNotification);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC supprimer Notification", e);
        }
    }

    @Override
    public String prochainId() {
        return "NTF-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
