package games.project.pong.controller;

import games.project.pong.metier.Score;
import games.project.pong.model.Ball;
import games.project.pong.model.Racket;
import games.project.pong.view.EndGameView;
import games.project.pong.view.GenericView;
import games.project.pong.view.StartMenuView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Racket racketPlayer2;
    private Racket racketPlayer1;
    private Timeline timeline;
    private Score scorePlayer1;
    private Score scorePlayer2;
    /** True quand le joueur a le focus sur l'écran de jeu, False sinon  **/
    private boolean gameState = false;
    private int difficulty = 0; // 0, 1, 2 ou 3
    private String gamemode = ""; //Ancienne instance de jeu

    private GameController() { /** **/
        super("Game.fxml");
        initGame();
        limitR.setVisible(false);
        limitL.setVisible(false);
    }

    public static GameController getInstance(){
        if(instance==null){
            instance = new GameController();
        }
        return instance;
    }



    public void run() {
        resetScore();
        setGamemode("PVP");
            this.timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 60), actionEvent -> {
                ball.hitboxWall(topBar);
                ball.hitboxWall(bottomBar);
                hitboxLimit();
                ball.moveBall();
                racketPlayer1.hitboxRacket(ball);
                racketPlayer2.hitboxRacket(ball);
                checkEndCondition();

            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            listenerKeyboard();
    }

    public void run(int difficulty){
        resetScore();
        setDifficulty(difficulty);
        setGamemode("IA");
            this.timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 60), actionEvent -> {
                ball.hitboxWall(topBar);
                ball.hitboxWall(bottomBar);
                hitboxLimit();
                ball.moveBall();
                racketPlayer2.racketAI(ball,difficulty);
                racketPlayer2.hitboxRacket(ball);
                racketPlayer1.hitboxRacket(ball);
                if(!(difficulty == 3)){checkEndCondition();}
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            listenerMouse();
    }

    /** Crée et Ajoute les éléments de base **/
    private void initGame(){
        this.ball = new Ball(640,360,12,-10,8);
        this.racketPlayer1 = new Racket(66,308,24,105);
        this.racketPlayer2 = new Racket(1190,308,24,105);

        scorePlayer1 = new Score();
        scorePlayer2 = new Score();

        scoreP1.textProperty().bind(new SimpleStringProperty("Score :").concat(scorePlayer1.getScoreProperty()));
        scoreP2.textProperty().bind(new SimpleStringProperty("Score :").concat(scorePlayer2.getScoreProperty()));

        this.getChildren().add(ball);
        this.getChildren().add(racketPlayer2);
        this.getChildren().add(racketPlayer1);
    }

    /** Reset le positionnement de tous les éléments **/
    public void resetPos(){
        this.ball.setPos(640,360);
        this.racketPlayer2.setPos(1190,308);
        this.racketPlayer1.setPos(66,308);
    }

    private void resetScore(){
        scorePlayer1.setScore(0);
        scorePlayer2.setScore(0);
    }
    public void hitboxLimit(){
        if(this.limitL.getBoundsInParent().intersects(this.ball.getBoundsInParent())){
            scorePlayer2.setScore(scorePlayer2.getScoreProperty().getValue()+1);
            endLoop();
            resetPos();
            ball.resetMoveSpeed();
            //ajouter score p1
        }
        else if(this.limitR.getBoundsInParent().intersects(this.ball.getBoundsInParent())){
            scorePlayer1.setScore(scorePlayer1.getScoreProperty().getValue()+1);
            endLoop();
            resetPos();
            ball.resetMoveSpeed();
            //ajouter score p2
        }
    }

    private void checkEndCondition(){
        if(scorePlayer1.getScoreProperty().getValue().equals(5)){
            resetScore();
            endLoop();
            displayScreen(new EndGameView("Player 1"));
        }
        else if(scorePlayer2.getScoreProperty().getValue().equals(5)){
            resetScore();
            endLoop();
            displayScreen(new EndGameView("Player 2"));
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
            if(event.getCode().equals(KeyCode.SPACE) && gameState){
                playLoop();
            }
            event.consume();
        });
    }

    public void listenerMouse(){
        this.getScene().setOnMouseMoved((MouseEvent event)->{
            if(event.getSceneY()>topBar.getLayoutY() + topBar.getHeight() && event.getSceneY()<bottomBar.getLayoutY() - (bottomBar.getHeight() + racketPlayer1.getHeight()*0.5)){
                this.racketPlayer1.setLayoutY(event.getSceneY());
            }
            event.consume();
        });
    }

    public void listenerKeyboard(){
        final List<KeyCode> acceptedCodes = Arrays.asList(KeyCode.Z, KeyCode.S, KeyCode.UP, KeyCode.DOWN);
        final Set<KeyCode> codes = new HashSet<>();
        final int DELAY = 10; // Délai en millisecondes

       Timeline timeline = new Timeline(new KeyFrame(Duration.millis(DELAY), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                boolean mvtConditionZ = racketPlayer1.getLayoutY()>topBar.getLayoutY() + topBar.getHeight();
                boolean mvtConditionS = racketPlayer1.getLayoutY()<bottomBar.getLayoutY() - (bottomBar.getHeight() + racketPlayer1.getHeight()*0.5);
                boolean mvtConditionUP = racketPlayer2.getLayoutY()>topBar.getLayoutY() + topBar.getHeight();
                boolean mvtConditionDOWN = racketPlayer2.getLayoutY()<bottomBar.getLayoutY() - (bottomBar.getHeight() + racketPlayer2.getHeight()*0.5);

                if (codes.contains(KeyCode.UP)) {
                    if(mvtConditionUP){
                        racketPlayer2.moveUp();
                    }
                }
                if (codes.contains(KeyCode.DOWN)) {
                    if(mvtConditionDOWN){
                        racketPlayer2.moveDown();
                    }
                }
                if (codes.contains(KeyCode.Z)) {
                    if(mvtConditionZ){
                        racketPlayer1.moveUp();
                    }
                }
                if (codes.contains(KeyCode.S)) {
                    if(mvtConditionS){
                        racketPlayer1.moveDown();
                    }
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        this.getScene().setOnKeyReleased((KeyEvent event) -> {
            codes.remove(event.getCode());
            if (codes.isEmpty()) {
                timeline.stop();
            }
        });
        this.getScene().setOnKeyPressed((KeyEvent event) -> {
            if (acceptedCodes.contains(event.getCode())) {
                codes.add(event.getCode());
                timeline.play();
            }
            if (event.getCode().equals(KeyCode.SPACE) && gameState) {
                playLoop();
            }
        });
        this.getScene().setOnMouseMoved((MouseEvent event)->{});
    }

    /** Lance la boucle de jeu **/
    public void playLoop(){
        timeline.play();
        information.setVisible(false);
    }

    /** Termine la boucle de jeu est reset tous les paramètres du jeu (position/vitesse objets)**/
    public void endLoop(){
        timeline.stop();
        resetPos();
        ball.resetMoveSpeed();
        information.setVisible(true);
    }
    public Rectangle getTopBar() {
        return topBar;
    }

    public Rectangle getBottomBar() {
        return bottomBar;
    }

    public String getGamemode() {
        return gamemode;
    }

    public void setGamemode(String gamemode) {
        this.gamemode = gamemode;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setGameState(boolean state){
        gameState = state;
    }
    @FXML
    public void clickMenu(){
        displayScreen(new StartMenuView());
        endLoop();
        setGameState(false);
    }
}
