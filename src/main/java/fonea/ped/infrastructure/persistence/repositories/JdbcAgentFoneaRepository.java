package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.securite.AgentFonea;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.SecuriteRowMappers;
import fonea.ped.useCase.useCaseSecurite.AgentFoneaRepository;

import java.sql.*;
import java.util.*;

public class JdbcAgentFoneaRepository implements AgentFoneaRepository {

    private static final String SQL_SAVE = """
        INSERT INTO AgentFonea (idAgent, nom, prenom, fonction, idUtilisateur)
        VALUES (?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE nom = VALUES(nom), prenom = VALUES(prenom),
                                fonction = VALUES(fonction), idUtilisateur = VALUES(idUtilisateur)
        """;
    private static final String SQL_FIND_ID = "SELECT * FROM AgentFonea WHERE idAgent = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM AgentFonea ORDER BY nom";
    private static final String SQL_EXISTS = "SELECT 1 FROM AgentFonea WHERE idAgent = ?";

    @Override
    public AgentFonea sauvegarder(AgentFonea a) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, a.getIdAgent());
            ps.setString(2, a.getNom());
            ps.setString(3, a.getPrenom());
            ps.setString(4, a.getFonction());
            ps.setString(5, a.getIdUtilisateur());
            ps.executeUpdate();
            return a;
        } catch (SQLException e) { throw new RuntimeException("JDBC Agent save", e); }
    }

    @Override
    public Optional<AgentFonea> trouverParId(String id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(SecuriteRowMappers.AGENT.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Agent find", e); }
    }

    @Override
    public List<AgentFonea> trouverTous() {
        List<AgentFonea> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(SecuriteRowMappers.AGENT.map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC Agent findAll", e); }
    }

    @Override
    public boolean existeParId(String id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC Agent exists", e); }
    }

    @Override
    public String prochainId() {
        return "AGT-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
