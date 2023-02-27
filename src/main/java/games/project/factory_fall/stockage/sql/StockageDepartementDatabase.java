package games.project.factory_fall.stockage.sql;

import games.project.factory_fall.logique.Departement;
import games.project.factory_fall.stockage.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe opère toutes les requêtes à effectuer dans la base de données relevant des départements.
 * <p> Les requêtes sont préparées pour éviter les injections SQL.
 *
 * @see factoryfall.stockage.DepartementManager
 */
public class StockageDepartementDatabase {

    public void update(String login, String nouveauDepartement) {
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "UPDATE JOUEURS SET numDepartement = ? WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            st.setString(2, nouveauDepartement);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getDepartementByLogin(String login) {
        String departementlogin = null;
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT numDepartement FROM JOUEURS WHERE login = ?";
        try (
                PreparedStatement st = connection.prepareStatement(req)
        ) {
            st.setString(1, login);
            try (ResultSet result = st.executeQuery()) {
                if (result.next()) {
                    departementlogin = result.getString("numDepartement");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departementlogin;
    }

    public List<Departement> getAll() {
        List<Departement> listeDepartement = new ArrayList<>();
        listeDepartement.add(new Departement("XX", "Pas de département"));
        SQLUtils utils = SQLUtils.getInstance();
        Connection connection = utils.getConnection();
        String req = "SELECT numDepartement, nomDepartement FROM DEPARTEMENTS";
        try (
                PreparedStatement st = connection.prepareStatement(req);
                ResultSet result = st.executeQuery()
        ) {
            while (result.next()) {
                String numDepartement = result.getString("numDepartement");
                String nomDepartement = result.getString("nomDepartement");
                Departement departement = new Departement(numDepartement, nomDepartement);

                listeDepartement.add(departement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listeDepartement;
    }
}
