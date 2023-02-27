package games.project.casse_briques.model;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.controller.BrickBreakerController;
import games.project.casse_briques.controller.SoundGestionController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Racket extends ImageView {

    private final BrickBreakerController brickBreakerController;
    private final SoundGestionController soundGestion;
    private final double minWidthN = 55;
    private final double maxWidthN = 275;
    private double minWidth;
    private double maxWidth;
    private final double minHeight;

    public Racket(double width, double height, BrickBreakerController brickBreakerController) {
        this.minWidth = width;
        this.minHeight = height;
        this.setFitWidth(width);
        this.setFitHeight(height);
        Image image = new Image(BrickBreakerApplication.class.getResource("textures/racketWood.png").toString());
        this.setImage(image);
        // this.setFill(new ImagePattern(image));
        this.brickBreakerController = brickBreakerController;
        this.soundGestion = new SoundGestionController(brickBreakerController);
    }

    public void racketResetPosition() {
        setLayoutX(brickBreakerController.getPrefWidth() / 2 - getFitWidth() / 2);
        setLayoutY(brickBreakerController.getPrefHeight() - 30);
    }

    public void racketHitbox(Ball ball) {
        if (ball.getBoundsInParent().intersects(getBoundsInParent())) {
            soundGestion.bounceBall();
            if (ball.getLayoutX() <= getLayoutX() + getFitWidth() * 0.25) { // Touche à gauche
                if (ball.getSpeedX() >= ball.getMinSpeedX() && ball.getSpeedX() <= ball.getMaxSpeedX()) {
                    ball.setLayoutY(ball.getLayoutY() - (-ball.getSpeedY()));
                    ball.setSpeedY(-ball.getSpeedY());
                    ball.setSpeedX(ball.getSpeedX() + 0.5);
                } else if (ball.getSpeedX() <= -ball.getMinSpeedX() && ball.getSpeedX() >= -ball.getMaxSpeedX()) {
                    ball.setLayoutY(ball.getLayoutY() - (-ball.getSpeedY()));
                    ball.setSpeedY(-ball.getSpeedY());
                    ball.setSpeedX(-ball.getSpeedX() + 0.5);
                }
            } else if (ball.getLayoutX() > getLayoutX() + getFitWidth() * 0.25 && ball.getLayoutX() < getLayoutX() + getFitWidth() * 0.75) { //touche milieu
                if (ball.getSpeedX() > ball.getMinSpeedX()) {
                    ball.setSpeedX(ball.getSpeedX() - 0.5);
                    ball.setLayoutY(ball.getLayoutY() - (-ball.getSpeedY()));
                    ball.setSpeedY(-ball.getSpeedY());
                } else if (ball.getSpeedX() < -ball.getMinSpeedX()) {
                    ball.setLayoutY(ball.getLayoutY() - (-ball.getSpeedY()));
                    ball.setSpeedY(-ball.getSpeedY());
                    ball.setSpeedX(ball.getSpeedX() + 0.5);
                } else {
                    ball.setLayoutY(ball.getLayoutY() - (-ball.getSpeedY()));
                    ball.setSpeedY(-ball.getSpeedY());
                }
            } else if (ball.getLayoutX() >= getLayoutX() + getFitWidth() * 0.75) { // touche droite
                if (ball.getSpeedX() <= -ball.getMinSpeedX() && ball.getSpeedX() >= -ball.getMaxSpeedX()) {
                    ball.setSpeedY(-ball.getSpeedY());
                    ball.setSpeedX(ball.getSpeedX() - 0.5);
                } else if (ball.getSpeedX() >= ball.getMinSpeedX() && ball.getSpeedX() <= ball.getMaxSpeedX()) {
                    ball.setSpeedX(-ball.getSpeedX() - 0.5);
                    ball.setSpeedY(-ball.getSpeedY());
                }
            }

        }

    }


    public void checkMouseRacket() { //Racket --> souris
        if (brickBreakerController.getSelectedMouse()) {
            this.getScene().setOnMouseMoved((MouseEvent event) -> {
                if (event.getSceneX() - getFitWidth() / 2 >= brickBreakerController.getWallL().getLayoutX() + brickBreakerController.getWallL().getFitWidth() * 0.74 && event.getSceneX() + getFitWidth() / 2 <= brickBreakerController.getWallR().getLayoutX() + brickBreakerController.getWallR().getFitWidth() * 0.28) {
                    setLayoutX(event.getSceneX() - getFitWidth() / 2);
                }
            });
        } else {
            this.getScene().setOnMouseMoved((MouseEvent event) -> {

            });
        }
    }

    public void checkInputRacket() {
        this.getScene().setOnKeyPressed((KeyEvent event) -> {
            if (!brickBreakerController.getSelectedMouse()) {
                if (event.getCode().equals(KeyCode.LEFT)) {
                    Timeline anim = new Timeline(new KeyFrame(Duration.millis(1000 / 144), actionEvent -> {
                        if (getLayoutX() >= brickBreakerController.getWallL().getLayoutX() + brickBreakerController.getWallL().getFitWidth() * 0.8) {
                            setLayoutX(getLayoutX() - 5);
                        }
                    }));
                    anim.setCycleCount(5);
                    anim.play();
                }
                if (event.getCode().equals(KeyCode.RIGHT)) {
                    Timeline anim2 = new Timeline(new KeyFrame(Duration.millis(1000 / 144), actionEvent -> {
                        if (getLayoutX() <= brickBreakerController.getWallR().getLayoutX() - getFitWidth() + brickBreakerController.getWallR().getFitWidth() * 0.2) {
                            setLayoutX(getLayoutX() + 5);
                        }
                    }));
                    anim2.setCycleCount(5);
                    anim2.play();
                }
            }
        });
    }

    public void resetSize() {
        this.setFitWidth(minWidth);
        this.setFitHeight(minHeight);
    }

    public void setRacketSize(double width) {
        this.setFitWidth(width);
    }

    public void setMinRacketSize(double minWidth) {
        this.minWidth = minWidth;
    }

    public double getMinWidth() {
        return minWidth;
    }

    public double getMinHeight() {
        return minHeight;
    }

    public double getMinWidthN() {
        return minWidthN;
    }

    public double getMaxWidthN() {
        return maxWidthN;
    }
}
