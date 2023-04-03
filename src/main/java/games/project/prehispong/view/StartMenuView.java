package games.project.prehispong.view;
import games.project.prehispong.MainPong;
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
            Runtime.getRuntime().exec("explorer.exe " + MainPong.class.getResource("pdf/CGU.pdf"));

        } catch (IOException e) {
            try {
                 Runtime.getRuntime().exec("./"+MainPong.class.getResource("pdf/CGU.pdf"));
            }catch (Exception exception){
                e.printStackTrace();
            }
        }
    }
}
