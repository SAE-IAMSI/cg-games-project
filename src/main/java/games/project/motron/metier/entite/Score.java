package games.project.motron.metier.entite;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Timestamp;

public class Score {

    private int id;
    private IntegerProperty score;
    private Timestamp horodatage;
    private String login;
    private static String gameCode = "TRON";

    public Score() {
        this.score = new SimpleIntegerProperty(0);
        this.horodatage = new Timestamp(System.currentTimeMillis());
        this.login = "";
    }

    public Score(int score) {
        this();
        this.score = new SimpleIntegerProperty(score);
        this.horodatage = new Timestamp(System.currentTimeMillis());
    }

    public Score(int score, Timestamp time) {
        this();
        this.score = new SimpleIntegerProperty(score);
        this.horodatage = time;
    }

    public Score(String login) {
        this.score = new SimpleIntegerProperty(0);
        this.horodatage = new Timestamp(System.currentTimeMillis());
        this.login = login;
    }

    public Score(int score, String login) {
        this();
        this.score = new SimpleIntegerProperty(score);
        this.horodatage = new Timestamp(System.currentTimeMillis());
        this.login = login;
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

    public void incrementScore(int score) {
        setScore(this.getScore() + score);
    }
}
