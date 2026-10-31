package fonea.ped.infrastructure.persistence.mappers;

import fonea.ped.domain.MiseEnStage.DecrochageMotif;
import fonea.ped.domain.Offres.CompetencePoste;
import fonea.ped.domain.Offres.CritereCompetence;
import fonea.ped.domain.Offres.CriterePrerequis;
import fonea.ped.domain.Offres.CritereTechniqueLinguistique;
import fonea.ped.domain.Offres.QualificationCompetence;
import fonea.ped.domain.convention.model.ResiliationMotif;
import fonea.ped.domain.diplome.DiplomeCompetence;
import fonea.ped.domain.entreprise.EntrepriseSecteur;
import fonea.ped.domain.securite.RolePermission;

/**
 * Regroupe tous les RowMappers des tables de liaison.
 * Chaque mapper lit les deux colonnes de clé étrangère.
 */
public final class LiaisonRowMappers {

    private LiaisonRowMappers() {}

    public static final RowMapper<RolePermission> ROLE_PERMISSION = rs ->
            new RolePermission(
                    rs.getString("idRole"),
                    rs.getString("idPermission")
            );

    public static final RowMapper<EntrepriseSecteur> ENTREPRISE_SECTEUR = rs ->
            new EntrepriseSecteur(
                    rs.getString("idEntreprise"),
                    rs.getString("idSecteur")
            );

    public static final RowMapper<DiplomeCompetence> DIPLOME_COMPETENCE = rs ->
            new DiplomeCompetence(
                    rs.getString("idDiplomé"),
                    rs.getString("idCompetence")
            );

    public static final RowMapper<CritereCompetence> CRITERE_COMPETENCE = rs ->
            new CritereCompetence(
                    rs.getString("idCritere"),
                    rs.getString("idCompetence")
            );

    public static final RowMapper<CompetencePoste> COMPETENCE_POSTE = rs ->
            new CompetencePoste(
                    rs.getString("idCompetence"),
                    rs.getString("idPoste")
            );

    public static final RowMapper<QualificationCompetence> QUALIFICATION_COMPETENCE = rs ->
            new QualificationCompetence(
                    rs.getString("idQualification"),
                    rs.getString("idCompetence")
            );

    public static final RowMapper<CriterePrerequis> CRITERE_PREREQUIS = rs ->
            new CriterePrerequis(
                    rs.getString("idCritere"),
                    rs.getString("idPrerequis")
            );

    public static final RowMapper<CritereTechniqueLinguistique> CRITERE_TECHNIQUE_LINGUISTIQUE = rs ->
            new CritereTechniqueLinguistique(
                    rs.getString("idCritere"),
                    rs.getString("idTechLing")
            );

    public static final RowMapper<ResiliationMotif> RESILIATION_MOTIF = rs ->
            new ResiliationMotif(
                    rs.getString("idResiliation"),
                    rs.getString("idMotifResiliation")
            );

    public static final RowMapper<DecrochageMotif> DECROCHAGE_MOTIF = rs ->
            new DecrochageMotif(
                    rs.getString("idDecrochage"),
                    rs.getString("idMotifDecrochage")
            );
}
