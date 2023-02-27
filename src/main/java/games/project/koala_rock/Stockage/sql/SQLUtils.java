package games.project.koala_rock.Stockage.sql;

import java.sql.*;

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
     * Ce main sert juste à tester si votre connection fonctionne correctement.
     * Vous pourrez le supprimer après.
     */
    public static void main(String[] args) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();

        try (
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM SCORES")
        ) {
            while (rs.next()) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}