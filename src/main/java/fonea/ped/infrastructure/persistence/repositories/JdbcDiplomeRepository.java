package fonea.ped.infrastructure.persistence.repositories;


import fonea.ped.domain.diplome.Diplome;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.DiplomeRowMappers;
import fonea.ped.useCase.useCaseDiplome.port.DiplomeRepository;

import java.sql.*;
import java.util.*;

public  class JdbcDiplomeRepository implements DiplomeRepository {

    private static final String SQL_SAVE = """
        INSERT INTO `Diplomé` (`idDiplomé`, nom, prenom, dateNaissance, sexe,
                               niveauDiplome, `annéeOptention`, Filiere, idUtilisateur)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE nom = VALUES(nom), prenom = VALUES(prenom),
                                dateNaissance = VALUES(dateNaissance), sexe = VALUES(sexe),
                                niveauDiplome = VALUES(niveauDiplome),
                                `annéeOptention` = VALUES(`annéeOptention`),
                                Filiere = VALUES(Filiere), idUtilisateur = VALUES(idUtilisateur)
        """;
    private static final String SQL_FIND_ID = "SELECT * FROM `Diplomé` WHERE `idDiplomé` = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM `Diplomé` ORDER BY nom";
    private static final String SQL_EXISTS = "SELECT 1 FROM `Diplomé` WHERE `idDiplomé` = ?";

    @Override
    public Diplome sauvegarder(Diplome d) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, d.getIdDiplome());
            ps.setString(2, d.getNom());
            ps.setString(3, d.getPrenom());
            if (d.getDateNaissance() != null)
                ps.setDate(4, java.sql.Date.valueOf(d.getDateNaissance()));
            else ps.setNull(4, Types.DATE);
            ps.setString(5, d.getSexe());
            ps.setString(6, d.getNiveauDiplome());
            ps.setString(7, d.getAnneeObtention());
            ps.setString(8, d.getFiliere());
            ps.setString(9, d.getIdUtilisateur());
            ps.executeUpdate();
            return d;
        } catch (SQLException e) { throw new RuntimeException("JDBC Diplome save", e); }
    }

    @Override
    public Optional<Diplome> trouverParId(String id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(DiplomeRowMappers.DIPLOME.map(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException("JDBC Diplome find", e); }
    }

    @Override
    public List<Diplome> trouverTous() {
        List<Diplome> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(DiplomeRowMappers.DIPLOME.map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC Diplome findAll", e); }
    }

    @Override
    public boolean existeParId(String id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC Diplome exists", e); }
    }

    @Override
    public String prochainId() {
        return "DPL-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
