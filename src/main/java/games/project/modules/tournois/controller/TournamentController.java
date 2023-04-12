package games.project.modules.tournois.controller;

import games.project.metier.manager.PlayerManager;
import games.project.modules.tournois.TournamentApplication;
import games.project.modules.tournois.metier.entite.Tournament;
import games.project.modules.tournois.metier.manager.TournamentManager;
import games.project.modules.tournois.view.TournamentCreationView;
import games.project.modules.tournois.view.TournamentDetailView;
import games.project.stockage.Session;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TournamentController extends AnchorPane {

    @FXML
    private VBox tournaments;
    @FXML
    private Button createTournamentBtn;

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
        refresh();
    }

    private void checkAdmin() {
        if (PlayerManager.getInstance().getPlayer(Session.getInstance().getLogin()).isAdmin()) {
            createTournamentBtn.setDisable(false);
            createTournamentBtn.setVisible(true);
        } else {
            createTournamentBtn.setDisable(true);
            createTournamentBtn.setVisible(false);
        }
    }

    public static String dateFormat(Timestamp t) {
        String date = "";
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(t.getTime());
        String month = "";
        switch (cal.get(Calendar.MONTH)) {
            case 0:
                month = "Janvier";
                break;
            case 1:
                month = "Fevrier";
                break;
            case 2:
                month = "Mars";
                break;
            case 3:
                month = "Avril";
                break;
            case 4:
                month = "Mai";
                break;
            case 5:
                month = "Juin";
                break;
            case 6:
                month = "Juillet";
                break;
            case 7:
                month = "Aout";
                break;
            case 8:
                month = "Septembre";
                break;
            case 9:
                month = "Octobre";
                break;
            case 10:
                month = "Novembre";
                break;
            default:
                month = "Decembre";
                break;
        }
        date = cal.get(Calendar.DAY_OF_MONTH) + " " + month + " " + cal.get(Calendar.YEAR);// + " " + cal.get(Calendar.HOUR_OF_DAY) + "h" + cal.get(Calendar.MINUTE);
        return date;
    }

    @FXML
    public void createTournamentMenu() {
        this.getChildren().add(new TournamentCreationView(this));
    }

    @FXML
    public void quit() {
        ((Stage) getScene().getWindow()).close();
    }

    public void refresh() {
        String login = Session.getInstance().getLogin();
        Timestamp today = new Timestamp(new Date().getTime());
        tournaments.getChildren().clear();
        List<Tournament> actualTournaments = PlayerManager.getInstance().getPlayer(login).isAdmin() ? TournamentManager.getInstance().getByDate(today) : TournamentManager.getInstance().getByLoginAndDate(login, today);
        for (Tournament t : actualTournaments) {
            Label l = new Label(t.getLabel() + "\t" + dateFormat(t.getStartDate()) + "\t" + dateFormat(t.getEndDate()));
            l.getStyleClass().add("tournament");
            l.setOnMouseClicked(mouseEvent -> getChildren().add(new TournamentDetailView(t, this)));
            tournaments.getChildren().add(l);
        }
    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

}
