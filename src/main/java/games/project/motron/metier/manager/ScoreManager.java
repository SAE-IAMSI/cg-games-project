package games.project.motron.metier.manager;

import games.project.motron.metier.entite.Score;
import games.project.motron.stockage.sql.StockageScoreDatabase;

import java.util.List;

public class ScoreManager {

    private static ScoreManager instance = null;
    private static StockageScoreDatabase stockage = new StockageScoreDatabase();

    private ScoreManager() {}

    public static ScoreManager getInstance() {
        if (instance ==null) instance = new ScoreManager();
        return instance;
    }

    public void createScore(int score, String login) {
        Score s = new Score(score);
        s.setLogin(login);
        stockage.create(s);
    }

    public void createScore(int id, int score, String login) {
        Score s = new Score(id, score);
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

    public Score getScoreById(int id) {
        return stockage.getById(id);
    }

    public Score getScoreByLogin(String login) {
        return stockage.getByLogin(login);
    }

    public Score getScoreRecentByLogin(String login) {return stockage.getByLoginRecent(login);}

    public List<Score> getScores() {
        return stockage.getAll();
    }

    public int sommeScore(String login) {
        return stockage.getSommeScore(login);
    }
    public int highScore(String login){return stockage.highScore(login);}

    public int getNewId(){return stockage.getNewId();}
}