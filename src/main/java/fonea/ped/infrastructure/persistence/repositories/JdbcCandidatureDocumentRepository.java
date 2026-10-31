package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fonea.ped.domain.recrutement.CandidatureDocument;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RowMapper;
import fonea.ped.useCase.useCaseRecrutement.ports.CandidatureDocumentRepository;

/**
 * Implementation JDBC du port CandidatureDocumentRepository (table piece_a_joindre).
 */
public class JdbcCandidatureDocumentRepository implements CandidatureDocumentRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO piece_a_joindre (idCandidature, idDocument) VALUES (?, ?)";
    private static final String SQL_FIND_CANDIDATURE =
        "SELECT idCandidature, idDocument FROM piece_a_joindre WHERE idCandidature = ?";
    private static final String SQL_FIND_DOCUMENT =
        "SELECT idCandidature, idDocument FROM piece_a_joindre WHERE idDocument = ?";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM piece_a_joindre WHERE idCandidature = ? AND idDocument = ?";
    private static final String SQL_DELETE =
        "DELETE FROM piece_a_joindre WHERE idCandidature = ? AND idDocument = ?";

    private static final RowMapper<CandidatureDocument> MAPPER = rs -> new CandidatureDocument(
            rs.getString("idCandidature"),
            rs.getString("idDocument")
    );

    @Override
    public CandidatureDocument sauvegarder(CandidatureDocument liaison) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, liaison.getIdCandidature());
            ps.setString(2, liaison.getIdDocument());
            ps.executeUpdate();
            return liaison;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder CandidatureDocument", e);
        }
    }

    @Override
    public List<CandidatureDocument> trouverParCandidature(String idCandidature) {
        return trouver(SQL_FIND_CANDIDATURE, idCandidature);
    }

    @Override
    public List<CandidatureDocument> trouverParDocument(String idDocument) {
        return trouver(SQL_FIND_DOCUMENT, idDocument);
    }

    private List<CandidatureDocument> trouver(String sql, String param) {
        List<CandidatureDocument> resultats = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultats.add(MAPPER.map(rs));
            }
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouver CandidatureDocument", e);
        }
    }

    @Override
    public boolean existe(String idCandidature, String idDocument) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idCandidature);
            ps.setString(2, idDocument);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existe CandidatureDocument", e);
        }
    }

    @Override
    public void supprimer(String idCandidature, String idDocument) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setString(1, idCandidature);
            ps.setString(2, idDocument);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC supprimer CandidatureDocument", e);
        }
    }
}
