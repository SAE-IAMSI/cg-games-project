package games.project.parametres;

import games.project.parametres.controller.ControllerFXML;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Parametres extends Application {
    private Scene sceneParametres;

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Parametres.class.getResource("parametres.fxml"));
        sceneParametres = new Scene(fxmlLoader.load());
        ControllerFXML controllerFXML = fxmlLoader.getController();

        stage.setTitle("MOTRON");
        stage.setScene(sceneParametres);
        stage.show();
    }
}
