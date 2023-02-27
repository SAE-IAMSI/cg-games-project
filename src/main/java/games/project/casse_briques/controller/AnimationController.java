package games.project.casse_briques.controller;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.model.BonusModel;
import games.project.casse_briques.model.Brick;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class AnimationController {

    private final Font scoreFont;
    private final Font lifeFont;
    private final BrickBreakerController brickBreakerController;
    private ChronometerController chronometerAnimation;
    private final BonusController bonus;
    private Timeline animationBonus;

    public AnimationController(BrickBreakerController brickBreakerController) {
        this.brickBreakerController = brickBreakerController;
        scoreFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 10);
        lifeFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 12);
        bonus = new BonusController(brickBreakerController);
    }

    private void initAnimationBonus(Text text) {
        chronometerAnimation = new ChronometerController(1);
        brickBreakerController.getChildren().add(text);
        chronometerAnimation.initChrono();
        chronometerAnimation.launch();

        chronometerAnimation.getIntegerProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (t1.intValue() <= 0) {
                    brickBreakerController.getChildren().remove(text);
                    chronometerAnimation.stop();
                }
            }
        });
    }

    /**
     * Affiche l'animation (+100) lors de la destruction d'une brique
     **/
    public void showScore(Brick brick) {
        Text score = new Text("+100");
        score.setFont(scoreFont);
        score.setFill(Color.WHITE);
        score.setLayoutX(brick.getLayoutX() + brick.getWidth() / 2 - 15);
        score.setLayoutY(brick.getLayoutY() + brick.getHeight() / 2);

        initAnimationBonus(score);
    }

    /**
     * Affichage d'un bonus et l'animation de descente d'un bonus après la destruction d'une brique + gestion de sa surpression
     **/
    public void showBonus(Brick brick) {
        BonusModel bn = bonus.generateBonus(); // Génère un bonus
        bn.getShapeBonus().setLayoutX(brick.getLayoutX());
        bn.getShapeBonus().setLayoutY(brick.getLayoutY());
        this.brickBreakerController.getChildren().add(bn.getShapeBonus()); //ajoute le bonus

        animationBonus = new Timeline(new KeyFrame(Duration.millis(1000 / 144), ActionEvent -> { // Gère l'animation du bonus
            bn.getShapeBonus().setLayoutY(bn.getShapeBonus().getLayoutY() + 2);
            if (bn.getShapeBonus().getBoundsInParent().intersects(brickBreakerController.getLimit().getBoundsInParent())) {
                brickBreakerController.getChildren().remove(bn.getShapeBonus()); // supprime si touche la limite
                animationBonus.stop();
            } else if (bn.getShapeBonus().getBoundsInParent().intersects(brickBreakerController.getRacket().getBoundsInParent())) {
                genericAnimation(bn.getBonusName());
                brickBreakerController.getChildren().remove(bn.getShapeBonus()); // supprime si touche la racket
                animationBonus.stop();
            }
        }));
        animationBonus.setCycleCount(Animation.INDEFINITE);
        animationBonus.play();
    }

    /**
     * Génère les animations de tous les bonus et leurs effets
     **/
    public void genericAnimation(String bonusName) {
        chronometerAnimation = new ChronometerController(1);
        Text bonusTextOutput = new Text();
        bonusTextOutput.setFont(lifeFont);
        if (bonusName == "life") {
            brickBreakerController.getPlayer().setLife(brickBreakerController.getPlayer().getLife() + 1);
            bonusTextOutput.setFill(Color.RED);
            bonusTextOutput.setText("+1");
        } else if (bonusName == "plusSize") {
            if (brickBreakerController.getRacket().getFitWidth() < brickBreakerController.getRacket().getMaxWidthN()) {
                brickBreakerController.getRacket().setFitWidth(brickBreakerController.getRacket().getFitWidth() + 20);
            }
            bonusTextOutput.setFill(Color.GREEN);
            bonusTextOutput.setText("+20");
        } else if (bonusName == "malusSize") {
            if (brickBreakerController.getRacket().getFitWidth() > brickBreakerController.getRacket().getMinWidthN()) {
                brickBreakerController.getRacket().setFitWidth(brickBreakerController.getRacket().getFitWidth() - 20);
            }
            bonusTextOutput.setFill(Color.PURPLE);
            bonusTextOutput.setText("-20");
        } else if (bonusName == "bonusTime") {
            brickBreakerController.getChronometer().addTime(10);
            bonusTextOutput.setFill(Color.YELLOW);
            bonusTextOutput.setText("+10");
        } else if (bonusName == "malusTime") {
            brickBreakerController.getChronometer().removeTime(10);
            bonusTextOutput.setFill(Color.YELLOW);
            bonusTextOutput.setText("-10");
        }
        bonusTextOutput.setLayoutX(brickBreakerController.getRacket().getLayoutX() + 20);
        bonusTextOutput.setLayoutY(brickBreakerController.getRacket().getLayoutY() - 20);
        initAnimationBonus(bonusTextOutput);
    }
}
