package games.project.modules.statistiques;

import games.project.modules.statistiques.views.ViewMain;
import javafx.application.Application;
import javafx.stage.Stage;

public class StatsLauncher extends Application {

    @Override
    public void start(Stage stage) {
        ViewMain viewMain = new ViewMain();
        viewMain.afficherMenu(stage);
    }
}
