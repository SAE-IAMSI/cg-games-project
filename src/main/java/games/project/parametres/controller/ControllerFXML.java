package games.project.parametres.controller;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.casse_briques.MainArtifacts;
import games.project.factory_fall.FactoryFall;
import games.project.koala_rock.RessourcesAccess;
import games.project.motron.Motron;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.FileSystems;

public class ControllerFXML {
    @FXML
    private Button motronButton;

    @FXML
    protected void lanceMotron(){
        Platform.runLater(() -> {
            try {
                new Motron().start(new Stage());
            } catch (IOException e) {
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
        Platform.runLater(() -> {
            new FactoryFall().start(new Stage());
        });
    }
}
