package games.project.pong.model;


import games.project.pong.controller.GameController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Racket extends Rectangle implements Element{

    public Racket(double posX, double posY,double width, double heigh) {
        setPos(posX,posY);
        this.setHeight(heigh);
        this.setWidth(width);
        this.setFill(Color.RED);
    }

    @Override
    public void setPos(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    /** Hitbox entre "racket" et "ball" **/
    public void hitboxRacket(Ball ball){

        boolean axisXReverse = ball.getLayoutX()>=this.getLayoutX();
        boolean axisX = (ball.getLayoutX()>=this.getLayoutX()+this.getWidth()) || axisXReverse;

        boolean left = this.getLayoutY()+this.getHeight()*0.25>=ball.getLayoutY() && axisX;
        boolean right = this.getLayoutY()+this.getHeight()*0.75<=ball.getLayoutY() && axisX;
        boolean middle = (this.getLayoutY()+this.getHeight()*0.25 < ball.getLayoutY() && this.getLayoutY()*0.75<ball.getLayoutY()) && axisX;


        if(this.getBoundsInParent().intersects(ball.getBoundsInParent())){

            if(left){
                System.out.println("toucher Left");
                ball.reverseSpeedX();
                ball.increaseSpeedX(1);
                ball.increaseSpeedY(1);

                if(ball.getSpeedY()>0){
                    ball.reverseSpeedY();//vers le haut
                }

                ball.setPos(ball.getLayoutX()+ball.getSpeedX(),ball.getLayoutY()+ball.getSpeedY());
            }
            else if(right) {
                System.out.println("Toucher Right");
                ball.reverseSpeedX();
                ball.increaseSpeedX(1);
                ball.increaseSpeedY(1);
                if(ball.getSpeedY()<0){
                    ball.reverseSpeedY(); //vers le bas
                }
                ball.setPos(ball.getLayoutX()+ball.getSpeedX(),ball.getLayoutY()+ball.getSpeedY());

            }
            else if(middle){
                System.out.println("toucher Middle");
                ball.reverseSpeedX();
                ball.increaseSpeedY(-ball.getSpeedY()*0.5);
                ball.increaseSpeedX(2);
                ball.setPos(ball.getLayoutX()+ball.getSpeedX(),ball.getLayoutY()+ball.getSpeedY());
            }
        }
        //System.out.println("SpeedX :"+ball.getSpeedX());
        System.out.println("SpeedY :"+ball.getSpeedY());
    }

    public void moveUp(){
        this.setLayoutY(this.getLayoutY()-5);
    }
    public void moveDown(){
        this.setLayoutY(this.getLayoutY()+5);
    }

    public void racketAI(Ball ball,int mods){

        double mvt = ball.getLayoutY()-this.getHeight()/2;
        int r1 = new Random().nextInt(0,500);

        switch (mods){
            case 0:{ //easy
                break;
            }
            case 1:{ //normal
                break;
            }
            case 2:{//hard
                break;
            }

            case 3:{ //invincible
                if(GameController.getInstance().topBar.getLayoutY() < mvt - this.getHeight()*0.5 && GameController.getInstance().bottomBar.getLayoutY() > mvt+(this.getHeight()*0.5 + GameController.getInstance().bottomBar.getHeight())){
                    this.setLayoutY(mvt);
                }
                break;
            }

        }



        if(GameController.getInstance().topBar.getLayoutY() < mvt - this.getHeight()*0.5 && GameController.getInstance().bottomBar.getLayoutY() > mvt+(this.getHeight()*0.5 + GameController.getInstance().bottomBar.getHeight())){
                if(this.getLayoutY()+this.getWidth()*0.5<mvt){ // 8 -
                    this.setLayoutY(this.getLayoutY()+8);
                }
                else{
                    this.setLayoutY(this.getLayoutY()-8);
                }
        }
    }
}
