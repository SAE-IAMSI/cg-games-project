package games.project.prehispong.view;

import javafx.fxml.FXML;

public class MenuPlayView extends GenericView {
    public MenuPlayView() {
        super("MenuPlayView.fxml");
    }

    @FXML
    private void pvp(){
        gameController.removeScreen(this);
        gameController.setGameState(true);
        gameController.run();
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
