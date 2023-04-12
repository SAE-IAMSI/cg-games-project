package games.project.modules.statistiques.views;

import games.project.modules.statistiques.Surcouche;
import games.project.modules.parametres.Parametres;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.FileSystems;

public class ViewStatsTournament {

    public void affichageStatsTournoi(Stage stage) throws IOException {

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
        String s = Surcouche.recupFonction("getNbTournois",null);
        Label nbTournois = new Label(s);
        nbTournois.getStyleClass().add("texte");
        nbTournois.setLayoutX(350);
        nbTournois.setLayoutY(191);

        Label moyenTournoi = new Label("Nombre de participants moyen par tournoi : ");
        moyenTournoi.getStyleClass().add("texte");
        moyenTournoi.setLayoutX(122);
        moyenTournoi.setLayoutY(250);

        //mettre le nombre de participants moyen par tournois
        String s1 = Surcouche.recupFonction("getAvgParticipants",null);
        Label avgTournoi = new Label(s1);
        avgTournoi.getStyleClass().add("texte");
        avgTournoi.setLayoutX(460);
        avgTournoi.setLayoutY(250);

        Label moyenTournoiAttente = new Label("Nombre de participants maximum par tournoi : ");
        moyenTournoiAttente.getStyleClass().add("texte");
        moyenTournoiAttente.setLayoutX(122);
        moyenTournoiAttente.setLayoutY(393);

        //mettre le nombre de participants moyen par tournois
        String s2 = Surcouche.recupFonction("getAvgAttendees",null);
        Label avgTournoiAttente = new Label(s2);
        avgTournoiAttente.getStyleClass().add("texte");
        avgTournoiAttente.setLayoutX(480);
        avgTournoiAttente.setLayoutY(393);

        Label dateDernierTournoi = new Label("La date la plus tard pour le début d'un tournoi: ");
        dateDernierTournoi.getStyleClass().add("texte");
        dateDernierTournoi.setLayoutX(122);
        dateDernierTournoi.setLayoutY(450);

        //mettre la date du dernier tournoi crée
        String s3 = Surcouche.dateLisible(Surcouche.recupFonction("getMaxDateDebutTournois",null));
        Label dateDernierTournoiCree = new Label(s3);
        dateDernierTournoiCree.getStyleClass().add("texte");
        dateDernierTournoiCree.setLayoutX(485);
        dateDernierTournoiCree.setLayoutY(450);

        ImageView graphe = new ImageView(FileSystems.getDefault().getPath("src/main/java/games/project/modules/statistiques/imgTemp/grapheTournoi.png").normalize().toAbsolutePath().toString());
        graphe.setLayoutX(625);
        graphe.setLayoutY(150);
        graphe.setFitHeight(480);
        graphe.setFitWidth(640);

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

        pane.getChildren().addAll(titre, tournoisCrees, nbTournois, moyenTournoi, avgTournoi, btnRetour, moyenTournoiAttente, avgTournoiAttente, dateDernierTournoi, dateDernierTournoiCree, graphe);
        stage.setScene(scene);
        stage.setTitle("Module Statistiques");
        stage.setResizable(false);
        stage.show();

    }

}
