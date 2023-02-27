package games.project.koala_rock.Metier.manager; //Votre package ici.

import games.project.koala_rock.Metier.entite.Score;
import games.project.koala_rock.Stockage.sql.StockageScoreDatabase;

import java.util.List;
import java.util.Map;

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
        return stockage.getHighScore(login);
    }

    //public List<Score> getHighScores() { return stockage.get10HighScore(); }

    public Score getScoreById(int id) {
        return stockage.getById(id);
    }

    public List<Score> getScoresHistoryByLogin(String login) {
        return stockage.getByLogin(login);
    }

    public List<Score> getScores() {
        return stockage.getAll();
    }

    public List<Score> getScoresTRON() {
        return stockage.getAllTRON();
    }

    public List<Score> getScoresCB() {
        return stockage.getAllCB();
    }

    public List<Score> getScoresTETRIS() {
        return stockage.getAllTETRIS();
    }


    public Map<Integer, Double> getScoresTemps() {
        return stockage.getAllTemps();
    }

    public String getLoginTemps(int id) {
        return stockage.getLoginTemps(id);
    }

    public int getDernierCode() {
        return stockage.getDernierCode();
    }

    public void addTemps(double temps, String login) {
        stockage.addTemps(temps, login);
    }

    public List<Double> getTempsByLogin(String login) {
        return stockage.getTempsByLogin(login);
    }

    public Map<Integer, Double> getAllTempsByDepartement(String departement) {
        return stockage.getAllTempsByDepartement(departement);
    }

    public List<Score> getAllByDepartement(String departement) {
        return stockage.getAllByDepartement(departement);
    }


}