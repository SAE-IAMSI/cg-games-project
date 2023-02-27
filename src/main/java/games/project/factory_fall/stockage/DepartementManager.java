package games.project.factory_fall.stockage;

import games.project.factory_fall.logique.Departement;
import games.project.factory_fall.stockage.sql.StockageDepartementDatabase;

import java.util.List;

/**
 * La classe ScoreManager est un singleton qui permet de gérer les scores dans la base de données.
 *
 * @see StockageDepartementDatabase
 */
public class DepartementManager {

    private static DepartementManager instance = null;
    private final StockageDepartementDatabase stockage = new StockageDepartementDatabase();

    private DepartementManager() {
    }

    public static DepartementManager getInstance() {
        if (instance == null) instance = new DepartementManager();
        return instance;
    }

    public void updateDepartement(String login, String newdepartement) {
        stockage.update(login, newdepartement);
    }

    public String getDepartementByLogin(String login) {
        return stockage.getDepartementByLogin(login);
    }

    public List<Departement> getAll() {
        return stockage.getAll();
    }

}