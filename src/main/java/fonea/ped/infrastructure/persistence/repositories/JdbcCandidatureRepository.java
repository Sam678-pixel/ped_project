package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.recrutement.Candidature;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RecrutementRowMappers;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Implémentation JDBC du port CandidatureRepository.
 * SQL MySQL uniquement.
 */
public class JdbcCandidatureRepository implements CandidatureRepository {

    // ============ SQL ============

    private static final String SQL_SAVE = """
        INSERT INTO Candidature (idCandidature, dateDepot, StatutCandidature, idDiplomé, idPublication)
        VALUES (?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            dateDepot = VALUES(dateDepot),
            StatutCandidature = VALUES(StatutCandidature),
            idDiplomé = VALUES(idDiplomé),
            idPublication = VALUES(idPublication)
        """;

    private static final String SQL_FIND_ID =
        "SELECT * FROM Candidature WHERE idCandidature = ?";

    private static final String SQL_FIND_ALL =
        "SELECT * FROM Candidature ORDER BY dateDepot DESC";

    private static final String SQL_FIND_BY_PUBLICATION =
        "SELECT * FROM Candidature WHERE idPublication = ?";

    private static final String SQL_FIND_BY_DIPLOME =
        "SELECT * FROM Candidature WHERE idDiplomé = ?";

    private static final String SQL_EXISTS_DIP_PUB =
        "SELECT 1 FROM Candidature WHERE idDiplomé = ? AND idPublication = ?";

    private static final String SQL_EXISTS =
        "SELECT 1 FROM Candidature WHERE idCandidature = ?";
    
    private static final String SQL_FIND_BY_OFFRE =
    	    "SELECT * FROM Candidature WHERE idPublication = ?";

    private static final String SQL_DELETE =
        "DELETE FROM Candidature WHERE idCandidature = ?";

    /**
     * Pièces jointes d'une candidature : la table {@code Candidature} ne porte
     * aucune colonne {@code Documents}, la liaison se fait par
     * {@code piece_a_joindre}.
     */
    private static final String SQL_FIND_DOCUMENTS = """
        SELECT d.idDocument, d.cheminFichier, d.idDiplomé, d.idTypedocument
        FROM piece_a_joindre p
        JOIN Document d ON d.idDocument = p.idDocument
        WHERE p.idCandidature = ?
        ORDER BY d.idTypedocument, d.idDocument
        """;

    // ============ MÉTHODES DU PORT ============

    /**
     * Mappe une ligne {@code Candidature} puis recharge ses pièces jointes.
     * La lecture des documents est faite ici (et non dans le mapper) car elle
     * nécessite une seconde requête.
     */
    private Candidature mapper(ResultSet rs) throws SQLException {
        Candidature candidature = RecrutementRowMappers.CANDIDATURE.map(rs);
        List<fonea.ped.domain.recrutement.Document> documents =
                documentsDe(candidature.getIdCandidature());
        return new Candidature(
                candidature.getIdCandidature(),
                candidature.getDateDepot(),
                candidature.getStatutCandidature(),
                candidature.getIdDiplome(),
                candidature.getIdPublication(),
                documents);
    }

    private List<fonea.ped.domain.recrutement.Document> documentsDe(String idCandidature) {
        List<fonea.ped.domain.recrutement.Document> documents = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_DOCUMENTS)) {

            ps.setString(1, idCandidature);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    documents.add(RecrutementRowMappers.DOCUMENT.map(rs));
                }
            }
            return documents;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur JDBC chargement des documents de la candidature", e);
        }
    }

    @Override
    public Candidature sauvegarder(Candidature candidature) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {

            ps.setString(1, candidature.getIdCandidature());

            if (candidature.getDateDepot() != null) {
                ps.setDate(2, Date.valueOf(candidature.getDateDepot()));
            } else {
                ps.setNull(2, Types.DATE);
            }

            ps.setString(3, candidature.getStatutCandidature().name());
            ps.setString(4, candidature.getIdDiplome());
            ps.setString(5, candidature.getIdPublication());

            ps.executeUpdate();
            return candidature;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Candidature", e);
        }
    }
    
    
    @Override
    public List<Candidature> trouverParOffre(String idPublication) {
        List<Candidature> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_OFFRE)) {

            ps.setString(1, idPublication);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultats.add(mapper(rs));
                }
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParOffre Candidature", e);
        }
    }

    @Override
    public Optional<Candidature> trouverParId(String idCandidature) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {

            ps.setString(1, idCandidature);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.of(mapper(rs))
                        : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Candidature", e);
        }
    }

    @Override
    public List<Candidature> trouverToutes() {
        List<Candidature> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultats.add(RecrutementRowMappers.CANDIDATURE.map(rs));
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverToutes Candidature", e);
        }
    }

    @Override
    public List<Candidature> trouverParPublication(String idPublication) {
        return trouverListe(SQL_FIND_BY_PUBLICATION, idPublication);
    }

    @Override
    public List<Candidature> candidaturesParDiplome(String idDiplome) {
        return trouverListe(SQL_FIND_BY_DIPLOME, idDiplome);
    }

    private List<Candidature> trouverListe(String sql, String param) {
        List<Candidature> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultats.add(mapper(rs));
                }
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC find Candidature", e);
        }
    }

    @Override
    public boolean existeParDiplomeEtPublication(String idDiplome, String idPublication) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS_DIP_PUB)) {

            ps.setString(1, idDiplome);
            ps.setString(2, idPublication);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParDiplomeEtPublication Candidature", e);
        }
    }

    @Override
    public boolean existeParId(String idCandidature) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {

            ps.setString(1, idCandidature);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Candidature", e);
        }
    }

    @Override
    public void supprimer(String idCandidature) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setString(1, idCandidature);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC supprimer Candidature", e);
        }
    }

    @Override
    public String prochainId() {
        return "CND-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
