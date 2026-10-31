package fonea.ped.infrastructure.persistence.mappers;

import fonea.ped.domain.Offres.Domaine;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DomaineRowMapper implements RowMapper<Domaine> {

    @Override
    public Domaine map(ResultSet rs) throws SQLException {
        return new Domaine(
                rs.getString("idDomain"),
                rs.getString("libelle")
        );
    }
}
