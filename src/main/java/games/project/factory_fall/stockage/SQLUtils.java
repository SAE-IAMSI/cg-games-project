package games.project.factory_fall.stockage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Classe utilitaire pour la gestion de la base de données. Elle établit la connexion avec la base de données et est utilisée pour effectuer des requêtes.
 * Comme il peut y avoir qu'une seule instance de la connexion à la base de données, cette classe suit le patron de conception Singleton.
 */
public class SQLUtils {

    private static SQLUtils instance = null;
    private Connection connection;

    private SQLUtils() {
        String url = "jdbc:oracle:thin:@162.38.222.149:1521:iut";
        String driver = "oracle.jdbc.driver.OracleDriver";
        String user = "etusae1";
        String pass = "3tus43";
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static SQLUtils getInstance() {
        if (instance == null) instance = new SQLUtils();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Ferme la connexion à la base de données. Nécessaire à chaque fois que le jeu est arrêté.
     */
    public void closeConnection() {
        String req = "COMMIT";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        instance = null;
    }
}