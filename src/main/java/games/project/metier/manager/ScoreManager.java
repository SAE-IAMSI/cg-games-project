package games.project.metier.manager;

import games.project.stockage.sql.StockageScoreDatabase;
import games.project.metier.entite.Score;

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

    public void createScore(int score, String login, String gameCode) {
        Score s = new Score(score, gameCode);
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

    /**
     * Supprime tous les scores du joueur d'identifiant login
     * @param login L'identifiant du joueur dont les scores seront supprimés.
     */
    public void deleteScoreByLogin(String login) {
        stockage.deleteByLogin(login);
    }

    /**
     * Récupère le meilleur score d'un joueur pour un jeu précisé.
     * @param login L'identifiant du joueur dont le meilleur score sera récupéré.
     * @param gameCode Le code du jeu sur lequel le meilleur score sera récupéré.
     * @return Le meilleur score d'un joueur sur un jeu précisé.
     */
    public Score getHighScoreByLoginAndGame(String login, String gameCode) {
        return stockage.getHighScore(login, gameCode);
    }

    public Score getScoreById(int id) {
        return stockage.getById(id);
    }

    /**
     * Récupère tous les scores d'un joueur identifié login.
     * @param login L'identifiant du joueur dont les scores seront récupérés.
     * @return La liste des scores du joueur identifié login.
     */
    public List<Score> getScoresHistoryByLogin(String login) {
        return stockage.getByLogin(login);
    }

    /**
     * Récupère tous les scores d'un jeu précisé réalisés par le joueur identifié login.
     * @param login L'identifiant du joueur dont les scores seront récupérés.
     * @param gameCode Le code du jeu sur lequel les scores ont été réalisés.
     * @return La liste des scores d'un jeu précisé réalisés par le joueur identifié login.
     */
    public List<Score> getScoresHistoryByLoginAndGame(String login, String gameCode) {
        return stockage.getByLoginAndGame(login, gameCode);
    }

    /**
     * Récupère l'ensemble des scores enregistrés.
     * @return La liste de tous les scores enregistrés.
     */
    public List<Score> getScores() {
        return stockage.getAll();
    }

    /**
     * Récupère le classement TOP 10 des meilleurs scores du jeu précisé.
     * @param gameCode Le code du jeu dont le classement est récupéré.
     * @return La liste des 10 meilleurs scores du jeu précisé.
     */
    public List<Score> getLeaderboardByGame(String gameCode) {
        return stockage.getLeaderboard(gameCode);
    }

    /**
     * Récupère tous les scores du jeu précisé.
     * @param gameCode Le code du jeu dont les scores sont récupérés.
     * @return La liste de tous les scores du jeu précisé.
     */
    public List<Score> getScoresByGame(String gameCode) {
        return stockage.getAllByGameCode(gameCode);
    }
}