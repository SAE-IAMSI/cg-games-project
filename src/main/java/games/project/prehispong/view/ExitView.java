package games.project.prehispong.view;

import games.project.prehispong.controller.GameController;
import javafx.fxml.FXML;

public class ExitView extends GenericView{

    public ExitView(GameController controller) {
        super("ExitView.fxml",controller);
    }

    @FXML
    private void clickOk(){
        try {
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void clickCancel(){
        gameController.removeScreen(this);
    }
}
