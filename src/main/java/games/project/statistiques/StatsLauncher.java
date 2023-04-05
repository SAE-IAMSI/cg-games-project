package games.project.statistiques;

import games.project.statistiques.views.ViewMain;
import javafx.application.Application;
import javafx.stage.Stage;

public class StatsLauncher extends Application {

    @Override
    public void start(Stage stage) {
        ViewMain viewMain = new ViewMain();
        viewMain.afficherMenu(stage);
    }
}
