package fonea.ped.infrastructure.persistence.mappers;




import java.sql.Date;
import java.sql.ResultSet;

import fonea.ped.domain.Offres.OffreCompetence;
import fonea.ped.domain.Offres.OffreCritere;
import fonea.ped.domain.Offres.OffrePrerequis;
import fonea.ped.domain.Offres.OffreQualification;
import fonea.ped.domain.Offres.OffresStages;
import fonea.ped.domain.Offres.Publication;
import fonea.ped.domain.Offres.StatutOffre;

public final class OffreRowMappers {

    private OffreRowMappers() {}

    public static final RowMapper<OffresStages> OFFRE = rs ->
            new OffresStages(
                    rs.getString("idOffre"),
                    rs.getString("intitule"),
                    rs.getString("description"),
                    rs.getString("villeStage"),
                    rs.getObject("dureeMois") != null ? rs.getInt("dureeMois") : null,
                    StatutOffre.valueOf(rs.getString("StatutOffre")),
                    rs.getObject("nbrePlaces") != null ? rs.getInt("nbrePlaces") : 0,
                    rs.getString("idPoste"),
                    rs.getString("idDomain"),
                    rs.getString("idConvention")
            );

    public static final RowMapper<Publication> PUBLICATION = rs -> {
        Date dp = rs.getDate("datePublication");
        Date de = rs.getDate("dateExpiration");
        return new Publication(
                rs.getString("idPublication"),
                dp != null ? dp.toLocalDate() : null,
                de != null ? de.toLocalDate() : null,
                rs.getString("idAgent"),
                rs.getString("idOffre")
        );

    };

    public static final RowMapper<OffreCritere> OFFRE_CRITERE = rs ->
            new OffreCritere(
                    rs.getString("idOffre"),
                    rs.getString("idCritere")
            );

    public static final RowMapper<OffreCompetence> OFFRE_COMPETENCE = rs ->
            new OffreCompetence(
                    rs.getString("idOffre"),
                    rs.getString("idCompetence")
            );

    public static final RowMapper<OffrePrerequis> OFFRE_PREREQUIS = rs ->
            new OffrePrerequis(
                    rs.getString("idOffre"),
                    rs.getString("idPrerequis")
            );

    public static final RowMapper<OffreQualification> OFFRE_QUALIFICATION = rs ->
            new OffreQualification(
                    rs.getString("idOffre"),
                    rs.getString("idQualification")
            );
}
