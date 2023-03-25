package games.project.statistiques;

import games.project.statistiques.controller.StatsController;
import games.project.statistiques.views.ViewMain;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StatsLauncher extends Application {

    @Override
    public void start(Stage stage){
        ViewMain viewMain = new ViewMain();
        viewMain.afficherMenu(stage);
    }
}
