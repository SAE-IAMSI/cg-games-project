package games.project.stockage.sql;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Jeu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StockageJeuDatabase {

    public void create(Jeu element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "INSERT INTO JEU(code, libelle, path) VALUES (?, ?, ?)";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, element.getCode());
            if (!element.getLibelle().isEmpty()) st.setString(2, element.getLibelle());
            else st.setNull(2, Types.VARCHAR);
            if (!element.getPath().isEmpty()) st.setString(3, element.getPath());
            else st.setNull(3, Types.VARCHAR);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Jeu element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE JEU SET libelle = ? WHERE code = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, element.getLibelle());
            st.setString(2, element.getCode());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String codeJeu) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "DELETE FROM JEU WHERE code = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, codeJeu);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Jeu> getAll() {
        List<Jeu> jeux = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM JEU";
        try (
                PreparedStatement st = connection.prepareStatement(req);
                ResultSet result = st.executeQuery()
        ) {
            while (result.next()) {
                String code = result.getString("code");
                String libelle = result.getString("libelle");
                String path = result.getString("path");
                Jeu jeu = new Jeu(code, libelle, path);
                jeux.add(jeu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jeux;
    }

    public Jeu getByCode(int code) {
        Jeu jeu = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM JEU WHERE code = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setInt(1, code);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    String codeJeu = result.getString("code");
                    String libelle = result.getString("libelle");
                    String path = result.getString("path");
                    jeu = new Jeu(codeJeu, libelle, path);
                    jeu.setCode(codeJeu);
                    jeu.setLibelle(libelle);
                    jeu.setLibelle(path);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jeu;
    }
}