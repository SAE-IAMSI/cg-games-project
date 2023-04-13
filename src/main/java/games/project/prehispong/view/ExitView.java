package games.project.prehispong.view;

import games.project.prehispong.MainPong;
import games.project.prehispong.controller.GameController;
import javafx.fxml.FXML;

public class ExitView extends GenericView {

    public ExitView(GameController controller) {
        super("ExitView.fxml", controller);
    }

    @FXML
    private void clickOk() {
        try {
            MainPong.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickCancel() {
        gameController.removeScreen(this);
    }
}
