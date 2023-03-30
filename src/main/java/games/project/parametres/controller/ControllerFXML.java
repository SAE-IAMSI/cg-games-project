package games.project.parametres.controller;

import games.project.casse_briques.BrickBreakerApplication;
import games.project.factory_fall.FactoryFall;
import games.project.koala_rock.RessourcesAccess;
import games.project.metier.manager.JeuManager;
import games.project.motron.Motron;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerFXML {
    @FXML
    private Button motronButton;
    @FXML
    private VBox vboxJeux;

    @FXML
    public void setGames(){
        List<String> jeuPath = new ArrayList<>(JeuManager.getInstance().getPaths());
        System.out.println(jeuPath);
        for (int i = 0; i < jeuPath.size(); i = i + 2) {
            final int fin = i + 1;
            Button b = new Button(jeuPath.get(i));
            b.setOnAction(actionEvent -> Platform.runLater(() -> {
                File file = new File(jeuPath.get(fin).split("\\.")[3]);
                try {
                    Application m = (Application) Class.forName(jeuPath.get(fin)).newInstance();
                    m.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
            vboxJeux.getChildren().add(b);
        }
    }
}
