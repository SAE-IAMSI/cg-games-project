package games.project.modules.statistiques.views;

import games.project.modules.statistiques.Surcouche;
import games.project.parametres.Parametres;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewStatsGames {

    public void affichageStatsJeu(Stage stage) throws IOException {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(Parametres.class.getResource("css/parametresStyle.css")));
        ImageView i = new ImageView(String.valueOf(Parametres.class.getResource("images/Background.png")));
        pane.getChildren().add(i);

        Label titre = new Label("Statistiques Par Jeu");
        titre.setId("CGGamesTitle");
        titre.setLayoutX(350);
        titre.setLayoutY(50);

        Label select = new Label("Sélection d'un jeu : ");
        select.getStyleClass().add("texte");
        select.setLayoutX(122);
        select.setLayoutY(191);

        ComboBox<String> comboSelect = new ComboBox<>();
        ArrayList<String> listeJeux = Surcouche.splitTableau(Surcouche.recupFonction("getAllGame",null));
        comboSelect.getItems().setAll(listeJeux);
        comboSelect.setLayoutX(274);
        comboSelect.setLayoutY(187);

        Label scoreM = new Label("Score moyen : ");
        scoreM.getStyleClass().add("texte");
        scoreM.setLayoutX(122);
        scoreM.setLayoutY(343);

        //résuperer le score moyen suivant un jeu
        String s = "";
        Label avgScore = new Label(s);
        avgScore.getStyleClass().add("texte");
        avgScore.setLayoutX(225);
        avgScore.setLayoutY(343);

        Label scoreB = new Label("Meilleur score : ");
        scoreB.getStyleClass().add("texte");
        scoreB.setLayoutX(122);
        scoreB.setLayoutY(476);

        //mettre le meilleur score suivant un jeu
        String s1 = "";
        Label bestScore = new Label(s1);
        avgScore.getStyleClass().add("texte");
        avgScore.setLayoutX(225);
        avgScore.setLayoutY(475);

        Button btnRetour = new Button("Retour");
        btnRetour.getStyleClass().add("button");
        btnRetour.setContentDisplay(ContentDisplay.CENTER);
        btnRetour.setLayoutX(64);
        btnRetour.setLayoutY(616);

        btnRetour.setOnAction(actionEvent -> {
            ViewMain v = new ViewMain();
            v.afficherMenu(stage);
        });

        pane.getChildren().addAll(titre, select, scoreB, scoreM ,comboSelect, bestScore ,btnRetour);
        stage.setScene(scene);
        stage.setTitle("Module Statistiques");
        stage.setResizable(false);
        stage.show();
    }
}
