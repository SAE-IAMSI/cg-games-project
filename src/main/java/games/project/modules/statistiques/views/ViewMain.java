package games.project.modules.statistiques.views;

import games.project.parametres.Parametres;
import javafx.animation.PauseTransition;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class ViewMain extends Stage {

    public void afficherMenu(Stage stage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(Parametres.class.getResource("css/parametresStyle.css")));
        ImageView i = new ImageView(String.valueOf(Parametres.class.getResource("images/Background.png")));
        pane.getChildren().add(i);

        Label titre = new Label("Module Statistiques");
        titre.setId("CGGamesTitle");
        titre.setLayoutX(350);
        titre.setLayoutY(50);

        Label chargement = new Label("Chargement en cours... (cela peut prendre quelques secondes)");
        chargement.getStyleClass().add("texte");
        chargement.setLayoutX(400);
        chargement.setLayoutY(340);

        Button btnTournois = new Button("Statistiques Tournoi");
        btnTournois.getStyleClass().add("button");
        btnTournois.setMinHeight(100);
        btnTournois.setMinWidth(300);
        btnTournois.setLayoutX(190);
        btnTournois.setLayoutY(202);

        Button btnJoueurs = new Button("Statistiques Joueurs");
        btnJoueurs.getStyleClass().add("button");
        btnJoueurs.setMinWidth(300);
        btnJoueurs.setMinHeight(100);
        btnJoueurs.setLayoutX(190);
        btnJoueurs.setLayoutY(403);


        Button btnJeux = new Button("Statistiques par Jeux");
        btnJeux.getStyleClass().add("button");
        btnJeux.setMinHeight(100);
        btnJeux.setMinWidth(300);
        btnJeux.setLayoutX(750);
        btnJeux.setLayoutY(202);

        Button btnGlobales = new Button("Statistiques Globales");
        btnGlobales.getStyleClass().add("button");
        btnGlobales.setMinHeight(100);
        btnGlobales.setMinWidth(300);
        btnGlobales.setLayoutX(750);
        btnGlobales.setLayoutY(403);

        Button btnRetour = new Button("Retour");
        btnRetour.getStyleClass().add("button");
        btnRetour.setContentDisplay(ContentDisplay.CENTER);
        btnRetour.setLayoutX(64);
        btnRetour.setLayoutY(616);

        //ajout des actions pour les boutons
        btnTournois.setOnAction(actionEvent -> {
            pane.getChildren().add(chargement);
            ViewStatsTournament v = new ViewStatsTournament();
            v.affichageStatsTournoi(stage);
        });

        btnTournois.setOnMouseEntered(mouseEvent -> {
            pane.getChildren().add(chargement);
        });

        btnTournois.setOnMouseExited(mouseEvent -> {
            pane.getChildren().remove(chargement);
        });

        btnGlobales.setOnAction(actionEvent -> {
            pane.getChildren().add(chargement);
            ViewStatsGlobal v = new ViewStatsGlobal();
            try {
                v.affichageStatsGlobales(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnGlobales.setOnMouseEntered(mouseEvent -> {
            pane.getChildren().add(chargement);
        });

        btnGlobales.setOnMouseExited(mouseEvent -> {
            pane.getChildren().remove(chargement);
        });

        btnJeux.setOnAction(actionEvent -> {
            pane.getChildren().remove(chargement);
            ViewStatsGames v = new ViewStatsGames();
            v.affichageStatsJeu(stage);
        });

        btnJeux.setOnMouseEntered(mouseEvent -> {
            pane.getChildren().add(chargement);
        });

        btnJeux.setOnMouseExited(mouseEvent -> {
            pane.getChildren().remove(chargement);
        });

        btnJoueurs.setOnAction(actionEvent -> {
            ViewStatsPlayers v = new ViewStatsPlayers();
            try {
                v.affichageJoueurs(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnJoueurs.setOnMouseEntered(mouseEvent -> {
            pane.getChildren().add(chargement);
        });

        btnJoueurs.setOnMouseExited(mouseEvent -> {
            pane.getChildren().remove(chargement);
        });


        btnRetour.setOnAction(actionEvent -> {
            //doit appeler le launcher
        });


        pane.getChildren().addAll(titre, btnTournois, btnJoueurs, btnJeux, btnGlobales, btnRetour);
        stage.setScene(scene);
        stage.setTitle("Module Statistiques");
        stage.setResizable(false);
        stage.show();
    }

}
