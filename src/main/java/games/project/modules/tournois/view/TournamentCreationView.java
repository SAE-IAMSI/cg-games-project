package games.project.modules.tournois.view;

import games.project.modules.tournois.TournamentApplication;
import games.project.modules.tournois.controller.TournamentController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class TournamentCreationView extends AnchorPane {

    private TournamentController controller;
    @FXML
    private Button adminMode;

    public TournamentCreationView(TournamentController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));

            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.controller = controller;
        adminMode.setDisable(true);

    }

    public void quit() {
        controller.getChildren().remove(this);
    }
}
