package games.project.modules.statistiques;

import games.project.modules.statistiques.views.ViewMain;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class StatsLauncher extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ViewMain viewMain = new ViewMain();
        viewMain.afficherMenu(stage);
    }
}
