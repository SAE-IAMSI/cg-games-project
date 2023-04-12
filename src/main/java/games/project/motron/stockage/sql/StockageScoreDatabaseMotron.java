package games.project.motron.stockage.sql;

import games.project.stockage.sql.StockageScoreDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockageScoreDatabaseMotron extends StockageScoreDatabase {

    public int getSommeScore(String login) {
        int score = 0;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT Sum(score) FROM SCORES WHERE login = ? AND codeJeu = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            st.setString(2, "TRON");
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    score = result.getInt("Sum(score)");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    public int highScore(String login) {
        int score = 0;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT MAX(score) FROM SCORES\n" +
                "WHERE login = ? AND codeJeu = ?" +
                "GROUP BY codeJeu";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            st.setString(2, "TRON");
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    score = result.getInt("MAX(score)");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    public int getMaxId() {
        int codeScore = 0;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT MAX(codeScore) AS MAXSCORE FROM SCORES";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    codeScore = result.getInt("MAXSCORE");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codeScore;
    }

}