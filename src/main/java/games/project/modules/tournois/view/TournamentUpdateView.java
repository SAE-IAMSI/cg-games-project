package games.project.modules.tournois.view;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Jeu;
import games.project.metier.manager.JeuManager;
import games.project.metier.manager.PlayerManager;
import games.project.modules.tournois.controller.TournamentController;
import games.project.modules.tournois.metier.entite.Tournament;
import games.project.modules.tournois.metier.manager.TournamentManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TournamentUpdateView extends AnchorPane {

    private TournamentController controller;
    private Tournament tournament;

    @FXML
    private TextField tournamentName;
    @FXML
    private DatePicker tournamentStart;
    @FXML
    private DatePicker tournamentEnd;
    @FXML
    private TextField tournamentMax;
    @FXML
    private TextField player;
    @FXML
    private Button updateTournamentBtn;
    @FXML
    private Button registerPlayerBtn;
    @FXML
    private Label errorMessage;


    public TournamentUpdateView(Tournament t, TournamentController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateTournament.fxml"));

            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.controller = controller;
        this.tournament = t;

        tournamentName.setText(tournament.getLabel());
        tournamentStart.setValue(tournament.getStartDate().toLocalDateTime().toLocalDate());
        tournamentEnd.setValue(tournament.getEndDate().toLocalDateTime().toLocalDate());
        tournamentMax.setText(String.valueOf(tournament.getMaxParticipants()));
    }

    public void quit() {
        controller.getChildren().remove(this);
        controller.getChildren().add(new TournamentDetailView(tournament, controller));
    }

    @FXML
    public void updateTournament() {
        String name = tournamentName.getText();
        //Vérification qu'un nom a été saisi.
        if (name == null || name.isEmpty()) {
            errorMessage.setVisible(true);
            errorMessage.setText("Veuillez saisir un nom de tournoi.");
            tournamentName.setText(tournament.getLabel());
        } else {
            errorMessage.setText("");
            errorMessage.setVisible(false);
            LocalDate start = tournamentStart.getValue();
            LocalDate end = tournamentEnd.getValue();
            //Vérification qu'une date de début et qu'une date de fin ont été saisies.
            if (start == null || end == null) {
                errorMessage.setVisible(true);
                errorMessage.setText("Veuillez saisir une date de débit et une date de fin");
                tournamentStart.setValue(tournament.getStartDate().toLocalDateTime().toLocalDate());
                tournamentEnd.setValue(tournament.getEndDate().toLocalDateTime().toLocalDate());
            } else {
                errorMessage.setText("");
                errorMessage.setVisible(false);
                //Vérification que la date de début est postérieure à la date actuelle.
                if (start.isBefore(tournament.getStartDate().toLocalDateTime().toLocalDate())) {
                    errorMessage.setText("Veuillez choisir une date de début postérieure à la date actuelle de début de tournoi.");
                    errorMessage.setVisible(true);
                    tournamentStart.setValue(tournament.getStartDate().toLocalDateTime().toLocalDate());
                } else {
                    errorMessage.setText("");
                    errorMessage.setVisible(false);
                    //Vérification que la date de début est bien antérieure à la date de fin.
                    if (start.isAfter(end)) {
                        errorMessage.setText("Veuillez choisir une date de début antérieure à la date de fin.");
                        errorMessage.setVisible(true);
                        tournamentStart.setValue(tournament.getStartDate().toLocalDateTime().toLocalDate());
                        tournamentEnd.setValue(tournament.getEndDate().toLocalDateTime().toLocalDate());
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
                                    tournamentMax.setText(String.valueOf(tournament.getMaxParticipants()));
                                } else {
                                    errorMessage.setText("");
                                    errorMessage.setVisible(false);
                                    TournamentManager.getInstance().updateTournament(tournament.getTournamentCode(), name, startDate, endDate, maxParticipants);
                                    tournament = TournamentManager.getInstance().getByCode(tournament.getTournamentCode());

                                    //Remise à zéro du formulaire
                                    tournamentName.setText(name);
                                    tournamentStart.setValue(start);
                                    tournamentEnd.setValue(end);
                                    tournamentMax.setText(String.valueOf(maxParticipants));

                                    //Retour à l'utilisateur
                                    updateTournamentBtn.setDisable(true);
                                    updateTournamentBtn.setText("Tournoi édité !");
                                    TournamentController.delay(2000, () -> {
                                        updateTournamentBtn.setText("Modifier tournoi");
                                        updateTournamentBtn.setDisable(false);
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

    @FXML
    public void registerPlayer() {
        String login = player.getText();
        if (login == null || login.isEmpty()) {
            errorMessage.setText("Veuillez saisir un login de joueur.");
            errorMessage.setVisible(true);
        } else {
            errorMessage.setText("");
            errorMessage.setVisible(false);
            List<AuthPlayer> players = PlayerManager.getInstance().getPlayers();
            if (!players.stream().map((p) -> p.getLogin()).anyMatch(Predicate.isEqual(login))) {
                errorMessage.setText("Veuillez saisir un login existant.");
                errorMessage.setVisible(true);
                player.clear();
            } else {
                errorMessage.setText("");
                errorMessage.setVisible(false);
                AuthPlayer p = PlayerManager.getInstance().getPlayer(login);
                tournament.registerPlayer(p);
                TournamentManager.getInstance().addParticipant(p, tournament.getTournamentCode());

                player.clear();
                registerPlayerBtn.setDisable(true);
                registerPlayerBtn.setText("Joueur " + login + " inscrit !");
                TournamentController.delay(2000, () -> {
                    registerPlayerBtn.setText("Inscrire");
                    registerPlayerBtn.setDisable(false);
                });
            }
        }
    }
}
