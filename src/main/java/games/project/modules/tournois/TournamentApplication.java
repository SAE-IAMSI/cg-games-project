package games.project.modules.tournois;


import games.project.modules.tournois.controller.TournamentController;
import games.project.stockage.sql.SQLUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TournamentApplication extends Application {

    private TournamentController controller;

    @Override
    public void start(Stage stage) throws IOException {
        controller = new TournamentController();
        Scene scene = new Scene(controller, 1280, 720);
        stage.setTitle("Tournois");
        stage.setScene(scene);
        stage.setResizable(false); // Empêche le redimensionnement de la fenêtre
        stage.show();

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
