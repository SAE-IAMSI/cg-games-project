package games.project.pong.view;

import games.project.pong.MainPong;
import games.project.pong.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class GenericView extends Pane {

    public static GameController gameController = GameController.getInstance();
    public GenericView(String nameView){
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
