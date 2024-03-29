package games.project.metier.manager;

import games.project.metier.entite.AuthPlayer;
import games.project.stockage.Security;
import games.project.stockage.sql.StockagePlayerDatabase;

import java.util.List;
import java.util.Map;

public class PlayerManager {

    protected static PlayerManager instance = null;
    protected StockagePlayerDatabase stockage = new StockagePlayerDatabase();
    protected final Map<String, String> departments;

    protected PlayerManager() {
        departments = stockage.getDepartments();
    }

    public static PlayerManager getInstance() {
        if (instance == null) instance = new PlayerManager();
        return instance;
    }

    public void createPlayer(String login, String department, String password, boolean isAdmin) {
        AuthPlayer p = new AuthPlayer(login);
        byte[] salt = Security.getSalt();
        p.setSalt(salt);
        p.setPassword(password);
        p.setDepartment(department);
        p.setAdmin(isAdmin);
        stockage.create(p);
    }

    public void updatePlayer(String login, String department, String password, boolean isAdmin) {
        AuthPlayer p = stockage.getByLogin(login);
        byte[] salt = Security.getSalt();
        p.setSalt(salt);
        p.setPassword(password);
        p.setDepartment(department);
        p.setAdmin(isAdmin);
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