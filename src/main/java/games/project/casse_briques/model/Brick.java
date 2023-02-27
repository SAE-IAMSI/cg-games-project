package games.project.casse_briques.model;

import games.project.casse_briques.controller.AnimationController;
import games.project.casse_briques.controller.BrickBreakerController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Brick extends Rectangle {
    private final IntegerProperty life;

    public Brick(int width, int height, int life) {
        this.life = new SimpleIntegerProperty(life);
        this.setHeight(height);
        this.setWidth(width);

        this.setStrokeWidth(1);
        this.setStroke(Color.WHITE);

        switch (this.life.getValue()) {
            case 1 -> Brick.this.setFill(Paint.valueOf(Color.YELLOW.toString()));
            case 2 -> Brick.this.setFill(Paint.valueOf(Color.PINK.toString()));
            case 3 -> Brick.this.setFill(Paint.valueOf(Color.GREEN.toString()));
            case 4 -> Brick.this.setFill(Paint.valueOf(Color.RED.toString()));
        }

        this.life.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                switch (t1.intValue()) {
                    case 1 -> Brick.this.setFill(Paint.valueOf(Color.YELLOW.toString()));
                    case 2 -> Brick.this.setFill(Paint.valueOf(Color.PINK.toString()));
                    case 3 -> Brick.this.setFill(Paint.valueOf(Color.GREEN.toString()));
                    case 4 -> Brick.this.setFill(Paint.valueOf(Color.RED.toString()));
                }
            }
        });
    }

    public int getLife() {
        return this.life.get();
    }

    public IntegerProperty getLifeProperty() {
        return this.life;
    }

    public void setLife(int life) {
        this.life.set(life);
    }

    public void decreaseLife() {
        this.life.set(this.life.get() - 1);
    }

    /**
     * Permet de verifier qu'une brique doit être détruite, gère la destruction de la brique si nécessaire
     **/
    public void deleteBrick(BrickBreakerController brickBreakerController) {
        Brick brick = this;
        AnimationController animation = new AnimationController(brickBreakerController);
        this.life.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (t1.intValue() <= 0) {
                    brickBreakerController.getPlayer().getScore().setScore(brickBreakerController.getPlayer().getScore().getScore() + 100);
                    brickBreakerController.getSoundGestion().brickDestroyed();
                    animation.showScore(brick);
                    randomBonus(brickBreakerController, brick); // génère bonus
                    brickBreakerController.getChildren().remove(brick);
                    brickBreakerController.getBrickMemoryRemove().add(brick);
                }
            }
        });
    }

    /**
     * Détermine si un bonus doit être génèré ou non
     **/
    public void randomBonus(BrickBreakerController brickBreakerController, Brick brick) {

        int a = new Random().nextInt(8); // 1/8 chance d'avoir un bonus
        if (a == 5) {
            AnimationController animations = new AnimationController(brickBreakerController);
            animations.showBonus(brick);
        }

    }
}