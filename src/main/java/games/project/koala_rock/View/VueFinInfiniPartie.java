package games.project.koala_rock.View;

import games.project.koala_rock.Model.Fond;
import games.project.koala_rock.RessourcesAccess;
import games.project.koala_rock.Stockage.Session;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class VueFinInfiniPartie {
    private VueJeu vueJeu;


    public void screenLose(int scoreGame, Stage stage) {
        if (vueJeu == null) {
            vueJeu = new VueJeu();
        }

        BorderPane borderPane = new BorderPane();
        Pane pane = new Pane();
        borderPane.setCenter(pane);

        Scene scene = new Scene(borderPane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(RessourcesAccess.class.getResource("css/style.css")));

        Label menuScreen = new Label();
        menuScreen.setGraphic(Fond.getChoixFond());

        Label nameGame = new Label("Fin de la partie");
        nameGame.getStyleClass().add("nameGame");
        nameGame.setLayoutX(226);
        nameGame.setLayoutY(80);

        Label score = new Label("Score : " + scoreGame);
        score.getStyleClass().add("nameGame");
        score.setLayoutX(226);
        score.setLayoutY(180);

        Label deadScreen = new Label();
        Image image = new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("fond/classic.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(720);
        imageView.setFitWidth(1280);
        deadScreen.setGraphic(imageView);

        Button retourMenu = new Button("Retour au menu");
        retourMenu.getStyleClass().add("buttonEcran");
        retourMenu.setLayoutX(325);
        retourMenu.setLayoutY(570);

        Button recommencer = new Button("Recommencer");
        recommencer.getStyleClass().add("buttonEcran");
        recommencer.setLayoutX(540);
        recommencer.setLayoutY(570);

        Button quitter = new Button("Quitter");
        quitter.getStyleClass().add("buttonEcran");
        quitter.setLayoutX(740);
        quitter.setLayoutY(570);

        // Recommencer la partie
        recommencer.setOnMouseClicked(event -> {
            try {
                vueJeu.demarrerJeu(stage, "Infini");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Quitter le jeu
        quitter.setOnMouseClicked(event -> {
            System.exit(0);
        });

        // Retour au menu
        retourMenu.setOnMouseClicked(event -> {
            VueMenu vueMenu = new VueMenu();
            vueMenu.demarrerMenu(stage);
        });

        // Lors du click sur le bouton quitter de la fenetre (affichage confirmation)
        stage.setOnCloseRequest(event -> {
            event.consume();
            Label alerte = new Label("Voulez vous vraiment \n" + "quitter le jeu ?");
            alerte.getStyleClass().add("LabelError");
            alerte.setLayoutX(520);
            alerte.setLayoutY(250);

            Rectangle rectangle = new Rectangle();
            rectangle.setX(500);
            rectangle.setY(200);
            rectangle.setWidth(300);
            rectangle.setHeight(200);

            rectangle.setArcHeight(50);
            rectangle.setArcWidth(50);
            rectangle.setFill(Color.BLACK);
            rectangle.setEffect(new DropShadow(10, Color.WHITE));

            Button oui = new Button("Oui");
            oui.getStyleClass().add("btnGrey");
            oui.setLayoutX(520);
            oui.setLayoutY(325);

            Button non = new Button("Non");
            non.getStyleClass().add("btnRed");
            non.setLayoutX(720);
            non.setLayoutY(325);

            oui.setOnAction(e -> {
                Session.getInstance().disconnect();
                System.exit(0);
            });
            non.setOnAction(e -> {
                pane.getChildren().removeAll(oui, non, rectangle, alerte);
            });
            pane.getChildren().addAll(rectangle, alerte, oui, non);
        });

        pane.getChildren().addAll(menuScreen, deadScreen, nameGame, score, recommencer, quitter, retourMenu);

        stage.setTitle("Koala Rock");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
