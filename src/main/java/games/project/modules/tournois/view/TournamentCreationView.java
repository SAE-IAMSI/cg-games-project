package games.project.modules.tournois.view;

import games.project.metier.entite.Jeu;
import games.project.metier.manager.JeuManager;
import games.project.modules.tournois.controller.TournamentController;
import games.project.modules.tournois.metier.entite.Tournament;
import games.project.modules.tournois.metier.manager.TournamentManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @FXML
    private Label errorMessage;

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
        controller.refresh();
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
        //Vérification qu'au moins un jeu est sélectionné.
        if (!atLeastOneGameChecked()) {
            errorMessage.setVisible(true);
            errorMessage.setText("Veuillez sélectionner au minimum un jeu.");
        } else {
            errorMessage.setText("");
            errorMessage.setVisible(false);
            String name = tournamentName.getText();
            //Vérification qu'un nom a été saisi.
            if (name == null || name.isEmpty()) {
                errorMessage.setVisible(true);
                errorMessage.setText("Veuillez saisir un nom de tournoi.");
            } else {
                errorMessage.setText("");
                errorMessage.setVisible(false);
                LocalDate start = tournamentStart.getValue();
                LocalDate end = tournamentEnd.getValue();
                //Vérification qu'une date de début et qu'une date de fin ont été saisies.
                if (start == null || end == null) {
                    errorMessage.setVisible(true);
                    errorMessage.setText("Veuillez saisir une date de débit et une date de fin");
                } else {
                    errorMessage.setText("");
                    errorMessage.setVisible(false);
                    //Vérification que la date de début est bien antérieure à la date de fin.
                    if (start.isAfter(end)) {
                        errorMessage.setText("Veuillez choisir une date de début antérieure à la date de fin.");
                        errorMessage.setVisible(true);
                        tournamentStart.setValue(null);
                        tournamentEnd.setValue(null);
                    } else {
                        Timestamp startDate = Timestamp.valueOf(start.atStartOfDay());
                        Timestamp endDate = Timestamp.valueOf(end.atStartOfDay());
                        String nbMax = tournamentMax.getText();
                        //Vérification qu'un maximum a bien été saisi.
                        if (nbMax == null || nbMax.isEmpty()) {
                            errorMessage.setText("Veuillez saisir un entier pour Participants max");
                            errorMessage.setVisible(true);
                        } else {
                            //Vérification que le maximum saisi est bien un entier.
                            try {
                                errorMessage.setText("");
                                errorMessage.setVisible(false);
                                int maxParticipants = Integer.parseInt(nbMax);
                                //Vérification que le maximum saisi est supérieur ou égal à 2.
                                if (maxParticipants < 2) {
                                    errorMessage.setText("Veuillez choisir un maximum supérieur ou égal à 2.");
                                    errorMessage.setVisible(true);
                                } else {
                                    errorMessage.setText("");
                                    errorMessage.setVisible(false);
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

                                    //Remise à zéro du formulaire
                                    tournamentName.clear();
                                    tournamentStart.setValue(null);
                                    tournamentEnd.setValue(null);
                                    tournamentMax.clear();
                                    for (CheckBox cb : gamesCheckbox) cb.setSelected(false);

                                    //Retour à l'utilisateur
                                    createTournament.setDisable(true);
                                    createTournament.setText("Tournoi créé !");
                                    TournamentController.delay(2000, () -> {
                                        createTournament.setText("Créer tournoi");
                                        createTournament.setDisable(false);
                                    });
                                }
                            } catch (NumberFormatException e) {
                                errorMessage.setText("Veuillez saisir un entier pour Participants max");
                                errorMessage.setVisible(true);
                                tournamentMax.clear();
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean atLeastOneGameChecked() {
        for (CheckBox c : gamesCheckbox) {
            if (c.isSelected()) return true;
        }
        return false;
    }
}
