package games.project.koala_rock.Stockage.sql; //Votre package ici.

import games.project.koala_rock.Metier.entite.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
            st.setString(3, Score.getGameCode());
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

    public Score getHighScore(String login) {
        Score score = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE login = ? AND codeJeu = ? ORDER BY score DESC";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            st.setString(2, Score.getGameCode());
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    int id = result.getInt("codeScore");
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

    /**
     * Renvoie l'historique des scores sur votre jeu.
     *
     * @param login
     * @return
     */
    public List<Score> getByLogin(String login) {
        List<Score> scoresList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE login = ? AND codeJeu = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            st.setString(2, Score.getGameCode());
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

    public List<Double> getTempsByLogin(String login) {
        List<Double> tempsList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM KOALAROCK k JOIN Scores s ON k.codeScore=s.codeScore WHERE login = ? ORDER BY temps ASC";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    double temps = result.getInt("temps");
                    tempsList.add(temps);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempsList;
    }

    /**
     * Renvoie tous les scores de votre jeu.
     *
     * @return
     */
    public List<Score> getAll() {
        List<Score> scoreList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE codeJeu = ? AND score IS NOT NULL ORDER BY score DESC";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, Score.getGameCode());
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

    public Map<Integer, Double> getAllTemps() {
        Map<Integer, Double> scoreList = new LinkedHashMap<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM KOALAROCK ORDER BY temps ASC";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("codeScore");
                    double temps = result.getDouble("temps");
                    scoreList.put(id, temps);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scoreList;
    }

    public String getLoginTemps(int id) {
        String res = "";
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM KOALAROCK k JOIN Scores s ON k.codeScore=s.codeScore WHERE s.codeScore = ?";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setInt(1, id);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    res = result.getString("login");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public int getDernierCode() {
        int res = 0;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT MAX(codeScore) from Scores where codeJeu='KR'";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    res = result.getInt("codeScore");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public void addTemps(double temps, String login) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        Score tempo = new Score(0);
        tempo.setLogin(login);
        create(tempo);
        String req = "INSERT INTO KOALAROCK (codeScore,temps) VALUES (?,?,?)";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setInt(1, getDernierCode() + 1);
            st.setDouble(2, temps);
            st.setString(3, login);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TRON
     */
    public List<Score> getAllTRON() {
        List<Score> scoreList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE codeJeu = ? ORDER BY score DESC";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, Score.getGameCodeTRON());
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

    /**
     * CB
     */
    public List<Score> getAllCB() {
        List<Score> scoreList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE codeJeu = ? ORDER BY score DESC";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, Score.getGameCodeCB());
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

    /**
     * TETRIS
     */

    public List<Score> getAllTETRIS() {
        List<Score> scoreList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE codeJeu = ? ORDER BY score DESC";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, Score.getGameCodeTETRIS());
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


    public List<Score> getAllByDepartement(String numDepartement) {
        ArrayList<Score> scores = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM scores WHERE codeJeu = ? AND login IN (SELECT login FROM Joueurs WHERE numDepartement = ?) ORDER BY score DESC";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, Score.getGameCode());
            st.setString(2, numDepartement);
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("codeScore");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    String login = result.getString("login");
                    Score score = new Score(scoreValue, time);
                    score.setId(id);
                    score.setLogin(login);
                    scores.add(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public Map<Integer, Double> getAllTempsByDepartement(String numDepartement) {
        Map<Integer, Double> temps = new LinkedHashMap<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM KOALAROCK k JOIN Scores s ON k.codeScore=s.codeScore WHERE login IN (SELECT login FROM Joueurs WHERE numDepartement = ?) ORDER BY temps ASC";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, numDepartement);
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("codeScore");
                    double tempsValue = result.getDouble("temps");
                    temps.put(id, tempsValue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(temps);
        return temps;
    }
}