package games.project.koala_rock.Stockage; //Votre package ici.

public class Session {

    private static Session instance = null;
    private String login;

    private Session() {
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

    public void connect(String login) {
        this.login = login;
    }

    public void disconnect() {
        this.login = null;
    }
}