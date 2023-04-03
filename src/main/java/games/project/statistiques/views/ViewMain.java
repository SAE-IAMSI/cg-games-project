package games.project.statistiques.views;

import games.project.statistiques.StatsLauncher;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewMain extends Stage {

    public void afficherMenu(Stage stage){
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(StatsLauncher.class.getResource("css/styleStats.css")));
        pane.getStyleClass().add("fond");

        Label titre = new Label("Module Statistiques");
        titre.getStyleClass().add("titre");
        titre.setLayoutX(600);
        titre.setLayoutY(100);

        Button btnTournois = new Button("Statistiques Tournoi");
        btnTournois.getStyleClass().add("btn");
        btnTournois.setMinHeight(100);
        btnTournois.setMinWidth(300);
        btnTournois.setLayoutX(190);
        btnTournois.setLayoutY(202);

        Button btnJoueurs = new Button("Statistiques Joueurs");
        btnJoueurs.getStyleClass().add("btn");
        btnJoueurs.setMinWidth(300);
        btnJoueurs.setMinHeight(100);
        btnJoueurs.setLayoutX(190);
        btnJoueurs.setLayoutY(403);


        Button btnJeux = new Button("Statistiques par Jeux");
        btnJeux.getStyleClass().add("btn");
        btnJeux.setMinHeight(100);
        btnJeux.setMinWidth(300);
        btnJeux.setLayoutX(750);
        btnJeux.setLayoutY(202);

        Button btnGlobales = new Button("Statistiques Globales");
        btnGlobales.getStyleClass().add("btn");
        btnGlobales.setMinHeight(100);
        btnGlobales.setMinWidth(300);
        btnGlobales.setLayoutX(750);
        btnGlobales.setLayoutY(403);

        ImageView imageRetour = new ImageView(String.valueOf(StatsLauncher.class.getResource("textures/button.png")));
        imageRetour.getStyleClass().add("btnRetour");
        imageRetour.setFitHeight(33);
        imageRetour.setFitWidth(100);

        Button btnRetour = new Button("Retour");
        btnRetour.getStyleClass().add("btnRetour");
        btnRetour.setGraphic(imageRetour);
        btnRetour.setContentDisplay(ContentDisplay.CENTER);
        btnRetour.setLayoutX(64);
        btnRetour.setLayoutY(616);

        //ajout des actions pour les boutons
        btnTournois.setOnAction(actionEvent -> {
            ViewStatsTournament v = new ViewStatsTournament();
            v.affichageStatsTournoi(stage);
        });

        btnGlobales.setOnAction(actionEvent -> {
            ViewStatsGlobal v = new ViewStatsGlobal();
            v.affichageStatsGlobales(stage);
        });

        btnJeux.setOnAction(actionEvent -> {
            ViewStatsGames v = new ViewStatsGames();
            v.affichageStatsJeu(stage);
        });

        btnJoueurs.setOnAction(actionEvent -> {
            ViewStatsPlayers v = new ViewStatsPlayers();
            v.affichageJoueurs(stage);
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
