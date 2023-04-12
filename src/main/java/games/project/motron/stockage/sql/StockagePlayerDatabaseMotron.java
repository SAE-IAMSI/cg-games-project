package games.project.motron.stockage.sql;

import games.project.metier.entite.Player;
import games.project.metier.entite.Score;
import games.project.stockage.sql.StockagePlayerDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockagePlayerDatabaseMotron extends StockagePlayerDatabase {

    public List<Player> getMeilleurs() {
        List<Player> playerList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT DISTINCT login, score FROM (SELECT login, score FROM Scores WHERE codeJeu = ? ORDER BY score DESC) WHERE ROWNUM <= 10 ORDER BY score DESC";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, "TRON");
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    String login = result.getString("login");
                    int scoreValue = result.getInt("score");
                    Player player = new Player(login, new Score("TRON"));
                    player.setScore(scoreValue);
                    playerList.add(player);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerList;
    }

    public int getNbPartie(String login) {
        int nbPartie = 0;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT COUNT(*) AS \"nbParties\" FROM SCORES " +
                "WHERE login = ?" +
                "AND codeJeu = 'TRON'";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    nbPartie = result.getInt("nbParties");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nbPartie;
    }

    public String getDepByLogin(String login) {
        String dep = "";
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT DISTINCT nomdepartement FROM Departements d JOIN Joueurs j ON d.numdepartement = j.numdepartement " +
                "WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    dep = result.getString("nomdepartement");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dep;
    }
}