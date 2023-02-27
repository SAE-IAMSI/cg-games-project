package games.project.casse_briques.metier.manager;

import games.project.casse_briques.metier.entite.AuthPlayer;
import games.project.casse_briques.stockage.Security;
import games.project.casse_briques.stockage.sql.StockagePlayerDatabase;

import java.util.List;
import java.util.Map;

public class PlayerManager {

    private static PlayerManager instance = null;
    private final StockagePlayerDatabase stockage = new StockagePlayerDatabase();
    private final Map<String, String> departments;

    private PlayerManager() {
        departments = stockage.getDepartments();
    }

    public static PlayerManager getInstance() {
        if (instance == null) instance = new PlayerManager();
        return instance;
    }

    public void createPlayer(String login, String department, String password) {
        AuthPlayer p = new AuthPlayer(login);
        byte[] salt = Security.getSalt();
        p.setSalt(salt);
        p.setPassword(password);
        p.setDepartment(department);
        stockage.create(p);
    }

    public void updatePlayer(String login, String department, String password) {
        AuthPlayer p = stockage.getByLogin(login);
        byte[] salt = Security.getSalt();
        p.setSalt(salt);
        p.setPassword(password);
        p.setDepartment(department);
        stockage.update(p);
    }

    public void deletePlayer(String login) {
        stockage.deleteByLogin(login);
    }

    public AuthPlayer getPlayer(String login) {
        return stockage.getByLogin(login);
    }

    public List<AuthPlayer> getPlayers() {
        return stockage.getAll();
    }

    public Map<String, String> getDepartments() {
        return departments;
    }
}