package games.project.prehispong.model;


import games.project.prehispong.MainPong;
import games.project.prehispong.controller.GameController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Racket extends Rectangle implements Element{

    public Racket(double posX, double posY,double width, double heigh, int sensImage) {
        setPos(posX,posY);
        this.setHeight(heigh);
        this.setWidth(width);
        if(sensImage==0){
            Image img = new Image(MainPong.class.getResource("textures/raquetteDroit.png").toString());
            this.setFill(new ImagePattern(img));
        }else if(sensImage==1){
            Image img = new Image(MainPong.class.getResource("textures/raquetteGauche.png").toString());
            this.setFill(new ImagePattern(img));
        }

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
                ball.increaseSpeedY(-ball.getSpeedY()*0.15);
                ball.increaseSpeedX(2);
                ball.setPos(ball.getLayoutX()+ball.getSpeedX(),ball.getLayoutY()+ball.getSpeedY());
            }
        }
    }

    public void moveUp(){
        this.setLayoutY(this.getLayoutY()-6);
    }
    public void moveDown(){
        this.setLayoutY(this.getLayoutY()+6);
    }

    /** Défini le comportement de la raquette en mode IA **/
    public void racketAI(Ball ball,int mods){

        double mvt = ball.getLayoutY()-this.getHeight()/2;
        int r1 = new Random().nextInt(0,500);

        switch (mods){
            case 0:{ //easy
                if(r1>100){
                    if(this.getLayoutY()<=mvt){
                        this.setLayoutY(this.getLayoutY()+4);
                    }
                    else{
                        this.setLayoutY(this.getLayoutY()-4);
                    }
                }
                else{
                    if(this.getLayoutY()<=mvt){
                        this.setLayoutY(this.getLayoutY()-3);
                    }
                    else{
                        this.setLayoutY(this.getLayoutY()+3);
                    }
                }

                break;
            }
            case 1:{ //normal
                if(r1>100){
                    if(this.getLayoutY()<=mvt){
                        this.setLayoutY(this.getLayoutY()+6);
                    }
                    else{
                        this.setLayoutY(this.getLayoutY()-6);
                    }
                }
                else {
                    if (this.getLayoutY() <= mvt) {
                        this.setLayoutY(this.getLayoutY() - 3);
                    } else {
                        this.setLayoutY(this.getLayoutY() + 3);
                    }
                }
                break;
            }
            case 2:{//hard
                if(r1>100){
                    if(this.getLayoutY()<=mvt){
                        this.setLayoutY(this.getLayoutY()+8);
                    }
                    else{
                        this.setLayoutY(this.getLayoutY()-8);
                    }
                }
                else {
                    if (this.getLayoutY() <= mvt) {
                        this.setLayoutY(this.getLayoutY() - 3);
                    } else {
                        this.setLayoutY(this.getLayoutY() + 3);
                    }
                }
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
