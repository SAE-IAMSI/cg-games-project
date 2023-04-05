package games.project.prehispong.view;

import games.project.prehispong.MainPong;
import games.project.prehispong.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class GenericView extends Pane {

    public static GameController gameController;
    public GenericView(String nameView,GameController controller){
        gameController = controller;
        try {
            FXMLLoader loader = new FXMLLoader(MainPong.class.getResource("view/"+nameView));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
