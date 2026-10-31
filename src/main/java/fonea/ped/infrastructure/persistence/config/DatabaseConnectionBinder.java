package fonea.ped.infrastructure.persistence.config;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;

/**
 * Branche le {@link DataSource} géré par Spring sur {@link DatabaseConnection}.
 *
 * <p>Le proxy « transaction aware » est indispensable : les repositories
 * utilisent {@code try (Connection c = DatabaseConnection.getConnection())}.
 * Sans ce proxy, chaque {@code close()} fermerait la connexion physique et
 * casserait la transaction ouverte par le use case.</p>
 */
@Component
public class DatabaseConnectionBinder {

    public DatabaseConnectionBinder(DataSource dataSource) {
        DatabaseConnection.utiliser(new TransactionAwareDataSourceProxy(dataSource));
    }
}
