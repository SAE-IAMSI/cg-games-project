package games.project.modules.tournois.view;

import games.project.metier.entite.Jeu;
import games.project.metier.manager.JeuManager;
import games.project.modules.tournois.TournamentApplication;
import games.project.modules.tournois.controller.TournamentController;
import games.project.modules.tournois.metier.entite.Tournament;
import games.project.modules.tournois.metier.manager.TournamentManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TournamentCreationView extends AnchorPane {

    private TournamentController controller;

    @FXML
    private TextField tournamentName;
    @FXML
    private DatePicker tournamentStart;
    @FXML
    private DatePicker tournamentEnd;
    @FXML
    private TextField tournamentMax;
    @FXML
    private Pane tournamentGames;
    @FXML
    private Button createTournament;

    private List<CheckBox> gamesCheckbox;

    public TournamentCreationView(TournamentController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateTournament.fxml"));

            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.controller = controller;
        gamesCheckbox = new ArrayList<>();
        fillGames();
    }

    public void quit() {
        controller.getChildren().remove(this);
    }

    private void fillGames() {
        List<Jeu> games = JeuManager.getInstance().getJeux();
        for (int i = 0; i < games.size(); i++) {
            Jeu game = games.get(i);
            CheckBox cb = new CheckBox(game.getCode() + " - " + game.getLibelle());
            cb.setLayoutX(38.0);
            cb.setLayoutY(60.0 + i * 40.0);
            tournamentGames.getChildren().add(cb);
            gamesCheckbox.add(cb);
        }
    }

    @FXML
    public void createNewTournament() {
        try {
            String name = tournamentName.getText();
            Timestamp startDate = Timestamp.valueOf(tournamentStart.getValue().atStartOfDay());
            Timestamp endDate = Timestamp.valueOf(tournamentEnd.getValue().atStartOfDay());
            int maxParticipants = Integer.valueOf(tournamentMax.getText());
            List<Jeu> games = new ArrayList<>();
            for (CheckBox cb : gamesCheckbox) {
                if (cb.isSelected()) {
                    String code = cb.getText().split(" - ")[0];
                    Jeu game = JeuManager.getInstance().getJeu(code);
                    games.add(game);
                }
            }
            Tournament t = new Tournament(name, startDate, endDate, maxParticipants);
            t.setGames(games);
            TournamentManager.getInstance().createTournament(t);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
