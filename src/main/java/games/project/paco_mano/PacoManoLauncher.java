package games.project.paco_mano;

import games.project.paco_mano.controller.PacoManoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PacoManoLauncher extends Application {

    private Scene scenemenu;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PacoManoLauncher.class.getResource("pacomano.fxml"));
        scenemenu = new Scene(fxmlLoader.load());
        PacoManoController pacoManoController = fxmlLoader.getController();

        primaryStage.setTitle("PACOMANO");
        primaryStage.setScene(scenemenu);
        primaryStage.show();

    }
}
