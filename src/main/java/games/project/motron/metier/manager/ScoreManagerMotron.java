package games.project.motron.metier.manager;

import games.project.metier.manager.ScoreManager;
import games.project.motron.stockage.sql.StockageScoreDatabaseMotron;

public class ScoreManagerMotron extends ScoreManager {

    private ScoreManagerMotron() {
        super();
        stockage = new StockageScoreDatabaseMotron();
    }

    public static ScoreManagerMotron getInstance() {
        if (instance == null) instance = new ScoreManagerMotron();
        return (ScoreManagerMotron) instance;
    }

    public int sommeScore(String login) {
        return ((StockageScoreDatabaseMotron) stockage).getSommeScore(login);
    }

    public int highScore(String login) {
        return ((StockageScoreDatabaseMotron) stockage).highScore(login);
    }

    public int getMaxId() {
        return ((StockageScoreDatabaseMotron) stockage).getMaxId();
    }
}