package games.project.prehispong.metier;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Score {
    private IntegerProperty score;

    public Score(){
        this.score = new SimpleIntegerProperty(0);
    }

    public IntegerProperty getScoreProperty() {
        return score;
    }

    public void setScore(int score) {
        this.score.setValue(score);
    }
}
