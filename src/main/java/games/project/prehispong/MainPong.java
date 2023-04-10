package games.project.prehispong;

import games.project.prehispong.controller.GameController;
import games.project.prehispong.view.StartMenuView;
import games.project.stockage.sql.SQLUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPong extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GameController gameController = new GameController(null);

        Scene scene = new Scene(gameController);
        stage.setTitle("Pong");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        gameController.listener();
        gameController.displayScreen(new StartMenuView(gameController));
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