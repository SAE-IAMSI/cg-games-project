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
import java.util.HashMap;
import java.util.Map;

public class ViewStatsGames {

    public void affichageStatsJeu(Stage stage) throws IOException {

        ArrayList<String> listeJeux = Surcouche.splitTableau(Surcouche.recupFonction("getAllGame",null));
        String jeuCourant = listeJeux.get(0);
        ArrayList<String> args = new ArrayList<>();
        Map<String,String> mapAvgScore = new HashMap<>();
        Map<String,String> mapBestScore = new HashMap<>();

        for(String game : listeJeux){
            args.clear();args.add(game);
            mapAvgScore.put(game,Surcouche.recupFonction("getScoreMoyen",args));
            mapBestScore.put(game,Surcouche.recupFonction("getBestScore",args));
            args.clear();
        }

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
        comboSelect.getItems().addAll(listeJeux);
        comboSelect.setValue(jeuCourant);
        comboSelect.setLayoutX(274);
        comboSelect.setLayoutY(187);

        Label scoreM = new Label("Score moyen : ");
        scoreM.getStyleClass().add("texte");
        scoreM.setLayoutX(122);
        scoreM.setLayoutY(343);

        //résuperer le score moyen suivant un jeu
        args.clear();args.add(jeuCourant);
        String s = mapAvgScore.get(jeuCourant);
        Label avgScore = new Label(s);
        avgScore.getStyleClass().add("texte");
        avgScore.setLayoutX(225);
        avgScore.setLayoutY(343);

        Label scoreB = new Label("Meilleur score : ");
        scoreB.getStyleClass().add("texte");
        scoreB.setLayoutX(122);
        scoreB.setLayoutY(476);

        //mettre le meilleur score suivant un jeu
        String s1 = mapBestScore.get(jeuCourant);
        Label bestScore = new Label(s1);
        bestScore.getStyleClass().add("texte");
        bestScore.setLayoutX(225);
        bestScore.setLayoutY(476);

        Button btnRetour = new Button("Retour");
        btnRetour.getStyleClass().add("button");
        btnRetour.setContentDisplay(ContentDisplay.CENTER);
        btnRetour.setLayoutX(64);
        btnRetour.setLayoutY(616);

        btnRetour.setOnAction(actionEvent -> {
            ViewMain v = new ViewMain();
            v.afficherMenu(stage);
        });

        comboSelect.setOnAction(actionEvent -> {
            Label scoreMoyen=null;
            Label meilleurScore=null;
            String jeu = comboSelect.getValue();
            args.clear();pane.getChildren().remove(avgScore);pane.getChildren().remove(bestScore);
            pane.getChildren().remove(scoreMoyen);
            pane.getChildren().remove(meilleurScore);
            args.add(jeu);
            String s2 = mapAvgScore.get(jeu);
            String s3 = mapBestScore.get(jeu);
            scoreMoyen = new Label(s2);
            scoreMoyen.getStyleClass().add("texte");
            scoreMoyen.setLayoutX(225);
            scoreMoyen.setLayoutY(343);

            meilleurScore = new Label(s3);
            meilleurScore.getStyleClass().add("texte");
            meilleurScore.setLayoutX(225);
            meilleurScore.setLayoutY(476);

            pane.getChildren().addAll(scoreMoyen, meilleurScore);
        });

        pane.getChildren().addAll(titre, select, scoreB, scoreM ,comboSelect, bestScore ,btnRetour, avgScore);
        stage.setScene(scene);
        stage.setTitle("Module Statistiques");
        stage.setResizable(false);
        stage.show();
    }
}
