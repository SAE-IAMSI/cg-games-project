package games.project.factory_fall.stockage;

import games.project.factory_fall.logique.Score;
import games.project.factory_fall.stockage.sql.StockageScoreDatabase;

import java.util.List;

/**
 * La classe ScoreManager est un singleton qui permet de gérer les scores dans la base de données.
 *
 * @see StockageScoreDatabase
 */
public class ScoreManager {

    private static ScoreManager instance = null;
    private final StockageScoreDatabase stockage = new StockageScoreDatabase();

    public static ScoreManager getInstance() {
        if (instance == null) instance = new ScoreManager();
        return instance;
    }

    public void createScore(int score, int nbLignes, String login) {
        Score s = new Score(score, nbLignes);
        s.setLogin(login);
        stockage.create(s);
    }

    public void deleteScoreById(int id) {
        stockage.deleteById(id);
    }

    public void deleteScoreByLogin(String login) {
        stockage.deleteByLogin(login);
    }

    public Score getHighScoreByLogin(String login) {
        return stockage.getHighScore(login);
    }

    public List<Score> getTopScoreParLogin(String login) {return stockage.getTopScoreParLogin(login);}

    public List<Score> getScoresHistoryByLogin(String login) {
        return stockage.getByLogin(login);
    }

    public List<Score> getScores() {
        return stockage.getAll();
    }

    public List<Score> getTopScores() {
        return stockage.getTopScores();
    }

    public List<Score> getTopScore() { return  stockage.getTopScores();}

    public List<Score> getTopScoresAnonyme() {
        return stockage.GetTopScoreAnonyme();
    }

    public List<Score> getTopScoresParDepartement(String departement) {
        return stockage.GetTopScoreParDepartement(departement);
    }
}