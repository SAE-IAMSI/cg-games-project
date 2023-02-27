package games.project.casse_briques.view;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.controller.BrickBreakerController;
import games.project.casse_briques.controller.GameMenuController;
import games.project.casse_briques.controller.SoundGestionController;
import games.project.casse_briques.stockage.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class ViewEndScreen extends Pane {
    private final SoundGestionController soundGestion;
    private final BrickBreakerController brickBreakerController;

    @FXML
    private Text scoreTxt;
    @FXML
    private Text restartTxt;
    @FXML
    private ImageView btRestart;
    @FXML
    private Text quitTxt;
    @FXML
    private ImageView btQuit;
    @FXML
    private ImageView scoreBt;
    @FXML
    private Text btScoreText;


    private final GameMenuController gameMenu;

    public ViewEndScreen(BrickBreakerController brickBreakerController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EndScreen.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        brickBreakerController.getPauseBt().setDisable(true);
        brickBreakerController.getPauseBt().setVisible(false);

        this.brickBreakerController = brickBreakerController;
        gameMenu = new GameMenuController(brickBreakerController);
        brickBreakerController.setLaunched(false);
        soundGestion = new SoundGestionController(brickBreakerController);
        soundGestion.gameOver();

        Font cFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 30);
        scoreTxt.setFont(cFont);

        Font btFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 8);
        restartTxt.setFont(btFont);
        quitTxt.setFont(btFont);
        btScoreText.setFont(btFont);

        scoreTxt.textProperty().bind(new SimpleStringProperty("Score : ").concat(brickBreakerController.getScoreProperty()));

        initBtScore();
    }


    private void initBtScore() {
        if (Session.getInstance().isConnected()) {
            scoreBt.setOpacity(1);
            scoreBt.setDisable(false);
            btScoreText.setDisable(false);
        } else {
            scoreBt.setOpacity(0.5);
            scoreBt.setDisable(true);
            btScoreText.setDisable(true);
        }
    }

    @FXML
    private void clickRestart(MouseEvent event) {
        brickBreakerController.resetGame(brickBreakerController.getPickedLevel());
        this.brickBreakerController.getChildren().remove(this);
        brickBreakerController.setLaunched(true);
        brickBreakerController.getPauseBt().setVisible(true);
        brickBreakerController.getPauseBt().setDisable(false);
    }

    @FXML
    private void clickQuit(MouseEvent event) {
        brickBreakerController.getChildren().remove(this);
        brickBreakerController.getPauseBt().setDisable(false);
        brickBreakerController.getPauseBt().setVisible(true);
        gameMenu.startMenu();

    }

    @FXML
    private void clickPlayerLeaderBoard() {
        gameMenu.playerLeaderBoard();


    }

}
