package games.project.casse_briques;

import games.project.casse_briques.controller.BrickBreakerController;
import games.project.casse_briques.controller.GameMenuController;
import games.project.stockage.sql.SQLUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BrickBreakerApplication extends Application {
    private BrickBreakerController brickBreakerController;
    private GameMenuController gameMenu;

    @Override
    public void start(Stage stage) throws IOException {
        brickBreakerController = new BrickBreakerController();
        gameMenu = new GameMenuController(brickBreakerController);
        Scene scene = new Scene(brickBreakerController, 1280, 720);
        stage.setTitle("Casse-Briques");
        stage.setScene(scene);
        stage.setResizable(false); // Empêche le redimensionnement de la fenêtre
        stage.show();
        gameMenu.startMenu();
        brickBreakerController.createBindings();

    }

    @Override
    public void stop() throws Exception {
        SQLUtils.getInstance().closeConnection();
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}