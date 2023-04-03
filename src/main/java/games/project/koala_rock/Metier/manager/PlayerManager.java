package games.project.koala_rock.Metier.manager; //Votre package ici.


import games.project.metier.entite.AuthPlayer;
import games.project.stockage.Security;
import games.project.stockage.sql.StockagePlayerDatabase;

import java.util.List;

public class PlayerManager {

    private static PlayerManager instance = null;
    private final StockagePlayerDatabase stockage = new StockagePlayerDatabase();

    private PlayerManager() {
    }

    public static PlayerManager getInstance() {
        if (instance == null) instance = new PlayerManager();
        return instance;
    }

    public void createPlayer(String login, String password, String departement) {
        AuthPlayer p = new AuthPlayer(login);
        byte[] salt = Security.getSalt(); //Génération d'un sel de hachage
        p.setSalt(salt); //Application du sel au nouveau joueur.
        p.setPassword(password); //Hachage du mot de passe avec le sel.
        p.setDepartment(departement);
        stockage.create(p);
    }

    public void updatePlayer(String login, String password, String departement) {
        AuthPlayer p = stockage.getByLogin(login);
        byte[] salt = Security.getSalt();
        p.setSalt(salt);
        p.setPassword(password);
        p.setDepartment(departement);
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
}