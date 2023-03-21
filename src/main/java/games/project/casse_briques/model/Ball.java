package games.project.casse_briques.model;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.controller.BrickBreakerController;
import games.project.casse_briques.controller.SoundGestionController;
import games.project.metier.entite.Player;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Ball extends Circle {
    private double minSpeedX; //valeur minimale que peut atteindre la vitesse de la balle (toujours positif)
    private double minSpeedY;

    private double speedX; //initialiser à min
    private double speedY;

    private final double maxSpeedX = 10;
    private final double maxSpeedY = 10;

    private final BrickBreakerController brickBreakerController;
    private final SoundGestionController soundGestion;

    public Ball(double minSpeedX, double minSpeedY, double radius, BrickBreakerController brickBreakerController) {
        this.minSpeedX = minSpeedX;
        this.minSpeedY = minSpeedY;
        this.speedX = minSpeedX;
        this.speedY = minSpeedY;
        this.brickBreakerController = brickBreakerController;
        soundGestion = this.brickBreakerController.getSoundGestion();
        Image image = new Image(BrickBreakerApplication.class.getResource("textures/ballWood.png").toString());
        this.setFill(new ImagePattern(image));
        this.setRadius(radius);
    }

    /**
     * Gère les collisions entre la balle et les murs (dont la limit)
     **/
    public void wallHitbox(Player player) {
        //------------Hitbox Murs-----------//
        if (getBoundsInParent().intersects(brickBreakerController.getWallL().getBoundsInParent())) { //Mur Gauche
            if (this.getLayoutX() <= brickBreakerController.getWallL().getLayoutX() + brickBreakerController.getWallL().getFitWidth() * 0.85) {
                setLayoutX(getLayoutX() + getSpeedX());
                setSpeedX(-getSpeedX());
                soundGestion.bounceBall();
            }
        }
        if (getLayoutY() <= 0) { //mur haut
            setSpeedY(-getSpeedY());
            soundGestion.bounceBall();
        }

        if (getBoundsInParent().intersects(brickBreakerController.getWallR().getBoundsInParent())) { // Mur droit
            if (getLayoutX() >= brickBreakerController.getWallR().getLayoutX() + brickBreakerController.getWallR().getFitWidth() * 0.15) {
                setLayoutX(getLayoutX() + getSpeedX());
                setSpeedX(-getSpeedX());
                soundGestion.bounceBall();
            }
        }
        //-----------Hitbox limit-----------//
        if (getBoundsInParent().intersects(brickBreakerController.getLimit().getBoundsInParent())) {
            soundGestion.loosedBall();
            player.loseLife();
            brickBreakerController.resetPosition();
            brickBreakerController.resetObjectValues();
            brickBreakerController.setGamePause();
            setSpeedY(-getSpeedY());
        }
    }

    /**
     * Définis la hitbox entre la balle et les briques
     **/
    public void brickHitbox(ArrayList<Brick> brickMemoryAdd) {
        boolean collided = false;
        for (Brick brick : brickMemoryAdd) {
            if (getBoundsInParent().intersects(brick.getBoundsInParent()) && brick.getLife() > 0 && !collided) {
                collided = true;
                boolean right = getLayoutX() > ((brick.getLayoutX() + brick.getWidth()) - getRadius());
                boolean left = getLayoutX() < (brick.getLayoutX() + getRadius());
                boolean bottom = getLayoutY() > ((brick.getLayoutY() + brick.getHeight()) - getRadius());
                boolean top = getLayoutY() < (brick.getLayoutY() + getRadius());

                if (right && !bottom && !top || left && !bottom && !top) {
                    brick.decreaseLife();
                    setLayoutX(getLayoutX() + getSpeedX());
                    setSpeedX(-getSpeedX());

                } else if (bottom || top) {
                    brick.decreaseLife();
                    setLayoutY(getLayoutY() + getSpeedY());
                    setSpeedY(-getSpeedY());
                }
            }
        }
    }

    /**
     * Remet à zéro la vitesse de la balle
     **/
    public void resetBallSpeed() {
        setSpeedX(getMinSpeedX());
        setSpeedY(getMinSpeedY());
    }

    /**
     * Réinitialise la position de la balle
     **/
    public void ballResetPosition() {
        setLayoutX(brickBreakerController.getPrefWidth() / 2);
        setLayoutY(brickBreakerController.getPrefHeight() - (getRadius() + 35)); //(+20) à changer pour racket
    }

    public double getSpeedX() {
        return this.speedX;
    }

    public double getSpeedY() {
        return this.speedY;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double getMinSpeedX() {
        return minSpeedX;
    }

    public double getMinSpeedY() {
        return minSpeedY;
    }

    public double getMaxSpeedX() {
        return maxSpeedX;
    }

    public double getMaxSpeedY() {
        return maxSpeedY;
    }

    public void setMinSpeedX(double minSpeedX) {
        this.minSpeedX = minSpeedX;
    }

    public void setMinSpeedY(double minSpeedY) {
        this.minSpeedY = minSpeedY;
    }
}
