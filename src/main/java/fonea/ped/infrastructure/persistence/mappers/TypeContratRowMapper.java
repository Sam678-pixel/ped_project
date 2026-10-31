package fonea.ped.infrastructure.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import fonea.ped.domain.insertion.TypeContrat;

public class TypeContratRowMapper implements RowMapper<TypeContrat> {

    @Override
    public TypeContrat map(ResultSet rs) throws SQLException {
        return new TypeContrat(
                rs.getString("idTypeContrat"),
                rs.getString("libelle")
        );
    }
}
