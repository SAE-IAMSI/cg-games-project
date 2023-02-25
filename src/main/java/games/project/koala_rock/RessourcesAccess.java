package games.project.koala_rock;

import javafx.application.Application;
import javafx.stage.Stage;
import games.project.koala_rock.Sound.Son;
import games.project.koala_rock.View.VueDemarrage;

import java.io.IOException;


public class RessourcesAccess extends Application {

    private final VueDemarrage vueDemarrage = new VueDemarrage();

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        vueDemarrage.screenStart();
        Son.playMusic();
    }

}
