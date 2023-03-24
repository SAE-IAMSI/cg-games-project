package games.project.pong.controller;

import games.project.pong.model.Ball;
import games.project.pong.model.Racket;
import games.project.pong.view.GenericView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.security.Key;

public class GameController extends GenericView {


    @FXML
    public Rectangle bottomBar;
    @FXML
    public Rectangle topBar;
    @FXML
    public Text gameTitle;
    @FXML
    public Text p1;
    @FXML
    public Text p2;
    @FXML
    public Text scoreP1;
    @FXML
    public Text scoreP2;
    @FXML
    public Text information;
    @FXML
    public Rectangle limitL;
    @FXML
    public Rectangle limitR;


    private static GameController instance;
    private Ball ball;
    private Racket racketIA;
    private Racket racketPlayer;
    private Timeline timeline;


    private GameController() { /** **/
        super("Game.fxml");
        initGame();
        limitR.setVisible(false);
        limitL.setVisible(false);
        run();
    }

    public static GameController getInstance(){
        if(instance==null){
            instance = new GameController();
        }
        return instance;
    }


    public void run(){
        this.timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 60), actionEvent -> {
            ball.hitboxWall(topBar);
            ball.hitboxWall(bottomBar);
            hitboxLimit();
            ball.moveBall();
            racketIA.racketAI(ball,3);
            racketIA.hitboxRacket(ball);
            racketPlayer.hitboxRacket(ball);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    /** Crée et Ajoute les éléments de base **/
    private void initGame(){
        this.ball = new Ball(640,360,12);
        this.racketPlayer = new Racket(66,308,24,105);
        this.racketIA = new Racket(1190,308,24,105);

        this.getChildren().add(ball);
        this.getChildren().add(racketIA);
        this.getChildren().add(racketPlayer);
    }

    /** Reset le positionnement de tous les éléments **/
    public void resetPos(){
        this.ball.setPos(640,360);
        this.racketIA.setPos(1190,308);
        this.racketPlayer.setPos(66,308);
    }

    public void hitboxLimit(){
        if(this.limitL.getBoundsInParent().intersects(this.ball.getBoundsInParent())){
            stopLoop();
            resetPos();
            ball.resetMoveSpeed();
            //ajouter score p1
        }
        else if(this.limitR.getBoundsInParent().intersects(this.ball.getBoundsInParent())){
            stopLoop();
            resetPos();
            ball.resetMoveSpeed();
            //ajouter score p2
        }
    }

    public void displayScreen(GenericView genericView){
        this.getChildren().add(genericView);
    }
    public void removeScreen(GenericView genericView){
        this.getChildren().remove(genericView);
    }

    public void listener(){
        this.getScene().setOnKeyPressed((KeyEvent event)->{
            if(event.getCode().equals(KeyCode.UP)){
                this.racketPlayer.moveUp();
            }
            if(event.getCode().equals(KeyCode.DOWN)){
                this.racketPlayer.moveDown();
            }
            event.consume();
        });
        this.getScene().setOnMouseMoved((MouseEvent event)->{
            if(event.getSceneY()>topBar.getLayoutY() + topBar.getHeight() && event.getSceneY()<bottomBar.getLayoutY() - (bottomBar.getHeight() + racketPlayer.getHeight()*0.5)){
                this.racketPlayer.setLayoutY(event.getSceneY());
            }
            event.consume();
        });

        this.getScene().setOnMouseClicked((MouseEvent event)->{
            playLoop();
            event.consume();
        });
    }

    public void playLoop(){
        timeline.play();
        information.setVisible(false);
    }
    public void stopLoop(){
        timeline.stop();
        information.setVisible(true);
    }
    public Rectangle getTopBar() {
        return topBar;
    }

    public Rectangle getBottomBar() {
        return bottomBar;
    }

    @FXML
    public void clickMenu(){
        MenuController.startMenu();
        stopLoop();
        resetPos();
    }
}
