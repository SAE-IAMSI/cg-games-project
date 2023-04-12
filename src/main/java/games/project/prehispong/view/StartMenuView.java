package games.project.prehispong.view;

import games.project.prehispong.MainPong;
import games.project.prehispong.controller.GameController;
import games.project.prehispong.sound.GameSound;
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
        GenericView exit = new ExitView(gameController);
        exit.setLayoutX(gameController.getPrefWidth() * 0.5 - exit.getPrefWidth() * 0.5);
        exit.setLayoutY(gameController.getPrefHeight() * 0.5 - exit.getPrefHeight() * 0.5);
        gameController.displayScreen(exit);
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

    @FXML
    private void changeSound() {
        GameSound.getInstance().changeSound();
    }

    @FXML
    private void tcp() {
        GameSound.getInstance().playTcp();
    }

    @FXML
    private void stopSound() {
        GameSound.getInstance().stop();
    }
}
