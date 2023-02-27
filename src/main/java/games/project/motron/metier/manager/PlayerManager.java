package games.project.motron.metier.manager;

import games.project.motron.metier.entite.AuthPlayer;
import games.project.motron.metier.entite.Player;
import games.project.motron.stockage.Security;
import games.project.motron.stockage.sql.StockagePlayerDatabase;

import java.util.List;

public class PlayerManager {

    private static PlayerManager instance = null;
    private static StockagePlayerDatabase stockage = new StockagePlayerDatabase();

    private PlayerManager() {}

    public static PlayerManager getInstance() {
        if (instance == null) instance = new PlayerManager();
        return instance;
    }

    public void createPlayer(String login, String password) {
        AuthPlayer p = new AuthPlayer(login);
        byte[] salt = Security.getSalt();
        p.setSalt(salt);
        p.setPassword(password);
        stockage.create(p);
    }

    public void updatePlayer(String login, String password) {
        AuthPlayer p = stockage.getByLogin(login);
        byte[] salt = Security.getSalt();
        p.setSalt(salt);
        p.setPassword(password);
        stockage.update(p);
    }

    public void deletePlayer(String login) {
        stockage.deleteByLogin(login);
    }

    public AuthPlayer getPlayer(String login) {
        return stockage.getByLogin(login);
    }

    public List<AuthPlayer> getPlayers() { return stockage.getAll(); }

    public List<Player> getMeilleurs(){return stockage.getMeilleurs();}
    public int getNbPartie(String login){return stockage.getNbPartie(login);}

    public int getRangByLogin(String login){return stockage.getRangByLogin(login);}
}