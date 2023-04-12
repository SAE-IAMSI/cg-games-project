package games.project.prehispong.model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class Chronometer {

    private final IntegerProperty time;
    private Timeline timeline;

    public Chronometer() {
        this.time = new SimpleIntegerProperty();
        this.time.set(0);

    }

    public void initChrono() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            addTime();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void addTime() {
        time.set(time.getValue() + 1);
    }

    public void reset() {
        time.set(0);
    }

    public void launch() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public int getTime() {
        return this.time.getValue();
    }

    public IntegerProperty getIntegerProperty() {
        return time;
    }
}
