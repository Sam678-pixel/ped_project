package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.recrutement.Document;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RecrutementRowMappers;
import fonea.ped.useCase.useCaseRecrutement.ports.DocumentRepository;

import java.sql.*;
import java.util.*;

/**
 * Implémentation JDBC du port DocumentRepository.
 * SQL MySQL uniquement.
 */
public class JdbcDocumentRepository implements DocumentRepository {

    // ============ SQL ============

    private static final String SQL_SAVE = """
        INSERT INTO Document (idDocument, cheminFichier, idDiplomé, idTypedocument)
        VALUES (?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            cheminFichier = VALUES(cheminFichier),
            idDiplomé = VALUES(idDiplomé),
            idTypedocument = VALUES(idTypedocument)
        """;

    private static final String SQL_FIND_ID =
        "SELECT * FROM Document WHERE idDocument = ?";

    private static final String SQL_FIND_BY_DIPLOME =
        "SELECT * FROM Document WHERE idDiplomé = ?";

    private static final String SQL_FIND_BY_TYPE =
        "SELECT * FROM Document WHERE idTypedocument = ?";

    private static final String SQL_EXISTS =
        "SELECT 1 FROM Document WHERE idDocument = ?";

    private static final String SQL_DELETE =
        "DELETE FROM Document WHERE idDocument = ?";

    // ============ MÉTHODES DU PORT ============

    @Override
    public Document sauvegarder(Document document) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {

            ps.setString(1, document.getIdDocument());
            ps.setString(2, document.getCheminFichier());
            ps.setString(3, document.getIdDiplome());
            ps.setString(4, document.getIdTypeDocument());

            ps.executeUpdate();
            return document;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Document", e);
        }
    }

    @Override
    public Optional<Document> trouverParId(String idDocument) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {

            ps.setString(1, idDocument);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.of(RecrutementRowMappers.DOCUMENT.map(rs))
                        : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Document", e);
        }
    }

    @Override
    public List<Document> trouverParDiplome(String idDiplome) {
        return trouverListe(SQL_FIND_BY_DIPLOME, idDiplome);
    }

    @Override
    public List<Document> trouverParType(String idTypeDocument) {
        return trouverListe(SQL_FIND_BY_TYPE, idTypeDocument);
    }

    private List<Document> trouverListe(String sql, String param) {
        List<Document> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultats.add(RecrutementRowMappers.DOCUMENT.map(rs));
                }
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC find Document", e);
        }
    }

    @Override
    public boolean existeParId(String idDocument) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {

            ps.setString(1, idDocument);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Document", e);
        }
    }

    @Override
    public void supprimer(String idDocument) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setString(1, idDocument);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC supprimer Document", e);
        }
    }

    @Override
    public String prochainId() {
        return "DOC-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
