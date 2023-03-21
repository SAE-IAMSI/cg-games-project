package games.project.metier.entite;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Player {
    String name;
    private Score score;
    private IntegerProperty life;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, int life) {
        this(name);
        this.life = new SimpleIntegerProperty(life);
        this.score = new Score(0);
    }

    public Player(String name, int life, Score score) {
        this(name, life);
        this.score = score;
    }

    public Score getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score.setScore(score);
    }

    public int getLife() {
        return life.get();
    }

    public IntegerProperty lifeProperty() {
        return life;
    }

    public void setLife(int life) {
        this.life.set(life);
    }

    public void loseLife() {
        this.life.set(this.life.get() - 1);
    }
}