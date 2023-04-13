package games.project.prehispong;

import games.project.prehispong.controller.GameController;
import games.project.prehispong.sound.GameSound;
import games.project.prehispong.view.StartMenuView;
import games.project.stockage.sql.SQLUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;


public class MainPong extends Application {
    private static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(String.valueOf(getClass().getResource("font/prehistoric.ttf")), 10);
        GameSound gameSound = GameSound.getInstance();
        gameSound.changeSound();
        GameController gameController = new GameController(null);
        Scene scene = new Scene(gameController);
        this.stage = stage;
        this.stage.setTitle("Pong");
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
        gameController.listener();
        gameController.displayScreen(new StartMenuView(gameController));
    }

    public static void main(String[] args) {
        launch();
    }

    public static void close(){
        stage.close();
    }
}