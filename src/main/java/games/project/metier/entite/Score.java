package games.project.metier.entite;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Timestamp;
import java.util.Objects;

public class Score {

    private int id;
    private IntegerProperty score;
    private Timestamp horodatage;
    private String login;
    private String gameCode;

    public Score(String gameCode) {
        this.score = new SimpleIntegerProperty(0);
        this.horodatage = new Timestamp(System.currentTimeMillis());
        this.gameCode = gameCode;
        this.login = "";
    }

    public Score(int score, String gameCode) {
        this(gameCode);
        this.score = new SimpleIntegerProperty(score);
        this.horodatage = new Timestamp(System.currentTimeMillis());
    }

    public Score(int score, Timestamp time, String gameCode) {
        this(gameCode);
        this.score = new SimpleIntegerProperty(score);
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

    public String getGameCode() {
        return this.gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return id == score1.id && Objects.equals(score, score1.score) && Objects.equals(horodatage, score1.horodatage) && Objects.equals(login, score1.login) && Objects.equals(gameCode, score1.gameCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, horodatage, login, gameCode);
    }
}
