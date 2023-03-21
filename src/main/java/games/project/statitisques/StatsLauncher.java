package games.project.statitisques;

import games.project.statitisques.controller.StatsController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StatsLauncher extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        StatsController statsController= new StatsController();
        Scene scene = new Scene(statsController, 1280, 720);
        stage.setTitle("Module Statistiques");
        stage.setScene(scene);
        stage.setResizable(false); // Empêche le redimensionnement de la fenêtre
        stage.show();
    }
}
