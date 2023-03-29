package games.project.parametres.controller;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.factory_fall.FactoryFall;
import games.project.koala_rock.RessourcesAccess;
import games.project.motron.Motron;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class ControllerFXML {
    @FXML
    private Button motronButton;
    @FXML
    private VBox vboxJeux;

    @FXML
    public void setGames(){

    }

    @FXML
    protected void lanceMotron(){
        Platform.runLater(() -> {
            File file = new File("Motron.java");
            System.out.println(file.getName());
            try {
                Application m = (Application) Class.forName("games.project.motron.Motron").newInstance();
                m.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    protected void lanceKoalaRock(){
        Platform.runLater(() -> {
            try {
                new RessourcesAccess().start(new Stage());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    protected void lanceBrickBreaker(){
        Platform.runLater(() -> {
            try {
                new BrickBreakerApplication().start(new Stage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    protected void lanceFactoryFall(){
        Platform.runLater(() -> new FactoryFall().start(new Stage()));
    }
}
