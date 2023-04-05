package games.project.prehispong.view;

import games.project.prehispong.controller.GameController;
import javafx.fxml.FXML;

public class MenuPlayView extends GenericView {
    public MenuPlayView(GameController controller) {
        super("MenuPlayView.fxml", controller);
    }

    @FXML
    private void pvp() {
        gameController.removeScreen(this);
        gameController.setGameState(true);
        gameController.run();
    }

    @FXML
    private void pvi() {
        gameController.removeScreen(this);
        gameController.displayScreen(new DifficultyMenuView(gameController));
    }

    @FXML
    private void back() {
        gameController.removeScreen(this);
        gameController.displayScreen(new StartMenuView(gameController));
    }
}
