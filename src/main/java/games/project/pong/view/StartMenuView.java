package games.project.pong.view;

import games.project.pong.controller.GameController;
import javafx.fxml.FXML;

public class StartMenuView extends GenericView {

    public StartMenuView() {
        super("StartMenu.fxml");
    }
    @FXML
    private void clickJouer(){
        GameController.getInstance().removeScreen(this);
        GameController.getInstance().displayScreen(new MenuPlayView());
    }

    @FXML
    private void exit(){

    }
}
