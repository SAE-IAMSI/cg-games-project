package games.project.casse_briques.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class ChronometerController {

    private final IntegerProperty time;
    private Timeline timeline;

    public ChronometerController(int time) {
        this.time = new SimpleIntegerProperty();
        this.time.set(time);

    }

    public void initChrono() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            reduceTime();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void reduceTime() {
        time.set(time.getValue() - 1);
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

    public void addTime(int value) {
        time.set(time.getValue() + value);
    }

    public void removeTime(int value) {
        time.set(time.getValue() - value);
    }

    public IntegerProperty getIntegerProperty() {
        return time;
    }
}
