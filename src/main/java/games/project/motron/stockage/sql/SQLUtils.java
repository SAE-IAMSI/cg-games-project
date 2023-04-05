package games.project.motron.stockage.sql;

import java.sql.*;

public class SQLUtils {

    private static SQLUtils instance = null;
    private Connection connection;

    private SQLUtils() {
        String url = "jdbc:oracle:thin:@162.38.222.149:1521:iut";
        String driver = "oracle.jdbc.driver.OracleDriver";
        String user = "etusae1";
        String pass = "3tuS43";
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

    public static void main(String[] args) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        System.out.println(connection);

        try (
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM SCORES")
        ) {
            while (rs.next()) {
                System.out.println(rs.getInt("codeScore"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}