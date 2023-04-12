package games.project.modules.statistiques.views;

import games.project.modules.parametres.Parametres;
import games.project.modules.statistiques.Surcouche;
import games.project.modules.statistiques.StatsLauncher;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewStatsGames {

    private static final int compteur = 0;
    private Label avgScore;
    Label bestScore;
    ImageView graphe;

    public void affichageStatsJeu(Stage stage) throws IOException {

        ArrayList<String> listeJeux = Surcouche.splitTableau(Surcouche.recupFonction("getAllGame", null));
        String jeuCourant = listeJeux.get(0);
        ArrayList<String> args = new ArrayList<>();
        Map<String, String> mapAvgScore = new HashMap<>();
        Map<String, String> mapBestScore = new HashMap<>();

        for (String game : listeJeux) {
            args.clear();
            args.add(game);
            mapAvgScore.put(game, Surcouche.recupFonction("getScoreMoyen", args));
            mapBestScore.put(game, Surcouche.recupFonction("getBestScore", args));
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

    }
}