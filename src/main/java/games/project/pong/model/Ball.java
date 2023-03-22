package games.project.pong.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class Ball extends Circle implements Element {

    private double speedX = -10;
    private double speedY = 10 ;

    public Ball(double posX, double posY, double radius) {
        setPos(posX, posY);
        this.setRadius(radius);
        this.setFill(Color.WHITE);
    }

    @Override
    public void setPos(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void resetMoveSpeed(){
        int a = new Random().nextInt(2);

        if(a==0){
            speedY = 10;
            speedX = -10;
        }
        else{
            speedY = -10;
            speedX = 10;
        }
    }

    public void increaseSpeedX(double speedX ){
        if(this.speedX + speedX<30 && this.speedX + speedX>-30){
            this.speedX = this.speedX + speedX;
        }
    }

    public void increaseSpeedY(double speedY){
        if(this.speedY + speedY<30 && this.speedY + speedY>-30){
            this.speedY = this.speedY + speedY;
        }
    }

    public void reverseSpeedX() {
        this.speedX = -this.speedX;
    }

    public void reverseSpeedY() {
        this.speedY = -this.speedY;
    }


    /**
     * déplace "ball" selon sa vitesse X et Y
     **/
    public void moveBall() {
        double x = this.getLayoutX() + this.getSpeedX();
        double y = this.getLayoutY() + this.getSpeedY();
        this.setPos(x, y);
    }

    /** Hitbox réaction avec les murs **/
    public void hitboxWall(Rectangle wall) {
        if(this.getBoundsInParent().intersects(wall.getBoundsInParent())){
            reverseSpeedY();
            this.setPos(this.getLayoutX(),this.getLayoutY()+this.getSpeedY()*1.2);
        }
    }
}