package games.project.prehispong.controller;

import games.project.metier.entite.Player;
import games.project.metier.entite.Score;
import games.project.metier.manager.ScoreManager;
import games.project.prehispong.model.Ball;
import games.project.prehispong.model.Chronometer;
import games.project.prehispong.model.Racket;
import games.project.prehispong.view.EndGameView;
import games.project.prehispong.view.GenericView;
import games.project.prehispong.view.StartMenuView;
import games.project.stockage.Session;
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

import java.util.*;

public class GameController extends GenericView {


    @FXML
    public Rectangle bottomBar;
    @FXML
    public Rectangle topBar;
    @FXML
    public Text chronoText;
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

    private Ball ball;
    private Racket racketPlayer2;
    private Racket racketPlayer1;
    private Timeline timeline;
    private Player player1;
    private Player player2;
    private Chronometer chronometer;
    /**
     * True quand le joueur a le focus sur l'écran de jeu, False sinon
     **/
    private boolean gameState = false;
    private int difficulty = 0; // 0, 1, 2 ou 3
    private String gamemode = ""; // Ancien mode de jeu

    public GameController(GameController controller) { /** **/
        super("Game.fxml", controller);
        initGame();
        limitR.setVisible(false);
        limitL.setVisible(false);
    }


    public void run() {
        chronometer.reset();
        resetScore();
        setGamemode("PVP");
        this.timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 60), actionEvent -> {
            ball.hitboxWall(topBar);
            ball.hitboxWall(bottomBar);
            hitboxLimit();
            ball.moveBall();
            racketPlayer1.hitboxRacket(ball);
            racketPlayer2.hitboxRacket(ball);
            checkEndConditionPVP();

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        listenerKeyboard();
    }

