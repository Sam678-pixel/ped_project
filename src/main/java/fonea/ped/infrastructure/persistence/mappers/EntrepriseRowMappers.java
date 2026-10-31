package fonea.ped.infrastructure.persistence.mappers;

import fonea.ped.domain.entreprise.Entreprise;
import fonea.ped.domain.entreprise.EntrepriseSecteur;


public final class EntrepriseRowMappers {

    private EntrepriseRowMappers() {}

    public static final RowMapper<Entreprise> ENTREPRISE = rs ->
            new Entreprise(
                    rs.getString("idEntreprise"),
                    rs.getString("raisonSociale"),
                    rs.getString("adresse"),
                    rs.getString("niu"),
                    rs.getString("idUtilisateur")
            );

    public static final RowMapper<EntrepriseSecteur> ENTREPRISE_SECTEUR = rs ->
            new EntrepriseSecteur(
                    rs.getString("idEntreprise"),
                    rs.getString("idSecteur")
            );
}
