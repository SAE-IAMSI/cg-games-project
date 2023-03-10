package games.project.motron.metier.manager;

import games.project.motron.metier.entite.ScorePartie;
import games.project.motron.metier.entite.Stat;
import games.project.motron.stockage.sql.StockageScorePartieDatabase;

public class ScorePartieManager {

    private static ScorePartieManager instance = null;
    private static StockageScorePartieDatabase stockage = new StockageScorePartieDatabase();
    private ScorePartieManager() {}
    public static ScorePartieManager getInstance() {
        if (instance ==null) instance = new ScorePartieManager();
        return instance;
    }

    public int getLastId(){
        return stockage.LastId();
    }

    public void createScorePartie(int idPartie, String login, int score, int kill, int death, int nbBlocs,  String resultat){
        ScorePartie s = new ScorePartie(idPartie, login, score, kill, death, nbBlocs, resultat);
        stockage.create(s);
    }

    public Stat createStat(String login) {
        Stat stat = new Stat(stockage.getStat(login));
        stat.setNbVictoire(stockage.getNbVictoire(login));
        stat.setNbDefaite(stockage.getNbDefaite(login));
        stat.setNbEgalite(stockage.getNbEgalite(login));
        stat.setMoyenneKillParPartie();
        return stat;
    }

}
