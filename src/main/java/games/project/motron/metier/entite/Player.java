package games.project.motron.metier.entite;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Player {
    String name;
    private Score score;
    private IntegerProperty life;
//    private String departement;

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

//    public Player(String name, int life, Score score, String departement)
//    {
//        this(name, life, score);
//        this.departement = departement;
//    }

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
        this.score = new Score(score, this.name);
    }
}