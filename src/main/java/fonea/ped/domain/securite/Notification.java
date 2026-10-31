package fonea.ped.domain.securite;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Notification = (idNotification VARCHAR(50), typeNotification VARCHAR(50),
 *                 message TEXT, dateEnvoi DATETIME, lue LOGICAL, #idUtilisateur)
 */
public class Notification {

    private String idNotification;
    private String typeNotification;
    private String message;
    private LocalDateTime dateEnvoi;
    private boolean lue;
    private String idUtilisateur;

    public Notification(String idNotification, String typeNotification,
                        String message, String idUtilisateur) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Le message de la notification est obligatoire.");
        }
        this.idNotification = idNotification;
        this.typeNotification = typeNotification;
        this.message = message;
        this.dateEnvoi = LocalDateTime.now();
        this.lue = false;
        this.idUtilisateur = idUtilisateur;
    }

    public void marquerLue() {
        this.lue = true;
    }

    public String getIdNotification() { return idNotification; }
    public String getTypeNotification() { return typeNotification; }
    public String getMessage() { return message; }
    public LocalDateTime getDateEnvoi() { return dateEnvoi; }
    public boolean isLue() { return lue; }
    public String getIdUtilisateur() { return idUtilisateur; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(idNotification, that.idNotification);
    }
    @Override public int hashCode() { return Objects.hash(idNotification); }
    @Override public String toString() {
        return "Notification{idNotification='" + idNotification +
                "', type='" + typeNotification + "', lue=" + lue + "}";
    }
}
