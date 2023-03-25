package games.project.pong.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Ball extends Circle implements Element {

    private double speedX;
    private double speedY;

    private double defaultSpX;
    private double defaultSpY;
    public Ball(double posX, double posY, double radius,double spdX,double spdY) {
        setPos(posX, posY);
        this.setRadius(radius);
        this.setFill(Color.WHITE);
        defaultSpX=spdX;
        defaultSpY=spdY;
        speedX = spdX;
        speedY=spdY;
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
            speedY = defaultSpY;
            speedX = defaultSpX;
        }
        else{
            speedY = -defaultSpY;
            speedX = -defaultSpX;
        }
    }

    public void increaseSpeedX(double speedX ){
        if(this.speedX + speedX<30 && this.speedX>=0){
            this.speedX = this.speedX + speedX;
        }
        else if(this.speedX + speedX>-30 && this.speedX<0){
            this.speedX = this.speedX-speedX;
        }
    }

    public void increaseSpeedY(double speedY){

        if(this.speedY + speedY<30 && this.speedY>=0){
            this.speedY = this.speedY + speedY;
        }
        else if(this.speedY + speedY>-30 && this.speedY<0){
            this.speedY = this.speedY-speedY;
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