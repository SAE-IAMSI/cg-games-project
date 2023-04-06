package games.project.statistiques.views;

import games.project.parametres.Parametres;
import games.project.statistiques.StatsLauncher;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewStatsTournament {

    public void affichageStatsTournoi(Stage stage) {

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(Parametres.class.getResource("css/parametresStyle.css")));
        ImageView i = new ImageView(String.valueOf(Parametres.class.getResource("images/Background.png")));
        pane.getChildren().add(i);

        Label titre = new Label("Statistiques Tournois");
        titre.setId("CGGamesTitle");
        titre.setLayoutX(350);
        titre.setLayoutY(50);

        Label tournoisCrees = new Label("Nombres de tournois crées  : ");
        tournoisCrees.getStyleClass().add("texte");
        tournoisCrees.setLayoutX(122);
        tournoisCrees.setLayoutY(191);

        //mettre le nombre de tournois crées
        String s = "";
        Label nbTournois = new Label(s);
        nbTournois.getStyleClass().add("texte");
        nbTournois.setLayoutX(296);
        nbTournois.setLayoutY(191);

        Label moyenTournoi = new Label("Nombre de participants moyen par tournoi : ");
        moyenTournoi.getStyleClass().add("texte");
        moyenTournoi.setLayoutX(122);
        moyenTournoi.setLayoutY(343);

        //mettre le nombre de participants moyen par tournois
        String s1 = "";
        Label avgTournoi = new Label(s1);
        avgTournoi.getStyleClass().add("texte");
        avgTournoi.setLayoutX(378);
        avgTournoi.setLayoutY(343);

        Button btnRetour = new Button("Retour");
        btnRetour.getStyleClass().add("button");
        btnRetour.setContentDisplay(ContentDisplay.CENTER);
        btnRetour.setLayoutX(64);
        btnRetour.setLayoutY(616);

        btnRetour.setOnAction(actionEvent -> {
            ViewMain v = new ViewMain();
            v.afficherMenu(stage);
        });

        pane.getChildren().addAll(titre, tournoisCrees, nbTournois, moyenTournoi, avgTournoi, btnRetour);
        stage.setScene(scene);
        stage.setTitle("Module Statistiques");
        stage.setResizable(false);
        stage.show();

    }

}
