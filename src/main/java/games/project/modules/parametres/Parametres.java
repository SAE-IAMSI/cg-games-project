package games.project.modules.parametres;

import games.project.modules.parametres.controller.ControllerFXML;
import games.project.stockage.Session;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Parametres extends Application{
    private Scene sceneParametres;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Parametres.class.getResource("parametres.fxml"));
        sceneParametres = new Scene(fxmlLoader.load());
        ControllerFXML controllerFXML = fxmlLoader.getController();

        stage.setTitle("CG Games");
        stage.setScene(sceneParametres);
        stage.show();

        controllerFXML.setGames();
    }
}
