package games.project.modules.statistiques.views;

import games.project.modules.parametres.Parametres;
import games.project.modules.statistiques.Surcouche;
import games.project.modules.statistiques.StatsLauncher;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.FileSystems;

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
        String s = Surcouche.recupFonction("getJoueursActifs", null);
        Label nbJoueursActifs = new Label(s);
        nbJoueursActifs.setLayoutX(400);
        nbJoueursActifs.setLayoutY(191);

        Surcouche.recupFonction("getPieActifsNonActifs", null);
        ImageView graphe = new ImageView(FileSystems.getDefault().getPath("src/main/java/games/project/modules/statistiques/imgTemp/pieActifsNonActifs.png").normalize().toAbsolutePath().toString());
        graphe.setLayoutX(600);
        graphe.setLayoutY(200);
        graphe.setFitHeight(400);
        graphe.setFitWidth(600);

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

        pane.getChildren().addAll(titre, joueursActifs, nbJoueursActifs, btnRetour, graphe);
        stage.setScene(scene);
        stage.setTitle("Module Statistiques");
        stage.setResizable(false);
        stage.show();

    }
}
