package fonea.ped.infrastructure.persistence.repositories;

import fonea.ped.domain.entreprise.Entreprise;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.EntrepriseRowMappers;
import fonea.ped.useCase.useCaseEntreprise.port.EntrepriseRepository;

import java.sql.*;
import java.util.*;

public class JdbcEntrepriseRepository implements EntrepriseRepository {

    private static final String SQL_SAVE = """
        INSERT INTO Entreprise (idEntreprise, raisonSociale, adresse, niu, idUtilisateur)
        VALUES (?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE raisonSociale = VALUES(raisonSociale), adresse = VALUES(adresse),
                                niu = VALUES(niu), idUtilisateur = VALUES(idUtilisateur)
        """;
    private static final String SQL_FIND_ID = "SELECT * FROM Entreprise WHERE idEntreprise = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM Entreprise ORDER BY raisonSociale";
    private static final String SQL_EXISTS = "SELECT 1 FROM Entreprise WHERE idEntreprise = ?";

    @Override
    public Entreprise sauvegarder(Entreprise e) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, e.getIdEntreprise());
            ps.setString(2, e.getRaisonSociale());
            ps.setString(3, e.getAdresse());
            ps.setString(4, e.getNiu());
            ps.setString(5, e.getIdUtilisateur());
            ps.executeUpdate();
            return e;
        } catch (SQLException ex) { throw new RuntimeException("JDBC Entreprise save", ex); }
    }

    @Override
    public Optional<Entreprise> trouverParId(String id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(EntrepriseRowMappers.ENTREPRISE.map(rs)) : Optional.empty();
            }
        } catch (SQLException ex) { throw new RuntimeException("JDBC Entreprise find", ex); }
    }

    @Override
    public List<Entreprise> trouverToutes() {
        List<Entreprise> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(EntrepriseRowMappers.ENTREPRISE.map(rs));
            return list;
        } catch (SQLException ex) { throw new RuntimeException("JDBC Entreprise findAll", ex); }
    }

    @Override
    public boolean existeParId(String id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException ex) { throw new RuntimeException("JDBC Entreprise exists", ex); }
    }

    @Override
    public String prochainId() {
        return "ENT-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
