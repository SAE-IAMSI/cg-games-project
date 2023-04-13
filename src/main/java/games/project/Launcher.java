package games.project;

import games.project.modules.parametres.Parametres;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new Parametres().start(new Stage());
    }
}
