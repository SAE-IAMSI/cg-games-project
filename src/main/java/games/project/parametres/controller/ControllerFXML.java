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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private VBox vboxJeux;
    @FXML
    private Pane paneCompte;
    @FXML
    private Pane paneConnexion;
    @FXML
    private Pane paneCreationCompte;
    @FXML
    private Button buttonRetour;
    @FXML
    private ImageView imageFond;
    @FXML
    private Pane infoJeu;
    @FXML
    private HBox visuelJeu;
    @FXML
    private ImageView imageJeu;
    @FXML
    private ImageView flecheGauche;
    @FXML
    private ImageView flecheDroite;
    @FXML
    private HBox boutonJeu;

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

    @FXML
    public void lanceCompte(){
        resetPane();
        imageFond.setOpacity(0.5);
        paneCompte.setVisible(true);
        buttonRetour.setVisible(true);
        buttonRetour.setOnMouseClicked(e -> {
            resetPane();
            buttonRetour.setVisible(false);
        });
    }

    @FXML
    public void lanceConnexion(){
        resetPane();
        imageFond.setOpacity(0.5);
        paneConnexion.setVisible(true);
        buttonRetour.setOnMouseClicked(e -> lanceCompte());
    }

    @FXML
    public void lanceCreationCompte(){
        resetPane();
        imageFond.setOpacity(0.5);
        paneCreationCompte.setVisible(true);
        buttonRetour.setOnMouseClicked(e -> lanceCompte());
    }

    @FXML
    public void resetPane(){
        paneCompte.setVisible(false);
        paneConnexion.setVisible(false);
        paneCreationCompte.setVisible(false);
        imageFond.setOpacity(1);
    }

    @FXML
    public void lanceInfoJeu() {
        resetPane();
        imageFond.setOpacity(0.5);
    }
}
