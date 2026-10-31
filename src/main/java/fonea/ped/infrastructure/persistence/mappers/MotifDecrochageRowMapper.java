package fonea.ped.infrastructure.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import fonea.ped.domain.MiseEnStage.MotifDecrochage;

public class MotifDecrochageRowMapper implements RowMapper<MotifDecrochage> {

    @Override
    public MotifDecrochage map(ResultSet rs) throws SQLException {
        return new MotifDecrochage(
                rs.getString("idMotifDecrochage"),
                rs.getString("libelle"),
                rs.getString("typeMotif")
        );
    }
}
