package games.project.casse_briques.metier.manager;

import games.project.casse_briques.metier.entite.Score;
import games.project.casse_briques.stockage.sql.StockageScoreDatabase;

import java.util.List;

public class ScoreManager {

    private static ScoreManager instance = null;
    private final StockageScoreDatabase stockage = new StockageScoreDatabase();

    private ScoreManager() {
    }

    public static ScoreManager getInstance() {
        if (instance == null) instance = new ScoreManager();
        return instance;
    }

    public void createScore(int score, String login) {
        Score s = new Score(score);
        s.setLogin(login);
        stockage.create(s);
    }

    public void updateScore(int id, int score) {
        Score s = stockage.getById(id);
        s.setScore(score);
        stockage.update(s);
    }

    public void deleteScoreById(int id) {
        stockage.deleteById(id);
    }

    public void deleteScoreByLogin(String login) {
        stockage.deleteByLogin(login);
    }

    public Score getHighScoreByLogin(String login) {
        return stockage.getHighScore(login, "CB");
    }

    public Score getScoreById(int id) {
        return stockage.getById(id);
    }

    public Score getHighScoreByGame(String login, String gameCode) {
        return stockage.getHighScore(login, gameCode);
    }

    public List<Score> getScoresHistoryByLogin(String login) {
        return stockage.getByLogin(login);
    }

    public List<Score> getScoresHistoryByLoginAndGame(String login, String gameCode) {
        return stockage.getByLoginAndGame(login, gameCode);
    }

    public List<Score> getScores() {
        return stockage.getAll();
    }

    public List<Score> getLeaderboard() {
        return stockage.getLeaderboard();
    }

    public List<Score> getScoresByGame(String gameCode) {
        return stockage.getAllByGameCode(gameCode);
    }
}