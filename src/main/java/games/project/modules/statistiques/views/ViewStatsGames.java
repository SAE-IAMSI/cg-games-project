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

        System.out.println(mapAvgScore);
        System.out.println(mapBestScore);

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(Parametres.class.getResource("css/parametresStyle.css")));
        ImageView i = new ImageView(String.valueOf(Parametres.class.getResource("images/Background.png")));
        pane.getChildren().add(i);

        Label titre = new Label("Statistiques Par Jeu");
        titre.getStyleClass().add("titre");
        titre.setLayoutX(600);
        titre.setLayoutY(100);

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
        args.clear();
        args.add(jeuCourant);
        String s = mapAvgScore.get(jeuCourant);
        avgScore = new Label(s);
        avgScore.getStyleClass().add("texte");
        avgScore.setLayoutX(245);
        avgScore.setLayoutY(343);

        Label scoreB = new Label("Meilleur score : ");
        scoreB.getStyleClass().add("texte");
        scoreB.setLayoutX(122);
        scoreB.setLayoutY(476);

        //mettre le meilleur score suivant un jeu
        String s1 = mapBestScore.get(jeuCourant);
        bestScore = new Label(s1);
        bestScore.getStyleClass().add("texte");
        bestScore.setLayoutX(245);
        bestScore.setLayoutY(476);

        Button btnRetour = new Button("Retour");
        btnRetour.getStyleClass().add("button");
        btnRetour.setContentDisplay(ContentDisplay.CENTER);
        btnRetour.setLayoutX(64);
        btnRetour.setLayoutY(616);

        btnRetour.setOnAction(actionEvent -> {
            ViewMain v = new ViewMain();
            try {
                v.afficherMenu(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        graphe = new ImageView(FileSystems.getDefault().getPath("src/main/java/games/project/modules/statistiques/imgTemp/graphe" + jeuCourant + ".png").normalize().toAbsolutePath().toString());
        graphe.setLayoutX(500);
        graphe.setLayoutY(200);
        graphe.setFitHeight(400);
        graphe.setFitWidth(700);

        comboSelect.setOnAction(actionEvent -> {
            String jeu = comboSelect.getValue();
            args.clear();
            args.add(jeu);
            pane.getChildren().removeAll(bestScore, avgScore);
            String s2 = mapAvgScore.get(jeu);
            String s3 = mapBestScore.get(jeu);
            avgScore = new Label(s2);
            avgScore.getStyleClass().add("texte");
            avgScore.setLayoutX(245);
            avgScore.setLayoutY(343);

            graphe.setImage(new ImageView(FileSystems.getDefault().getPath("src/main/java/games/project/modules/statistiques/imgTemp/graphe" + jeu + ".png").normalize().toAbsolutePath().toString()).getImage());

            bestScore = new Label(s3);
            bestScore.getStyleClass().add("texte");
            bestScore.setLayoutX(245);
            bestScore.setLayoutY(476);

            pane.getChildren().addAll(bestScore, avgScore);
        });

        pane.getChildren().addAll(titre, select, scoreB, scoreM, comboSelect, bestScore, btnRetour, avgScore, graphe);
        stage.setScene(scene);
        stage.setTitle("Module Statistiques");
        stage.setResizable(false);
        stage.show();
    }
}
