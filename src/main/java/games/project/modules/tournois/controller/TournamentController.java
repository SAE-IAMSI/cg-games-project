package games.project.modules.tournois.controller;

import games.project.metier.manager.PlayerManager;
import games.project.modules.tournois.TournamentApplication;
import games.project.modules.tournois.view.TournamentCreationView;
import games.project.stockage.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class TournamentController extends AnchorPane {

    @FXML
    private VBox tournaments;

    @FXML
    private Button adminMode;


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
        if (Session.getInstance().isConnected()) checkAdmin();
    }

    @FXML
    public void open() {
        this.getChildren().add(new TournamentCreationView(this));
    }

    @FXML
    public void quit() {
        ((Stage) getScene().getWindow()).close();
    }

    private void checkAdmin() {
        if (PlayerManager.getInstance().getPlayer(Session.getInstance().getLogin()).isAdmin()) {
            adminMode.setDisable(false);
            adminMode.setVisible(true);
        } else {
            adminMode.setDisable(true);
            adminMode.setVisible(false);
        }
    }
}
