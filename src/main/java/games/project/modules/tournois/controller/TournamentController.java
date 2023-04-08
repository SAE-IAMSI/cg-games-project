package games.project.modules.tournois.controller;

import games.project.modules.tournois.TournamentApplication;
import games.project.parametres.Parametres;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
        for (int i = 0; i < 100; i++) {
            HBox box = new HBox();
            Label lab = new Label("                       Tournoi n" + i + "   nom : " + "   date debut : " + "   date fin :"
                    );
            Button bouton = new Button("detail");
            box.getChildren().add(lab);
            box.getChildren().add(bouton);
            tournaments.getChildren().add(box);
        }
    }
}
