package games.project.motron.metier.manager;

import games.project.metier.entite.Player;
import games.project.metier.manager.PlayerManager;
import games.project.motron.stockage.sql.StockagePlayerDatabaseMotron;

import java.util.List;

public class PlayerManagerMotron extends PlayerManager {

    private PlayerManagerMotron() {
        super();
        stockage = new StockagePlayerDatabaseMotron();
    }

    public static PlayerManagerMotron getInstance() {
        if (instance == null) instance = new PlayerManagerMotron();
        return (PlayerManagerMotron) instance;
    }

    public List<Player> getMeilleurs() {
        return ((StockagePlayerDatabaseMotron) stockage).getMeilleurs();
    }

    public int getNbPartie(String login) {
        return ((StockagePlayerDatabaseMotron) stockage).getNbPartie(login);
    }

    public String getDepartementByLogin(String login) {
        return ((StockagePlayerDatabaseMotron) stockage).getDepByLogin(login);
    }
}