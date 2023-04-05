package games.project.casse_briques.metier.entite;

import games.project.metier.entite.Player;
import games.project.metier.entite.Score;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BrickBreakerPlayer extends Player {
    private IntegerProperty life;

    public BrickBreakerPlayer(String name, Score score, int life) {
        super(name, score);
        this.life = new SimpleIntegerProperty(life);
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
