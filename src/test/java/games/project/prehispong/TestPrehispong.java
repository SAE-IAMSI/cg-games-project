package games.project.prehispong;

import games.project.metier.entite.Player;
import games.project.metier.entite.Score;
import games.project.prehispong.model.Ball;
import games.project.prehispong.model.Chronometer;
import games.project.prehispong.model.Racket;
import javafx.scene.shape.Rectangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPrehispong {

 /** TESTER LES HITBOX RAQUETTE / BALLE  + TESTER LES DIFFICULTEES **/

private Ball ball;
private Racket racket;
private Chronometer chronometer;
private Player player1;
private Score score;
private Rectangle limitR;

private final double h = 3;
    private final double l = 3;
 @BeforeEach()
 void init(){
     ball = new Ball(h,l,2,h,l);
     racket = new Racket(h,l,h,l,0);
     chronometer = new Chronometer();
     score = new Score("");
     player1 = new Player("test",score);
     limitR = new Rectangle();
 }

 /** tester les mouvements de la balle **//*

 @Test
    public void testBallMVT(){
        ball.moveBall();
        assertEquals(h+ball.getSpeedX(),ball.getLayoutX());
        assertEquals(l+ball.getSpeedY(),ball.getLayoutY());
 }*/

 /** Tester les déplacements de la raquette **//*
 @Test
    public void testRacketMVT(){
        racket.moveDown();
        assertEquals(h+6,racket.getLayoutY());
        racket.moveUp();
        assertEquals(l,racket.getLayoutY());
 }*/

 /** Tester les hitbox de la balle contre un mur **//*
 @Test
    public void testBallHitboxWall(){
     assertEquals(h,ball.getSpeedX());
     assertEquals(l,ball.getSpeedY());
     assertEquals(l,ball.getLayoutY());
     ball.hitboxWall(racket); //racket prend le rôle d'un mur
     assertEquals(-l,ball.getSpeedY());
     assertEquals(h,ball.getLayoutX());
     assertEquals( l + ball.getSpeedY() * 1.2,ball.getLayoutY());
 }
    @Test
    void testLimit(){ //Doit incrémenter le score du joueur de 1 point lorsque la balle touche 'limit'
        limitR.setLayoutX(50);
        limitR.setLayoutY(50);
        ball.setLayoutY(limitR.getLayoutY());
        ball.setLayoutX(limitR.getLayoutX());
        hitboxLimitReproduction();
        assertEquals(1,player1.getScore().getScore());
    }*/


/** Réplique miniature de la méthode hitboxLimit de GameController **//*
    public void hitboxLimitReproduction() {
        if (this.limitR.getBoundsInParent().intersects(this.ball.getBoundsInParent())) {
            player1.getScore().setScore(player1.getScore().scoreProperty().getValue() + 1);
            ball.resetMoveSpeed();
        }
    }*/

    /** Tester les accélérations de la balle **//*
    @Test
    void testBallIncreaseX(){
        ball.increaseSpeedX(30);
        assertEquals(h,ball.getSpeedX()); // ne peut pas aller au-delà de 30 en vitesse
        ball.increaseSpeedX(20);
        assertEquals(h+20,ball.getSpeedX());
    }
    @Test
    void testBallIncreaseY(){
        ball.increaseSpeedY(30);
        assertEquals(l,ball.getSpeedY()); // ne peut pas aller au-delà de 30 en vitesse
        ball.increaseSpeedY(20);
        assertEquals(l+20,ball.getSpeedY());
    }

    @Test
    void testBallDecreaseX(){
        ball.increaseSpeedX(-30);
        assertEquals(l-30,ball.getSpeedX());
        ball.increaseSpeedX(-30);
        assertEquals(l-30,ball.getSpeedX());// Ne doit pas changer car dépasse -30
    }

    @Test
    void testBallDecreaseY(){
        ball.increaseSpeedY(-30);
        assertEquals(l-30,ball.getSpeedY());
        ball.increaseSpeedY(-30);
        assertEquals(l-30,ball.getSpeedY());// Ne doit pas changer car dépasse -30
    }*/

}






