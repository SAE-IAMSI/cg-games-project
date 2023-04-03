package games.project.koala_rock.View;

import games.project.koala_rock.Metier.entite.Score;
import games.project.koala_rock.Metier.manager.ScoreManager;
import games.project.koala_rock.Model.Fond;
import games.project.koala_rock.RessourcesAccess;
import games.project.stockage.Session;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VueMeilleurScore {

    public void affichageVueMeilleurScore(Stage stage) throws IOException {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1280, 720);
        scene.getStylesheets().add(String.valueOf(RessourcesAccess.class.getResource("css/style.css")));

        Label menuScreen = new Label();
        menuScreen.setGraphic(Fond.getChoixFond());
        pane.getChildren().add(menuScreen);


        Label labelMeilleurScore = new Label("Meilleurs scores");
        labelMeilleurScore.setLayoutX(480);
        labelMeilleurScore.setLayoutY(40);
        labelMeilleurScore.setFont(new javafx.scene.text.Font("Goldman", 40));
        labelMeilleurScore.setTextFill(Color.WHITE);
        labelMeilleurScore.setUnderline(true);

        Label labelClassic = new Label("Classic");
        labelClassic.setLayoutX(250);
        labelClassic.setLayoutY(120);
        labelClassic.setFont(new javafx.scene.text.Font("Goldman", 25));
        labelClassic.setTextFill(Color.WHITE);

        Label labelInfini = new Label("Infini");
        labelInfini.setLayoutX(900);
        labelInfini.setLayoutY(120);
        labelInfini.setFont(new javafx.scene.text.Font("Goldman", 25));
        labelInfini.setTextFill(Color.WHITE);

        Line line = new Line();
        line.setStartX(640.0f);
        line.setStartY(200.0f);
        line.setEndX(640.0f);
        line.setEndY(450.0f);
        line.setStrokeWidth(2);
        line.setStroke(Color.WHITE);

        Button buttonRetour = new Button("Retour");
        buttonRetour.getStyleClass().add("btnGrey");
        buttonRetour.setLayoutX(645);
        buttonRetour.setLayoutY(620);

        Button menu = new Button("Menu");
        menu.getStyleClass().add("btnGrey");
        menu.setLayoutX(545);
        menu.setLayoutY(620);

        ComboBox<String> comboBoxDepartement = new ComboBox<>();
        comboBoxDepartement.setLayoutX(50);
        comboBoxDepartement.setLayoutY(30);
        comboBoxDepartement.setPromptText("Choisissez un département");
        comboBoxDepartement.setMinSize(250, 45);
        comboBoxDepartement.getItems().addAll("Global", "01 - Ain", "02 - Aisne", "03 - Allier", "04 - Alpes-de-Haute-Provence", "05 - Hautes-Alpes", "06 - Alpes-Maritimes", "07 - Ardèche", "08 - Ardennes", "09 - Ariège", "10 - Aube", "11 - Aude", "12 - Aveyron", "13 - Bouches-du-Rhône", "14 - Calvados", "15 - Cantal", "16 - Charente", "17 - Charente-Maritime", "18 - Cher", "19 - Corrèze", "2A - Corse-du-Sud", "2B - Haute-Corse", "21 - Côte-d'Or", "22 - Côtes-d'Armor", "23 - Creuse", "24 - Dordogne", "25 - Doubs", "26 - Drôme", "27 - Eure", "28 - Eure-et-Loir", "29 - Finistère", "30 - Gard", "31 - Haute-Garonne", "32 - Gers", "33 - Gironde", "34 - Hérault", "35 - Ille-et-Vilaine", "36 - Indre", "37 - Indre-et-Loire", "38 - Isère", "39 - Jura", "40 - Landes", "41 - Loir-et-Cher", "42 - Loire", "43 - Haute-Loire", "44 - Loire-Atlantique", "45 - Loiret", "46 - Lot", "47 - Lot-et-Garonne", "48 - Lozère", "49 - Maine-et-Loire", "50 - Manche", "51 - Marne", "52 - Haute-Marne", "53 - Mayenne", "54 - Meurthe-et-Moselle", "55 - Meuse", "56 - Morbihan", "57 - Moselle", "58 - Nièvre", "59 - Nord", "60 - Oise", "61 - Orne", "62 - Pas-de-Calais", "63 - Puy-de-Dôme", "64 - Pyrénées-Atlantiques", "65 - Hautes-Pyrénées", "66 - Pyrénées-Orientales", "67 - Bas-Rhin", "68 - Haut-Rhin", "69 - Rhône", "70 - Haute-Saône", "71 - Saône-et-Loire", "72 - Sarthe", "73 - Savoie", "74 - Haute-Savoie", "75 - Paris", "76 - Seine-Maritime", "77 - Seine-et-Marne", "78 - Yvelines", "79 - Deux-Sèvres", "80 - Somme", "81 - Tarn", "82 - Tarn-et-Garonne", "83 - Var", "84 - Vaucluse", "85 - Vendée", "86 - Vienne", "87 - Haute-Vienne", "88 - Vosges", "89 - Yonne", "90 - Territoire de Belfort", "91 - Essonne", "92 - Hauts-de-Seine", "93 - Seine-Saint-Denis", "94 - Val-de-Marne", "95 - Val-d'Oise", "971 - Guadeloupe", "972 - Martinique", "973 - Guyane", "974 - La Réunion", "976 - Mayotte");
        comboBoxDepartement.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            pane.getChildren().removeAll(pane.getChildren());
            pane.getChildren().remove(line);
            pane.getChildren().addAll(menuScreen, labelMeilleurScore, labelClassic, labelInfini, line, buttonRetour, menu, comboBoxDepartement);
            if (newValue.equals("Global")) {
                List<Score> scores = ScoreManager.getInstance().getScores();
                int i = 0;
                while (i < 10 && i < scores.size()) {
                    Label labelPseudoInfini;
                    if (scores.get(i).getLogin() == null) {
                        labelPseudoInfini = new Label("Invité");
                    } else {
                        labelPseudoInfini = new Label(scores.get(i).getLogin());
                    }
                    Label labelScoreInfini = new Label(String.valueOf(scores.get(i).getScore()));
                    Label placeInfini = new Label(i + 1 + ".");

                    if (i == 0) {
                        labelScoreInfini.setTextFill(Color.GOLD);
                        labelPseudoInfini.setTextFill(Color.GOLD);
                        placeInfini.setTextFill(Color.GOLD);
                    } else if (i == 1) {
                        labelScoreInfini.setTextFill(Color.GRAY);
                        labelPseudoInfini.setTextFill(Color.GRAY);
                        placeInfini.setTextFill(Color.GRAY);
                    } else if (i == 2) {
                        labelScoreInfini.setTextFill(Color.BROWN);
                        labelPseudoInfini.setTextFill(Color.BROWN);
                        placeInfini.setTextFill(Color.BROWN);
                    } else {
                        labelScoreInfini.setTextFill(Color.WHITE);
                        labelPseudoInfini.setTextFill(Color.WHITE);
                        placeInfini.setTextFill(Color.WHITE);
                    }

                    labelScoreInfini.getStyleClass().add("LabelScore");
                    labelPseudoInfini.getStyleClass().add("LabelScore");
                    placeInfini.getStyleClass().add("LabelScore");

                    labelPseudoInfini.setLayoutX(800);
                    labelPseudoInfini.setLayoutY(165 + 35 * (i + 1));
                    labelScoreInfini.setLayoutX(1100);
                    labelScoreInfini.setLayoutY(165 + 35 * (i + 1));
                    placeInfini.setLayoutX(750);
                    placeInfini.setLayoutY(165 + 35 * (i + 1));

                    pane.getChildren().add(labelPseudoInfini);
                    pane.getChildren().add(labelScoreInfini);
                    pane.getChildren().add(placeInfini);
                    i++;
                }

                int j = 0;
                Map<Integer, Double> scoresTemps = ScoreManager.getInstance().getScoresTemps();
                while (j < 10 && j < scoresTemps.size()) {
                    Label labelPseudoTemps;
                    if (ScoreManager.getInstance().getLoginTemps((Integer) scoresTemps.keySet().toArray()[j]) == null) {
                        labelPseudoTemps = new Label("Invité");
                    } else {
                        labelPseudoTemps = new Label(ScoreManager.getInstance().getLoginTemps((Integer) scoresTemps.keySet().toArray()[j]));
                    }
                    Label labelScoreTemps = new Label(scoresTemps.values().toArray()[j] + " S");
                    Label placeTemps = new Label(j + 1 + ".");
                    if (j == 0) {
                        labelScoreTemps.setTextFill(Color.GOLD);
                        labelPseudoTemps.setTextFill(Color.GOLD);
                        placeTemps.setTextFill(Color.GOLD);
                    } else if (j == 1) {
                        labelScoreTemps.setTextFill(Color.GRAY);
                        labelPseudoTemps.setTextFill(Color.GRAY);
                        placeTemps.setTextFill(Color.GRAY);
                    } else if (j == 2) {
                        labelScoreTemps.setTextFill(Color.BROWN);
                        labelPseudoTemps.setTextFill(Color.BROWN);
                        placeTemps.setTextFill(Color.BROWN);
                    } else {
                        labelScoreTemps.setTextFill(Color.WHITE);
                        labelPseudoTemps.setTextFill(Color.WHITE);
                        placeTemps.setTextFill(Color.WHITE);
                    }

                    labelScoreTemps.getStyleClass().add("LabelScore");
                    labelPseudoTemps.getStyleClass().add("LabelScore");
                    placeTemps.getStyleClass().add("LabelScore");

                    labelPseudoTemps.setLayoutX(200);
                    labelPseudoTemps.setLayoutY(165 + 35 * (j + 1));
                    labelScoreTemps.setLayoutX(500);
                    labelScoreTemps.setLayoutY(165 + 35 * (j + 1));
                    placeTemps.setLayoutX(150);
                    placeTemps.setLayoutY(165 + 35 * (j + 1));

                    pane.getChildren().add(labelPseudoTemps);
                    pane.getChildren().add(labelScoreTemps);
                    pane.getChildren().add(placeTemps);
                    j++;
                }
            } else {
                String departement;
                ArrayList<String> listeDrom = new ArrayList<>(Arrays.asList("971 - Guadeloupe", "972 - Martinique", "973 - Guyane", "974 - La Réunion", "976 - Mayotte"));
                if (listeDrom.contains(newValue)) {
                    departement = newValue.substring(0, 3);
                } else {
                    departement = newValue.substring(0, 2);
                }
                List<Score> scores = ScoreManager.getInstance().getAllByDepartement(departement);
                int i = 0;
                while (i < 10 && i < scores.size()) {
                    Label labelPseudoInfini;
                    if (scores.get(i).getLogin() == null) {
                        labelPseudoInfini = new Label("Invité");
                    } else {
                        labelPseudoInfini = new Label(scores.get(i).getLogin());
                    }
                    Label labelScoreInfini = new Label(String.valueOf(scores.get(i).getScore()));
                    Label placeInfini = new Label(i + 1 + ".");

                    if (i == 0) {
                        labelScoreInfini.setTextFill(Color.GOLD);
                        labelPseudoInfini.setTextFill(Color.GOLD);
                        placeInfini.setTextFill(Color.GOLD);
                    } else if (i == 1) {
                        labelScoreInfini.setTextFill(Color.GRAY);
                        labelPseudoInfini.setTextFill(Color.GRAY);
                        placeInfini.setTextFill(Color.GRAY);
                    } else if (i == 2) {
                        labelScoreInfini.setTextFill(Color.BROWN);
                        labelPseudoInfini.setTextFill(Color.BROWN);
                        placeInfini.setTextFill(Color.BROWN);
                    } else {
                        labelScoreInfini.setTextFill(Color.WHITE);
                        labelPseudoInfini.setTextFill(Color.WHITE);
                        placeInfini.setTextFill(Color.WHITE);
                    }

                    labelScoreInfini.getStyleClass().add("LabelScore");
                    labelPseudoInfini.getStyleClass().add("LabelScore");
                    placeInfini.getStyleClass().add("LabelScore");

                    labelPseudoInfini.setLayoutX(800);
                    labelPseudoInfini.setLayoutY(165 + 35 * (i + 1));
                    labelScoreInfini.setLayoutX(1100);
                    labelScoreInfini.setLayoutY(165 + 35 * (i + 1));
                    placeInfini.setLayoutX(750);
                    placeInfini.setLayoutY(165 + 35 * (i + 1));

                    pane.getChildren().add(labelPseudoInfini);
                    pane.getChildren().add(labelScoreInfini);
                    pane.getChildren().add(placeInfini);
                    i++;
                }

                int j = 0;
                Map<Integer, Double> scoresTemps = ScoreManager.getInstance().getAllTempsByDepartement(departement);
                while (j < 10 && j < scoresTemps.size()) {
                    Label labelPseudoTemps;
                    if (ScoreManager.getInstance().getLoginTemps((Integer) scoresTemps.keySet().toArray()[j]) == null) {
                        labelPseudoTemps = new Label("Invité");
                    } else {
                        labelPseudoTemps = new Label(ScoreManager.getInstance().getLoginTemps((Integer) scoresTemps.keySet().toArray()[j]));
                    }
                    Label labelScoreTemps = new Label(scoresTemps.values().toArray()[j] + " S");
                    Label placeTemps = new Label(j + 1 + ".");
                    if (j == 0) {
                        labelScoreTemps.setTextFill(Color.GOLD);
                        labelPseudoTemps.setTextFill(Color.GOLD);
                        placeTemps.setTextFill(Color.GOLD);
                    } else if (j == 1) {
                        labelScoreTemps.setTextFill(Color.GRAY);
                        labelPseudoTemps.setTextFill(Color.GRAY);
                        placeTemps.setTextFill(Color.GRAY);
                    } else if (j == 2) {
                        labelScoreTemps.setTextFill(Color.BROWN);
                        labelPseudoTemps.setTextFill(Color.BROWN);
                        placeTemps.setTextFill(Color.BROWN);
                    } else {
                        labelScoreTemps.setTextFill(Color.WHITE);
                        labelPseudoTemps.setTextFill(Color.WHITE);
                        placeTemps.setTextFill(Color.WHITE);
                    }

                    labelScoreTemps.getStyleClass().add("LabelScore");
                    labelPseudoTemps.getStyleClass().add("LabelScore");
                    placeTemps.getStyleClass().add("LabelScore");

                    labelPseudoTemps.setLayoutX(200);
                    labelPseudoTemps.setLayoutY(165 + 35 * (j + 1));
                    labelScoreTemps.setLayoutX(500);
                    labelScoreTemps.setLayoutY(165 + 35 * (j + 1));
                    placeTemps.setLayoutX(150);
                    placeTemps.setLayoutY(165 + 35 * (j + 1));

                    pane.getChildren().add(labelPseudoTemps);
                    pane.getChildren().add(labelScoreTemps);
                    pane.getChildren().add(placeTemps);
                    j++;
                }
            }
        });
        comboBoxDepartement.getStyleClass().add("buttonEcran");
        comboBoxDepartement.setStyle("-fx-font-size: 12px; -fx-pref-width: 130px; -fx-pref-height: 20px;");


        //  Retour sur le menu
        buttonRetour.setOnAction(event -> {
            VueParametre vueParametre = new VueParametre();
            try {
                vueParametre.affichageVueParametre(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //  Retour sur le menu
        menu.setOnAction(event -> {
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

        // Affichage des meilleurs scores
        List<Score> scores = ScoreManager.getInstance().getScores();
        int i = 0;
        while (i < 10 && i < scores.size()) {
            Label labelPseudoInfini;
            if (scores.get(i).getLogin() == null) {
                labelPseudoInfini = new Label("Invité");
            } else {
                labelPseudoInfini = new Label(scores.get(i).getLogin());
            }
            Label labelScoreInfini = new Label(String.valueOf(scores.get(i).getScore()));
            Label placeInfini = new Label(i + 1 + ".");

            if (i == 0) {
                labelScoreInfini.setTextFill(Color.GOLD);
                labelPseudoInfini.setTextFill(Color.GOLD);
                placeInfini.setTextFill(Color.GOLD);
            } else if (i == 1) {
                labelScoreInfini.setTextFill(Color.GRAY);
                labelPseudoInfini.setTextFill(Color.GRAY);
                placeInfini.setTextFill(Color.GRAY);
            } else if (i == 2) {
                labelScoreInfini.setTextFill(Color.BROWN);
                labelPseudoInfini.setTextFill(Color.BROWN);
                placeInfini.setTextFill(Color.BROWN);
            } else {
                labelScoreInfini.setTextFill(Color.WHITE);
                labelPseudoInfini.setTextFill(Color.WHITE);
                placeInfini.setTextFill(Color.WHITE);
            }

            labelScoreInfini.getStyleClass().add("LabelScore");
            labelPseudoInfini.getStyleClass().add("LabelScore");
            placeInfini.getStyleClass().add("LabelScore");

            labelPseudoInfini.setLayoutX(800);
            labelPseudoInfini.setLayoutY(165 + 35 * (i + 1));
            labelScoreInfini.setLayoutX(1100);
            labelScoreInfini.setLayoutY(165 + 35 * (i + 1));
            placeInfini.setLayoutX(750);
            placeInfini.setLayoutY(165 + 35 * (i + 1));

            pane.getChildren().add(labelPseudoInfini);
            pane.getChildren().add(labelScoreInfini);
            pane.getChildren().add(placeInfini);
            i++;
        }

        int j = 0;
        Map<Integer, Double> scoresTemps = ScoreManager.getInstance().getScoresTemps();
        while (j < 10 && j < scoresTemps.size()) {
            Label labelPseudoTemps;
            if (ScoreManager.getInstance().getLoginTemps((Integer) scoresTemps.keySet().toArray()[j]) == null) {
                labelPseudoTemps = new Label("Invité");
            } else {
                labelPseudoTemps = new Label(ScoreManager.getInstance().getLoginTemps((Integer) scoresTemps.keySet().toArray()[j]));
            }
            Label labelScoreTemps = new Label(scoresTemps.values().toArray()[j] + " S");
            Label placeTemps = new Label(j + 1 + ".");
            if (j == 0) {
                labelScoreTemps.setTextFill(Color.GOLD);
                labelPseudoTemps.setTextFill(Color.GOLD);
                placeTemps.setTextFill(Color.GOLD);
            } else if (j == 1) {
                labelScoreTemps.setTextFill(Color.GRAY);
                labelPseudoTemps.setTextFill(Color.GRAY);
                placeTemps.setTextFill(Color.GRAY);
            } else if (j == 2) {
                labelScoreTemps.setTextFill(Color.BROWN);
                labelPseudoTemps.setTextFill(Color.BROWN);
                placeTemps.setTextFill(Color.BROWN);
            } else {
                labelScoreTemps.setTextFill(Color.WHITE);
                labelPseudoTemps.setTextFill(Color.WHITE);
                placeTemps.setTextFill(Color.WHITE);
            }

            labelScoreTemps.getStyleClass().add("LabelScore");
            labelPseudoTemps.getStyleClass().add("LabelScore");
            placeTemps.getStyleClass().add("LabelScore");

            labelPseudoTemps.setLayoutX(200);
            labelPseudoTemps.setLayoutY(165 + 35 * (j + 1));
            labelScoreTemps.setLayoutX(500);
            labelScoreTemps.setLayoutY(165 + 35 * (j + 1));
            placeTemps.setLayoutX(150);
            placeTemps.setLayoutY(165 + 35 * (j + 1));

            pane.getChildren().add(labelPseudoTemps);
            pane.getChildren().add(labelScoreTemps);
            pane.getChildren().add(placeTemps);
            j++;
        }

        // pane.getChildren().add(menuScreen);
        pane.setStyle("-fx-border-color: white ; -fx-border-width: 10px ; -fx-background-color: black ; -fx-background-radius: 10px ;");
        pane.getChildren().addAll(labelMeilleurScore, buttonRetour, comboBoxDepartement, menu, labelClassic, labelInfini, line);
        stage.setScene(scene);
        stage.setTitle("Meilleurs scores");
        stage.show();
    }
}
