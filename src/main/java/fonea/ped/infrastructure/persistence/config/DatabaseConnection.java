package fonea.ped.infrastructure.persistence.config;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Point d'accès unique au pool de connexions pour les repositories JDBC.
 *
 * <p>Le {@link DataSource} est fourni par Spring au démarrage (voir
 * {@link DatabaseConnectionBinder}) : il s'agit d'un
 * {@code TransactionAwareDataSourceProxy} qui rend les connexions sensibles aux
 * transactions Spring. Ainsi un {@code try (Connection c = ...)} dans un
 * repository libère correctement la connexion, et participe à la transaction
 * ouverte par {@code @Transactional} sur le use case.</p>
 */
public final class DatabaseConnection {

    private static volatile DataSource dataSource;

    private DatabaseConnection() {
    }

    /** Injecté une seule fois au démarrage par Spring. */
    public static void utiliser(DataSource source) {
        dataSource = source;
    }

    public static Connection getConnection() throws SQLException {
        DataSource source = dataSource;
        if (source == null) {
            throw new IllegalStateException(
                    "DatabaseConnection non initialisée : le DataSource Spring n'a pas été injecté.");
        }
        return source.getConnection();
    }

    public static boolean estInitialisee() {
        return dataSource != null;
    }
}
