package games.project.modules.tournois.view;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Jeu;
import games.project.modules.tournois.metier.entite.Tournament;
import games.project.stockage.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeSet;

public class TournamentDetailView extends AnchorPane {

    private Tournament tournament;

    @FXML
    private VBox mainLeaderboard;

    public TournamentDetailView(Tournament tournament) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TournamentDetails.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tournament = tournament;
        displayMainLeaderboard();
    }

    private void displayMainLeaderboard() {
        Map<AuthPlayer, Integer> points = tournament.getMainLeaderboard();
        Comparator<AuthPlayer> comp = (o1, o2) -> points.get(o2) - points.get(o1);

        TreeSet<AuthPlayer> sorted = new TreeSet<>(comp);
        sorted.addAll(points.keySet());

        for (AuthPlayer p : sorted) {
            HBox box = new HBox();
            box.getStyleClass().add("lbHbox");
            Label playerLabel = new Label(p.getLogin());
            playerLabel.getStyleClass().add("leaderBoard");
            HBox.setHgrow(playerLabel, Priority.ALWAYS);
            Label pointsLabel = new Label(points.get(p).toString());
            pointsLabel.getStyleClass().add("leaderBoard");
            HBox.setHgrow(pointsLabel, Priority.ALWAYS);
            box.getChildren().addAll(playerLabel, pointsLabel);
            if (Session.getInstance().isLoggedIn(p.getLogin())) box.getStyleClass().add("myScore");
            mainLeaderboard.getChildren().add(box);
        }
    }
}
