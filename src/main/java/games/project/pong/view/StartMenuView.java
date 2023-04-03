package games.project.pong.view;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.pong.MainPong;
import games.project.pong.controller.GameController;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


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
    private void parameter() {
        gameController.removeScreen(this);
        gameController.displayScreen(new ParameterMenuView());
    }

    @FXML
    private void clickCGU() {
        /*try {
            if(Desktop.isDesktopSupported()){
                File file = new File(MainPong.class.getClassLoader().getResourceAsStream("pdf/CGU.pdf").toString());
                Desktop.getDesktop().open(file);
            }
            else{
                Runtime.getRuntime().exec("explorer.exe" + MainPong.class.getResource("pdf/CGU.pdf"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
}
