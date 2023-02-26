package games.project.casse_briques.view;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.controller.BrickBreakerController;
import games.project.casse_briques.controller.GameMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;


public class ViewPause extends Pane {

    @FXML
    private ImageView btQuit;
    @FXML
    private ImageView btParameters;
    @FXML
    private ImageView btContinue;
    @FXML
    private Text quitTxt;
    @FXML
    private Text continueTxt;
    @FXML
    private Text parametersTxt;

    private final BrickBreakerController brickBreakerController;
    private final GameMenuController gameMenu;
    private final Font cFont;

    public ViewPause(BrickBreakerController brickBreakerController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Pause.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        this.brickBreakerController = brickBreakerController;
        gameMenu = new GameMenuController(brickBreakerController);
        cFont = Font.loadFont(BrickBreakerApplication.class.getResource("font/m42.TTF").toString(), 8);
        continueTxt.setFont(cFont);
        quitTxt.setFont(cFont);
        parametersTxt.setFont(cFont);
        this.brickBreakerController.setLaunched(false);
    }


    @FXML
    private void clickContinue(MouseEvent event) {
        brickBreakerController.getPauseBt().setVisible(true);
        brickBreakerController.getPauseBt().setDisable(false);
        brickBreakerController.getChildren().remove(this);
        brickBreakerController.setLaunched(true);
        brickBreakerController.setGameOn();
    }

    @FXML
    private void clickParameters(MouseEvent event) {
        brickBreakerController.getChildren().remove(this);
        gameMenu.parameterMenu();
    }

    @FXML
    private void clickQuit(MouseEvent event) {
        brickBreakerController.getChildren().remove(this);
        brickBreakerController.getPauseBt().setVisible(true);
        brickBreakerController.getPauseBt().setDisable(false);
        brickBreakerController.getInfoTxt().setVisible(true);
        gameMenu.startMenu();
    }

}
