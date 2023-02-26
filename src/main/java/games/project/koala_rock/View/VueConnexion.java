package games.project.koala_rock.View;

import games.project.koala_rock.Metier.entite.AuthPlayer;
import games.project.koala_rock.Metier.manager.PlayerManager;
import games.project.koala_rock.Model.Fond;
import games.project.koala_rock.RessourcesAccess;
import games.project.koala_rock.Stockage.Security;
import games.project.koala_rock.Stockage.Session;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class VueConnexion {


    public void affichageVueConnexion(Stage stage) throws IOException {

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(RessourcesAccess.class.getResource("css/style.css")));

        Label menuScreen = new Label();
        menuScreen.setGraphic(Fond.getChoixFond());

        // CONNEXION //
        Label label = new Label("Connexion");
        label.getStyleClass().add("labelConnexion");
        label.setLayoutX(200);
        label.setLayoutY(50);

        Label labelPseudo = new Label("Pseudo");
        labelPseudo.getStyleClass().add("LabelConnexionField");
        labelPseudo.setLayoutX(275);
        labelPseudo.setLayoutY(250);

        Label labelMotDePasse = new Label("Mot de passe");
        labelMotDePasse.getStyleClass().add("LabelConnexionField");
        labelMotDePasse.setLayoutX(275);
        labelMotDePasse.setLayoutY(350);

        TextField textFieldPseudo = new TextField();
        textFieldPseudo.getStyleClass().add("TextFieldConnexion");
        textFieldPseudo.setLayoutX(275);
        textFieldPseudo.setLayoutY(275);

        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("TextFieldConnexion");
        passwordField.setLayoutX(275);
        passwordField.setLayoutY(375);

        Button buttonConnexion = new Button("Se connecter");
        buttonConnexion.getStyleClass().add("btnGrey");
        buttonConnexion.setLayoutX(285);
        buttonConnexion.setLayoutY(450);

        // Inscription //
        Label labelInscription = new Label("Inscription");
        labelInscription.getStyleClass().add("labelConnexion");
        labelInscription.setLayoutX(720);
        labelInscription.setLayoutY(50);

        Label labelPseudoInscription = new Label("Pseudo");
        labelPseudoInscription.getStyleClass().add("LabelConnexionField");
        labelPseudoInscription.setLayoutX(790);
        labelPseudoInscription.setLayoutY(150);

        Label labelMotDePasseInscription = new Label("Mot de passe");
        labelMotDePasseInscription.getStyleClass().add("LabelConnexionField");
        labelMotDePasseInscription.setLayoutX(790);
        labelMotDePasseInscription.setLayoutY(250);

        Label labelMotDePasseInscription2 = new Label("Confirmation");
        labelMotDePasseInscription2.getStyleClass().add("LabelConnexionField");
        labelMotDePasseInscription2.setLayoutX(790);
        labelMotDePasseInscription2.setLayoutY(350);

        TextField textFieldPseudoInscription = new TextField();
        textFieldPseudoInscription.getStyleClass().add("TextFieldConnexion");
        textFieldPseudoInscription.setLayoutX(790);
        textFieldPseudoInscription.setLayoutY(175);

        PasswordField passwordFieldInscription = new PasswordField();
        passwordFieldInscription.getStyleClass().add("TextFieldConnexion");
        passwordFieldInscription.setLayoutX(790);
        passwordFieldInscription.setLayoutY(275);

        PasswordField passwordFieldInscription2 = new PasswordField();
        passwordFieldInscription2.getStyleClass().add("TextFieldConnexion");
        passwordFieldInscription2.setLayoutX(790);
        passwordFieldInscription2.setLayoutY(375);

        //combo box pour le choix du departement
        ComboBox<String> comboBoxDepartement = new ComboBox<>();
        comboBoxDepartement.setLayoutX(790);
        comboBoxDepartement.setLayoutY(425);
        comboBoxDepartement.getItems().addAll("01 - Ain", "02 - Aisne", "03 - Allier", "04 - Alpes-de-Haute-Provence", "05 - Hautes-Alpes", "06 - Alpes-Maritimes", "07 - Ardèche", "08 - Ardennes", "09 - Ariège", "10 - Aube", "11 - Aude", "12 - Aveyron", "13 - Bouches-du-Rhône", "14 - Calvados", "15 - Cantal", "16 - Charente", "17 - Charente-Maritime", "18 - Cher", "19 - Corrèze", "2A - Corse-du-Sud", "2B - Haute-Corse", "21 - Côte-d'Or", "22 - Côtes-d'Armor", "23 - Creuse", "24 - Dordogne", "25 - Doubs", "26 - Drôme", "27 - Eure", "28 - Eure-et-Loir", "29 - Finistère", "30 - Gard", "31 - Haute-Garonne", "32 - Gers", "33 - Gironde", "34 - Hérault", "35 - Ille-et-Vilaine", "36 - Indre", "37 - Indre-et-Loire", "38 - Isère", "39 - Jura", "40 - Landes", "41 - Loir-et-Cher", "42 - Loire", "43 - Haute-Loire", "44 - Loire-Atlantique", "45 - Loiret", "46 - Lot", "47 - Lot-et-Garonne", "48 - Lozère", "49 - Maine-et-Loire", "50 - Manche", "51 - Marne", "52 - Haute-Marne", "53 - Mayenne", "54 - Meurthe-et-Moselle", "55 - Meuse", "56 - Morbihan", "57 - Moselle", "58 - Nièvre", "59 - Nord", "60 - Oise", "61 - Orne", "62 - Pas-de-Calais", "63 - Puy-de-Dôme", "64 - Pyrénées-Atlantiques", "65 - Hautes-Pyrénées", "66 - Pyrénées-Orientales", "67 - Bas-Rhin", "68 - Haut-Rhin", "69 - Rhône", "70 - Haute-Saône", "71 - Saône-et-Loire", "72 - Sarthe", "73 - Savoie", "74 - Haute-Savoie", "75 - Paris", "76 - Seine-Maritime", "77 - Seine-et-Marne", "78 - Yvelines", "79 - Deux-Sèvres", "80 - Somme", "81 - Tarn", "82 - Tarn-et-Garonne", "83 - Var", "84 - Vaucluse", "85 - Vendée", "86 - Vienne", "87 - Haute-Vienne", "88 - Vosges", "89 - Yonne", "90 - Territoire de Belfort", "91 - Essonne", "92 - Hauts-de-Seine", "93 - Seine-Saint-Denis", "94 - Val-de-Marne", "95 - Val-d'Oise", "971 - Guadeloupe", "972 - Martinique", "973 - Guyane", "974 - La Réunion", "976 - Mayotte");
        comboBoxDepartement.setValue("01 - Ain");
        comboBoxDepartement.getStyleClass().add("buttonEcran");
        comboBoxDepartement.setStyle("-fx-font-size: 12px; -fx-pref-width: 130px; -fx-pref-height: 20px;");

        Button buttonInscription = new Button("S'inscrire");
        buttonInscription.getStyleClass().add("btnGrey");
        buttonInscription.setLayoutX(800);
        buttonInscription.setLayoutY(540);

        Button buttonRetour = new Button("Retour");
        buttonRetour.getStyleClass().add("btnGrey");
        buttonRetour.setLayoutX(645);
        buttonRetour.setLayoutY(620);

        Label labelErreur = new Label();
        labelErreur.getStyleClass().add("LabelConnexionField");
        labelErreur.setLayoutX(350);
        labelErreur.setLayoutY(580);

        Label cgu = new Label("En cliquant sur l'inscrire vous acceptez les conditions générales d'utilisation");
        cgu.getStyleClass().add("LabelCgu");
        cgu.setStyle("-fx-font-size: 12px; -fx-pref-width: 500px; -fx-pref-height: 20px;");
        cgu.setUnderline(true);
        cgu.setLayoutX(630);
        cgu.setLayoutY(490);
        cgu.setOnMouseClicked(event1 -> {
            try {
                Desktop.getDesktop().open(new File("document/CGU.pdf"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button menu = new Button("Menu");
        menu.getStyleClass().add("btnGrey");
        menu.setLayoutX(545);
        menu.setLayoutY(620);

        // Retour menu
        menu.setOnAction(event -> {
            VueMenu vueMenu = new VueMenu();
            vueMenu.demarrerMenu(stage);
        });

        // Inscription
        buttonInscription.setOnAction(event -> {
            //if error -> labelErreur.setText("Erreur");
            labelErreur.setText("");
            if (PlayerManager.getInstance().getPlayer(textFieldPseudoInscription.getText()) == null) {
                if (textFieldPseudoInscription.getText().equals("") || passwordFieldInscription.getText().equals("") || passwordFieldInscription2.getText().equals("")) {
                    labelErreur.setText("Veuillez remplir tous les champs d'incription");
                } else if (!passwordFieldInscription.getText().equals(passwordFieldInscription2.getText())) {
                    labelErreur.setText("Les mots de passe ne correspondent pas");
                    passwordFieldInscription.setText("");
                    passwordFieldInscription2.setText("");
                } else {
                    try {
                        String departement;
                        ArrayList<String> listeDrom = new ArrayList<>(Arrays.asList("971 - Guadeloupe", "972 - Martinique", "973 - Guyane", "974 - La Réunion", "976 - Mayotte"));
                        if (listeDrom.contains(comboBoxDepartement.getValue())) {
                            departement = comboBoxDepartement.getValue().substring(0, 3);
                        } else {
                            departement = comboBoxDepartement.getValue().substring(0, 2);
                        }
                        PlayerManager.getInstance().createPlayer(textFieldPseudoInscription.getText(), passwordFieldInscription.getText(), departement);
                        Session.getInstance().connect(textFieldPseudoInscription.getText());
                        textFieldPseudoInscription.setText("");
                        passwordFieldInscription.setText("");
                        passwordFieldInscription2.setText("");
                        VueMenu vueMenu = new VueMenu();
                        vueMenu.demarrerMenu(stage);
//                        labelErreur.setText("Inscription réussie");
                    } catch (Exception e) {
                        labelErreur.setText("Erreur lors de l'inscription");
                    }
                }
            } else {
                labelErreur.setText("Ce pseudo est déjà utilisé");
            }
        });

        // Connexion
        buttonConnexion.setOnAction(event -> {
            labelErreur.setText("");
            if (textFieldPseudo.getText().equals("") || passwordField.getText().equals("")) {
                labelErreur.setText("Veuillez remplir tous les champs d'incription");
            } else {
                if (PlayerManager.getInstance().getPlayer(textFieldPseudo.getText()) == null) {
                    labelErreur.setText("Ce pseudo n'existe pas");
                } else {
                    AuthPlayer authPlayer = PlayerManager.getInstance().getPlayer(textFieldPseudo.getText());
                    try {
                        if (Security.checkPassword(passwordField.getText(), authPlayer.getSalt(), authPlayer.getHashedPassword())) {
                            if (Session.getInstance().isConnected()) {
                                Session.getInstance().disconnect();
                            }
                            Session.getInstance().connect(textFieldPseudo.getText());
                            textFieldPseudo.setText("");
                            passwordField.setText("");
                            VueMenu vueMenu = new VueMenu();
                            vueMenu.demarrerMenu(stage);
                        } else {
                            labelErreur.setText("Mot de passe incorrect");
                            passwordField.setText("");
                        }
                    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        // Retour
        buttonRetour.setOnAction(event -> {
            VueParametre vueParametre = new VueParametre();
            try {
                vueParametre.affichageVueParametre(stage);
            } catch (IOException e) {
                e.printStackTrace();
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
        pane.getChildren().addAll(menuScreen, label, labelPseudo, labelMotDePasse, textFieldPseudo, passwordField, buttonConnexion, labelInscription, labelPseudoInscription, labelMotDePasseInscription, labelMotDePasseInscription2, textFieldPseudoInscription, passwordFieldInscription, passwordFieldInscription2, buttonInscription, comboBoxDepartement, buttonRetour, labelErreur, menu, cgu);
        stage.setTitle("Connexion");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }
}
