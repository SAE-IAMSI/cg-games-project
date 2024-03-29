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
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ViewStatsPlayers {

    public void affichageJoueurs(Stage stage) throws IOException {

        ArrayList<String> listeDPT = Surcouche.splitTableau(Surcouche.recupFonction("getTop10Departement", null));

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(Parametres.class.getResource("css/parametresStyle.css")));
        ImageView i = new ImageView(String.valueOf(Parametres.class.getResource("images/Background.png")));
        pane.getChildren().add(i);

        Label titre = new Label("Statistiques Joueurs");
        titre.setId("CGGamesTitle");
        titre.setLayoutX(350);
        titre.setLayoutY(50);

        Label total = new Label("Nombres d'inscrits : ");
        total.getStyleClass().add("texte");
        total.setLayoutX(122);
        total.setLayoutY(191);

        //mettre le total d'inscrits
        String s = Surcouche.recupFonction("getNbPlayers", null);
        Label nbTotal = new Label(s);
        nbTotal.getStyleClass().add("texte");
        nbTotal.setLayoutX(400);
        nbTotal.setLayoutY(191);

        Label dernierInscrit = new Label("Nombre d'administrateur : ");
        dernierInscrit.getStyleClass().add("texte");
        dernierInscrit.setLayoutX(122);
        dernierInscrit.setLayoutY(343);

        //mettre le pseudo du dernier inscrit
        String s1 = Surcouche.recupFonction("getNbAdmin", null);
        Label pseudo = new Label(s1);
        pseudo.getStyleClass().add("texte");
        pseudo.setLayoutX(400);
        pseudo.setLayoutY(343);

        Label topDep = new Label("Département avec le plus d'inscrits : ");
        topDep.getStyleClass().add("texte");
        topDep.setLayoutX(122);
        topDep.setLayoutY(476);

        //mettre le top département
        Label dep = new Label(listeDPT.get(0));
        dep.getStyleClass().add("texte");
        dep.setLayoutX(420);
        dep.setLayoutY(476);

        Label topDepScrollLabel = new Label("Classement des départements par nombre de joueurs");
        topDepScrollLabel.getStyleClass().add("texte");
        topDepScrollLabel.setLayoutX(800);
        topDepScrollLabel.setLayoutY(170);

        Pane topDepScroll = new Pane();
        topDepScroll.setPrefSize(400, 400);
        topDepScroll.setLayoutX(800);
        topDepScroll.setLayoutY(200);

        LinkedHashMap<String, String> map = Surcouche.splitDPT(Surcouche.recupFonction("getDptPlusJoueurs", null));

        ArrayList<Label> nomDep = new ArrayList<>();
        ArrayList<Label> nbInscrits = new ArrayList<>();
        for (String key : map.keySet()) {
            nomDep.add(new Label(key));
            nbInscrits.add(new Label(map.get(key)));
        }

        //mettre les departements dans la pane topDepScroll
        for(int j=0; j<nomDep.size(); j++){
            Label l = new Label((j+1)+". ");
            l.getStyleClass().add("texte");
            l.setLayoutX(0);
            l.setLayoutY(30*j);
            topDepScroll.getChildren().add(l);

            nomDep.get(j).getStyleClass().add("texte");
            nomDep.get(j).setLayoutX(30);
            nomDep.get(j).setLayoutY(30*j);
            topDepScroll.getChildren().add(nomDep.get(j));
        }

        for (int j = 0; j < nbInscrits.size(); j++) {
            nbInscrits.get(j).getStyleClass().add("texte");
            nbInscrits.get(j).setLayoutX(300);
            nbInscrits.get(j).setLayoutY(30 * j);
            topDepScroll.getChildren().add(nbInscrits.get(j));
        }
        pane.getChildren().add(topDepScroll);

        Button btnGraph = new Button("Graphiques");
        btnGraph.getStyleClass().add("button");
        btnGraph.setLayoutX(875);
        btnGraph.setLayoutY(616);

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

        btnGraph.setOnAction(actionEvent -> {
            ViewStatsGraphs v = new ViewStatsGraphs();
            v.affichageGraphiques(stage);
        });

        pane.getChildren().addAll(titre, total, nbTotal, dernierInscrit, pseudo, topDep, dep, topDepScrollLabel, btnGraph, btnRetour);
        stage.setScene(scene);
        stage.setTitle("Module Statistiques");
        stage.setResizable(false);
        stage.show();

    }
}
