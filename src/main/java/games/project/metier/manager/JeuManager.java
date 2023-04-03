package games.project.metier.manager;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Jeu;
import games.project.stockage.Security;
import games.project.stockage.sql.StockageJeuDatabase;
import games.project.stockage.sql.StockagePlayerDatabase;

import java.util.List;
import java.util.Map;

public class JeuManager {
    private static JeuManager instance = null;
    private final StockageJeuDatabase stockage = new StockageJeuDatabase();

    private JeuManager() {
    }

    public static JeuManager getInstance() {
        if (instance == null) instance = new JeuManager();
        return instance;
    }

    public void createJeu(String code, String libelle, String path) {
        Jeu jeu = new Jeu(code, libelle, path);
        stockage.create(jeu);
    }

    public void updatePlayer(String code, String libelle) {
        Jeu jeu = stockage.getByCode(code);
        jeu.setLibelle(libelle);
        stockage.update(jeu);
    }

    public void deletePlayer(String code) {
        stockage.deleteByCode(code);
    }

    public Jeu getJeu(String code) {
        return stockage.getByCode(code);
    }

    public List<Jeu> getJeux() {
        return stockage.getAll();
    }

    public List<String> getPaths() {
        return stockage.getPaths();
    }
}
