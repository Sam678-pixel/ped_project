package fonea.ped.infrastructure.persistence.mappers;

import fonea.ped.domain.MiseEnStage.Decrochage;
import fonea.ped.domain.MiseEnStage.MiseEnStage;
import fonea.ped.domain.MiseEnStage.Renouvellement;
import fonea.ped.domain.MiseEnStage.StatutStage;

import java.sql.Date;

public final class MiseEnStageRowMappers {

    private MiseEnStageRowMappers() {}

    public static final RowMapper<MiseEnStage> MISE_EN_STAGE = rs -> {
        Date debut = rs.getDate("dateDebut");
        Date fin = rs.getDate("dateFin");
        String statut = rs.getString("StatutStage");
        return new MiseEnStage(
                rs.getString("idMiseStage"),
                debut != null ? debut.toLocalDate() : null,
                fin != null ? fin.toLocalDate() : null,
                statut != null ? StatutStage.valueOf(statut) : null,
                rs.getString("idSelection"),
                rs.getString("idEntreprise")
        );
    };

    public static final RowMapper<Decrochage> DECROCHAGE = rs -> {
        Date date = rs.getDate("DateDecrochage");
        return new Decrochage(
                rs.getString("idDecrochage"),
                date != null ? date.toLocalDate() : null,
                rs.getString("idMiseStage")
        );
    };

    public static final RowMapper<Renouvellement> RENOUVELLEMENT = rs -> {
        Date debut = rs.getDate("dateDebut");
        Date fin = rs.getDate("dateFin");
        return new Renouvellement(
                rs.getString("idRenouvellement"),
                debut != null ? debut.toLocalDate() : null,
                fin != null ? fin.toLocalDate() : null,
                rs.getString("idMiseStage")
        );
    };
}
