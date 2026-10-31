package fonea.ped.infrastructure.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import fonea.ped.domain.Offres.Secteur;

public class SecteurRowMapper implements RowMapper<Secteur> {

    @Override
    public Secteur map(ResultSet rs) throws SQLException {
        return new Secteur(
                rs.getString("idSecteur"),
                rs.getString("libelle")
        );
    }
}
