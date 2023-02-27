package games.project.factory_fall.stockage;

import java.util.Objects;

/**
 * La classe Session garde en mémoire les informations concernant le joueur actuellement connecté.
 * Un seul joueur peut être connecté à la fois. Cette classe suit donc le patron de conception Singleton.
 * <p>
 * Les deux classes suivantes utilisent la classe Session pour insérer des données relatives au joueur dans la base de données.
 *
 * @see PlayerManager
 * @see ScoreManager
 */
public class Session {

    private static Session instance = null;
    private String login = "";

    private String departement = "";

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) instance = new Session();
        return instance;
    }

    /**
     * Compare le login passé en paramètre au login du joueur actuellement connecté.
     *
     * @param login Le login à comparer.
     * @return true si les deux logins sont identiques, false sinon.
     */
    public boolean isLoggedIn(String login) {
        return this.login.equals(login);
    }

    /**
     * Vérifie que le joueur est connecté en vérifiant que le login est non vide et qu'il n'est pas égal à Anonyme.
     *
     * @return true ssi le joueur est connecté.
     */
    public boolean isConnected() {
        return !Objects.equals(this.login, "Anonyme") && !this.login.isEmpty();
    }

    /**
     * Connecte le joueur à la session.
     *
     * @param login       login du joueur
     * @param departement departement du joueur
     */
    public void connect(String login, String departement) {
        this.login = login;
        this.departement = departement;
    }

    /**
     * Déconnecte le joueur actuellement connecté. Réinitialise les informations de la session.
     */
    public void disconnect() {
        this.login = "Anonyme";
        this.departement = "";
    }

    public String getLogin() {
        return login;
    }

    public String getDepartement() {
        return departement;
    }
}