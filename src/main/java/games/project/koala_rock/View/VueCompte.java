package games.project.koala_rock.View;

import games.project.koala_rock.Metier.entite.Score;
import games.project.koala_rock.Metier.manager.PlayerManager;
import games.project.koala_rock.Metier.manager.ScoreManager;
import games.project.koala_rock.Model.Fond;
import games.project.koala_rock.RessourcesAccess;
import games.project.koala_rock.Stockage.Session;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class VueCompte {

    private int ScoreAffiche = 1;

    public void affichageVueCompte(Stage stage) throws IOException {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(RessourcesAccess.class.getResource("css/style.css")));

        ScrollPane paneKoalaRock = new ScrollPane();
        paneKoalaRock.setPrefSize(350, 475);
        paneKoalaRock.setLayoutX(450);
        paneKoalaRock.setLayoutY(155);
        paneKoalaRock.setStyle("-fx-border-color: white ; -fx-border-width: 5px ; -fx-background: black ; -fx-background-radius: 15px ; -fx-border-radius: 10px ; ");
        paneKoalaRock.setEffect(new DropShadow(20, Color.WHITE));

        VBox contentVKR = new VBox();

        ScrollPane scrollPaneTRON = new ScrollPane();
        scrollPaneTRON.setPrefSize(350, 100);
        scrollPaneTRON.setLayoutX(875);
        scrollPaneTRON.setLayoutY(155);
        scrollPaneTRON.setStyle("-fx-border-color: white ; -fx-border-width: 5px ; -fx-background: black ; -fx-background-radius: 15px ; -fx-border-radius: 10px ;");

        VBox contentVTRON = new VBox();

        ScrollPane scrollPaneCB = new ScrollPane();
        scrollPaneCB.setPrefSize(350, 100);
        scrollPaneCB.setLayoutX(875);
        scrollPaneCB.setLayoutY(530);
        scrollPaneCB.setStyle("-fx-border-color: white ; -fx-border-width: 5px ; -fx-background: black ;  -fx-background-radius: 15px ; -fx-border-radius: 10px ;  ");

        VBox contentVCB = new VBox();

        ScrollPane scrollPaneTETRIS = new ScrollPane();
        scrollPaneTETRIS.setPrefSize(350, 100);
        scrollPaneTETRIS.setLayoutX(875);
        scrollPaneTETRIS.setLayoutY(343);
        scrollPaneTETRIS.setStyle("-fx-border-color: white ; -fx-border-width: 5px ; -fx-background: black ; -fx-background-radius: 15px ; -fx-border-radius: 10px ; ");

        VBox contentVTETRIS = new VBox();

        Label label = new Label(Session.getInstance().getLogin());
        label.getStyleClass().add("nomJeu");
        label.setLayoutX(55);
        label.setLayoutY(25);

        Label menuScreen = new Label();
        menuScreen.setGraphic(Fond.getChoixFond());

        Label labelModificationMDP = new Label("Modification du mot de passe");
        labelModificationMDP.getStyleClass().add("LabelConnexionField");
        labelModificationMDP.setLayoutX(50);
        labelModificationMDP.setLayoutY(130);

        Line line = new Line();
        line.setStartX(400.0f);
        line.setStartY(150.0f);
        line.setEndX(400.0f);
        line.setEndY(500.0f);
        line.setStrokeWidth(2);
        line.setStroke(Color.WHITE);

        Label labelMotDePasse = new Label("Mot de passe");
        labelMotDePasse.getStyleClass().add("LabelConnexionField");
        labelMotDePasse.setLayoutX(110);
        labelMotDePasse.setLayoutY(200);
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("TextFieldConnexion");
        passwordField.setLayoutX(110);
        passwordField.setLayoutY(225);


        Label labelMotDePasse2 = new Label("Confirmation");
        labelMotDePasse2.getStyleClass().add("LabelConnexionField");
        labelMotDePasse2.setLayoutX(110);
        labelMotDePasse2.setLayoutY(300);

        PasswordField passwordField2 = new PasswordField();
        passwordField2.getStyleClass().add("TextFieldConnexion");
        passwordField2.setLayoutX(110);
        passwordField2.setLayoutY(325);

        ComboBox<String> comboBoxDepartement = new ComboBox<>();
        comboBoxDepartement.setLayoutX(110);
        comboBoxDepartement.setLayoutY(375);
        comboBoxDepartement.getItems().addAll("01 - Ain", "02 - Aisne", "03 - Allier", "04 - Alpes-de-Haute-Provence", "05 - Hautes-Alpes", "06 - Alpes-Maritimes", "07 - Ardèche", "08 - Ardennes", "09 - Ariège", "10 - Aube", "11 - Aude", "12 - Aveyron", "13 - Bouches-du-Rhône", "14 - Calvados", "15 - Cantal", "16 - Charente", "17 - Charente-Maritime", "18 - Cher", "19 - Corrèze", "2A - Corse-du-Sud", "2B - Haute-Corse", "21 - Côte-d'Or", "22 - Côtes-d'Armor", "23 - Creuse", "24 - Dordogne", "25 - Doubs", "26 - Drôme", "27 - Eure", "28 - Eure-et-Loir", "29 - Finistère", "30 - Gard", "31 - Haute-Garonne", "32 - Gers", "33 - Gironde", "34 - Hérault", "35 - Ille-et-Vilaine", "36 - Indre", "37 - Indre-et-Loire", "38 - Isère", "39 - Jura", "40 - Landes", "41 - Loir-et-Cher", "42 - Loire", "43 - Haute-Loire", "44 - Loire-Atlantique", "45 - Loiret", "46 - Lot", "47 - Lot-et-Garonne", "48 - Lozère", "49 - Maine-et-Loire", "50 - Manche", "51 - Marne", "52 - Haute-Marne", "53 - Mayenne", "54 - Meurthe-et-Moselle", "55 - Meuse", "56 - Morbihan", "57 - Moselle", "58 - Nièvre", "59 - Nord", "60 - Oise", "61 - Orne", "62 - Pas-de-Calais", "63 - Puy-de-Dôme", "64 - Pyrénées-Atlantiques", "65 - Hautes-Pyrénées", "66 - Pyrénées-Orientales", "67 - Bas-Rhin", "68 - Haut-Rhin", "69 - Rhône", "70 - Haute-Saône", "71 - Saône-et-Loire", "72 - Sarthe", "73 - Savoie", "74 - Haute-Savoie", "75 - Paris", "76 - Seine-Maritime", "77 - Seine-et-Marne", "78 - Yvelines", "79 - Deux-Sèvres", "80 - Somme", "81 - Tarn", "82 - Tarn-et-Garonne", "83 - Var", "84 - Vaucluse", "85 - Vendée", "86 - Vienne", "87 - Haute-Vienne", "88 - Vosges", "89 - Yonne", "90 - Territoire de Belfort", "91 - Essonne", "92 - Hauts-de-Seine", "93 - Seine-Saint-Denis", "94 - Val-de-Marne", "95 - Val-d'Oise", "971 - Guadeloupe", "972 - Martinique", "973 - Guyane", "974 - La Réunion", "976 - Mayotte");
        comboBoxDepartement.setValue("01 - Ain");
        comboBoxDepartement.getStyleClass().add("buttonEcran");
        comboBoxDepartement.setStyle("-fx-font-size: 12px; -fx-pref-width: 130px; -fx-pref-height: 20px;");

        Button buttonModifPassword = new Button("Mettre à jour le compte");
        buttonModifPassword.getStyleClass().add("btnGrey");
        buttonModifPassword.setLayoutX(83);
        buttonModifPassword.setLayoutY(450);

        Label labelErreur = new Label();
        labelErreur.getStyleClass().add("LabelError");
        labelErreur.setLayoutX(60);
        labelErreur.setLayoutY(500);

        Button supprimerCompte = new Button("Supprimer Compte");
        supprimerCompte.getStyleClass().add("btnRed");
        supprimerCompte.setLayoutX(212);
        supprimerCompte.setLayoutY(650);

        Button deconnexion = new Button("Deconnexion");
        deconnexion.getStyleClass().add("btnGrey");
        deconnexion.setLayoutX(50);
        deconnexion.setLayoutY(650);

        Button boutonRetour = new Button("Retour");
        boutonRetour.getStyleClass().add("btnGrey");
        boutonRetour.setLayoutX(212);
        boutonRetour.setLayoutY(600);

        Button changerModeJeu = new Button("Changer le mode de jeu");
        changerModeJeu.getStyleClass().add("btnGrey");
        changerModeJeu.setLayoutX(520);
        changerModeJeu.setLayoutY(650);

        Button menu = new Button("Menu");
        menu.getStyleClass().add("btnGrey");
        menu.setLayoutX(50);
        menu.setLayoutY(600);

        menu.setOnAction(event -> {
            VueMenu vueMenu = new VueMenu();
            vueMenu.demarrerMenu(stage);
        });

        boutonRetour.setOnAction(event -> {
            VueParametre vueParametre = new VueParametre();
            try {
                vueParametre.affichageVueParametre(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buttonModifPassword.setOnAction(event -> {
            if (!Objects.equals(passwordField.getText(), "") && !Objects.equals(passwordField2.getText(), "")) {
                if (passwordField.getText().equals(passwordField2.getText())) {
                    String departement;
                    ArrayList<String> listeDrom = new ArrayList<>(Arrays.asList("971 - Guadeloupe", "972 - Martinique", "973 - Guyane", "974 - La Réunion", "976 - Mayotte"));
                    if (listeDrom.contains(comboBoxDepartement.getValue())) {
                        departement = comboBoxDepartement.getValue().substring(0, 3);
                    } else {
                        departement = comboBoxDepartement.getValue().substring(0, 2);
                    }
                    PlayerManager.getInstance().updatePlayer(Session.getInstance().getLogin(), passwordField.getText(), departement);
                    labelErreur.setText("Mot de passe modifié");
                } else {
                    labelErreur.setText("Les mots de passe" + "\n" + "ne correspondent pas");
                }
            } else {
                labelErreur.setText("Veuillez remplir les champs");
            }
        });

        Label labelMeilleurScore = new Label("Meilleurs scores");
        labelMeilleurScore.getStyleClass().add("nomJeu");
        labelMeilleurScore.setLayoutX(600);
        labelMeilleurScore.setLayoutY(25);

        Label titreJeu = new Label("KOALA ROCK • Mode Infini");
        titreJeu.getStyleClass().add("labelJeu");
        titreJeu.setLayoutX(465);
        titreJeu.setLayoutY(110);

        changerModeJeu.setOnAction(event -> {
            if (ScoreAffiche == 0) {
                titreJeu.setText("KOALA ROCK • Mode Infini");
                contentVKR.getChildren().clear();
                List<Score> scoresDK = ScoreManager.getInstance().getScores();
                int i = 0;
                if (scoresDK.isEmpty()) {
                    Label labelScoreDKDK = new Label(" ");
                    labelScoreDKDK.getStyleClass().add("LabelConnexionField");
                    paneKoalaRock.setContent(labelScoreDKDK);
                } else {
                    for (Score scoreDK : scoresDK) {
                        if (scoreDK.getLogin() != null && scoreDK.getLogin().equals(Session.getInstance().getLogin())) {
                            Label labelScoreDK = new Label(scoreDK.getScore() + "");
                            labelScoreDK.getStyleClass().add("LabelConnexionField");
                            labelScoreDK.setLayoutX(450);
                            labelScoreDK.setLayoutY(150 + i * 35);
                            Label labelDateDK = new Label(scoreDK.getHorodatage().toLocalDateTime().getDayOfMonth() + "-"
                                    + scoreDK.getHorodatage().toLocalDateTime().getMonth() + "-"
                                    + scoreDK.getHorodatage().toLocalDateTime().getYear() + " ");
                            labelDateDK.getStyleClass().add("LabelConnexionField");
                            labelDateDK.setLayoutX(700);
                            labelDateDK.setLayoutY(150 + i * 35);
                            Label placeDK = new Label((i + 1) + ".");
                            placeDK.getStyleClass().add("LabelConnexionField");
                            placeDK.setLayoutX(400);
                            placeDK.setLayoutY(150 + i * 35);
                            Label labeldkScore = new Label();
                            labeldkScore.getStyleClass().add("LabelConnexionField");
                            labeldkScore.setText(placeDK.getText() + " " + labelScoreDK.getText() + " " + labelDateDK.getText());
                            contentVKR.getChildren().addAll(labeldkScore);
                            //contentVKR.getChildren().addAll(placeDK, labelScoreDK, labelDateDK);
                            //labeldkScore.setText("");
                            i++;
                        }
                    }
                    paneKoalaRock.setContent(contentVKR);
                    //paneKoalaRock.getChildren().addAll(contentVKR);
                }
                ScoreAffiche = 1;
            } else {
                titreJeu.setText("KOALA ROCK • Mode Classique");
                contentVKR.getChildren().clear();
                List<Double> scoresDK = ScoreManager.getInstance().getTempsByLogin(Session.getInstance().getLogin());
                int l = 0;
                if (!scoresDK.isEmpty()) {
                    for (Double scoreDK : scoresDK) {
                        if (scoreDK != null) {
                            Label labelScoreDK = new Label(scoreDK + " S");
                            labelScoreDK.getStyleClass().add("LabelConnexionField");
                            labelScoreDK.setLayoutX(450);
                            labelScoreDK.setLayoutY(150 + l * 35);
                            Label placeDK = new Label((l + 1) + ".");
                            placeDK.getStyleClass().add("LabelConnexionField");
                            placeDK.setLayoutX(400);
                            placeDK.setLayoutY(150 + l * 35);
                            Label labeldkScore = new Label();
                            labeldkScore.getStyleClass().add("LabelConnexionField");
                            labeldkScore.setText(placeDK.getText() + " " + labelScoreDK.getText());
                            contentVKR.getChildren().addAll(labeldkScore);
                            //paneKoalaRock.getChildren().add(contentVKR);
                            l++;
                        }
                    }
                    paneKoalaRock.setContent(contentVKR);
                } else {
                    Label labelScoreDK = new Label(" ");
                    labelScoreDK.getStyleClass().add("LabelConnexionField");
                    labelScoreDK.setLayoutX(450);
                    labelScoreDK.setLayoutY(150 + l * 35);
                    contentVKR.getChildren().addAll(labelScoreDK);
                    paneKoalaRock.setContent(contentVKR);
                    //paneKoalaRock.getChildren().add(contentVKR);
                }
                ScoreAffiche = 0;
            }
        });

        List<Score> scoresDK = ScoreManager.getInstance().getScores();
        int i = 0;

        if (scoresDK.isEmpty()) {
            Label labelScoreDK = new Label("Aucun score!");
            labelScoreDK.getStyleClass().add("LabelConnexionField");
            paneKoalaRock.setContent(labelScoreDK);
        } else {
            for (Score scoreDK : scoresDK) {
                if (scoreDK.getLogin() != null && scoreDK.getLogin().equals(Session.getInstance().getLogin())) {
                    Label labelScoreDK = new Label(scoreDK.getScore() + "");
                    labelScoreDK.getStyleClass().add("LabelConnexionField");
                    labelScoreDK.setLayoutX(450);
                    labelScoreDK.setLayoutY(150 + i * 35);
                    Label labelDateDK = new Label(scoreDK.getHorodatage().toLocalDateTime().getDayOfMonth() + "-"
                            + scoreDK.getHorodatage().toLocalDateTime().getMonth() + "-"
                            + scoreDK.getHorodatage().toLocalDateTime().getYear() + " ");
                    labelDateDK.getStyleClass().add("LabelConnexionField");
                    labelDateDK.setLayoutX(600);
                    labelDateDK.setLayoutY(150 + i * 35);
                    Label placeDK = new Label((i + 1) + ".");
                    placeDK.getStyleClass().add("LabelConnexionField");
                    placeDK.setLayoutX(400);
                    placeDK.setLayoutY(150 + i * 35);

                    Label labeldkScore = new Label();
                    labeldkScore.getStyleClass().add("LabelConnexionField");
                    labeldkScore.setText(placeDK.getText() + " " + labelScoreDK.getText() + " " + labelDateDK.getText());
                    contentVKR.getChildren().addAll(labeldkScore);
                    i++;
                }
            }
            paneKoalaRock.setContent(contentVKR);
        }

        Label titreJeuTRON = new Label("MOTRON");
        titreJeuTRON.getStyleClass().add("labelJeu");
        titreJeuTRON.setLayoutX(995);
        titreJeuTRON.setLayoutY(120);

        List<Score> scoresTRON = ScoreManager.getInstance().getScoresTRON();
        int j = 0;
        int compteurTRON = 0;
        if (scoresTRON.isEmpty()) {
            Label tron = new Label("Aucun score dans ce jeu!");
            tron.getStyleClass().add("LabelConnexionField");
            //tron.setLayoutX(450);
            //tron.setLayoutY(150);
            contentVTRON.getChildren().add(tron);
            scrollPaneTRON.setContent(tron);
        } else {
            for (Score scoreTRON : scoresTRON) {
                if (scoreTRON.getLogin() != null && scoreTRON.getLogin().equals(Session.getInstance().getLogin())) {

                    Label labelScoreTRON = new Label(scoreTRON.getScore() + "");
                    labelScoreTRON.getStyleClass().add("LabelConnexionField");
                    labelScoreTRON.setLayoutX(450);
                    labelScoreTRON.setLayoutY(150 + j * 35);

                    Label labelDateTRON = new Label(scoreTRON.getHorodatage().toLocalDateTime().getDayOfMonth() + "-" + scoreTRON.getHorodatage().toLocalDateTime().getMonth() + "-" + scoreTRON.getHorodatage().toLocalDateTime().getYear() + " ");
                    labelDateTRON.getStyleClass().add("LabelConnexionField");
                    labelDateTRON.setLayoutX(600);
                    labelDateTRON.setLayoutY(150 + j * 35);

                    Label placeTRON = new Label((j + 1) + ".");
                    placeTRON.getStyleClass().add("LabelConnexionField");
                    placeTRON.setLayoutX(400);
                    placeTRON.setLayoutY(150 + j * 35);

                    Label labeltronScore = new Label();
                    labeltronScore.getStyleClass().add("LabelConnexionField");
                    labeltronScore.setText(placeTRON.getText() + " " + labelScoreTRON.getText() + " " + labelDateTRON.getText());


                    contentVTRON.getChildren().addAll(labeltronScore);

                    j++;
                    compteurTRON++;
                } else if (compteurTRON == 3) {
                    break;
                }
            }
            scrollPaneTRON.setContent(contentVTRON);
        }

        Label titreJeuCB = new Label("CASSE-BRIQUE");
        titreJeuCB.getStyleClass().add("labelJeu");
        titreJeuCB.setLayoutX(950);
        titreJeuCB.setLayoutY(492);

        List<Score> scoresCB = ScoreManager.getInstance().getScoresCB();
        int k = 0;
        int compteurCB = 0;
        if (scoresCB.isEmpty()) {
            Label cb = new Label("Aucun score dans ce jeu!");
            cb.getStyleClass().add("LabelConnexionField");
            //cb.setLayoutX(925);
            //cb.setLayoutY(150);
            contentVCB.getChildren().addAll(cb);
            scrollPaneCB.setContent(cb);
        } else {
            for (Score scoreCB : scoresCB) {
                if (scoreCB.getLogin() != null && scoreCB.getLogin().equals(Session.getInstance().getLogin())) {

                    Label labelScoreCB = new Label(scoreCB.getScore() + "");
                    labelScoreCB.getStyleClass().add("LabelConnexionField");
                    labelScoreCB.setLayoutX(925);
                    labelScoreCB.setLayoutY(150 + k * 35);

                    Label labelDateCB = new Label(scoreCB.getHorodatage().toLocalDateTime().getDayOfMonth() + "-" + scoreCB.getHorodatage().toLocalDateTime().getMonth() + "-" + scoreCB.getHorodatage().toLocalDateTime().getYear() + " ");
                    labelDateCB.getStyleClass().add("LabelConnexionField");
                    labelDateCB.setLayoutX(925);
                    labelDateCB.setLayoutY(150 + k * 35);

                    Label placeCB = new Label((k + 1) + ".");
                    placeCB.getStyleClass().add("LabelConnexionField");
                    placeCB.setLayoutX(925);
                    placeCB.setLayoutY(150 + k * 35);

                    Label labelcbScore = new Label();
                    labelcbScore.getStyleClass().add("LabelConnexionField");
                    labelcbScore.setText(placeCB.getText() + " " + labelScoreCB.getText() + " " + labelDateCB.getText());

                    contentVCB.getChildren().addAll(labelcbScore);

                    k++;
                    compteurCB++;

                } else if (compteurCB == 3) {
                    break;
                }
            }
            scrollPaneCB.setContent(contentVCB);
        }

        Label titreJeuTETRIS = new Label("FACTORY FALL");
        titreJeuTETRIS.getStyleClass().add("labelJeu");
        titreJeuTETRIS.setLayoutX(960);
        titreJeuTETRIS.setLayoutY(310);

        List<Score> scoresTETRIS = ScoreManager.getInstance().getScoresTETRIS();
        int p = 0;
        int compteurTETRIS = 0;
        if (scoresTETRIS.isEmpty()) {
            Label tetris = new Label("Aucun score dans ce jeu!");
            tetris.getStyleClass().add("LabelConnexionField");
            //tetris.setLayoutX(450);
            //tetris.setLayoutY(150);
            contentVTETRIS.getChildren().addAll(tetris);
            scrollPaneTETRIS.setContent(tetris);
        } else {
            for (Score scoreTETRIS : scoresTETRIS) {
                if (scoreTETRIS.getLogin() != null && scoreTETRIS.getLogin().equals(Session.getInstance().getLogin())) {

                    Label labelScoreTETRIS = new Label(scoreTETRIS.getScore() + "");
                    labelScoreTETRIS.getStyleClass().add("LabelConnexionField");
                    labelScoreTETRIS.setLayoutX(450);
                    labelScoreTETRIS.setLayoutY(150 + p * 35);

                    Label labelDateTETRIS = new Label(scoreTETRIS.getHorodatage().toLocalDateTime().getDayOfMonth() + "-" + scoreTETRIS.getHorodatage().toLocalDateTime().getMonth() + "-" + scoreTETRIS.getHorodatage().toLocalDateTime().getYear() + " ");
                    labelDateTETRIS.getStyleClass().add("LabelConnexionField");
                    labelDateTETRIS.setLayoutX(600);
                    labelDateTETRIS.setLayoutY(150 + p * 35);

                    Label placeTETRIS = new Label((p + 1) + ".");
                    placeTETRIS.getStyleClass().add("LabelConnexionField");
                    placeTETRIS.setLayoutX(400);
                    placeTETRIS.setLayoutY(150 + p * 35);

                    Label labeltetrisScore = new Label();
                    labeltetrisScore.getStyleClass().add("LabelConnexionField");
                    labeltetrisScore.setText(placeTETRIS.getText() + " " + labelScoreTETRIS.getText() + " " + labelDateTETRIS.getText());

                    contentVTETRIS.getChildren().addAll(labeltetrisScore);

                    p++;
                    compteurTETRIS++;
                } else if (compteurTETRIS == 3) {
                    break;
                }
            }
            scrollPaneTETRIS.setContent(contentVTETRIS);
        }

        supprimerCompte.setOnMouseClicked(event -> {
            Label alerte = new Label("Voulez vous vraiment" + "\n" + "supprimer votre compte ?");
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
                PlayerManager.getInstance().deletePlayer(Session.getInstance().getLogin());
                Session.getInstance().disconnect();
                VueMenu vueMenu = new VueMenu();
                vueMenu.demarrerMenu(stage);
                pane.getChildren().removeAll(oui, non, rectangle, alerte);
            });
            non.setOnAction(e -> {
                pane.getChildren().removeAll(oui, non, rectangle, alerte);
            });

            pane.getChildren().addAll(rectangle, oui, non, alerte);
        });

        deconnexion.setOnMouseClicked(event -> {
            Session.getInstance().disconnect();
            VueMenu vueMenu = new VueMenu();
            vueMenu.demarrerMenu(stage);
        });

        pane.getChildren().addAll(menuScreen, changerModeJeu, label, labelMotDePasse, passwordField, labelMotDePasse2, passwordField2, buttonModifPassword, comboBoxDepartement, labelMeilleurScore, boutonRetour, supprimerCompte, deconnexion, menu);
        pane.getChildren().add(labelErreur);
        pane.getChildren().add(labelModificationMDP);
        pane.getChildren().add(line);
        pane.getChildren().addAll(titreJeu, titreJeuTRON, titreJeuCB, titreJeuTETRIS);
        pane.getChildren().add(paneKoalaRock);
        pane.getChildren().addAll(scrollPaneTRON, scrollPaneCB, scrollPaneTETRIS);


        stage.setTitle("Paramètre");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        pane.setStyle("-fx-border-color: white ; -fx-border-width: 10px ; -fx-background-color: black ; -fx-background-radius: 10px ;");
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
    }
}
