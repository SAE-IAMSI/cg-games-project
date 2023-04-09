package games.project.modules.statistiques.views;

import games.project.modules.statistiques.Surcouche;
import games.project.parametres.Parametres;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewStatsGlobal {

    public void affichageStatsGlobales(Stage stage) throws IOException {

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(Parametres.class.getResource("css/parametresStyle.css")));
        ImageView i = new ImageView(String.valueOf(Parametres.class.getResource("images/Background.png")));
        pane.getChildren().add(i);

        Label titre = new Label("Statistiques Globales");
        titre.setId("CGGamesTitle");
        titre.setLayoutX(350);
        titre.setLayoutY(50);

        Label joueursActifs = new Label("Nombre de joueurs actifs : ");
        joueursActifs.getStyleClass().add("texte");
        joueursActifs.setLayoutX(122);
        joueursActifs.setLayoutY(191);

        //stocker le nombre de joueurs actifs
        String s = Surcouche.recupFonction("getJoueursActifs",null);
        Label nbJoueursActifs = new Label(s);
        nbJoueursActifs.setLayoutX(284);
        nbJoueursActifs.setLayoutY(191);

        Button btnRetour = new Button("Retour");
        btnRetour.getStyleClass().add("button");
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
