package games.project.factory_fall.stockage.sql;

import games.project.factory_fall.logique.Score;
import games.project.factory_fall.stockage.SQLUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe opère toutes les requêtes à effectuer dans la base de données relevant des scores.
 * Elle suit le modèle CRUD pour les requêtes, en plus de requêtes personnalisées.
 *
 * <p> Les requêtes sont préparées pour éviter les injections SQL.
 *
 * @see factoryfall.stockage.ScoreManager
 */
public class StockageScoreDatabase {

    public void create(Score element) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "call creationScoreTETRIS(?, ?, ?, ?, ?)";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setInt(1, element.getScore());
            st.setTimestamp(2, element.getHorodatage());
            st.setString(3, Score.getGameCode());
            st.setInt(4, element.getNbLignes());
            if (!element.getLogin().isEmpty()) st.setString(5, element.getLogin());
            else st.setNull(5, Types.VARCHAR);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue avec l'insertion.");
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
            System.out.println("Une erreur est survenue avec la mise à jour.");
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
            System.out.println("Une erreur est survenue avec la suppression.");
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
            System.out.println("Une erreur est survenue avec la suppression.");
        }
    }

    public List<Score> getTopScoreParLogin(String login) {
        List<Score> scoresList = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT score,horodatage FROM SCORES WHERE  codeJeu = 'TETRIS' AND login=? ORDER BY score DESC ";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    {
                        int scoreValue = result.getInt("score");
                        Timestamp time = result.getTimestamp("horodatage");
                        Score score = new Score(scoreValue, time);
                        score.setLogin(login);
                        scoresList.add(score);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue avec la récupération.");
        }
        return scoresList;
    }

    public Score getHighScore(String login) {
        Score score = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT * FROM SCORES WHERE login = ? AND codeJeu = ? AND ROWNUM <= 10 ORDER BY score DESC ";
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
            System.out.println("Une erreur est survenue avec la récupération.");
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
            System.out.println("Une erreur est survenue avec la récupération.");
        }
        return scoresList;
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
        String req = "SELECT * FROM SCORES WHERE codeJeu = ?";
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
            System.out.println("Une erreur est survenue avec la récupération.");
        }
        return scoreList;
    }


    public List<Score> getTopScores() {
        List<Score> topScores = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT score, horodatage, login FROM Scores WHERE codejeu='TETRIS' ORDER BY score DESC";

        try (
                PreparedStatement st = connection.prepareStatement(req);
                ResultSet result = st.executeQuery()
        ) {
            int i = 1;
            while (result.next() && i < 11) {
                String login = result.getString("login");
                int scoreValue = result.getInt("score");
                Timestamp time = result.getTimestamp("horodatage");
                Score score = new Score(scoreValue, time);
                score.setLogin(login);
                topScores.add(score);
                i++;
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue avec la récupération.");
        }
        return topScores;
    }

    public List<Score> GetTopScoreParDepartement(String departement) {
        List<Score> topscore = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT score, horodatage, Scores.login FROM Scores" +
                " LEFT OUTER JOIN joueurs ON joueurs.login=Scores.login" +
                " WHERE  Scores.login=joueurs.login  AND joueurs.numDepartement=? AND codejeu='TETRIS' ORDER BY score DESC";
        int i = 1;
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, departement);
            try (
                    ResultSet result = st.executeQuery()) {
                while (result.next() && i < 11) {
                    String login = result.getString("login");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    Score score = new Score(scoreValue, time);
                    score.setLogin(login);
                    topscore.add(score);
                    i++;
                }
            }

            return topscore;
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue avec la récupération.");
        }
        return topscore;
    }


    public List<Score> GetTopScoreAnonyme() {
        List<Score> topScoresAnonymes = new ArrayList<>();
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT score, horodatage, login FROM Scores WHERE login IS NULL AND codejeu='TETRIS'  ORDER BY score DESC";
        int i = 1;
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            try (
                    ResultSet result = st.executeQuery()) {
                while (result.next() && i < 11) {
                    String login = result.getString("login");
                    int scoreValue = result.getInt("score");
                    Timestamp time = result.getTimestamp("horodatage");
                    Score score = new Score(scoreValue, time);
                    score.setLogin(login);
                    topScoresAnonymes.add(score);
                    i++;
                }
            }
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue avec la récupération.");
        }
        return topScoresAnonymes;
    }
}

