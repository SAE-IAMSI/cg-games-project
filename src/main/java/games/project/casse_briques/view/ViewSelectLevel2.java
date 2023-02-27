package games.project.casse_briques.view;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.controller.BrickBreakerController;
import games.project.casse_briques.controller.GameDifficultyController;
import games.project.casse_briques.controller.GameMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class ViewSelectLevel2 extends Pane {
    private final Font cFont;
    private final Font btFont;
    private final Font instruFont;
    private final BrickBreakerController brickBreakerController;
    private final GameMenuController gameMenu;
    private final GameDifficultyController gameDifficulty;


    @FXML
    private ImageView backLevel;
    @FXML
    private Text txtLv4;
    @FXML
    private Text txtLv5;
    @FXML
    private Text txtLv6;
    @FXML
    private Text txtEasy;
    @FXML
    private Text txtNormal;
    @FXML
    private Text txtHard;
    @FXML
    private Text instruction;
    @FXML
    private ImageView btEasy;
    @FXML
    private ImageView btNormal;
    @FXML
    private ImageView btHard;
    @FXML
    private Pane level4Pane;
    @FXML
    private Pane level5Pane;
    @FXML
    private Pane level6Pane;

    private static boolean chooseLevel;

    public ViewSelectLevel2(BrickBreakerController brickBreakerController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LevelSelect2.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        chooseLevel = false;
        brickBreakerController.setPickedLevel("");

        cFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 8);
        btFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 7);
        instruFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 25);
        txtLv4.setFont(cFont);
        txtLv5.setFont(cFont);
        txtLv6.setFont(cFont);

        txtEasy.setFont(btFont);
        txtNormal.setFont(btFont);
        txtHard.setFont(btFont);

        instruction.setFont(instruFont);

        this.brickBreakerController = brickBreakerController;
        this.gameMenu = new GameMenuController(brickBreakerController);
        this.gameDifficulty = new GameDifficultyController(brickBreakerController);
    }

    private void setVisibleBt() {
        btEasy.setVisible(true);
        btNormal.setVisible(true);
        btHard.setVisible(true);
        txtEasy.setVisible(true);
        txtNormal.setVisible(true);
        txtHard.setVisible(true);

        btEasy.setDisable(false);
        btNormal.setDisable(false);
        btHard.setDisable(false);
        txtEasy.setDisable(false);
        txtNormal.setDisable(false);
        txtHard.setDisable(false);
    }

    private void setInvisibleBt() {
        btEasy.setVisible(false);
        btNormal.setVisible(false);
        btHard.setVisible(false);
        txtEasy.setVisible(false);
        txtNormal.setVisible(false);
        txtHard.setVisible(false);

        btEasy.setDisable(true);
        btNormal.setDisable(true);
        btHard.setDisable(true);
        txtEasy.setDisable(true);
        txtNormal.setDisable(true);
        txtHard.setDisable(true);
    }


    @FXML
    private void clickLevel4(MouseEvent event) {
        if (!chooseLevel) {
            level4Pane.setStyle("-fx-background-color: red ;-fx-background-radius: 15");
            chooseLevel = true;
            brickBreakerController.setPickedLevel("4");
            instruction.setVisible(false);
            setVisibleBt();
        } else if (chooseLevel && brickBreakerController.getPickedLevel() == "4") {
            level4Pane.setStyle("-fx-background-color: #b29476 ;-fx-background-radius: 15");
            chooseLevel = false;
            brickBreakerController.setPickedLevel(null);
            instruction.setVisible(true);
            setInvisibleBt();
        }

    }

    @FXML
    private void clickLevel5(MouseEvent event) {
        if (!chooseLevel) {
            level5Pane.setStyle("-fx-background-color: red;-fx-background-radius: 15");
            chooseLevel = true;
            brickBreakerController.setPickedLevel("5");
            instruction.setVisible(false);
            setVisibleBt();
        } else if (chooseLevel && brickBreakerController.getPickedLevel() == "5") {
            level5Pane.setStyle("-fx-background-color: #b29476 ;-fx-background-radius: 15");
            chooseLevel = false;
            brickBreakerController.setPickedLevel(null);
            instruction.setVisible(true);
            setInvisibleBt();
        }
    }

    @FXML
    private void clickLevel6(MouseEvent event) {
        if (!chooseLevel) {
            level6Pane.setStyle("-fx-background-color: red;-fx-background-radius: 15");
            chooseLevel = true;
            brickBreakerController.setPickedLevel("6");
            instruction.setVisible(false);
            setVisibleBt();
        } else if (chooseLevel && brickBreakerController.getPickedLevel() == "6") {
            level6Pane.setStyle("-fx-background-color: #b29476 ;-fx-background-radius: 15");
            chooseLevel = false;
            brickBreakerController.setPickedLevel(null);
            instruction.setVisible(true);
            setInvisibleBt();
        }
    }

    @FXML
    private void clickEasy() {
        this.brickBreakerController.getChildren().remove(this);
        gameDifficulty.setEasy();
        this.brickBreakerController.runGame(brickBreakerController.getPickedLevel());
        this.brickBreakerController.setLaunched(true);
    }

    @FXML
    private void clickNormal() {
        brickBreakerController.getChildren().remove(this);
        gameDifficulty.setMedium();
        this.brickBreakerController.setLaunched(true);
        this.brickBreakerController.runGame(brickBreakerController.getPickedLevel());
    }

    @FXML
    private void clickHard() {
        this.brickBreakerController.getChildren().remove(this);
        this.brickBreakerController.setLaunched(true);
        gameDifficulty.setHard();
        this.brickBreakerController.runGame(brickBreakerController.getPickedLevel());
    }

    @FXML
    private void backLevelClick() {
        this.brickBreakerController.getChildren().remove(this);
        this.gameMenu.levelSelectMenu();
    }


}

