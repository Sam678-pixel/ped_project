package fonea.ped.infrastructure.persistence.mappers;

import fonea.ped.domain.insertion.Insertion;
import fonea.ped.domain.insertion.Contrat;

import java.sql.Date;

/**
 * Regroupe tous les RowMappers du module Insertion.
 */
public final class InsertionRowMappers {

    private InsertionRowMappers() {}

    public static final RowMapper<Insertion> INSERTION = rs -> {
        Date emb = rs.getDate("DateEmbauche");
        return new Insertion(
                rs.getString("idInsertion"),
                emb != null ? emb.toLocalDate() : null,
                rs.getString("idPoste"),
                rs.getString("idMiseStage")
        );
    };

    public static final RowMapper<Contrat> CONTRAT = rs -> {
        Date debut = rs.getDate("dateDebut");
        Date fin = rs.getDate("dateFin");
        Double salaire = rs.getObject("Salaire") != null ? rs.getDouble("Salaire") : null;
        return new Contrat(
                rs.getString("idContrat"),
                debut != null ? debut.toLocalDate() : null,
                fin != null ? fin.toLocalDate() : null,
                salaire,
                rs.getString("idTypeContrat"),
                rs.getString("idInsertion")
        );
    };
}
