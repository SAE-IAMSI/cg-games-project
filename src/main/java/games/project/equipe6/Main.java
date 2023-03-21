package games.project.equipe6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader background = new FXMLLoader(Main.class.getResource("fxml/background.fxml"));
        Scene scene = new Scene(background.load(), 1280, 720);
        stage.setTitle("Equipe 6");
        stage.setScene(scene);
        stage.show();
    }
}
