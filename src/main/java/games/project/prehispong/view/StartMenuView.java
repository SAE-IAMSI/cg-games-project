package games.project.prehispong.view;

import games.project.prehispong.MainPong;
import games.project.prehispong.controller.GameController;
import javafx.fxml.FXML;

import java.io.IOException;


public class StartMenuView extends GenericView {

    public StartMenuView(GameController controller) {
        super("StartMenu.fxml", controller);
    }

    @FXML
    private void clickJouer() {
        gameController.removeScreen(this);
        gameController.displayScreen(new MenuPlayView(gameController));
    }

    @FXML
    private void exit() {

    }

    @FXML
    private void clickScore() {
        gameController.displayScreen(new ScoreMenuView(gameController));
    }

    @FXML
    private void parameter() {
        gameController.removeScreen(this);
        gameController.displayScreen(new ParameterMenuView(gameController));
    }

    @FXML
    private void clickCGU() {
        try {
            Runtime.getRuntime().exec("explorer.exe " + MainPong.class.getResource("pdf/CGU.pdf"));

        } catch (IOException e) {
            try {
                Runtime.getRuntime().exec("./" + MainPong.class.getResource("pdf/CGU.pdf"));
            } catch (Exception exception) {
                e.printStackTrace();
            }
        }
    }
}
