package games.project.statistiques.views;

import games.project.statistiques.StatsLauncher;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewStatsGlobal {

    public void affichageStatsGlobales(Stage stage) {

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(StatsLauncher.class.getResource("css/styleStats.css")));
        pane.getStyleClass().add("fond");

        Label titre = new Label("Statistiques Globales");
        titre.getStyleClass().add("titre");
        titre.setLayoutX(600);
        titre.setLayoutY(100);

        Label joueursActifs = new Label("Nombre de joueurs actifs : ");
        joueursActifs.getStyleClass().add("texte");
        joueursActifs.setLayoutX(122);
        joueursActifs.setLayoutY(191);

        //stocker le nombre de joueurs actifs
        String s = "";
        Label nbJoueursActifs = new Label(s);
        nbJoueursActifs.setLayoutX(284);
        nbJoueursActifs.setLayoutY(191);

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

        pane.getChildren().addAll(titre, joueursActifs, nbJoueursActifs, btnRetour);
        stage.setScene(scene);
        stage.setTitle("Module Statistiques");
        stage.setResizable(false);
        stage.show();

    }
}
