package games.project.statistiques.views;

import games.project.parametres.Parametres;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewStatsGraphs {
    public void affichageGraphiques(Stage stage){
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(Parametres.class.getResource("css/parametresStyle.css")));
        ImageView i = new ImageView(String.valueOf(Parametres.class.getResource("images/Background.png")));
        pane.getChildren().add(i);

        Label titre = new Label("Graphiques");
        titre.setId("CGGamesTitle");
        titre.setLayoutX(500);
        titre.setLayoutY(50);



        Button btnRetour = new Button("Retour");
        btnRetour.getStyleClass().add("button");
        btnRetour.setContentDisplay(ContentDisplay.CENTER);
        btnRetour.setLayoutX(64);
        btnRetour.setLayoutY(616);

        btnRetour.setOnAction(actionEvent -> {
            ViewStatsPlayers v = new ViewStatsPlayers();
            v.affichageJoueurs(stage);
        });

        pane.getChildren().addAll(titre, btnRetour);
        stage.setScene(scene);
        stage.setTitle("Module Statistiques");
        stage.setResizable(false);
        stage.show();
    }
}
