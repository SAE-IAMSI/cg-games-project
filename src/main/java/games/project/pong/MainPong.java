package games.project.pong;

import games.project.pong.controller.GameController;
import games.project.pong.view.StartMenuView;
import games.project.stockage.sql.SQLUtils;
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
        gameController.displayScreen(new StartMenuView());
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        SQLUtils.getInstance().closeConnection();
        super.stop();
    }
}