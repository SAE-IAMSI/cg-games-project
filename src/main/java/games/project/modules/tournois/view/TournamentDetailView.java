package games.project.modules.tournois.view;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Jeu;
import games.project.metier.entite.Score;
import games.project.metier.manager.JeuManager;
import games.project.metier.manager.PlayerManager;
import games.project.modules.tournois.controller.TournamentController;
import games.project.modules.tournois.metier.entite.Tournament;
import games.project.modules.tournois.metier.manager.TournamentManager;
import games.project.stockage.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class TournamentDetailView extends AnchorPane {

    private Tournament tournament;
    private TournamentController controller;

    @FXML
    private Label tournamentName;
    @FXML
    private Label tournamentStart;
    @FXML
    private Label tournamentEnd;
    @FXML
    private Label tournamentParticipants;

    @FXML
    private VBox mainLeaderboard;
    @FXML
    private ChoiceBox chosenGame;
    @FXML
    private VBox gameLeaderboard;

    @FXML
    private Button updateBtn;

    public TournamentDetailView(Tournament tournament, TournamentController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TournamentDetails.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tournament = tournament;
        this.controller = controller;
        tournamentName.setText(tournament.getLabel());
        tournamentStart.setText(TournamentController.dateFormat(tournament.getStartDate()));
        tournamentEnd.setText(TournamentController.dateFormat(tournament.getEndDate()));
        tournamentParticipants.setText(String.valueOf(tournament.getParticipants().size()));
        displayMainLeaderboard();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Jeu game : tournament.getGames()) {
            String choice = game.getCode() + " - " + game.getLibelle();
            list.add(choice);
        }
        chosenGame.setItems(list);
        chosenGame.getSelectionModel().select(0);

        checkAdmin();
    }

    private void checkAdmin() {
        if (PlayerManager.getInstance().getPlayer(Session.getInstance().getLogin()).isAdmin()) {
            updateBtn.setDisable(false);
            updateBtn.setVisible(true);
        } else {
            updateBtn.setDisable(true);
            updateBtn.setVisible(false);
        }
    }

    private void displayMainLeaderboard() {
        mainLeaderboard.getChildren().clear();
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

    private void displayGameLeaderboard(Jeu game) {
        gameLeaderboard.getChildren().clear();
        Map<Score, Integer> scores = tournament.getGameLeaderboard(game);
        Comparator<Score> comp = (o1, o2) -> scores.get(o2) - scores.get(o1);

        TreeSet<Score> sorted = new TreeSet<>(comp);
        sorted.addAll(scores.keySet());

        for (Score s : sorted) {
            HBox box = new HBox();
            box.getStyleClass().add("lbHbox");
            Label login = new Label(s.getLogin());
            login.getStyleClass().add("leaderBoard");
            HBox.setHgrow(login, Priority.ALWAYS);
            Label score = new Label(String.valueOf(s.getScore()));
            score.getStyleClass().add("leaderBoard");
            HBox.setHgrow(score, Priority.ALWAYS);
            Label point = new Label(String.valueOf(scores.get(s)));
            point.getStyleClass().add("leaderBoard");
            HBox.setHgrow(point, Priority.ALWAYS);
            box.getChildren().addAll(login, score, point);
            if (Session.getInstance().isLoggedIn(s.getLogin())) box.getStyleClass().add("myScore");
            gameLeaderboard.getChildren().add(box);
        }
    }

    @FXML
    public void chooseGame() {
        String gameCode = ((String) chosenGame.getSelectionModel().getSelectedItem()).split(" - ")[0];
        Jeu game = JeuManager.getInstance().getJeu(gameCode);
        displayGameLeaderboard(game);
    }

    @FXML
    public void quit() {
        controller.getChildren().remove(this);
    }

    @FXML void updateTournamentMenu() {
        controller.getChildren().remove(this);
        controller.getChildren().add(new TournamentUpdateView(tournament, controller));
    }
}
