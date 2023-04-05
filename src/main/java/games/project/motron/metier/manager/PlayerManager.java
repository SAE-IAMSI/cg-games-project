package games.project.motron.metier.manager;

import games.project.motron.metier.entite.AuthPlayer;
import games.project.motron.metier.entite.Player;
import games.project.motron.stockage.Security;
import games.project.motron.stockage.sql.StockagePlayerDatabase;

import java.util.List;

public class PlayerManager {

    private static PlayerManager instance = null;
    private static final StockagePlayerDatabase stockage = new StockagePlayerDatabase();

    private PlayerManager() {
    }

    public static PlayerManager getInstance() {
        if (instance == null) instance = new PlayerManager();
        return instance;
    }

    public void createPlayer(String login, String password, String departement) {
        AuthPlayer p = new AuthPlayer(login, departement);
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

    public AuthPlayer getPlayer(String login) {
        return stockage.getByLogin(login);
    }

    public List<Player> getMeilleurs() {
        return stockage.getMeilleurs();
    }

    public int getNbPartie(String login) {
        return stockage.getNbPartie(login);
    }

    public String getDepartementByLogin(String login) {
        return stockage.getDepByLogin(login);
    }
}