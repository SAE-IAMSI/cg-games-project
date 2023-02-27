package games.project.factory_fall.stockage.sql;

import games.project.factory_fall.logique.AuthPlayer;
import games.project.factory_fall.stockage.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe opère toutes les requêtes à effectuer dans la base de données relevant des joueurs.
 * Elle suit le modèle CRUD pour les requêtes, en plus de requêtes personnalisées.
 *
 * <p> Les requêtes sont préparées pour éviter les injections SQL.
 *
 * @see factoryfall.stockage.PlayerManager
 */
public class StockagePlayerDatabase {

    public void create(AuthPlayer element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "INSERT INTO JOUEURS(login, mdphache, selHachage, numDepartement) VALUES (?, ?, ?, ?)";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, element.getLogin());
            st.setString(2, element.getHashedPassword());
            st.setBytes(3, element.getSalt());
            st.setString(4, element.getDepartement());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(AuthPlayer element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE JOUEURS SET mdpHache = ?, selHachage = ?, numDepartement = ? WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(4, element.getLogin());
            st.setString(1, element.getHashedPassword());
            st.setBytes(2, element.getSalt());
            st.setString(3, element.getDepartement());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByLogin(String login) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "DELETE FROM JOUEURS WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public AuthPlayer getByLogin(String login) {
        AuthPlayer player = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT login, mdpHache, selHachage,numDepartement FROM JOUEURS" +
                " WHERE JOUEURS.login = ? ";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    String password = result.getString("mdpHache");
                    byte[] salt = result.getBytes("selHachage");
                    String numDepartement = result.getString("numDepartement");

                    player = new AuthPlayer(login, numDepartement, password, salt);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    public List<AuthPlayer> getAll() {
        List<AuthPlayer> playerList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT login, mdpHache, selHachage,numDepartement FROM JOUEURS ";

        try (
                PreparedStatement st = connection.prepareStatement(req);
                ResultSet result = st.executeQuery()
        ) {
            while (result.next()) {
                String login = result.getString("login");
                String password = result.getString("mdpHache");
                byte[] salt = result.getBytes("selHachage");
                String numdepartement = result.getString("numDepartement");
                AuthPlayer player = new AuthPlayer(login, numdepartement, password, salt);

                playerList.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerList;
    }


}