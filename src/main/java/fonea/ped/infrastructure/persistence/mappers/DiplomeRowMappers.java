package fonea.ped.infrastructure.persistence.mappers;

import fonea.ped.domain.diplome.Diplome;
import fonea.ped.domain.diplome.DiplomeCompetence;

public final class DiplomeRowMappers {

    private DiplomeRowMappers() {}

    public static final RowMapper<Diplome> DIPLOME = rs ->
            new Diplome(
                    rs.getString("idDiplomé"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getDate("dateNaissance") != null
                            ? rs.getDate("dateNaissance").toLocalDate() : null,
                    rs.getString("sexe"),
                    rs.getString("niveauDiplome"),
                    rs.getString("annéeOptention"),
                    rs.getString("Filiere"),
                    rs.getString("idUtilisateur")
            );

    public static final RowMapper<DiplomeCompetence> DIPLOME_COMPETENCE = rs ->
            new DiplomeCompetence(
                    rs.getString("idDiplomé"),
                    rs.getString("idCompetence")
            );
}
