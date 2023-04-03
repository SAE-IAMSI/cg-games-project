package games.project.koala_rock.View;

import games.project.koala_rock.Metier.manager.ScoreManager;
import games.project.koala_rock.Model.*;
import games.project.koala_rock.RessourcesAccess;
import games.project.stockage.Session;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class VueParametre {
    private static String choixPersonnage = "Panda";
    private static String choixEnnemi = "Koala";

    public void affichageVueParametre(Stage stage) throws IOException {

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(RessourcesAccess.class.getResource("css/style.css")));

        ComboBox<String> comboBoxJoueurPrincipale = new ComboBox<>();
        comboBoxJoueurPrincipale.getItems().addAll("Panda");
        comboBoxJoueurPrincipale.setValue("Panda");
        comboBoxJoueurPrincipale.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            choixPersonnage = newValue;
            if (choixPersonnage.equals("Panda")) {
                PersonnagePrincipale.setPersonnePrincipale("PANDA");
            } else if (choixPersonnage.equals("Samurai")) {
                PersonnagePrincipale.setPersonnePrincipale("SAMURAI");
            }
        });
        comboBoxJoueurPrincipale.setLayoutX(300);
        comboBoxJoueurPrincipale.setLayoutY(50);
        comboBoxJoueurPrincipale.getStyleClass().add("buttonEcran");
        comboBoxJoueurPrincipale.setStyle("-fx-font-size: 12px; -fx-pref-width: 130px; -fx-pref-height: 20px;");
        if (Session.getInstance().getLogin() != null) {
            if (ScoreManager.getInstance().getHighScoreByLogin(Session.getInstance().getLogin()) != null) {
                if (ScoreManager.getInstance().getHighScoreByLogin(Session.getInstance().getLogin()).getScore() > 10) {
                    comboBoxJoueurPrincipale.getItems().add("Samurai");
                }
            }
        }

        ComboBox<String> comboBoxPersonnageEnnemie = new ComboBox<>();
        comboBoxPersonnageEnnemie.getItems().addAll("Koala");
        comboBoxPersonnageEnnemie.setValue("Koala");
        comboBoxPersonnageEnnemie.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            choixEnnemi = newValue;
            if (choixEnnemi.equals("Koala")) {
                PersonnageEnnemi.setChoixPersonnage("KOALA");
                FondNiveau.setChoixFondNiveau("KOALA");
                Echelle.setChoixEchelle("KOALA");
                EchelleBroken.setChoixEchelleBroken("KOALA");
                ObjetAttaque.setChoixObjet("ROCHER");
            } else if (choixEnnemi.equals("Ninja")) {
                PersonnageEnnemi.setChoixPersonnage("NINJA");
                FondNiveau.setChoixFondNiveau("NINJA");
                Echelle.setChoixEchelle("NINJA");
                EchelleBroken.setChoixEchelleBroken("NINJA");
                ObjetAttaque.setChoixObjet("SHURIKANE");
            }
        });
        comboBoxPersonnageEnnemie.setLayoutX(300);
        comboBoxPersonnageEnnemie.setLayoutY(160);
        comboBoxPersonnageEnnemie.getStyleClass().add("buttonEcran");
        comboBoxPersonnageEnnemie.setStyle("-fx-font-size: 12px; -fx-pref-width: 130px; -fx-pref-height: 20px;");
        if (Session.getInstance().getLogin() != null) {
            if (ScoreManager.getInstance().getHighScoreByLogin(Session.getInstance().getLogin()) != null) {
                if (ScoreManager.getInstance().getHighScoreByLogin(Session.getInstance().getLogin()).getScore() > 10) {
                    comboBoxPersonnageEnnemie.getItems().add("Ninja");
                }
            }
        }

        ComboBox<String> comboBoxFond = new ComboBox<>();
        comboBoxFond.getItems().addAll("Défaut", "Nuit", "NuitBis", "Ville", "Foret", "Star");
        comboBoxFond.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            switch (newValue) {
                case "Défaut" -> Fond.setChoixFond("Défaut");
                case "Nuit" -> Fond.setChoixFond("Nuit");
                case "NuitBis" -> Fond.setChoixFond("NuitBis");
                case "Ville" -> Fond.setChoixFond("Ville");
                case "Foret" -> Fond.setChoixFond("Foret");
                case "Star" -> Fond.setChoixFond("Star");
            }
        });
        comboBoxFond.setLayoutX(300);
        comboBoxFond.setLayoutY(270);
        comboBoxFond.getStyleClass().add("buttonEcran");
        comboBoxFond.setValue(Fond.getNomChoixFond());
        comboBoxFond.setStyle("-fx-font-size: 12px; -fx-pref-width: 130px; -fx-pref-height: 20px;");

        Label menuScreen = new Label();
        menuScreen.setGraphic(Fond.getChoixFond());

        Label labelJoueurPrincipale = new Label("Personnage principal : ");
        labelJoueurPrincipale.setLayoutX(100);
        labelJoueurPrincipale.setLayoutY(60);
        labelJoueurPrincipale.getStyleClass().add("LabelUnderButton");


        Label labelPersonnageEnnemie = new Label("Personnage ennemie : ");
        labelPersonnageEnnemie.setLayoutX(100);
        labelPersonnageEnnemie.setLayoutY(170);
        labelPersonnageEnnemie.getStyleClass().add("LabelUnderButton");

        Label labelFond = new Label("Fond : ");
        labelFond.setLayoutX(230);
        labelFond.setLayoutY(280);
        labelFond.getStyleClass().add("LabelUnderButton");

        Label button_Bas = new Label();
        ImageView image_Bas = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Bas.png"))));
        image_Bas.setFitHeight(50);
        image_Bas.setFitWidth(50);
        button_Bas.setGraphic(image_Bas);
        button_Bas.getStyleClass().add("button_Action");
        button_Bas.setLayoutX(200);
        button_Bas.setLayoutY(485);

        Label button_Haut = new Label();
        ImageView image_Haut = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Haut.png"))));
        image_Haut.setFitHeight(50);
        image_Haut.setFitWidth(50);
        button_Haut.setGraphic(image_Haut);
        button_Haut.getStyleClass().add("button_Action");
        button_Haut.setLayoutX(200);
        button_Haut.setLayoutY(400);

        Label button_Gauche = new Label();
        ImageView image_Gauche = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Gauche.png"))));
        image_Gauche.setFitHeight(50);
        image_Gauche.setFitWidth(50);
        button_Gauche.setGraphic(image_Gauche);
        button_Gauche.getStyleClass().add("button_Action");
        button_Gauche.setLayoutX(110);
        button_Gauche.setLayoutY(485);

        Label button_Droite = new Label();
        ImageView image_Droite = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Droite.png"))));
        image_Droite.setFitHeight(50);
        image_Droite.setFitWidth(50);
        button_Droite.setGraphic(image_Droite);
        button_Droite.getStyleClass().add("button_Action");
        button_Droite.setLayoutX(285);
        button_Droite.setLayoutY(485);

        Label button_Espace = new Label();
        ImageView image_Espace = new ImageView(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("Button/Button_Espace.png"))));
        image_Espace.setFitHeight(50);
        image_Espace.setFitWidth(200);
        button_Espace.setGraphic(image_Espace);
        button_Espace.getStyleClass().add("button_Action");
        button_Espace.setLayoutX(500);
        button_Espace.setLayoutY(485);

        Button connexionRegister = new Button("Connexion/Inscription");
        connexionRegister.getStyleClass().add("buttonEcran");
        connexionRegister.setLayoutX(900);
        connexionRegister.setLayoutY(180);

        Button meilleurScore = new Button("Meilleurs Score");
        meilleurScore.getStyleClass().add("buttonEcran");
        meilleurScore.setLayoutX(900);
        meilleurScore.setLayoutY(100);

        Button monCompte = new Button("Mon Compte");
        monCompte.getStyleClass().add("buttonEcran");
        monCompte.setLayoutX(912);
        monCompte.setLayoutY(180);

        Label texte_Bas = new Label("Bas");
        texte_Bas.getStyleClass().add("LabelUnderButton");
        texte_Bas.setLayoutX(206);
        texte_Bas.setLayoutY(540);

        Label texte_Haut = new Label("Haut");
        texte_Haut.getStyleClass().add("LabelUnderButton");
        texte_Haut.setLayoutX(204);
        texte_Haut.setLayoutY(455);

        Label texte_Gauche = new Label("Gauche");
        texte_Gauche.getStyleClass().add("LabelUnderButton");
        texte_Gauche.setLayoutX(104);
        texte_Gauche.setLayoutY(540);

        Label texte_Droite = new Label("Droite");
        texte_Droite.getStyleClass().add("LabelUnderButton");
        texte_Droite.setLayoutX(282);
        texte_Droite.setLayoutY(540);

        Label texte_Espace = new Label("Espace");
        texte_Espace.getStyleClass().add("LabelUnderButton");
        texte_Espace.setLayoutX(570);
        texte_Espace.setLayoutY(540);

        Label labelError = new Label();
        labelError.getStyleClass().add("LabelError");
        labelError.setLayoutX(510);
        labelError.setLayoutY(250);

        Button buttonHide = new Button("Quitter / Sauvegarder");
        buttonHide.getStyleClass().add("btnGrey");
        buttonHide.setLayoutX(540);
        buttonHide.setLayoutY(620);

        pane.getChildren().add(menuScreen);

        if (Session.getInstance().isConnected()) {
            pane.getChildren().add(monCompte);
            pane.getChildren().remove(connexionRegister);
        } else {
            pane.getChildren().remove(monCompte);
            pane.getChildren().add(connexionRegister);
        }

        pane.getChildren().addAll(buttonHide, meilleurScore);
        pane.getChildren().addAll(button_Bas, button_Haut, button_Gauche, button_Droite, button_Espace);
        pane.getChildren().addAll(texte_Bas, texte_Haut, texte_Gauche, texte_Droite, texte_Espace);
        pane.getChildren().addAll(comboBoxPersonnageEnnemie, comboBoxJoueurPrincipale, comboBoxFond);
        pane.getChildren().addAll(labelJoueurPrincipale, labelPersonnageEnnemie, labelFond);
        pane.getChildren().add(labelError);

        // Retour menu
        buttonHide.setOnAction(event -> {
            VueMenu vueMenu = new VueMenu();
            vueMenu.demarrerMenu(stage);
        });

        // Ouvrir la fenêtre de connexion
        connexionRegister.setOnMouseClicked(event -> {
            VueConnexion vueConnexion = new VueConnexion();
            try {
                vueConnexion.affichageVueConnexion(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Ouvrir la fenêtre de meillur score
        meilleurScore.setOnMouseClicked(event -> {
            VueMeilleurScore vueMeilleurScore = new VueMeilleurScore();
            try {
                vueMeilleurScore.affichageVueMeilleurScore(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Ouvrir la fenêtre de mon compte
        monCompte.setOnMouseClicked(event -> {
            labelError.setText("");
            if (Session.getInstance().isConnected()) {
                VueCompte vueCompte = new VueCompte();
                try {
                    vueCompte.affichageVueCompte(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                labelError.setText("Veuillez vous connecter");
            }
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

        pane.setStyle("-fx-border-color: white ; -fx-border-width: 10px ; -fx-background-color: black ; -fx-background-radius: 10px ;");
        stage.setTitle("Paramètre");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