    public void run(int difficulty) {
        chronometer.reset();
        resetScore();
        setDifficulty(difficulty);
        setGamemode("IA");

        this.timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 60), actionEvent -> {
            ball.hitboxWall(topBar);
            ball.hitboxWall(bottomBar);
            hitboxLimit();
            ball.moveBall();
            racketAI(racketPlayer2, ball, difficulty);
            racketPlayer2.hitboxRacket(ball);
            racketPlayer1.hitboxRacket(ball);
            if (!(difficulty == 3)) {
                checkEndConditionPVIA(difficulty);
            }
            else{
                checkEndConditionSurvival();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        listenerMouse();
    }

    /**
     * Crée et Ajoute les éléments de base
     **/
    private void initGame() {
        this.ball = new Ball(640, 360, 20, -10, 8);
        this.racketPlayer1 = new Racket(66, 308, 50, 105, 0);
        this.racketPlayer2 = new Racket(1190, 308, 50, 105, 1);
        this.chronometer = new Chronometer();
        chronometer.initChrono();
        chronoText.textProperty().bind(new SimpleStringProperty("Chronometre : ").concat(chronometer.getIntegerProperty().asString()));
        player1 = new Player("Joueur 1",new Score("PONG"));
        player2 = new Player("Joueur 2",new Score("PONG"));
        p1.setText(player1.getName());
        p2.setText(player2.getName());

        scoreP1.textProperty().bind(new SimpleStringProperty("Score :").concat(player1.getScore().scoreProperty()));
        scoreP2.textProperty().bind(new SimpleStringProperty("Score :").concat(player2.getScore().scoreProperty()));

        this.getChildren().add(ball);
        this.getChildren().add(racketPlayer2);
        this.getChildren().add(racketPlayer1);
    }

    /**
     * Reset le positionnement de tous les éléments
     **/
    public void resetPos() {
        this.ball.setPos(640, 360);
        this.racketPlayer2.setPos(1190, 308);
        this.racketPlayer1.setPos(66, 308);
    }

    private void resetScore() {
        player1.getScore().setScore(0);
        player2.getScore().setScore(0);
    }

    public void hitboxLimit() {
        if (this.limitL.getBoundsInParent().intersects(this.ball.getBoundsInParent())) {
            player2.getScore().setScore(player2.getScore().scoreProperty().getValue() + 1);
            endLoop();
            resetPos();
            ball.resetMoveSpeed();
            //ajouter score p1
        } else if (this.limitR.getBoundsInParent().intersects(this.ball.getBoundsInParent())) {
            player1.getScore().setScore(player1.getScore().scoreProperty().getValue() + 1);
            endLoop();
            resetPos();
            ball.resetMoveSpeed();
            //ajouter score p2
        }
    }

    public void registerScore() {
        if (Session.getInstance().isConnected()) {
            ScoreManager.getInstance().createScore(player1.getScore().getScore(),player1.getName(),player1.getScore().getGameCode());
        }
    }

    private void checkEndConditionPVP() {
        if (player1.getScore().scoreProperty().getValue().equals(5)) {
            resetScore();
            endLoop();
            displayScreen(new EndGameView(player1.getName(), this));
        } else if (player2.getScore().scoreProperty().getValue().equals(5)) {
            resetScore();
            endLoop();
            displayScreen(new EndGameView(player2.getName(), this));
        }
    }

    private void checkEndConditionPVIA(int difficulty){
        if (player1.getScore().scoreProperty().getValue().equals(5)) {
            resetScore();
            endLoop();
            switch (difficulty){
                case 0 -> player1.setScore(1000-chronometer.getTime()*10);
                case 1 -> player1.setScore(5000-chronometer.getTime()*10);
                case 2 -> player1.setScore(10000-chronometer.getTime()*10);
            }
            registerScore();
            displayScreen(new EndGameView(player1.getName(),player1.getScore().getScore(), this));
        } else if (player2.getScore().scoreProperty().getValue().equals(5)) {
            resetScore();
            endLoop();
            displayScreen(new EndGameView(player2.getName(),0, this));
        }
    }

    private void checkEndConditionSurvival() {
        if (player1.getScore().scoreProperty().getValue() >= 1 || player2.getScore().scoreProperty().getValue()>=1) {
            resetScore();
            endLoop();
            player1.getScore().setScore(chronometer.getTime()*100);
            registerScore();
            displayScreen(new EndGameView(player1.getName(),player1.getScore().getScore(), this));
        }
    }

    public void displayScreen(GenericView genericView) {
        this.getChildren().add(genericView);
    }

    public void removeScreen(GenericView genericView) {
        this.getChildren().remove(genericView);
    }

    public void listener() {
        this.getScene().setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.SPACE) && gameState) {
                playLoop();
            }
            event.consume();
        });
    }

    public void listenerMouse() {
        this.getScene().setOnMouseMoved((MouseEvent event) -> {
            if (event.getSceneY() > topBar.getLayoutY() + topBar.getHeight() && event.getSceneY() < bottomBar.getLayoutY() - (bottomBar.getHeight() + racketPlayer1.getHeight() * 0.5)) {
                this.racketPlayer1.setLayoutY(event.getSceneY());
            }
            event.consume();
        });
        listener();
        this.getScene().setOnKeyReleased(keyEvent -> {
        });
    }

    public void listenerKeyboard() {
        final List<KeyCode> acceptedCodes = Arrays.asList(KeyCode.Z, KeyCode.S, KeyCode.UP, KeyCode.DOWN);
        final Set<KeyCode> codes = new HashSet<>();
        final int DELAY = 10; // Délai en millisecondes

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(DELAY), new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                boolean mvtConditionZ = racketPlayer1.getLayoutY() > topBar.getLayoutY() + topBar.getHeight();
                boolean mvtConditionS = racketPlayer1.getLayoutY() < bottomBar.getLayoutY() - (bottomBar.getHeight() + racketPlayer1.getHeight() * 0.5);
                boolean mvtConditionUP = racketPlayer2.getLayoutY() > topBar.getLayoutY() + topBar.getHeight();
                boolean mvtConditionDOWN = racketPlayer2.getLayoutY() < bottomBar.getLayoutY() - (bottomBar.getHeight() + racketPlayer2.getHeight() * 0.5);

                if (codes.contains(KeyCode.UP)) {
                    if (mvtConditionUP) {
                        racketPlayer2.moveUp();
                    }
                }
                if (codes.contains(KeyCode.DOWN)) {
                    if (mvtConditionDOWN) {
                        racketPlayer2.moveDown();
                    }
                }
                if (codes.contains(KeyCode.Z)) {
                    if (mvtConditionZ) {
                        racketPlayer1.moveUp();
                    }
                }
                if (codes.contains(KeyCode.S)) {
                    if (mvtConditionS) {
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
        this.getScene().setOnMouseMoved((MouseEvent event) -> {
        });
    }


    /**
     * Défini le comportement de la raquette en mode IA
     **/
    public void racketAI(Racket racket, Ball ball, int mods) {

        double mvt = ball.getLayoutY() - racket.getHeight() / 2;
        int r1 = new Random().nextInt(0, 500);

        switch (mods) {
            case 0: { //easy
                if (r1 > 100) {
                    if (racket.getLayoutY() <= mvt) {
                        racket.setLayoutY(racket.getLayoutY() + 4);
                    } else {
                        racket.setLayoutY(racket.getLayoutY() - 4);
                    }
                } else {
                    if (racket.getLayoutY() <= mvt) {
                        racket.setLayoutY(racket.getLayoutY() - 3);
                    } else {
                        racket.setLayoutY(racket.getLayoutY() + 3);
                    }
                }

                break;
            }
            case 1: { //normal
                if (r1 > 100) {
                    if (racket.getLayoutY() <= mvt) {
                        racket.setLayoutY(racket.getLayoutY() + 6);
                    } else {
                        racket.setLayoutY(racket.getLayoutY() - 6);
                    }
                } else {
                    if (racket.getLayoutY() <= mvt) {
                        racket.setLayoutY(racket.getLayoutY() - 3);
                    } else {
                        racket.setLayoutY(racket.getLayoutY() + 3);
                    }
                }
                break;
            }
            case 2: {//hard
                if (r1 > 100) {
                    if (racket.getLayoutY() <= mvt) {
                        racket.setLayoutY(racket.getLayoutY() + 8);
                    } else {
                        racket.setLayoutY(racket.getLayoutY() - 8);
                    }
                } else {
                    if (racket.getLayoutY() <= mvt) {
                        racket.setLayoutY(racket.getLayoutY() - 3);
                    } else {
                        racket.setLayoutY(racket.getLayoutY() + 3);
                    }
                }
                break;
            }

            case 3: { //invincible
                if (this.topBar.getLayoutY() < mvt - racket.getHeight() * 0.5 && this.bottomBar.getLayoutY() > mvt + (racket.getHeight() * 0.5 + this.bottomBar.getHeight())) {
                    racket.setLayoutY(mvt);
                }
                break;
            }

        }


        if (this.topBar.getLayoutY() < mvt - racket.getHeight() * 0.5 && this.bottomBar.getLayoutY() > mvt + (racket.getHeight() * 0.5 + this.bottomBar.getHeight())) {
            if (racket.getLayoutY() + racket.getWidth() * 0.5 < mvt) { // 8 -
                racket.setLayoutY(racket.getLayoutY() + 8);
            } else {
                racket.setLayoutY(racket.getLayoutY() - 8);
            }
        }
    }


    /**
     * Lance la boucle de jeu
     **/
    public void playLoop() {
        timeline.play();
        information.setVisible(false);
        if(gamemode.equals("IA")){
            chronometer.launch();
        }
    }

    /**
     * Termine la boucle de jeu est reset tous les paramètres du jeu (position/vitesse objets)
     **/
    public void endLoop() {
        timeline.stop();
        resetPos();
        ball.resetMoveSpeed();
        information.setVisible(true);
        chronometer.stop();
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

    public Player getPlayer1() {
        return player1;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setGameState(boolean state) {
        gameState = state;
    }

    @FXML
    public void clickMenu() {
        displayScreen(new StartMenuView(this));
        endLoop();
        setGameState(false);
    }
}
