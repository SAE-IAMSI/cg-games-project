package games.project.prehispong.view;

import games.project.prehispong.controller.GameController;
import javafx.fxml.FXML;

public class DifficultyMenuView extends GenericView {
    public DifficultyMenuView(GameController controller) {
        super("DifficultyMenu.fxml", controller);
    }

    @FXML
    private void easy() {
        gameController.removeScreen(this);
        gameController.setGameState(true);
        gameController.run(0);
    }

    @FXML
    private void normal() {
        gameController.removeScreen(this);
        gameController.setGameState(true);
        gameController.run(1);
    }

    @FXML
    private void hard() {
        gameController.removeScreen(this);
        gameController.setGameState(true);
        gameController.run(2);
    }

    @FXML
    private void training() {
        gameController.removeScreen(this);
        gameController.setGameState(true);
        gameController.run(3);
    }

    @FXML
    private void back() {
        gameController.removeScreen(this);
        gameController.setGameState(false);
        gameController.displayScreen(new StartMenuView(gameController));
    }
}
