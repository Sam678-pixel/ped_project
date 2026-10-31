package fonea.ped.infrastructure.persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fonea.ped.domain.recrutement.TypeDocument;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RowMapper;
import fonea.ped.useCase.useCaseRecrutement.ports.TypeDocumentRepository;

/**
 * Implementation JDBC du port TypeDocumentRepository (table TypeDocument).
 */
public class JdbcTypeDocumentRepository implements TypeDocumentRepository {

    private static final String SQL_SAVE = """
        INSERT INTO TypeDocument (idTypedocument, libelle)
        VALUES (?, ?)
        ON DUPLICATE KEY UPDATE libelle = VALUES(libelle)
        """;
    private static final String SQL_FIND_ID = """
        SELECT idTypedocument, libelle FROM TypeDocument WHERE idTypedocument = ?
        """;
    private static final String SQL_FIND_ALL = """
        SELECT idTypedocument, libelle FROM TypeDocument ORDER BY libelle
        """;
    private static final String SQL_EXISTS = """
        SELECT 1 FROM TypeDocument WHERE idTypedocument = ?
        """;

    private static final RowMapper<TypeDocument> MAPPER = rs ->
            new TypeDocument(rs.getString("idTypedocument"), rs.getString("libelle"));

    @Override
    public TypeDocument sauvegarder(TypeDocument typeDocument) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, typeDocument.getIdTypeDocument());
            ps.setString(2, typeDocument.getLibelle());
            ps.executeUpdate();
            return typeDocument;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder TypeDocument", e);
        }
    }

    @Override
    public Optional<TypeDocument> trouverParId(String idTypeDocument) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, idTypeDocument);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(MAPPER.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId TypeDocument", e);
        }
    }

    @Override
    public List<TypeDocument> trouverTous() {
        List<TypeDocument> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) resultats.add(MAPPER.map(rs));
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverTous TypeDocument", e);
        }
    }

    @Override
    public boolean existeParId(String idTypeDocument) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idTypeDocument);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId TypeDocument", e);
        }
    }

    @Override
    public String prochainId() {
        return "TDOC-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
