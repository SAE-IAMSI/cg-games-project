package games.project.statistiques.views;

import games.project.statistiques.StatsLauncher;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewStatsPlayers {

    public void affichageJoueurs(Stage stage){

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(StatsLauncher.class.getResource("css/styleStats.css")));
        pane.getStyleClass().add("fond");

        Label titre = new Label("Statistiques Joueurs");
        titre.getStyleClass().add("titre");
        titre.setLayoutX(600);
        titre.setLayoutY(100);

        Label total = new Label("Nombres d'inscrits : ");
        total.getStyleClass().add("texte");
        total.setLayoutX(122);
        total.setLayoutY(191);

        //mettre le total d'inscrits
        String s = "";
        Label nbTotal = new Label(s);
        nbTotal.getStyleClass().add("texte");
        nbTotal.setLayoutX(244);
        nbTotal.setLayoutY(191);

        Label dernierInscrit = new Label("Pseudo du dernier inscrit : ");
        dernierInscrit.getStyleClass().add("texte");
        dernierInscrit.setLayoutX(122);
        dernierInscrit.setLayoutY(343);

        //mettre le pseudo du dernier inscrit
        String s1 = "";
        Label pseudo = new Label(s);
        pseudo.getStyleClass().add("texte");
        pseudo.setLayoutX(244);
        pseudo.setLayoutY(191);

        Label topDep = new Label("Département avec le plus d'inscrits : ");
        topDep.getStyleClass().add("texte");
        topDep.setLayoutX(122);
        topDep.setLayoutY(476);

        //mettre le top département
        Label dep = new Label(s);
        dep.getStyleClass().add("texte");
        dep.setLayoutX(341);
        dep.setLayoutY(476);

        Label topDepScrollLabel = new Label("Classement des départements par nombre de joueurs");
        topDepScrollLabel.getStyleClass().add("texte");
        topDepScrollLabel.setLayoutX(811);
        topDepScrollLabel.setLayoutY(121);

        ScrollPane topDepScroll = new ScrollPane();
        topDepScroll.setLayoutX(775);
        topDepScroll.setLayoutY(151);
        topDepScroll.setMinHeight(435);
        topDepScroll.setMinWidth(345);
        FlowPane content = new FlowPane();
        //mettre les départements avec les nbInscrits

        //content.getChildren().addAll();
        topDepScroll.setContent(content);

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

        btnRetour.setOnAction(actionEvent -> {
            ViewMain v = new ViewMain();
            v.afficherMenu(stage);
        });

        pane.getChildren().addAll(titre,total, nbTotal, dernierInscrit, pseudo, topDep, dep, topDepScrollLabel, topDepScroll, btnRetour);
        stage.setScene(scene);
        stage.setTitle("Module Statistiques");
        stage.setResizable(false);
        stage.show();

    }
}
