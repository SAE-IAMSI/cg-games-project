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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class ViewSelectLevel extends Pane {
    private final Font cFont;
    private final Font btFont;
    private final Font instruFont;
    private final BrickBreakerController brickBreakerController;
    private final GameMenuController gameMenu;


    @FXML
    private Rectangle test;

    @FXML
    private Text txtLv1;
    @FXML
    private Text txtLv2;
    @FXML
    private Text txtLv3;
    @FXML
    private Text txtEasy;
    @FXML
    private Text txtNormal;
    @FXML
    private Text txtHard;
    @FXML
    private Text generateLevelText;

    @FXML
    private ImageView nextLevel;
    @FXML
    private Text instruction;
    @FXML
    private ImageView btEasy;
    @FXML
    private ImageView btNormal;
    @FXML
    private ImageView btHard;
    @FXML
    private Pane level1Pane;
    @FXML
    private Pane level2Pane;
    @FXML
    private Pane level3Pane;

    @FXML
    private Text txtRetour;

    private final GameDifficultyController gameDifficulty;


    private static boolean chooseLevel;

    public ViewSelectLevel(BrickBreakerController brickBreakerController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LevelSelect.fxml"));
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
        txtLv1.setFont(cFont);
        txtLv2.setFont(cFont);
        txtLv3.setFont(cFont);

        txtEasy.setFont(btFont);
        txtNormal.setFont(btFont);
        txtHard.setFont(btFont);
        txtRetour.setFont(cFont);

        generateLevelText.setFont(cFont);
        instruction.setFont(instruFont);

        this.brickBreakerController = brickBreakerController;
        this.gameMenu = new GameMenuController(brickBreakerController);

        gameDifficulty = new GameDifficultyController(brickBreakerController);
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
    private void clickLevel1(MouseEvent event) {
        if (!chooseLevel) {
            level1Pane.setStyle("-fx-background-color: red;-fx-background-radius: 15");
            chooseLevel = true;
            brickBreakerController.setPickedLevel("1");
            instruction.setVisible(false);
            setVisibleBt();
        } else if (chooseLevel && brickBreakerController.getPickedLevel() == "1") {
            level1Pane.setStyle("-fx-background-color: #b29476;-fx-background-radius: 15");
            chooseLevel = false;
            brickBreakerController.setPickedLevel(null);
            instruction.setVisible(true);
            setInvisibleBt();
        }
    }

    @FXML
    private void clickLevel2(MouseEvent event) {
        if (!chooseLevel) {
            level2Pane.setStyle("-fx-background-color: red;-fx-background-radius: 15");
            chooseLevel = true;
            brickBreakerController.setPickedLevel("2");
            instruction.setVisible(false);
            setVisibleBt();
        } else if (chooseLevel && brickBreakerController.getPickedLevel() == "2") {
            level2Pane.setStyle("-fx-background-color: #b29476;-fx-background-radius: 15");
            chooseLevel = false;
            brickBreakerController.setPickedLevel(null);
            instruction.setVisible(true);
            setInvisibleBt();
        }
    }

    @FXML
    private void clickLevel3(MouseEvent event) {
        if (!chooseLevel) {
            level3Pane.setStyle("-fx-background-color: red;-fx-background-radius: 15");
            chooseLevel = true;
            brickBreakerController.setPickedLevel("3");
            instruction.setVisible(false);
            setVisibleBt();
        } else if (chooseLevel && brickBreakerController.getPickedLevel() == "3") {
            level3Pane.setStyle("-fx-background-color: #b29476 ;-fx-background-radius: 15");
            chooseLevel = false;
            brickBreakerController.setPickedLevel(null);
            instruction.setVisible(true);
            setInvisibleBt();
        }
    }


    @FXML
    private void clickEasy() {
        this.brickBreakerController.getChildren().remove(this);
        this.brickBreakerController.runGame(brickBreakerController.getPickedLevel());
        this.brickBreakerController.setLaunched(true);
        this.gameDifficulty.setEasy();
    }

    @FXML
    private void clickNormal() {
        brickBreakerController.getChildren().remove(this);
        this.brickBreakerController.runGame(brickBreakerController.getPickedLevel());
        this.brickBreakerController.setLaunched(true);
        this.gameDifficulty.setMedium();
    }

    @FXML
    private void clickHard() {
        this.brickBreakerController.getChildren().remove(this);
        this.brickBreakerController.runGame(brickBreakerController.getPickedLevel());
        this.brickBreakerController.setLaunched(true);
        this.gameDifficulty.setHard();
    }


    @FXML
    private void clickT() {
        if (!chooseLevel) {
            test.setFill(Color.GREEN);
            chooseLevel = true;
            brickBreakerController.setPickedLevel("Aleatoire");
            instruction.setVisible(false);
            setVisibleBt();
        } else if (chooseLevel && brickBreakerController.getPickedLevel() == "Aleatoire") {
            test.setFill(Color.web("#684f37"));
            chooseLevel = false;
            brickBreakerController.setPickedLevel(null);
            instruction.setVisible(true);
            setInvisibleBt();
        }
    }

    //changer l'interface en appyuyant sur le button nextlvl
    @FXML
    private void nextLvL() {
        this.brickBreakerController.getChildren().remove(this);
        gameMenu.levelSelectMenu2();
    }

    @FXML
    private void backButtonOnClicked() {
        this.brickBreakerController.getChildren().remove(this);
        gameMenu.startMenu();
    }
}
