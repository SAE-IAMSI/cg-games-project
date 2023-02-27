package games.project.factory_fall.logique;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Timestamp;

/**
 * La classe Score représente un score dans le jeu. Elle est utilisée pour stocker les scores dans la base de données.
 * Elle est aussi utilisée pour afficher les scores dans le tableau de scores.
 * <br>
 * Un score est composé d'un nombre de points, d'un nombre de lignes complétées, d'un horodatage et d'un login.
 *
 * @see factoryfall.stockage.ScoreManager
 */
public class Score {

    private int id;
    private IntegerProperty score;
    private IntegerProperty nbLignes;
    private Timestamp horodatage;
    private String login;
    private static final String gameCode = "TETRIS";

    private Score() {
        this.score = new SimpleIntegerProperty(0);
        this.nbLignes = new SimpleIntegerProperty(0);
        this.horodatage = new Timestamp(System.currentTimeMillis());
        this.login = "";
    }

    public Score(int score, int nbLignes) {
        this();
        this.score = new SimpleIntegerProperty(score);
        this.nbLignes = new SimpleIntegerProperty(nbLignes);
        this.horodatage = new Timestamp(System.currentTimeMillis());
    }

    public Score(int score, Timestamp time) {
        this();
        this.score = new SimpleIntegerProperty(score);
        this.nbLignes = new SimpleIntegerProperty(0);
        this.horodatage = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public Timestamp getHorodatage() {
        return horodatage;
    }

    public void setHorodatage(Timestamp horodatage) {
        this.horodatage = horodatage;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static String getGameCode() {
        return gameCode;
    }

    public int getNbLignes() {
        return nbLignes.get();
    }

    public IntegerProperty nbLignesProperty() {
        return nbLignes;
    }

    public void setNbLignes(int nbLignes) {
        this.nbLignes.set(nbLignes);
    }
}
