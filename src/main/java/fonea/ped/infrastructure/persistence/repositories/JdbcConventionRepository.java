package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.convention.model.Convention;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.ConventionRowMappers;
import fonea.ped.useCase.UseCaseConvention.ports.ConventionRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JdbcConventionRepository implements ConventionRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Convention (idConvention, dateEffet, tauxFonea, tauxEntreprise, SatutConvention, TypeConvention, idEntreprise)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            dateEffet = VALUES(dateEffet),
            tauxFonea = VALUES(tauxFonea),
            tauxEntreprise = VALUES(tauxEntreprise),
            SatutConvention = VALUES(SatutConvention),
            TypeConvention = VALUES(TypeConvention),
            idEntreprise = VALUES(idEntreprise)
        """;
    private static final String SQL_FIND_ID =
        "SELECT * FROM Convention WHERE idConvention = ?";
    private static final String SQL_FIND_ALL =
        "SELECT * FROM Convention ORDER BY dateEffet DESC";
    private static final String SQL_FIND_BY_ENTREPRISE =
        "SELECT * FROM Convention WHERE idEntreprise = ? ORDER BY dateEffet DESC";
    private static final String SQL_FIND_ACTIVES =
        "SELECT * FROM Convention WHERE SatutConvention = 'ACTIVE'";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM Convention WHERE idConvention = ?";
    private static final String SQL_DELETE =
        "DELETE FROM Convention WHERE idConvention = ?";

    @Override
    public Convention sauvegarder(Convention c) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE)) {
            ps.setString(1, c.getIdConvention());
            if (c.getDateEffet() != null) ps.setDate(2, Date.valueOf(c.getDateEffet()));
            else ps.setNull(2, Types.DATE);
            if (c.getTauxFonea() != null) ps.setDouble(3, c.getTauxFonea());
            else ps.setNull(3, Types.DECIMAL);
            if (c.getTauxEntreprise() != null) ps.setDouble(4, c.getTauxEntreprise());
            else ps.setNull(4, Types.DECIMAL);
            ps.setString(5, c.getStatutConvention()!=null ? c.getStatutConvention().name():null);
            ps.setString(6, c.getTypeConvention().getIdTypeConvention());
            ps.setString(7, c.getIdEntreprise());
            ps.executeUpdate();
            return c;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC sauvegarder Convention", e);
        }
    }

    @Override
    public Optional<Convention> trouverParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(ConventionRowMappers.CONVENTION.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParId Convention", e);
        }
    }

    @Override
    public List<Convention> trouverToutes() {
        List<Convention> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(ConventionRowMappers.CONVENTION.map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverToutes Convention", e);
        }
    }

    @Override
    public List<Convention> trouverParEntreprise(String idEntreprise) {
        List<Convention> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ENTREPRISE)) {
            ps.setString(1, idEntreprise);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(ConventionRowMappers.CONVENTION.map(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverParEntreprise Convention", e);
        }
    }

    @Override
    public List<Convention> trouverActives() {
        List<Convention> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ACTIVES);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(ConventionRowMappers.CONVENTION.map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC trouverActives Convention", e);
        }
    }

    @Override
    public boolean existeParId(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC existeParId Convention", e);
        }
    }

    @Override
    public void supprimer(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC supprimer Convention", e);
        }
    }

    @Override
    public String prochainId() {
        return "CNV-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
