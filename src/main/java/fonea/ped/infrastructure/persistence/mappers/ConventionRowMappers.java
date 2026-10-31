package fonea.ped.infrastructure.persistence.mappers;

import fonea.ped.domain.convention.model.Convention;
import fonea.ped.domain.convention.model.Resiliation;
import fonea.ped.domain.convention.model.StatutConvention;
import fonea.ped.domain.convention.model.TypeConvention;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ConventionRowMappers {
	

    private ConventionRowMappers() {}

    public static final RowMapper<Convention> CONVENTION = rs -> {
        Date de = rs.getDate("dateEffet");
        return new Convention(
                rs.getString("idConvention"),
                de != null ? de.toLocalDate() : null,
                rs.getObject("tauxFonea") != null ? rs.getDouble("tauxFonea") : null,
                rs.getObject("tauxEntreprise") != null ? rs.getDouble("tauxEntreprise") : null,
                rs.getString("SatutConvention")!= null ? StatutConvention.valueOf(rs.getString("SatutConvention")) : null,
                rs.getString("TypeConvention")!= null ? new TypeConvention(rs.getString("TypeConvention")) : null,
                rs.getString("idEntreprise")
        );

    };

    public static final RowMapper<Resiliation> RESILIATION = rs -> {
        Date dr = rs.getDate("dateResiliation");
        return new Resiliation(
                rs.getString("idResiliation"),
                dr != null ? dr.toLocalDate() : null,
                rs.getString("initiateur"),
                rs.getString("idConvention")
        );
    };
}
