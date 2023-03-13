package games.project.motron.stockage.sql;

import games.project.motron.metier.entite.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockageScoreDatabase implements Stockage<Score> {

    @Override
    public void create(Score element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "INSERT INTO SCORES(score, horodatage, codeJeu, login) VALUES (?, ?, ?, ?)";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setInt(1, element.getScore());
            st.setTimestamp(2, element.getHorodatage());
            st.setString(3, element.getGameCode());
            if (!element.getLogin().isEmpty()) st.setString(4, element.getLogin());
            else st.setNull(4, Types.VARCHAR);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Score element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE SCORES SET score = ? WHERE codeScore = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setInt(1, element.getScore());
            st.setInt(3, element.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByLogin(String login) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "DELETE FROM SCORES WHERE login = ? AND codeJeu = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            st.setString(2, Score.getGameCode());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "DELETE FROM SCORES WHERE codeScore = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Score getById(int id) {
        Score score = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE codeScore = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setInt(1, id);
            try (ResultSet result = st.executeQuery();) {
                if (result.next()) {
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    String login = result.getString("login");
                    score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    @Override
    public Score getByLogin(String login) {
        Score score = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE login = ? AND codeJeu = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            st.setString(2, Score.getGameCode());
            try (ResultSet result = st.executeQuery();) {
                if (result.next()) {
                    int id = result.getInt("codeScore");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    public Score getByLoginRecent(String login) {
        Score score = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT Max(codeScore), score, horodatage FROM SCORES WHERE login = ? AND codeJeu = ? GROUP BY codeScore,score, horodatage ORDER BY CODESCORE DESC";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            st.setString(2, Score.getGameCode());
            try (ResultSet result = st.executeQuery();) {
                if (result.next()) {
                    int id = result.getInt("max(codeScore)");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }


    @Override
    public List<Score> getAll() {
        List<Score> scoreList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE codeJeu = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, Score.getGameCode());
            try (ResultSet result = st.executeQuery();) {
                while (result.next()) {
                    int id = result.getInt("codeScore");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    String login = result.getString("login");
                    Score score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                    scoreList.add(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scoreList;
    }

    public int getSommeScore(String login) {
        int score = 0;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT MAX(score) FROM SCORES\n" +
                "WHERE login = ? AND codeJeu = ?" +
                "GROUP BY codeJeu";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            st.setString(2, Score.getGameCode());
            try (ResultSet result = st.executeQuery();) {
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
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            st.setString(1, login);
            st.setString(2, Score.getGameCode());
            try (ResultSet result = st.executeQuery();) {
                if (result.next()) {
                    score = result.getInt("MAX(score)");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    public int getMaxId(){
        int codeScore = 0;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT MAX(codeScore) AS MAXSCORE FROM SCORES";
        try (
                PreparedStatement st = connection.prepareStatement(req);
        ) {
            try (ResultSet result = st.executeQuery();) {
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