package fonea.ped.infrastructure.persistence.mappers;

import fonea.ped.domain.recrutement.Candidature;
import fonea.ped.domain.recrutement.Document;
import fonea.ped.domain.recrutement.Preselection;
import fonea.ped.domain.recrutement.Selection;
import fonea.ped.domain.recrutement.StatutCandidature;

import java.time.LocalDate;
import java.util.List;

/**
 * Mappers JDBC des objets du recrutement.
 *
 * <p>La table {@code Candidature} ne contient aucune colonne {@code Documents} :
 * les pièces jointes sont portées par la table de liaison
 * {@code piece_a_joindre} et chargées séparément par
 * {@code JdbcCandidatureRepository}. Le mapper se limite donc aux colonnes
 * réellement présentes et renvoie une liste de documents vide, que le
 * repository enrichit ensuite.</p>
 */
public class RecrutementRowMappers {

    public static final RowMapper<Document> DOCUMENT = rs ->
        new Document(
                rs.getString("idDocument"),
                rs.getString("cheminFichier"),
                rs.getString("idDiplomé"),
                rs.getString("idTypedocument")
        );

    public static final RowMapper<Candidature> CANDIDATURE = rs -> {
        String statut = rs.getString("StatutCandidature");

        return new Candidature(
                rs.getString("idCandidature"),
                rs.getObject("dateDepot", LocalDate.class),
                statut != null ? StatutCandidature.valueOf(statut) : null,
                rs.getString("idDiplomé"),
                rs.getString("idPublication"),
                List.of()
        );
    };

    public static final RowMapper<Preselection> PRESELECTION = rs -> {
        String statut = rs.getString("StatutPreselection");

        return new Preselection(
                rs.getString("idPreselection"),
                rs.getObject("datePreselection", LocalDate.class),
                statut != null ? StatutCandidature.valueOf(statut) : null,
                rs.getString("idAgent"),
                rs.getString("idCandidature")
        );
    };

    public static final RowMapper<Selection> SELECTION = rs -> {
        String statut = rs.getString("StatutSelection");

        return new Selection(
                rs.getString("idSelection"),
                rs.getObject("dateSelection", LocalDate.class),
                statut != null ? StatutCandidature.valueOf(statut) : null,
                rs.getString("idPreselection")
        );
    };
}
