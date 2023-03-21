package games.project.stockage.sql;

import games.project.metier.entite.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockageScoreDatabase {

    public void create(Score element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "INSERT INTO SCORES(score, horodatage, codeJeu, login) VALUES (?, ?, ?, ?)";
        try (
                PreparedStatement st = connection.prepareStatement(req)
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

    public void update(Score element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE SCORES SET score = ? WHERE codeScore = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
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
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            st.setString(2, "CB");
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
                PreparedStatement st = connection.prepareStatement(req)
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
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setInt(1, id);
            try (ResultSet result = st.executeQuery()) {
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

    public Score getHighScore(String login, String gameCode) {
        Score score = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM (SELECT * FROM SCORES WHERE login = ? AND codeJeu = ? ORDER BY score DESC) WHERE ROWNUM = 1";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            st.setString(2, gameCode);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    int id = result.getInt("codeScore");
                    score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                    score.setGameCode(gameCode);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    public List<Score> getByLogin(String login) {
        List<Score> scoresList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE login = ? AND codeJeu = ? ORDER BY score DESC";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            st.setString(2, "CB");
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("codeScore");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    Score score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                    scoresList.add(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scoresList;
    }

    public List<Score> getByLoginAndGame(String login, String gameCode) {
        List<Score> scoreList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE login = ? AND codeJeu = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            st.setString(2, gameCode);
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("codeScore");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    Score score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                    score.setGameCode(gameCode);
                    scoreList.add(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scoreList;
    }

    public List<Score> getAll() {
        List<Score> scoreList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE codeJeu = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, "CB");
            try (ResultSet result = st.executeQuery()) {
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

    public List<Score> getAllByGameCode(String gameCode) {
        List<Score> scoreList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE codeJeu = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, gameCode);
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("codeScore");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    String login = result.getString("login");
                    Score score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                    score.setGameCode(gameCode);
                    scoreList.add(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scoreList;
    }

    public List<Score> getLeaderboard() {
        List<Score> scoreList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM (SELECT * FROM SCORES WHERE codeJeu = ? ORDER BY score DESC) WHERE ROWNUM <= 10";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, "CB");
            try (ResultSet result = st.executeQuery()) {
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
}