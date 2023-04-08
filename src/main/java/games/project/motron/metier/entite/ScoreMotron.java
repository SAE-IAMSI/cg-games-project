package games.project.motron.metier.entite;

import games.project.metier.entite.Score;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Timestamp;

public class ScoreMotron extends Score {

    private int id;
    private IntegerProperty score;
    private Timestamp horodatage;
    private String login;
    private static final String gameCode = "TRON";

    public ScoreMotron() {
        super("TRON");
    }

    public ScoreMotron(int score) {
        this();
        this.score = new SimpleIntegerProperty(score);
        this.horodatage = new Timestamp(System.currentTimeMillis());
    }

    public ScoreMotron(int id, int score) {
        this();
        this.id = id;
        this.score = new SimpleIntegerProperty(score);
        this.horodatage = new Timestamp(System.currentTimeMillis());
    }

    public ScoreMotron(int score, Timestamp time) {
        this();
        this.score = new SimpleIntegerProperty(score);
        this.horodatage = time;
    }

    public ScoreMotron(String login) {
        this();
        this.score = new SimpleIntegerProperty(0);
        this.horodatage = new Timestamp(System.currentTimeMillis());
        this.login = login;
    }

    public ScoreMotron(int score, String login) {
        this();
        this.score = new SimpleIntegerProperty(score);
        this.horodatage = new Timestamp(System.currentTimeMillis());
        this.login = login;
    }

    public void incrementScore(int score) {
        setScore(this.getScore() + score);
    }
}
