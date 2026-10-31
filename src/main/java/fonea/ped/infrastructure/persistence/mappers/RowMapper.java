package fonea.ped.infrastructure.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface générique pour mapper une ligne SQL en objet du domaine.
 */
public interface RowMapper<T> {

    T map(ResultSet rs) throws SQLException;
}
