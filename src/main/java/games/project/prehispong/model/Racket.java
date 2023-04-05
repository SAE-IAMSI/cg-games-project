package games.project.prehispong.model;


import games.project.prehispong.MainPong;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Racket extends Rectangle implements Element {

    public Racket(double posX, double posY, double width, double heigh, int sensImage) {
        setPos(posX, posY);
        this.setHeight(heigh);
        this.setWidth(width);
        if (sensImage == 0) {
            Image img = new Image(MainPong.class.getResource("textures/raquetteDroit.png").toString());
            this.setFill(new ImagePattern(img));
        } else if (sensImage == 1) {
            Image img = new Image(MainPong.class.getResource("textures/raquetteGauche.png").toString());
            this.setFill(new ImagePattern(img));
        }

    }

    @Override
    public void setPos(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    /**
     * Hitbox entre "racket" et "ball"
     **/
    public void hitboxRacket(Ball ball) {

        boolean axisXReverse = ball.getLayoutX() >= this.getLayoutX();
        boolean axisX = (ball.getLayoutX() >= this.getLayoutX() + this.getWidth()) || axisXReverse;

        boolean left = this.getLayoutY() + this.getHeight() * 0.25 >= ball.getLayoutY() && axisX;
        boolean right = this.getLayoutY() + this.getHeight() * 0.75 <= ball.getLayoutY() && axisX;
        boolean middle = (this.getLayoutY() + this.getHeight() * 0.25 < ball.getLayoutY() && this.getLayoutY() * 0.75 < ball.getLayoutY()) && axisX;


        if (this.getBoundsInParent().intersects(ball.getBoundsInParent())) {

            if (left) {
                System.out.println("toucher Left");
                ball.reverseSpeedX();
                ball.increaseSpeedX(1);
                ball.increaseSpeedY(1);

                if (ball.getSpeedY() > 0) {
                    ball.reverseSpeedY();//vers le haut
                }

                ball.setPos(ball.getLayoutX() + ball.getSpeedX(), ball.getLayoutY() + ball.getSpeedY());
            } else if (right) {
                System.out.println("Toucher Right");
                ball.reverseSpeedX();
                ball.increaseSpeedX(1);
                ball.increaseSpeedY(1);
                if (ball.getSpeedY() < 0) {
                    ball.reverseSpeedY(); //vers le bas
                }
                ball.setPos(ball.getLayoutX() + ball.getSpeedX(), ball.getLayoutY() + ball.getSpeedY());

            } else if (middle) {
                System.out.println("toucher Middle");
                ball.reverseSpeedX();
                ball.increaseSpeedY(-ball.getSpeedY() * 0.15);
                ball.increaseSpeedX(2);
                ball.setPos(ball.getLayoutX() + ball.getSpeedX(), ball.getLayoutY() + ball.getSpeedY());
            }
        }
    }

    public void moveUp() {
        this.setLayoutY(this.getLayoutY() - 6);
    }

    public void moveDown() {
        this.setLayoutY(this.getLayoutY() + 6);
    }
}
