package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.recrutement.Selection;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.RecrutementRowMappers;
import fonea.ped.useCase.useCaseRecrutement.ports.SelectionRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Implémentation JDBC du port SelectionRepository.
 * SQL MySQL uniquement.
 */
public class JdbcSelectionRepository implements SelectionRepository {

    // ============ SQL ============

    private static final String SQL_SAVE = """
        INSERT INTO Selection (idSelection, dateSelection, StatutSelection, idPreselection)
        VALUES (?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            dateSelection = VALUES(dateSelection),
            StatutSelection = VALUES(StatutSelection),
            idPreselection = VALUES(idPreselection)
        """;

    private static final String SQL_FIND_ID =
        "SELECT * FROM Selection WHERE idSelection = ?";

    private static final String SQL_FIND_ALL =
        "SELECT * FROM Selection ORDER BY dateSelection DESC";

    private static final String SQL_FIND_BY_PRESELECTION =
        "SELECT * FROM Selection WHERE idPreselection = ?";

    private static final String SQL_EXISTS_PRESEL =
        "SELECT 1 FROM Selection WHERE idPreselection = ?";

    private static final String SQL_EXISTS =
        "SELECT 1 FROM Selection WHERE idSelection = ?";

    private static final String SQL_DELETE =
        "DELETE FROM Selection WHERE idSelection = ?";

    // ============ MÉTHODES DU PORT ============

    @Override
    public Selection sauvegarder(Selection selection) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {

            ps.setString(1, selection.getIdSelection());

            if (selection.getDateSelection() != null) {
                ps.setDate(2, Date.valueOf(selection.getDateSelection()));
            } else {
                ps.setNull(2, Types.DATE);
            }

            ps.setString(3, selection.getStatutSelection().name());
            ps.setString(4, selection.getIdPreselection());

            ps.executeUpdate();
            return selection;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Selection", e);
        }
    }

    @Override
    public Optional<Selection> trouverParId(String idSelection) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {

            ps.setString(1, idSelection);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.of(RecrutementRowMappers.SELECTION.map(rs))
                        : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Selection", e);
        }
    }

    @Override
    public List<Selection> trouverToutes() {
        List<Selection> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultats.add(RecrutementRowMappers.SELECTION.map(rs));
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverToutes Selection", e);
        }
    }

    @Override
    public List<Selection> trouverParPreselection(String idPreselection) {
        List<Selection> resultats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_PRESELECTION)) {

            ps.setString(1, idPreselection);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultats.add(RecrutementRowMappers.SELECTION.map(rs));
                }
            }
            return resultats;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParPreselection Selection", e);
        }
    }

    @Override
    public boolean existeParPreselection(String idPreselection) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS_PRESEL)) {

            ps.setString(1, idPreselection);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParPreselection Selection", e);
        }
    }

    @Override
    public boolean existeParId(String idSelection) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {

            ps.setString(1, idSelection);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Selection", e);
        }
    }

    @Override
    public void supprimer(String idSelection) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setString(1, idSelection);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC supprimer Selection", e);
        }
    }

    @Override
    public String prochainId() {
        return "SEL-" + UUID.randomUUID().toString().substring(0, 8);
    }
}