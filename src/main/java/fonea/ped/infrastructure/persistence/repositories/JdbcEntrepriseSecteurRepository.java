package fonea.ped.infrastructure.persistence.repositories;


import fonea.ped.domain.entreprise.EntrepriseSecteur;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.LiaisonRowMappers;
import fonea.ped.useCase.useCaseOffres.ports.EntrepriseSecteurRepository;

import java.sql.*;
import java.util.*;

public class JdbcEntrepriseSecteurRepository implements EntrepriseSecteurRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO appartient_a (idEntreprise, idSecteur) VALUES (?, ?)";
    private static final String SQL_FIND_ENT =
        "SELECT * FROM appartient_a WHERE idEntreprise = ?";
    private static final String SQL_FIND_SEC =
        "SELECT * FROM appartient_a WHERE idSecteur = ?";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM appartient_a WHERE idEntreprise = ? AND idSecteur = ?";
    private static final String SQL_DELETE =
        "DELETE FROM appartient_a WHERE idEntreprise = ? AND idSecteur = ?";

    @Override public EntrepriseSecteur sauvegarder(EntrepriseSecteur l) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, l.getIdEntreprise());
            ps.setString(2, l.getIdSecteur());
            ps.executeUpdate();
            return l;
        } catch (SQLException e) { throw new RuntimeException("JDBC EntrepriseSecteur save", e); }
    }

    @Override public List<EntrepriseSecteur> trouverParEntreprise(String id) { return trouver(SQL_FIND_ENT, id); }
    @Override public List<EntrepriseSecteur> trouverParSecteur(String id) { return trouver(SQL_FIND_SEC, id); }
    private List<EntrepriseSecteur> trouver(String sql, String p) {
        List<EntrepriseSecteur> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(LiaisonRowMappers.ENTREPRISE_SECTEUR.map(rs));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC EntrepriseSecteur find", e); }
    }

    @Override public boolean existe(String idE, String idS) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idE); ps.setString(2, idS);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC EntrepriseSecteur exists", e); }
    }

    @Override public void supprimer(String idE, String idS) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setString(1, idE); ps.setString(2, idS);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC EntrepriseSecteur delete", e); }
    }
}

