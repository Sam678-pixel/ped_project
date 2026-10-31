package fonea.ped.infrastructure.persistence.repositories;


import java.sql.*;
import java.util.*;

import fonea.ped.domain.MiseEnStage.DecrochageMotif;
import fonea.ped.infrastructure.persistence.config.DatabaseConnection;
import fonea.ped.infrastructure.persistence.mappers.LiaisonRowMappers;
import fonea.ped.useCase.useCaseMiseEnStage.ports.DecrochageMotifRepository;

public class JdbcDecrochageMotifRepository implements DecrochageMotifRepository {

    private static final String SQL_SAVE =
        "INSERT IGNORE INTO est_justifie_par (idDecrochage, idMotifDecrochage) VALUES (?, ?)";
    private static final String SQL_FIND_DEC =
        "SELECT * FROM est_justifie_par WHERE idDecrochage = ?";
    private static final String SQL_FIND_MOT =
        "SELECT * FROM est_justifie_par WHERE idMotifDecrochage = ?";
    private static final String SQL_EXISTS =
        "SELECT 1 FROM est_justifie_par WHERE idDecrochage = ? AND idMotifDecrochage = ?";
    private static final String SQL_DELETE =
        "DELETE FROM est_justifie_par WHERE idDecrochage = ? AND idMotifDecrochage = ?";

    @Override public DecrochageMotif sauvegarder(DecrochageMotif l) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_SAVE)) {
            ps.setString(1, l.getIdDecrochage());
            ps.setString(2, l.getIdMotifDecrochage());
            ps.executeUpdate();
            return l;
        } catch (SQLException e) { throw new RuntimeException("JDBC DecrochageMotif save", e); }
    }

    @Override public List<DecrochageMotif> trouverParDecrochage(String id) { return trouver(SQL_FIND_DEC, id); }
    @Override public List<DecrochageMotif> trouverParMotif(String id) { return trouver(SQL_FIND_MOT, id); }
    private List<DecrochageMotif> trouver(String sql, String p) {
        List<DecrochageMotif> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(LiaisonRowMappers.DECROCHAGE_MOTIF.map(rs));
            }
            return list;
        } catch (SQLException e) { throw new RuntimeException("JDBC DecrochageMotif find", e); }
    }

    @Override public boolean existe(String idD, String idM) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_EXISTS)) {
            ps.setString(1, idD); ps.setString(2, idM);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException("JDBC DecrochageMotif exists", e); }
    }

    @Override public void supprimer(String idD, String idM) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SQL_DELETE)) {
            ps.setString(1, idD); ps.setString(2, idM);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("JDBC DecrochageMotif delete", e); }
    }
}
