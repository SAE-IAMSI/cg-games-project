package games.project.koala_rock.View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import games.project.koala_rock.Model.Fond;
import games.project.koala_rock.RessourcesAccess;
import games.project.koala_rock.Stockage.Session;

import java.io.IOException;
import java.util.Objects;

public class VueMenu {
    private final VueChoixModeJeu vueChoixModeJeu = new VueChoixModeJeu();

    public void demarrerMenu(Stage stage) {

        BorderPane borderPane = new BorderPane();
        Pane pane = new Pane();
        borderPane.setCenter(pane);

        Scene scene = new Scene(borderPane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(RessourcesAccess.class.getResource("css/style.css")));

        Label nameGame = new Label("Koala Rock");
        nameGame.getStyleClass().add("nameGame");
        nameGame.setLayoutX(355);
        nameGame.setLayoutY(100);

        Button demarrerPartie = new Button("Jouer");
        demarrerPartie.getStyleClass().add("buttonEcran");
        demarrerPartie.setLayoutX(590);
        demarrerPartie.setLayoutY(280);

        Button parametre = new Button("Paramètres");
        parametre.getStyleClass().add("buttonEcran");
        parametre.setLayoutX(555);
        parametre.setLayoutY(380);

        Button quitter = new Button("Quitter");
        quitter.getStyleClass().add("buttonEcran");
        quitter.setLayoutX(585);
        quitter.setLayoutY(480);

        Label menuScreenPremierPlan = new Label();
        ImagePattern fondPremierPlan = new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("fond/classic.png"))));
        Rectangle rectangleImg = new Rectangle(1280, 720);
        rectangleImg.setFill(fondPremierPlan);
        menuScreenPremierPlan.setGraphic(rectangleImg);

        Label menuScreen = new Label();
        menuScreen.setGraphic(Fond.getChoixFond());

        Label labelError = new Label();
        labelError.getStyleClass().add("LabelError");
        labelError.setLayoutX(510);
        labelError.setLayoutY(250);

        Label copyRight = new Label(" Koala Rock© 2023. Tous droits réservés");
        copyRight.getStyleClass().add("copyRight");
        copyRight.setLayoutX(880);
        copyRight.setLayoutY(590);

        Label client = new Label("CG games project");
        client.getStyleClass().add("copyRight");
        client.setLayoutX(1060);
        client.setLayoutY(560);

        Label copyRightName = new Label("Réalisé par : Célyan, Joris, Killian, Simon, Valentin");
        copyRightName.getStyleClass().add("copyRightName");
        copyRightName.setLayoutX(922);
        copyRightName.setLayoutY(620);

        pane.getChildren().add(menuScreen);
        pane.getChildren().add(menuScreenPremierPlan);
        pane.getChildren().add(labelError);
        pane.getChildren().addAll(demarrerPartie);
        pane.getChildren().addAll(parametre, quitter);
        pane.getChildren().add(nameGame);
        pane.getChildren().addAll(client, copyRight, copyRightName);

        // Permet de lancer le jeu en mode classique
        demarrerPartie.setOnMouseClicked(event -> {
            try {
                vueChoixModeJeu.affichageVueChoixModeJeu(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Permet d'ouvrir la fenêtre de paramétrage
        parametre.setOnMouseClicked(event -> {
            VueParametre vueParametre = new VueParametre();
            try {
                vueParametre.affichageVueParametre(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Permet de quitter le jeu
        quitter.setOnMouseClicked(event -> {
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

            pane.getChildren().addAll(rectangle, oui, non, alerte);
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
            pane.getChildren().addAll(rectangle, oui, non, alerte);
        });

        pane.setStyle("-fx-border-color: green ;");
        borderPane.setStyle("-fx-background-color: transparent ;");
        stage.setTitle("Koala Rock");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
