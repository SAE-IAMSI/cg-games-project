package games.project.motron.stockage.sql;

import games.project.motron.metier.entite.AuthPlayer;
import games.project.motron.metier.entite.Player;
import games.project.motron.metier.entite.Score;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockagePlayerDatabase implements Stockage<AuthPlayer> {
    @Override
    public void create(AuthPlayer element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "INSERT INTO JOUEURS(login, mdpHache, selHachage, numDepartement) VALUES (?, ?, ?, ?)";
        try (
                PreparedStatement st = connection.prepareStatement(req);
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

    @Override
    public void update(AuthPlayer element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE JOUEURS SET mdpHache = ?, selHachage = ?, numDepartement = ? WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
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
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AuthPlayer getByLogin(String login) {
        AuthPlayer player = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM JOUEURS WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery();) {
                if (result.next()) {
                    String password = result.getString("mdpHache");
                    byte[] salt = result.getBytes("selHachage");
                    player = new AuthPlayer(login, password, salt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    @Override
    public List<AuthPlayer> getAll() {
        List<AuthPlayer> playerList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM JOUEURS";
        try (
                PreparedStatement st = connection.prepareStatement(req);
                ResultSet result = st.executeQuery();
        ) {
            while (result.next()) {
                String login = result.getString("login");
                String password = result.getString("mdpHache");
                byte[] salt = result.getBytes("selHachage");
                AuthPlayer player = new AuthPlayer(login, password, salt);
                playerList.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerList;
    }

    public List<Player> getMeilleurs() {
        List<Player> playerList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT DISTINCT login, score FROM (SELECT login, score FROM Scores WHERE codeJeu = ? ORDER BY score DESC) WHERE ROWNUM <= 10 ORDER BY score DESC";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, Score.getGameCode());
            try (ResultSet result = st.executeQuery();) {
                while (result.next()) {
                    String login = result.getString("login");
                    int scoreValue = result.getInt("score");
                    Player player = new Player(login);
                    player.setScore(scoreValue);
                    playerList.add(player);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerList;
    }

    public int getNbPartie(String login){
        int nbPartie = 0;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT COUNT(*) AS \"nbParties\" FROM SCORES_TRON " +
                "WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery();) {
                while (result.next()) {
                    nbPartie = result.getInt("nbParties");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return nbPartie;
    }

    public String getDepByLogin(String login){
        String dep = "";
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT DISTINCT nomdepartement FROM Departements d JOIN Joueurs j ON d.numdepartement = j.numdepartement " +
                "WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery();) {
                while (result.next()) {
                    dep = result.getString("nomdepartement");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return dep;
    }
}