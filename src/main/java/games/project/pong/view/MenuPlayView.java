package games.project.pong.view;

import games.project.pong.controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MenuPlayView extends GenericView {
    public MenuPlayView() {
        super("MenuPlayView.fxml");
    }

    @FXML
    private void pvp(){
        //gameController.displayScreen(this);
        //gameController.setGameState(true);
    }

    @FXML
    private void pvi(){
        gameController.removeScreen(this);
        gameController.displayScreen(new DifficultyMenuView());
    }

    @FXML
    private void back(){
        gameController.removeScreen(this);
        gameController.displayScreen(new StartMenuView());
    }
}
