package games.project.casse_briques.controller;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.metier.entite.BrickBreakerPlayer;
import games.project.metier.entite.Player;
import games.project.metier.entite.Score;
import games.project.metier.manager.ScoreManager;
import games.project.casse_briques.model.Ball;
import games.project.casse_briques.model.Brick;
import games.project.casse_briques.model.Racket;
import games.project.stockage.Session;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BrickBreakerController extends AnchorPane {
    private Ball ball;
    private Font cFont;
    private Timeline timeline;
    private ChronometerController chronometer;
    private BrickBreakerPlayer player;
    private Racket racket;

    private final ArrayList<Brick> brickMemoryAdd; //prend en mémoire les bricks ajouter
    private final ArrayList<Brick> brickMemoryRemove; //prend en mémoire les bricks supprimer


    private Font cFontChrono;
    private Font cFontLife;
    private GameMenuController gameMenu;
    private LevelConstructorController levelConstructor;
    private SoundGestionController soundGestion;

    private static String pickedLevel; //Renvoie la valeur du niveau sélectionner
    private static Boolean selectedMouse = false; //Permet au joueur d'utiliser la souris pour déplacer la 'racket'
    private static boolean launched = false; //Vérifie si le jeu est lancé
    private static boolean musicState = true; //Active ou non la musique

    @FXML
    private Pane limit; // Permet de détecter si la balle est sortie de l'écran du joueur
    @FXML
    private ImageView wallL;
    @FXML
    private ImageView wallR;
    @FXML
    private Text title;
    @FXML
    private ImageView pauseBt;
    @FXML
    private Text lifeText; //af fiche vie du joueur
    @FXML
    private Text chronoText; //affiche temps restant
    @FXML
    private Text scoreText; // score du joueur courant
    @FXML
    private Text levelText;
    @FXML
    private Text deplacementText;
    @FXML
    private Text infoTxt;
    @FXML
    private Text l1Text;
    @FXML
    private Text l2Text;
    @FXML
    private Text l3Text;
    @FXML
    private Text l4Text;

    private final double ballSpeedX = 3;

    private final double ballSpeedY = 3;

    private final double racketSizeWidth = 200;

    public BrickBreakerController() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(BrickBreakerApplication.class.getResource("view/Fenetre.fxml").toString()));

            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initFxml();
        initGameClass();
        brickMemoryAdd = new ArrayList<>();
        brickMemoryRemove = new ArrayList<>();
        initObjects();
    }

    private void initFxml() {
        pauseBt.setFocusTraversable(false); //empêche le focus sur le bouton

        cFontLife = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 10);
        cFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 12);
        cFontChrono = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 15);
        Font infoFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 15);
        Font levelFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 8);

        levelText.setFont(levelFont);
        deplacementText.setFont(levelFont);

        infoTxt.setFont(infoFont);

        lifeText.setFont(cFontLife);
        l1Text.setFont(cFontLife);
        l2Text.setFont(cFontLife);
        l3Text.setFont(cFontLife);
        l4Text.setFont(cFontLife);
        title.setFont(cFontLife);

        scoreText.setFont(cFont);

        chronoText.setFont(cFontChrono);
    }

    private void initGameClass() {
        gameMenu = new GameMenuController(this);
        levelConstructor = new LevelConstructorController(this);
        soundGestion = new SoundGestionController(this);
    }


    public ImageView getPauseBt() {
        return pauseBt;
    }

    public ImageView getWallL() {
        return wallL;
    }

    public ImageView getWallR() {
        return wallR;
    }

    public BrickBreakerPlayer getPlayer() {
        return player;
    }

    public Boolean getSelectedMouse() {
        return selectedMouse;
    }

    public void setSelectedMouse(Boolean state) {
        selectedMouse = state;
    }

    public boolean getMusicState() {
        return musicState;
    }

    public void setMusicState(Boolean state) {
        musicState = state;
    }

    public Pane getLimit() {
        return limit;
    }

    public SoundGestionController getSoundGestion() {
        return soundGestion;
    }

    public ChronometerController getChronometer() {
        return this.chronometer;
    }

    public ArrayList<Brick> getBrickMemoryAdd() {
        return brickMemoryAdd;
    }

    public ArrayList<Brick> getBrickMemoryRemove() {
        return brickMemoryRemove;
    }

    public boolean getLaunched() {
        return launched;
    }

    public void setLaunched(boolean state) {
        launched = state;
    }

    public String getPickedLevel() {
        return pickedLevel;
    }

    public void setPickedLevel(String level) {
        pickedLevel = level;
    }

    private void initBall() {
        ball = new Ball(ballSpeedX, ballSpeedY, 10, this);
        this.getChildren().add(ball);
    }

    private void initRacket() {
        this.racket = new Racket(racketSizeWidth, 20, this);
        this.getChildren().add(racket);
    }

    private void initObjects() { //Initialiser tous les objets (Ex : Ball,Racket)
        initBall();
        initRacket();
    }

    private void initChronometer() { //Initialise le chronomètre
        chronometer = new ChronometerController(100); // Valeur en seconde
        chronometer.initChrono();
        chronoText.textProperty().bind(new SimpleStringProperty("").concat(chronometer.getIntegerProperty().asString()));
    }

    private void initPlayer() {
        this.player = new BrickBreakerPlayer("Anonyme", new Score("CB"), 3);
        lifeText.textProperty().bind(new SimpleStringProperty("X").concat(player.lifeProperty().asString()));
        scoreText.textProperty().bind(new SimpleStringProperty("Score : ").concat(this.player.getScore().scoreProperty().asString()));
    }

    public void resetPosition() { // Remet les objets à leurs emplacements d'origine
        ball.ballResetPosition();
        racket.racketResetPosition();
    }

    public void resetObjectValues() {
        ball.resetBallSpeed();
        racket.resetSize();
    }

    public void resetGame(String name) { //Remet le jeu à son état d'origine --> construit niveau, resetBallSpeed, Player, Chronometer, Positions des objets
        levelText.textProperty().bind(new SimpleStringProperty("Niveau : ").concat(name));
        if (name.equals("1")) {
            levelConstructor.initBrickLevel1();
        } else if (name.equals("2")) {
            levelConstructor.initBrickLevel2();
        } else if (name.equals("3")) {
            levelConstructor.initBrickLevel3();
        } else if (name.equals("Aleatoire")) {
            levelConstructor.initBrickLevelRandom();
        } else if (name.equals("4")) {
            levelConstructor.initBrickLevel4();
        } else if (name.equals("5")) {
            levelConstructor.initBrickLevel5();
        } else if (name.equals("6")) {
            levelConstructor.initBrickLevel6();
        }
        initPlayer();
        resetPosition();
        resetObjectValues();
        initChronometer();
    }

    public void createBindings() {
        this.getScene().setOnKeyReleased((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.SPACE) && launched) {
                setGameOn(); //lance les animation du jeu, lis les inputs utilisateur
            }
        });
    }

    public IntegerProperty getScoreProperty() {
        return player.getScore().scoreProperty();
    }

    private void defeatCondition() {
        if (chronometer.getTime() == 0) { // défaite temps
            setGamePause();
            gameMenu.endScreen();
        }
        if (player.getLife() == 0) { // défaite vie
            setGamePause();
            gameMenu.endScreen();
        }
    }

    /**
     * Trigger de victoire, vérifie si toutes les briques sont détruites, met à jour le leaderBoard, génère le menu de fin
     **/
    private void winCondition() {
        if (brickMemoryAdd.size() == brickMemoryRemove.size()) { //brick détruit
            setGamePause();
            registerScore();
            gameMenu.endScreen();
        }
    }

    /**
     * Enregistre le score dans la BD
     **/
    private void registerScore() {
        ScoreManager sm = ScoreManager.getInstance();
        if (Session.getInstance().isConnected()) {
            sm.createScore(player.getScore().getScore(), Session.getInstance().getLogin(), "CB");
        } else {
            sm.createScore(player.getScore().getScore(), "", "CB");
        }

    }


    /**
     * Lance le jeu, initialise la timeline et remet à 0
     **/
    public void runGame(String name) {
        launched = true;
        levelConstructor.clearBricks();
        resetGame(name);
        timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 144), actionEvent -> {
            racket.checkMouseRacket();
            racket.checkInputRacket();
            ballMovement();
            objectHitBox();
            defeatCondition();
            winCondition();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Définis le mouvement de la balle
     **/
    private void ballMovement() {
        ball.setLayoutX(ball.getLayoutX() - ball.getSpeedX());
        ball.setLayoutY(ball.getLayoutY() - ball.getSpeedY());
    }

    /**
     * Gère les hitbox des briques, des murs et de la racket
     **/
    private void objectHitBox() {
        ball.wallHitbox(player);
        ball.brickHitbox(brickMemoryAdd);
        racket.racketHitbox(ball);
    }

    /**
     * Lance la timeline et le chronomètre
     **/
    public void setGameOn() {
        infoTxt.setVisible(false);
        timeline.play();
        chronometer.launch();

    }

    /**
     * Met le jeu sur pause (stop timeline + chronomètre)
     **/
    public void setGamePause() {
        infoTxt.setVisible(true);
        timeline.stop();
        chronometer.stop();
        this.getScene().setOnMouseMoved((MouseEvent event) -> {

        });
        this.getScene().setOnKeyPressed((KeyEvent event) -> {

        });
    }

    @FXML
    /** Executer quand le joueur clic sur le bouton pause, met en pause le jeu **/
    public void pauseAction() {
        setGamePause();
        infoTxt.setVisible(false);
        pauseBt.setVisible(false);
        pauseBt.setDisable(true);
        gameMenu.pauseMenu();
    }

    public Racket getRacket() {
        this.racket = racket;
        return racket;
    }

    public Ball getBall() {
        this.ball = ball;
        return ball;
    }

    public Text getLifeText() {
        return lifeText;
    }

    public Text getInfoTxt() {
        return infoTxt;
    }
}