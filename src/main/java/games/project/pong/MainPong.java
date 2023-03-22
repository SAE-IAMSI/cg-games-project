package games.project.pong;

import games.project.pong.controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPong extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GameController gameController = GameController.getInstance();

        Scene scene = new Scene(gameController);
        stage.setTitle("Pong");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        gameController.listener();
    }

    public static void main(String[] args) {
        launch();
    }
}