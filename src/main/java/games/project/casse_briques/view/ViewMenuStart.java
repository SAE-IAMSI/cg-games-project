package games.project.casse_briques.view;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.controller.BrickBreakerController;
import games.project.casse_briques.controller.GameMenuController;
import games.project.stockage.Session;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class ViewMenuStart extends Pane {

    @FXML
    private Text txtStart;
    @FXML
    private Text txtRules;
    @FXML
    private Text txtLeaderBoard;
    @FXML
    private Text connectText;
    @FXML
    private ImageView connectBt;

    @FXML
    private Text dcText;
    @FXML
    private ImageView dcBt;
    @FXML
    private Text playerScoreText;
    @FXML
    private ImageView playerScoreBt;
    @FXML
    private ImageView btStart;
    @FXML
    private ImageView btLeaderBoard;
    @FXML
    private ImageView btRules;
    @FXML
    private ImageView profilBt;
    @FXML
    private Text profilText;
    @FXML
    private Text cguText;
    @FXML
    private ImageView btCGU;

    private final GameMenuController gameMenu;
    private final BrickBreakerController brickBreakerController;
    private final Font cFont;
    private final Font connectFont;

    public ViewMenuStart(BrickBreakerController brickBreakerController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuStart.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        cFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 12);
        connectFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 7);
        txtStart.setFont(cFont);
        dcText.setFont(connectFont);
        cguText.setFont(cFont);
        txtRules.setFont(cFont);
        txtLeaderBoard.setFont(cFont);
        connectText.setFont(connectFont);
        playerScoreText.setFont(connectFont);
        profilText.setFont(connectFont);
        this.brickBreakerController = brickBreakerController;
        this.gameMenu = new GameMenuController(brickBreakerController);

        checkConnect();
        connectListener();
    }

    private void checkConnect() {
        if (Session.getInstance().isConnected()) {
            disableConnect();
        } else {
            enableConnect();
        }
    }

    public void showBtDisconnected() {
        connectBt.setDisable(false);
        connectBt.setVisible(true);
        connectText.setVisible(true);
        connectText.setDisable(false);

        btStart.setDisable(false);
        btRules.setDisable(false);
        btLeaderBoard.setDisable(false);
        txtStart.setDisable(false);
        txtLeaderBoard.setDisable(false);
        txtRules.setDisable(false);
        cguText.setDisable(false);
        btCGU.setDisable(false);
    }

    private void hiddeBtDisconnected() {
        connectBt.setDisable(true);
        connectBt.setVisible(false);
        connectText.setVisible(false);
        connectText.setDisable(true);

        btStart.setDisable(true);
        btRules.setDisable(true);
        btLeaderBoard.setDisable(true);
        txtStart.setDisable(true);
        txtLeaderBoard.setDisable(true);
        txtRules.setDisable(true);
        cguText.setDisable(true);
        btCGU.setDisable(true);
    }

    public void showBtConnected() {
        dcText.setVisible(true);
        dcText.setDisable(false);
        dcBt.setVisible(true);
        dcBt.setDisable(false);

        playerScoreBt.setVisible(true);
        playerScoreBt.setDisable(false);
        playerScoreText.setVisible(true);
        playerScoreText.setDisable(false);

        profilBt.setVisible(true);
        profilBt.setDisable(false);
        profilText.setVisible(true);
        profilText.setDisable(false);

        btStart.setDisable(false);
        btRules.setDisable(false);
        btLeaderBoard.setDisable(false);
        txtStart.setDisable(false);
        txtLeaderBoard.setDisable(false);
        txtRules.setDisable(false);
        cguText.setDisable(false);
        btCGU.setDisable(false);
    }

    private void hiddeBtConnected() {
        dcText.setVisible(false);
        dcText.setDisable(true);
        dcBt.setVisible(false);
        dcBt.setDisable(true);

        playerScoreBt.setVisible(false);
        playerScoreBt.setDisable(true);
        playerScoreText.setVisible(false);
        playerScoreText.setDisable(true);

        profilBt.setVisible(false);
        profilBt.setDisable(true);
        profilText.setVisible(false);
        profilText.setDisable(true);

        btStart.setDisable(true);
        btRules.setDisable(true);
        btLeaderBoard.setDisable(true);
        txtStart.setDisable(true);
        txtLeaderBoard.setDisable(true);
        txtRules.setDisable(true);
        cguText.setDisable(true);
        btCGU.setDisable(true);
    }

    private void connectListener() {
        Session session = Session.getInstance();
        session.getBooleanProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1.booleanValue()) {
                    disableConnect();
                } else {
                    enableConnect();
                }
            }
        });
    }


    private void enableConnect() {
        dcText.setVisible(false);
        dcText.setDisable(true);
        dcBt.setVisible(false);
        dcBt.setDisable(true);


        connectBt.setDisable(false);
        connectBt.setVisible(true);
        connectText.setVisible(true);
        connectText.setDisable(false);

        playerScoreBt.setVisible(false);
        playerScoreBt.setDisable(true);
        playerScoreText.setVisible(false);
        playerScoreText.setDisable(true);

        profilBt.setVisible(false);
        profilBt.setDisable(true);
        profilText.setVisible(false);
        profilText.setDisable(true);
    }

    private void disableConnect() {
        dcText.setVisible(true);
        dcText.setDisable(false);
        dcBt.setVisible(true);
        dcBt.setDisable(false);

        connectBt.setDisable(true);
        connectBt.setVisible(false);
        connectText.setVisible(false);
        connectText.setDisable(true);

        playerScoreBt.setVisible(true);
        playerScoreBt.setDisable(false);
        playerScoreText.setVisible(true);
        playerScoreText.setDisable(false);

        profilBt.setVisible(true);
        profilBt.setDisable(false);
        profilText.setVisible(true);
        profilText.setDisable(false);
    }

    @FXML
    private void clickStart(MouseEvent event) {
        brickBreakerController.getChildren().remove(this);
        gameMenu.levelSelectMenu();
    }

    @FXML
    private void clickRules(MouseEvent event) {
        brickBreakerController.getChildren().remove(this);
        gameMenu.rules();
    }

    @FXML
    private void clickLeaderBoard(MouseEvent event) {
        brickBreakerController.getChildren().remove(this);
        gameMenu.leaderBoard();
    }

    @FXML
    private void clickConnect(MouseEvent event) {
        gameMenu.connectPlayer();
        hiddeBtDisconnected();
    }

    @FXML
    private void clickDc() {
        Session.getInstance().disconnect();
    }

    @FXML
    private void clickLeaderBoardPlayer() {
        hiddeBtConnected();
        gameMenu.playerLeaderBoard();
    }

    @FXML
    private void clickProfile() {
        hiddeBtConnected();
        gameMenu.playerProfile();
    }

    @FXML
    private void clickCGU() {
        try {
            Runtime.getRuntime().exec("explorer.exe " + BrickBreakerApplication.class.getResource("pdf/CGU.pdf"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
