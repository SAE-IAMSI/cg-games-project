package games.project.stockage;

import games.project.metier.manager.PlayerManager;
import games.project.modules.parametres.Parametres;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class Session {

    private static Session instance = null;
    private String login;
    private final BooleanProperty booleanProperty;

    private Session() {
        booleanProperty = new SimpleBooleanProperty();
    }

    public static Session getInstance() {
        if (instance == null) instance = new Session();
        return instance;
    }

    public String getLogin() {
        return login;
    }

    public boolean isLoggedIn(String login) {
        return this.login.equals(login);
    }

    public boolean isConnected() {
        return this.login != null && !this.login.isEmpty();
    }

    public void booleanState() {
        this.booleanProperty.set(isConnected());
    }

    public BooleanProperty getBooleanProperty() {
        return this.booleanProperty;
    }

    public void connect(String login) {
        this.login = login;
        booleanState();
        Parametres.changeConnexion(login, true);
        Parametres.changeAdmin(PlayerManager.getInstance().getPlayer(login).isAdmin());
    }

    public void disconnect() {
        this.login = null;
        booleanState();
        Parametres.changeConnexion(login, false);
    }
}