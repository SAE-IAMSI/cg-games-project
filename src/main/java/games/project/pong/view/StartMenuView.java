package games.project.pong.view;
import games.project.pong.MainPong;
import games.project.pong.controller.GameController;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class StartMenuView extends GenericView {

    public StartMenuView() {
        super("StartMenu.fxml");
    }

    @FXML
    private void clickJouer() {
        gameController.removeScreen(this);
        gameController.displayScreen(new MenuPlayView());
    }

    @FXML
    private void exit() {

    }

    @FXML
    private void clickScore(){
        gameController.displayScreen(new ScoreMenuView());
    }
    @FXML
    private void parameter() {
        gameController.removeScreen(this);
        gameController.displayScreen(new ParameterMenuView());
    }

    @FXML
    private void clickCGU() {
        try {
            if(Desktop.isDesktopSupported()){
                File file = new File(MainPong.class.getResource("pdf/CGU.pdf").toExternalForm());
                Desktop.getDesktop().open(file);
            }
            else{
                Runtime.getRuntime().exec("explorer.exe" + MainPong.class.getResource("pdf/CGU.pdf"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
