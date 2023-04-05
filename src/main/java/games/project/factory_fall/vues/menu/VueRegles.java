package games.project.factory_fall.vues.menu;

import games.project.factory_fall.FactoryFall;
import games.project.factory_fall.parametres.Preferences;
import games.project.factory_fall.parametres.Ressources;
import games.project.factory_fall.vues.Menu;
import games.project.factory_fall.vues.VueMenuPrincipal;
import games.project.factory_fall.vues.helpers.BarreNavigation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class VueRegles extends Stage implements Menu {

    private final BorderPane root;
    private final Scene scene;
    private final VBox style, regles, infoScore;
    private final BorderPane conteneur;
    //Label des règles
    private final Label but, completionLigne, gainScore, prochainePiece, pieceSauvegardee, gameOver;
    // Label description score
    private final Label titre, softDrop, hardDrop, singleLine, doubleLine, tripleLine, Tetris, asterix;


    public VueRegles() {
        // Initialisations
        style = new VBox();

        but = new Label("OBJECTIF - Mettez votre sens de l'organisation et votre endurance\nà l'épreuve en dégageant le plus de lignes possible.\n");
        completionLigne = new Label("EFFACER LES LIGNES - Manipulez les pièces qui tombent pour les\nassembler dans le tableau. Pour effacer une ligne, remplissez\ntoutes les cases d'une même ligne.\n");
        gainScore = new Label("POINTS DE SCORE - Gagnez des points en supprimant des lignes.\nDégagez plusieurs lignes à la fois pour augmenter vos chances\nde marquer des points.\n");
        prochainePiece = new Label("PIECE SUIVANTE - Prévisualisez la prochaine pièce afin\nd'anticiper et d'augmenter vos chances de marquer des points.\n");
        pieceSauvegardee = new Label("PIECE SAUVEGARDÉE - Stocke la pièce qui tombe dans un conteneur.\nPièce qui pourra être réutilisée par la suite.\n");
        gameOver = new Label("GAME OVER - Empilez les pièces trop haut et le jeu est terminé!\n");

        //Créer une VBox tampon pour permettre le remplissage de la VBox règles plus simplement (sans prendre 20 ou 30 lignes de code)
        VBox temp = new VBox(but, completionLigne, gainScore, prochainePiece, pieceSauvegardee, gameOver);
        regles = new VBox();
        for (int i = 0; i < 6; i++) {
            regles.getChildren().add(new HBox(new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("icons/regle.png")))), temp.getChildren().get(0)));
        }

        titre = new Label("Scores :");
        softDrop = new Label("Descente simple : 1 point x ligne");
        hardDrop = new Label("Descente rapide : 2 point x ligne ");
        singleLine = new Label("Une ligne : 100 points ");
        doubleLine = new Label("Deux lignes : 300 points  ");
        tripleLine = new Label("Trois lignes : 500 points");
        Tetris = new Label("Quatre lignes (Tetris) : 800 point");
        asterix = new Label("* Les scores sont mutiplié par le rang");

        infoScore = new VBox();

        //Uniquement présent pour centrer le titre
        VBox titreVb = new VBox(titre);

        infoScore.getChildren().addAll(titreVb, softDrop, hardDrop, singleLine, doubleLine, tripleLine, Tetris, asterix);

        conteneur = new BorderPane();
        conteneur.setLeft(regles);
        conteneur.setRight(infoScore);

        root = new BorderPane();
        root.setCenter(conteneur);
        scene = new Scene(root, 1280, 720);


        // Styles et bindings
        styliser();

        // Affections


        // Affichage
        root.setTop(new BarreNavigation("Règles", VueMenuPrincipal.getInstance().getSceneMenuPrincipal()));
        this.setScene(scene);
    }

    public void styliser() {
        root.setBackground(Preferences.getInstance().getBackground());
        for (int i = 0; i < regles.getChildren().size(); i++) {
            ((HBox) regles.getChildren().get(i)).setPadding(new Insets(15, 10, 15, 10));
            ((Label) ((HBox) regles.getChildren().get(i)).getChildren().get(1)).setFont(Ressources.getPolice(20));
            ((Label) ((HBox) regles.getChildren().get(i)).getChildren().get(1)).setPadding(new Insets(0, 5, 0, 5));
            ((HBox) regles.getChildren().get(i)).getChildren().get(1).setStyle("-fx-text-fill: white");
        }

        regles.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        regles.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        regles.setMaxHeight(500);
        infoScore.setMaxHeight(500);
        infoScore.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        infoScore.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        conteneur.setPadding(new Insets(20));

        for (int i = 0; i < infoScore.getChildren().size(); i++) {
            if (i == 0) {
                ((Label) ((VBox) infoScore.getChildren().get(i)).getChildren().get(0)).setFont(Ressources.getPolice(30));
                ((VBox) infoScore.getChildren().get(i)).setAlignment(Pos.CENTER);
                ((VBox) infoScore.getChildren().get(i)).setPadding(new Insets(10));
                ((VBox) infoScore.getChildren().get(i)).getChildren().get(0).setStyle("-fx-text-fill: white");
            } else if (i == infoScore.getChildren().size() - 1) {
                ((Label) infoScore.getChildren().get(i)).setFont(Ressources.getPolice(15));
                ((Label) infoScore.getChildren().get(i)).setPadding(new Insets(10));
                infoScore.getChildren().get(i).setStyle("-fx-text-fill: white");
            } else {
                ((Label) infoScore.getChildren().get(i)).setFont(Ressources.getPolice(24));
                ((Label) infoScore.getChildren().get(i)).setPadding(new Insets(10));
                infoScore.getChildren().get(i).setStyle("-fx-text-fill: white");
            }
        }
    }


    @Override
    public void afficherScene() {
        VueMenuPrincipal.getInstance().setScene(scene);
        mettreAJourFond();
    }

    public void mettreAJourFond() {
        root.setBackground(Preferences.getInstance().getBackground());
    }
}
