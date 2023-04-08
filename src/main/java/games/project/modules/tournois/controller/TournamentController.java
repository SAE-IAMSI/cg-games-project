package games.project.modules.tournois.controller;

import games.project.modules.tournois.TournamentApplication;
import games.project.parametres.Parametres;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class TournamentController extends AnchorPane {

    @FXML
    private VBox tournaments;

    public TournamentController() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(TournamentApplication.class.getResource("view/MainPage.fxml").toString()));

            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) tournaments.getChildren().add(new Label("TEST"));
    }
}
