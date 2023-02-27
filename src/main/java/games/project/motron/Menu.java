package games.project.motron;

import games.project.motron.metier.entite.AuthPlayer;
import games.project.motron.metier.entite.Player;
import games.project.motron.metier.manager.PlayerManager;
import games.project.motron.stockage.Security;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import static javafx.scene.input.KeyCode.*;

public class Menu extends GridPane {


    private Button buJouer;
    private Button buClassement;
    private Button buPersonnaliser;
    private Button buParametres;
    private Button buRegles;
    private Button buQuitter;
    private Button buSprites;
    private Label titre;
    private VBox listeBouton;
    private VBox listePersonnaliser;
    private VBox listeSprites;
    private Scene scene;
    private boolean menuOn = true;
    private Moto j1;
    private Moto j2;
    private Arene arene;
    private ArrayList<KeyCode> keyList = new ArrayList<>();
    private int largeurMenu = 800;
    private int hauteurMenu = 800;
    private MatriceArene matriceArene = new MatriceArene();
    private MediaPlayer startSound;
    private MediaPlayer mediaPlayerMenu;
    private MediaPlayer mediaPlayerJeu;
    private Pane fen;
    private Button buConnexionJ1;
    private Button buConnexionJ2;
    private Button buCreeCompte;
    private Button buCompte;
    private Player player1;
    private Player player2;

    public Menu() {
        this.setMinHeight(hauteurMenu);
        this.setMinWidth(largeurMenu);
        this.setAlignment(Pos.CENTER);
        this.getStylesheets().add("file:src/main/resources/css/style.css");

        buJouer = new Button("JOUER");
        buClassement = new Button("CLASSEMENT");
        buPersonnaliser = new Button("PERSONNALISER");
        buParametres = new Button("PARAMETRES");
        buRegles = new Button("REGLES");
        buQuitter = new Button("QUITTER");
        buSprites = new Button("SPRITES");
        titre = new Label("MOTRON");
        player1 = new Player("J1");
        player2 = new Player("J2");
        j1 = new Moto("J1", new Sprite(new Image("file:src/main/resources/images/moto/TronLegacyBleu.png", 60, 40, true, true), Color.LIGHTBLUE, "Motron Bleu"), player1.getName());
        j2 = new Moto("J2", new Sprite(new Image("file:src/main/resources/images/moto/TronLegacyJaune.png", 60, 40, true, true), Color.YELLOW, "Motron Jaune"), player2.getName());


        buCompte = new Button("COMPTE");
        buConnexionJ1 = new Button("CONNEXION J1");
        buConnexionJ2 = new Button("CONNEXION J2");
        buCreeCompte = new Button("CREER COMPTE");

        j1.setAdversaire(j2);
        j2.setAdversaire(j1);

        String musicMenu = "src/main/resources/music/MenuMusic8bit.mp3";
        Media soundMenu = new Media(new File(musicMenu).toURI().toString());
        mediaPlayerMenu = new MediaPlayer(soundMenu);
        mediaPlayerMenu.setCycleCount(MediaPlayer.INDEFINITE);

        String musicJeu = "src/main/resources/music/GameMusic8bit.mp3";
        Media soundJeu = new Media(new File(musicJeu).toURI().toString());
        mediaPlayerJeu = new MediaPlayer(soundJeu);
        mediaPlayerJeu.setCycleCount(MediaPlayer.INDEFINITE);
        String linkSoundStart = "src/main/resources/music/StartSound.mp3";
        Media soundStart = new Media(new File(linkSoundStart).toURI().toString());
        startSound = new MediaPlayer(soundStart);

        keyList.add(Z);
        keyList.add(Q);
        keyList.add(S);
        keyList.add(D);
        keyList.add(F);
        keyList.add(UP);
        keyList.add(LEFT);
        keyList.add(DOWN);
        keyList.add(RIGHT);
        keyList.add(N);
        keyList.add(G);
        keyList.add(H);

        ArrayList<Button> b = new ArrayList<>();
        Button z = new Button("Z");
        Button q = new Button("Q");
        Button s = new Button("S");
        Button d = new Button("D");
        Button f = new Button("F");
        Button up = new Button("UP");
        Button left = new Button("LEFT");
        Button down = new Button("DOWN");
        Button right = new Button("RIGHT");
        Button n = new Button("N");
        Button g = new Button("G");
        Button h = new Button("H");

        Button retour = new Button("RETOUR");
        retour.setFont(Jeu.policeTRON);
        retour.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        retour.setCursor(Cursor.HAND);

        b.add(z);
        b.add(q);
        b.add(s);
        b.add(d);
        b.add(f);
        b.add(up);
        b.add(left);
        b.add(down);
        b.add(right);
        b.add(n);
        b.add(g);
        b.add(h);

        ArrayList<Label> labelTouches = new ArrayList<>();
        Label hautJ1 = new Label("J1 HAUT");
        Label gaucheJ1 = new Label("J1 GAUCHE");
        Label basJ1 = new Label("J1 BAS");
        Label droiteJ1 = new Label("J1 DROITE");
        Label traineeJ1 = new Label("J1 TRAINEE");
        Label hautJ2 = new Label("J2 HAUT");
        Label basJ2 = new Label("J2 BAS");
        Label gaucheJ2 = new Label("J2 GAUCHE");
        Label droiteJ2 = new Label("J2 DROITE");
        Label traineeJ2 = new Label("J2 TRAINEE");
        Label hitbox = new Label("HITBOX");
        Label invis = new Label("INVISIBLE");

        labelTouches.add(hautJ1);
        labelTouches.add(gaucheJ1);
        labelTouches.add(basJ1);
        labelTouches.add(droiteJ1);
        labelTouches.add(traineeJ1);
        labelTouches.add(hautJ2);
        labelTouches.add(gaucheJ2);
        labelTouches.add(basJ2);
        labelTouches.add(droiteJ2);
        labelTouches.add(traineeJ2);
        labelTouches.add(hitbox);
        labelTouches.add(invis);

        titre.setFont(Jeu.policeTITRE);
        titre.setStyle("-fx-text-fill: white; \n" +
                "-fx-stroke: black");


        buJouer.setFont(Jeu.policeTRON);
        buJouer.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        buJouer.setCursor(Cursor.HAND);

        buClassement.setFont(Jeu.policeTRON);
        buClassement.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        buClassement.setCursor(Cursor.HAND);

        buPersonnaliser.setFont(Jeu.policeTRON);
        buPersonnaliser.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        buPersonnaliser.setCursor(Cursor.HAND);


        buParametres.setFont(Jeu.policeTRON);
        buParametres.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        buParametres.setCursor(Cursor.HAND);

        buRegles.setFont(Jeu.policeTRON);
        buRegles.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        buRegles.setCursor(Cursor.HAND);

        buQuitter.setFont(Jeu.policeTRON);
        buQuitter.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        buQuitter.setCursor(Cursor.HAND);

        buSprites.setFont(Jeu.policeTRON);
        buSprites.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        buSprites.setCursor(Cursor.HAND);

        buConnexionJ1.setFont(Jeu.policeTRON);
        buConnexionJ1.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        buConnexionJ1.setCursor(Cursor.HAND);

        buConnexionJ2.setFont(Jeu.policeTRON);
        buConnexionJ2.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        buConnexionJ2.setCursor(Cursor.HAND);

        buCreeCompte.setFont(Jeu.policeTRON);
        buCreeCompte.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        buCreeCompte.setCursor(Cursor.HAND);

        buCompte.setFont(Jeu.policeTRON);
        buCompte.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        buCompte.setCursor(Cursor.HAND);

        ImageView imgCourant = new ImageView();
        Image imgcourant = new Image("file:src/main/resources/cartesParCoul/aliceblue.png", 200, 200, true, true);
        imgCourant.setImage(imgcourant);

        // PARAMETRE TOUCHE --------------------------------------------------------------------------------------------

        listeBouton = new VBox();
        fen = new BorderPane();
        listeBouton.getChildren().add(titre);
        listeBouton.getChildren().add(buJouer);
        listeBouton.getChildren().add(buClassement);
        listeBouton.getChildren().add(buPersonnaliser);
        listeBouton.getChildren().add(buParametres);
        listeBouton.getChildren().add(buRegles);
        listeBouton.getChildren().add(buQuitter);

        listeBouton.setPadding(new Insets(20));
        listeBouton.setSpacing(25);
        listeBouton.setAlignment(Pos.CENTER);
        listeBouton.setMinWidth(largeurMenu);
        listeBouton.setMinHeight(hauteurMenu);

        // CHOIX CARTE(map) --------------------------------------------------------------------------------------------
        buJouer.setOnMouseClicked(actionEvent -> {
            buCompte.setVisible(false);
            listeBouton.setVisible(false);
            ArrayList<Button> bouton = new ArrayList<>();
            ArrayList<VBox> carte = new ArrayList<>();
            GridPane listeCarte = new GridPane();

            Image imgC1 = new Image("file:src/main/resources/cartes/carte1.png", 180, 180, true, true);
            ImageView imgVwC1 = new ImageView(imgC1);
            Image imgC2 = new Image("file:src/main/resources/cartes/carte2.png", 180, 180, true, true);
            ImageView imgVwC2 = new ImageView(imgC2);
            Image imgC3 = new Image("file:src/main/resources/cartes/carte3.png", 180, 180, true, true);
            ImageView imgVwC3 = new ImageView(imgC3);
            Image imgC4 = new Image("file:src/main/resources/cartes/carte4.png", 180, 180, true, true);
            ImageView imgVwC4 = new ImageView(imgC4);

            Button c1 = new Button("carte1");
            Button c2 = new Button("carte2");
            Button c3 = new Button("carte3");
            Button c4 = new Button("carte4");

            VBox carte1 = new VBox();
            VBox carte2 = new VBox();
            VBox carte3 = new VBox();
            VBox carte4 = new VBox();

            carte1.getChildren().add(imgVwC1);
            carte1.getChildren().add(c1);
            carte2.getChildren().add(imgVwC2);
            carte2.getChildren().add(c2);
            carte3.getChildren().add(imgVwC3);
            carte3.getChildren().add(c3);
            carte4.getChildren().add(imgVwC4);
            carte4.getChildren().add(c4);

            bouton.add(c1);
            bouton.add(c2);
            bouton.add(c3);
            bouton.add(c4);
            bouton.add(retour);
            carte.add(carte1);
            carte.add(carte2);
            carte.add(carte3);
            carte.add(carte4);

            for (int i = 0; i < carte.size(); i++) {
                carte.get(i).setSpacing(10);
                carte.get(i).setAlignment(Pos.CENTER);

                bouton.get(i).setFont(Jeu.policeTRON);
                bouton.get(i).setStyle("-fx-background-color: \n" +
                        "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                        "-fx-border-width: 2;\n" +
                        "-fx-border-color: #ffffff;\n" +
                        "-fx-text-fill: black;");
                bouton.get(i).setCursor(Cursor.HAND);

                int nb = i;

                bouton.get(i).setOnMouseClicked(jouerEvent -> {
//                        if (nb == 0){
//                            arene = new Arene(nb);
//                        }
//                        else if (nb == 2){
//                            arene = new Arene(matriceArene.getMatArene1());
//                        }
//                        else if (nb == 3){
//                            arene = new Arene(matriceArene.getMatArene2());
//                        }
//                        else {
//                            arene = new Arene(matriceArene.getMatArene3());
//                        }

                    if (nb <= 1) {
                        arene = new Arene(nb);
                    } else if (nb == 3) {
                        arene = new Arene(nb);
                    } else {
                        arene = new Arene(matriceArene.getMatArene2());
                    }

                    //jeu.setMurs();
                    setMenuOn(false);
                });
            }
            listeCarte.addRow(0, carte1, carte3);
            listeCarte.addRow(1, carte2, carte4);
            listeCarte.setHgap(30);
            listeCarte.setVgap(10);
            retour.setFont(Jeu.policeTRON);
            bouton.get(3).setStyle("-fx-background-color: \n" +
                    "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                    "-fx-border-width: 2;\n" +
                    "-fx-border-color: #ffffff;\n" +
                    "-fx-text-fill: black;");
            bouton.get(3).setCursor(Cursor.HAND);

            listeCarte.setAlignment(Pos.CENTER);


            this.add(listeCarte, 2, 2);

            listeCarte.addRow(2, retour);
            retour.setOnMouseClicked(retourEvent -> {
                listeCarte.setVisible(false);
                listeBouton.setVisible(true);
                buCompte.setVisible(true);

            });
        });

        buClassement.setOnMouseClicked(actionEvent ->{
            listeBouton.setVisible(false);
            buCompte.setVisible(false);

            VBox page = new VBox();
            page.setAlignment(Pos.TOP_CENTER);
            Label lClass = new Label("CLASSEMENT");
            lClass.setFont(Jeu.policeTITRE);
            HBox ligneInfo = new HBox();
            ligneInfo.setAlignment(Pos.CENTER);

            Boolean estTopJ1 = false;
            Boolean estTopJ2 = false;

            Label lRangT = new Label("RANG");
            lRangT.setMinSize(100, 50);
            lRangT.setFont(Jeu.policeTRON);
            lRangT.setAlignment(Pos.CENTER);
            lRangT.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            lRangT.setTextFill(Color.WHITE);

            Label lJoueurT = new Label("JOUEUR");
            lJoueurT.setMinSize(400, 50);
            lJoueurT.setFont(Jeu.policeTRON);
            lJoueurT.setAlignment(Pos.CENTER);
            lJoueurT.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            lJoueurT.setTextFill(Color.WHITE);

            Label lScoreT = new Label("SCORE");
            lScoreT.setMinSize(300, 50);
            lScoreT.setFont(Jeu.policeTRON);
            lScoreT.setAlignment(Pos.CENTER);
            lScoreT.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            lScoreT.setTextFill(Color.WHITE);

            ligneInfo.getChildren().add(lRangT);
            ligneInfo.getChildren().add(lJoueurT);
            ligneInfo.getChildren().add(lScoreT);

            page.getChildren().add(lClass);
            page.getChildren().add(ligneInfo);

            List<Player> meilleurs = PlayerManager.getInstance().getMeilleurs();

            for (int i = 0 ;i < meilleurs.size() ; i++){
                //System.out.println(meilleurs.get(i).getName()+ " " + meilleurs.get(i).getScore().getScore());
                HBox ligne = new HBox();
                ligne.setAlignment(Pos.CENTER);

                Label lRang = new Label("");
                lRang.setMinSize(100, 50);
                lRang.setFont(Jeu.policeTRON);
                lRang.setAlignment(Pos.CENTER);
                lRang.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                Label lJoueur = new Label("");
                lJoueur.setMinSize(400, 50);
                lJoueur.setFont(Jeu.policeTRON);
                lJoueur.setAlignment(Pos.CENTER);
                lJoueur.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                Label lScore = new Label("");
                lScore.setMinSize(300, 50);
                lScore.setFont(Jeu.policeTRON);
                lScore.setAlignment(Pos.CENTER);
                lScore.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


                if (j1.isConnecter() && meilleurs.get(i).getName().equals(player1.getName())){
                    lRang.setTextFill(Color.LIGHTBLUE);
                    lJoueur.setTextFill(Color.LIGHTBLUE);
                    lScore.setTextFill(Color.LIGHTBLUE);
                    estTopJ1 = true;
                }
                else if (j2.isConnecter() && meilleurs.get(i).getName().equals(player2.getName())){
                    lRang.setTextFill(Color.CHOCOLATE);
                    lJoueur.setTextFill(Color.CHOCOLATE);
                    lScore.setTextFill(Color.CHOCOLATE);
                    estTopJ2 = true;
                }
                else {
                    lRang.setTextFill(Color.WHITE);
                    lJoueur.setTextFill(Color.WHITE);
                    lScore.setTextFill(Color.WHITE);
                }

                lRang.setText(String.valueOf(i+1));
                lJoueur.setText(meilleurs.get(i).getName());

                if (meilleurs.get(i).getScore() == null){
                    lScore.setText("0");
                }
                else {
                    lScore.setText(String.valueOf(meilleurs.get(i).getScore().getScore()));
                }

                ligne.getChildren().add(lRang);
                ligne.getChildren().add(lJoueur);
                ligne.getChildren().add(lScore);
                page.getChildren().add(ligne);
            }
            if (!estTopJ1 && j1.isConnecter()){
                HBox ligneJ1 = new HBox();
                Label lRangJ1 = new Label("");
                lRangJ1.setMinSize(100, 50);
                lRangJ1.setFont(Jeu.policeTRON);
                lRangJ1.setAlignment(Pos.CENTER);
                lRangJ1.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                Label lJoueurJ1 = new Label("");
                lJoueurJ1.setMinSize(400, 50);
                lJoueurJ1.setFont(Jeu.policeTRON);
                lJoueurJ1.setAlignment(Pos.CENTER);
                lJoueurJ1.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                Label lScoreJ1 = new Label("");
                lScoreJ1.setMinSize(300, 50);
                lScoreJ1.setFont(Jeu.policeTRON);
                lScoreJ1.setAlignment(Pos.CENTER);
                lScoreJ1.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                lRangJ1.setText(String.valueOf(PlayerManager.getInstance().getRangByLogin(player1.getName())));
                lJoueurJ1.setText(player1.getName());
                lScoreJ1.setText(String.valueOf(player1.getScore()));
                lRangJ1.setTextFill(Color.LIGHTBLUE);
                lJoueurJ1.setTextFill(Color.LIGHTBLUE);
                lScoreJ1.setTextFill(Color.LIGHTBLUE);

                ligneJ1.getChildren().add(lRangJ1);
                ligneJ1.getChildren().add(lJoueurJ1);
                ligneInfo.getChildren().add(lScoreJ1);
                page.getChildren().add(ligneJ1);
            }
            if (!estTopJ2 && j2.isConnecter()){
                HBox ligneJ2 = new HBox();
                Label lRangJ2 = new Label("");
                lRangJ2.setMinSize(100, 50);
                lRangJ2.setFont(Jeu.policeTRON);
                lRangJ2.setAlignment(Pos.CENTER);
                lRangJ2.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                Label lJoueurJ2 = new Label("");
                lJoueurJ2.setMinSize(400, 50);
                lJoueurJ2.setFont(Jeu.policeTRON);
                lJoueurJ2.setAlignment(Pos.CENTER);
                lJoueurJ2.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                Label lScoreJ2 = new Label("");
                lScoreJ2.setMinSize(300, 50);
                lScoreJ2.setFont(Jeu.policeTRON);
                lScoreJ2.setAlignment(Pos.CENTER);
                lScoreJ2.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                lRangJ2.setText(String.valueOf(PlayerManager.getInstance().getRangByLogin(player1.getName())));
                lJoueurJ2.setText(player1.getName());
                lScoreJ2.setText(String.valueOf(player1.getScore()));
                lRangJ2.setTextFill(Color.LIGHTBLUE);
                lJoueurJ2.setTextFill(Color.LIGHTBLUE);
                lScoreJ2.setTextFill(Color.LIGHTBLUE);

                ligneJ2.getChildren().add(lRangJ2);
                ligneJ2.getChildren().add(lJoueurJ2);
                ligneInfo.getChildren().add(lScoreJ2);
                page.getChildren().add(ligneJ2);

            }

            page.getChildren().add(retour);
            this.add(page, 2, 2);
            retour.setOnMouseClicked(retourEvent -> {
                page.setVisible(false);
                listeBouton.setVisible(true);
                buCompte.setVisible(true);

            });
        });

            // PARAMETRES ----------------------------------------------------------------------------------------------
        buParametres.setOnMouseClicked(actionEvent -> {
            listeBouton.setVisible(false);
            buCompte.setVisible(false);

            ScrollPane listeTouchePane = new ScrollPane();

            // VOLUME --------------------------------------------------------------------------------------------------

            VBox volume = new VBox();
            HBox volumeMusique = new HBox();
            Slider barreMusique = new Slider();
            MediaView menu = new MediaView();
            menu.setMediaPlayer(mediaPlayerMenu);
            DoubleProperty widthMenu = menu.fitWidthProperty();
            DoubleProperty heightMenu = menu.fitHeightProperty();
            widthMenu.bind(Bindings.selectDouble(menu.sceneProperty(), "width"));
            heightMenu.bind(Bindings.selectDouble(menu.sceneProperty(), "height"));
            barreMusique.setValue(mediaPlayerMenu.getVolume() * 100);
            barreMusique.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    mediaPlayerMenu.setVolume(barreMusique.getValue() / 100);
                    mediaPlayerJeu.setVolume(barreMusique.getValue() / 100);
                }
            });
            barreMusique.setMinWidth(300);
            volumeMusique.getChildren().add(barreMusique);
            barreMusique.setPadding(new Insets(15));
            Label musiques = new Label("MUSIQUE");
            musiques.setPadding(new Insets(5));
            musiques.setStyle("-fx-background-color: \n" +
                    "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                    "-fx-border-width: 2;\n" +
                    "-fx-border-color: #ffffff;\n" +
                    "-fx-text-fill: black;");
            musiques.setFont(Jeu.policeTRONmini);
            volumeMusique.getChildren().add(musiques);
            volumeMusique.setPadding(new Insets(10));


            HBox volumeBruitage = new HBox();
            Slider barreBruitage = new Slider();
            MediaView bruitage = new MediaView();
            bruitage.setMediaPlayer(startSound);
            DoubleProperty widthBruitage = bruitage.fitWidthProperty();
            DoubleProperty heightBruitage = bruitage.fitHeightProperty();
            widthBruitage.bind(Bindings.selectDouble(bruitage.sceneProperty(), "width"));
            heightBruitage.bind(Bindings.selectDouble(bruitage.sceneProperty(), "height"));
            barreBruitage.setValue(startSound.getVolume() * 100);
            barreBruitage.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    startSound.setVolume(barreBruitage.getValue() / 100);
                    j1.getDeathSound().setVolume(barreBruitage.getValue() / 100);
                    j2.getDeathSound().setVolume(barreBruitage.getValue() / 100);
                }
            });
            barreBruitage.setMinWidth(300);
            volumeBruitage.getChildren().add(barreBruitage);
            barreBruitage.setPadding(new Insets(15));
            Label bruitages = new Label("BRUITAGES");
            bruitages.setPadding(new Insets(5));
            bruitages.setStyle("-fx-background-color: \n" +
                    "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                    "-fx-border-width: 2;\n" +
                    "-fx-border-color: #ffffff;\n" +
                    "-fx-text-fill: black;");
            bruitages.setFont(Jeu.policeTRONmini);
            volumeBruitage.getChildren().add(bruitages);
            volumeBruitage.setPadding(new Insets(10));

            volume.getChildren().add(volumeMusique);
            volume.getChildren().add(volumeBruitage);
//            volume.setPadding(new Insets(10));

            // FIN VOLUME ----------------------------------------------------------------------------------------------

            listeTouchePane.setMaxWidth(largeurMenu);
            listeTouchePane.setMaxHeight(hauteurMenu);

            VBox listeTouche = new VBox();

            listeTouche.getChildren().add(volume);

            for (int i = 0; i < b.size(); i++) {
                b.get(i).setFont(Jeu.policeTRON);
//                b.get(i).setStyle("-fx-background-color: \n" +
//                        "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
//                        "-fx-border-width: 3;\n" +
//                        "-fx-border-color: #ffffff;\n" +
//                        "-fx-text-fill: black;");
                b.get(i).setCursor(Cursor.HAND);

                labelTouches.get(i).setFont(Jeu.policeTRON);
//                labelTouches.get(i).setStyle("-fx-background-color: \n" +
//                        "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
//                        "-fx-border-width: 3;\n" +
//                        "-fx-border-color: #ffffff;\n" +
//                        "-fx-text-fill: black;");

                HBox paramTouche = new HBox();
                paramTouche.setStyle("-fx-background-color: \n" +
                        "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                        "-fx-border-width: 3;\n" +
                        "-fx-border-color: #ffffff;\n" +
                        "-fx-text-fill: black;");
//                paramTouche.setCursor(Cursor.HAND);
                paramTouche.getChildren().add(b.get(i));
                paramTouche.getChildren().add(labelTouches.get(i));
                paramTouche.setSpacing(10);

                listeTouche.getChildren().add(paramTouche);

                int nb = i;

                b.get(i).setOnMouseClicked(upEvent -> {
                    EventHandler<KeyEvent> keyEvent = new EventHandler<KeyEvent>() {
                        public void handle(KeyEvent keyEvent) {
                            System.out.println(keyEvent.getCode());
                            if (!keyList.contains(keyEvent.getCode())) {
                                b.get(nb).setText(keyEvent.getCode().getName());
                                keyList.set(nb, keyEvent.getCode());
                            }
                        }
                    };
                    this.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent);
                    this.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent2 -> {
                        this.removeEventFilter(KeyEvent.KEY_PRESSED, keyEvent);
                    });
                });
            }

//            this.add(listeTouche, 2, 2);

//            listeTouche.getChildren().add(retour);
            listeTouche.setSpacing(10);

            listeTouchePane.setContent(listeTouche);
//            HBox paramTouche = new HBox();
//            paramTouche.setMinHeight(hauteurMenu);
//            paramTouche.setMinWidth(largeurMenu);
//            paramTouche.getChildren().add(listeTouchePane);
//            paramTouche.getChildren().add(retour);
//            this.add(paramTouche, 2, 2);

            GridPane param = new GridPane();
            param.add(listeTouchePane, 0, 0);
            param.add(retour, 1, 1);
            param.getColumnConstraints().add(new ColumnConstraints(largeurMenu - 200));
            this.add(param, 2, 2);

//            this.add(listeTouchePane, 2, 2);
//            this.add(retour, 2, 2);

            retour.setOnMouseClicked(retourEvent -> {
                param.setVisible(false);
//                listeTouchePane.setVisible(false);
//                retour.setVisible(false);
                listeBouton.setVisible(true);
                buCompte.setVisible(true);

            });
        });

        buRegles.setOnMouseClicked(actionEvent -> {
            buCompte.setVisible(false);
            listeBouton.setVisible(false);

            VBox pageRegles = new VBox();
            ArrayList<Label> listLab = new ArrayList<>();
            pageRegles.setAlignment(Pos.CENTER);
            pageRegles.setSpacing(20);
            Label titre = new Label("REGLES");
            titre.setFont(Jeu.policeTITRE);
            titre.setTextFill(Color.BLACK);
            Label intro = new Label(" Voici les règles et quelque explication pour mieux comprendre ce qu'il se passe");
            intro.setFont(Jeu.policeTRONpetit);
            Label partie = new Label("Une partie :");
            partie.setFont(Jeu.policeTRON);
            Label expliPartie = new Label(" - Une partie est gagné quand un des joueurs elimine son adversaire trois fois.\n - Chaque joueur peut se déplacer dans toutes les directions, sauf d'où il vient.          \n - La liste des touches dans est dans les PARAMETRES et est affiché pendant la partie.\n - Un joueur est éliminé s'il percute un mur ou une traînée.");
            expliPartie.setFont(Jeu.policeTRONpetit);
            Label scoreTitre = new Label("Les scores :");
            scoreTitre.setFont(Jeu.policeTRON);
            Label score = new Label(" - Le score des deux joueurs augmente graduellement pendant chaque round.\n - Le score du perdant d'un round voit son score diviser par deux.\n - Si connecter avec son compte, chaque joueur a son score enregistrer en fin de partie.\n - Bonus de 5 000 points si le gagnant de la partie ne perd pas de vie.");
            score.setFont(Jeu.policeTRONpetit);
            Label persona = new Label(" N'hésitez pas à personnaliser votre moto et la couleur de l'arène.");
            persona.setFont(Jeu.policeTRONpetit);

            listLab.add(titre);
            listLab.add(intro);
            listLab.add(partie);
            listLab.add(expliPartie);
            listLab.add(scoreTitre);
            listLab.add(score);
            listLab.add(persona);

            for (Label l:
                 listLab) {
                if (l == partie || l == scoreTitre || l == titre){
                    l.setTextFill(Color.BLACK);
                }
                else {
                    l.setTextFill(Color.ALICEBLUE);
                }
                pageRegles.getChildren().add(l);
            }
            pageRegles.getChildren().add(retour);
            this.add(pageRegles, 2, 2);
            retour.setOnMouseClicked(retourEvent -> {
                pageRegles.setVisible(false);
                listeBouton.setVisible(true);
                buCompte.setVisible(true);
            });
        });
        // QUITTER JEU -------------------------------------------------------------------------------------------------
        buQuitter.setOnMouseClicked(actionEvent -> {
            Platform.exit();
            //shutdown();
        });

        // MENU SPRITES ------------------------------------------------------------------------------------------------
        listePersonnaliser = new VBox();
        listePersonnaliser.setPadding(new Insets(50));
        listePersonnaliser.setSpacing(100);
        listePersonnaliser.setAlignment(Pos.CENTER);
        listePersonnaliser.setMinWidth(largeurMenu);
        listePersonnaliser.setMinHeight(hauteurMenu);
        this.add(listePersonnaliser, 2, 2);
        listePersonnaliser.setVisible(false);

        buPersonnaliser.setOnMouseClicked(actionEvent -> {
            buCompte.setVisible(false);

            listeBouton.setVisible(false);
            listePersonnaliser.setVisible(true);
            GridPane choix = new GridPane();
            //choix.setPadding(new Insets(50));
            choix.setAlignment(Pos.CENTER);
            choix.setMinWidth(largeurMenu);
            //choix.setMinHeight(hauteurMenu);
            HBox choixSpritesJ1 = new HBox();
            Label nomSpritesJ1 = new Label();
            HBox choixSpritesJ2 = new HBox();
            Label nomSpritesJ2 = new Label();
            ImageView flecheBleueG = new ImageView(new Image("file:src/main/resources/images/flèche-bleue.png", 60, 60, true, true));
            ImageView flecheBleueD = new ImageView(new Image("file:src/main/resources/images/flèche-bleue.png", 60, 60, true, true));
            ImageView flecheRougeG = new ImageView(new Image("file:src/main/resources/images/flèche-rouge.png", 60, 60, true, true));
            ImageView flecheRougeD = new ImageView(new Image("file:src/main/resources/images/flèche-rouge.png", 60, 60, true, true));
            LinkedHashMap<Sprite, Color> spritesJ1 = Sprite.genererSprites();
            LinkedHashMap<Sprite, Color> spritesJ2 = Sprite.genererSprites();
            recompenseMoto(j1, spritesJ1);
            recompenseMoto(j2, spritesJ2);
            Label joueur1 = new Label("J1");
            joueur1.setFont(new Font(20));
            joueur1.setFont(Font.loadFont("file:src/main/resources/font/Megatron.otf", 75));
            joueur1.setStyle("-fx-text-fill: rgb(0,212,255); \n" + "-fx-stroke: black");
            joueur1.setPadding(new Insets(-20, -75, 20, 75));
            Label joueur2 = new Label("J2");
            joueur2.setFont(new Font(20));
            joueur2.setFont(Font.loadFont("file:src/main/resources/font/Megatron.otf", 75));
            joueur2.setStyle("-fx-text-fill: red; \n" + "-fx-stroke: black");
            joueur2.setPadding(new Insets(-20, -125, 20, 125));

            var ref = new Object() {        // Pour modifier des valeurs dans setOnMouseClicked
                int selectBleu;
                int selectRouge;
            };
            String[] keys1 = new String[spritesJ1.size()];
            int i = 0;
            for (Sprite key1 : spritesJ1.keySet()) {
                keys1[i] = key1.getSkinMoto().getUrl();
                if (keys1[i].equals(j1.getSprite().getSkinMoto().getUrl())) {
                    ref.selectBleu = i;
                }
                i++;
            }
            String[] keys2 = new String[spritesJ2.size()];
            i = 0;
            for (Sprite key2 : spritesJ2.keySet()) {
                keys2[i] = key2.getSkinMoto().getUrl();
                if (keys2[i].equals(j2.getSprite().getSkinMoto().getUrl())) {
                    ref.selectRouge = i;
                }
                i++;
            }
            flecheBleueG.setRotate(-90);
            choixSpritesJ1.getChildren().add(flecheBleueG);
            choixSpritesJ1.getChildren().add(new ImageView(new Image(j1.getSprite().getSkinMoto().getUrl(), 125, 125, true, true)));
            nomSpritesJ1.setText(j1.getSprite().getNom());
            nomSpritesJ1.setStyle("-fx-background-color: #ffffff");
            nomSpritesJ1.setPadding(new Insets(3));
            nomSpritesJ1.setFont(Jeu.policeTRONmini);
            flecheBleueD.setRotate(90);
            choixSpritesJ1.getChildren().add(flecheBleueD);
            choixSpritesJ1.setPadding(new Insets(0, 30, 0, -30));
            nomSpritesJ2.setText(j2.getSprite().getNom());
            nomSpritesJ2.setStyle("-fx-background-color: #ffffff");
            nomSpritesJ2.setPadding(new Insets(3));
            nomSpritesJ2.setFont(Jeu.policeTRONmini);
            flecheRougeG.setRotate(-90);
            choixSpritesJ2.getChildren().add(flecheRougeG);
            choixSpritesJ2.getChildren().add(new ImageView(new Image(j2.getSprite().getSkinMoto().getUrl(), 125, 125, true, true)));
            flecheRougeD.setRotate(90);
            choixSpritesJ2.getChildren().add(flecheRougeD);
            choixSpritesJ2.setPadding(new Insets(0, -30, 0, 30));

            //choix couleur arene
            HBox choixCoul = new HBox();
            GridPane gridCoul = new GridPane();
            ArrayList<Button> listeBoutonCoul = new ArrayList<>();
            ArrayList<Color> listeCouleurs = new ArrayList<>();
            ArrayList<Image> listeImg = new ArrayList<>();
            Image imga = new Image("file:src/main/resources/cartesParCoul/aliceblue.png", 200, 200, true, true);
            ImageView imgVwa = new ImageView(imga);
            listeImg.add(imga);
            Image imgj = new Image("file:src/main/resources/cartesParCoul/jaune.png", 200, 200, true, true);
            ImageView imgVwj = new ImageView(imgj);
            listeImg.add(imgj);
            Image imgb = new Image("file:src/main/resources/cartesParCoul/bleu.png", 200, 200, true, true);
            ImageView imgVwb = new ImageView(imgb);
            listeImg.add(imgb);
            Image imgo = new Image("file:src/main/resources/cartesParCoul/orange.png", 200, 200, true, true);
            ImageView imgVwo = new ImageView(imgo);
            listeImg.add(imgo);

            Button coul1 = new Button("Blanc");
            listeCouleurs.add(Color.ALICEBLUE);
            coul1.setStyle("-fx-background-color: #ffffff");
            Button coul2 = new Button("Jaune");
            listeCouleurs.add(Color.YELLOW);
            coul2.setStyle("-fx-background-color: #ffff00");
            Button coul3 = new Button("Bleu");
            listeCouleurs.add(Color.DARKBLUE);
            coul3.setStyle("-fx-background-color: #00008b");
            Button coul4 = new Button("Orange");
            listeCouleurs.add(Color.CHOCOLATE);
            coul4.setStyle("-fx-background-color: #FFA500");

            listeBoutonCoul.add(coul1);
            listeBoutonCoul.add(coul2);
            listeBoutonCoul.add(coul3);
            listeBoutonCoul.add(coul4);

            for (int j = 0; j < listeBoutonCoul.size(); j++) {
                listeBoutonCoul.get(j).setCursor(Cursor.HAND);
                listeBoutonCoul.get(j).setFont(Jeu.policeTRONmini);
                listeBoutonCoul.get(j).setTextAlignment(TextAlignment.CENTER);
                int nb = j;
                listeBoutonCoul.get(j).setOnMouseClicked(couleurEvent -> {
                    Mur.setCouleurMurs(listeCouleurs.get(nb));
                    imgCourant.setImage(listeImg.get(nb));

                });
            }
            gridCoul.addRow(0, coul1, coul2);
            gridCoul.addRow(1, coul3, coul4);
            gridCoul.setVgap(50);
            gridCoul.setHgap(75);
            gridCoul.setAlignment(Pos.CENTER);

            choix.add(choixSpritesJ1, 0, 0);
            choix.add(choixSpritesJ2, 1, 0);
            choix.add(nomSpritesJ1, 0, 1);
            choix.add(nomSpritesJ2, 1, 1);
            choix.add(joueur1, 0, 2);
            choix.add(joueur2, 1, 2);

            GridPane.setHalignment(choixSpritesJ1, HPos.CENTER);
            GridPane.setHalignment(choixSpritesJ2, HPos.CENTER);
            GridPane.setHalignment(nomSpritesJ1, HPos.CENTER);
            GridPane.setHalignment(nomSpritesJ2, HPos.CENTER);

            choixCoul.setSpacing(100);
            choixCoul.setAlignment(Pos.CENTER);
            choixCoul.setMinWidth(largeurMenu);
            choixCoul.getChildren().add(imgCourant);
            choixCoul.getChildren().add(gridCoul);

            i = 0;
            flecheBleueD.setOnMouseClicked(upEvent -> {
                if (ref.selectBleu == keys1.length - 1) {
                    ref.selectBleu = 0;
                } else {
                    ref.selectBleu = ref.selectBleu + 1;
                }
                for (Sprite sprite : spritesJ1.keySet()) {
                    if (sprite.getSkinMoto().getUrl().equals(keys1[ref.selectBleu])) {
                        choixSpritesJ1.getChildren().remove(choixSpritesJ1.getChildren().get(1));
                        choixSpritesJ1.getChildren().add(1, sprite.getSkinMotoView());
                        j1.getSprite().setSkinMoto(
                                new Image((sprite.getSkinMoto().getUrl()), 60, 40, true, true),
                                spritesJ1.get(sprite)
                        );
                        nomSpritesJ1.setText(sprite.getNom());
                        j1.getSprite().setNom(sprite.getNom());
                    }
                }
            });
            flecheBleueG.setOnMouseClicked(upEvent -> {
                if (ref.selectBleu == 0) {
                    ref.selectBleu = keys1.length - 1;
                } else {
                    ref.selectBleu = ref.selectBleu - 1;
                }
                for (Sprite sprite : spritesJ1.keySet()) {
                    if (sprite.getSkinMoto().getUrl().equals(keys1[ref.selectBleu])) {
                        choixSpritesJ1.getChildren().remove(choixSpritesJ1.getChildren().get(1));
                        choixSpritesJ1.getChildren().add(1, sprite.getSkinMotoView());
                        j1.getSprite().setSkinMoto(
                                new Image((sprite.getSkinMoto().getUrl()), 60, 40, true, true),
                                spritesJ1.get(sprite)
                        );
                        nomSpritesJ1.setText(sprite.getNom());
                        j1.getSprite().setNom(sprite.getNom());
                    }
                }
            });
            flecheRougeD.setOnMouseClicked(upEvent -> {
                if (ref.selectRouge == keys2.length - 1) {
                    ref.selectRouge = 0;
                } else {
                    ref.selectRouge = ref.selectRouge + 1;
                }
                for (Sprite sprite : spritesJ2.keySet()) {
                    if (sprite.getSkinMoto().getUrl().equals(keys2[ref.selectRouge])) {
                        choixSpritesJ2.getChildren().remove(choixSpritesJ2.getChildren().get(1));
                        choixSpritesJ2.getChildren().add(1, sprite.getSkinMotoView());
                        j2.getSprite().setSkinMoto(
                                new Image((sprite.getSkinMoto().getUrl()), 60, 40, true, true),
                                spritesJ2.get(sprite)
                        );
                        nomSpritesJ2.setText(sprite.getNom());
                        j2.getSprite().setNom(sprite.getNom());
                    }
                }
            });
            flecheRougeG.setOnMouseClicked(upEvent -> {
                if (ref.selectRouge == 0) {
                    ref.selectRouge = keys2.length - 1;
                } else {
                    ref.selectRouge = ref.selectRouge - 1;
                }
                for (Sprite sprite : spritesJ2.keySet()) {
                    if (sprite.getSkinMoto().getUrl().equals(keys2[ref.selectRouge])) {
                        choixSpritesJ2.getChildren().remove(choixSpritesJ2.getChildren().get(1));
                        choixSpritesJ2.getChildren().add(1, sprite.getSkinMotoView());
                        j2.getSprite().setSkinMoto(
                                new Image((sprite.getSkinMoto().getUrl()), 60, 40, true, true),
                                spritesJ2.get(sprite)
                        );
                        nomSpritesJ2.setText(sprite.getNom());
                        j2.getSprite().setNom(sprite.getNom());
                    }
                }
            });
            //choix.setPadding(new Insets(125,0,-125,0));
            listePersonnaliser.getChildren().add(choix);
            listePersonnaliser.getChildren().add(choixCoul);
            listePersonnaliser.setAlignment(Pos.CENTER);
            listePersonnaliser.setPadding(new Insets(-50, 0, -50, 0));
            listePersonnaliser.getChildren().add(retour);
            retour.setFont(Jeu.policeTRON);
            retour.setStyle("-fx-background-color: \n" +
                    "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                    "-fx-border-width: 3;\n" +
                    "-fx-border-color: #ffffff;\n" +
                    "-fx-text-fill: black;");
            retour.setCursor(Cursor.HAND);

            retour.setOnMouseClicked(retourEvent -> {
                listePersonnaliser.setVisible(false);
                listeBouton.setVisible(true);
                listePersonnaliser.getChildren().clear();
                buCompte.setVisible(true);

            });
        });


        //COMPTE -------------------------------------------------------------------------------------------------------
        VBox listeCompte = new VBox();
        HBox connexionCompte = new HBox();
        listeCompte.getChildren().addAll(connexionCompte, buCreeCompte);
        connexionCompte.getChildren().addAll(buConnexionJ1, buConnexionJ2);
        connexionCompte.setSpacing(25);
        connexionCompte.setAlignment(Pos.CENTER);
        this.add(listeCompte, 2, 2);
        listeCompte.setVisible(false);


        buCompte.setOnMouseClicked(actionEvent -> {
            if (!listeCompte.getChildren().contains(retour)) {
                listeCompte.getChildren().add(retour);
            }
            retour.setOnMouseClicked(retourEvent -> {
                listeCompte.setVisible(false);
                listeBouton.setVisible(true);
                buCompte.setVisible(true);
            });
            listeBouton.setVisible(false);
            buCompte.setVisible(false);
            if(!j1.isConnecter()){
                j1.setConnecter(false);
                buConnexionJ1.setText("CONNEXION J1");
            }
            else{
                buConnexionJ1.setText("DÉCONNECTER");
            }
            if(!j2.isConnecter()){
                j2.setConnecter(false);
                buConnexionJ2.setText("CONNEXION J2");
            }
            else{
                buConnexionJ2.setText("DÉCONNECTER");
            }
            listeCompte.setAlignment(Pos.CENTER);
            listeCompte.setSpacing(25);
            listeCompte.setVisible(true);
            retour.setFont(Jeu.policeTRON);
            retour.setStyle("-fx-background-color: \n" +
                    "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                    "-fx-border-width: 3;\n" +
                    "-fx-border-color: #ffffff;\n" +
                    "-fx-text-fill: black;");
            retour.setCursor(Cursor.HAND);

        });

        VBox champConnexion = new VBox();
        //HBox connexionCompte = new HBox();
        Label loginLabel = new Label("                           Identifiant : ");
        Label passwordLabel = new Label("                      Mot de passe : ");
        Label passwordConfirmLabel = new Label("Confirmation mot de passe : ");
        TextField loginField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField passwordConfirmField = new PasswordField();
        HBox loginBox = new HBox();
        HBox passwordBox = new HBox();
        HBox passwordConfirmBox = new HBox();

        loginBox.getChildren().addAll(loginLabel, loginField);
        passwordBox.getChildren().addAll(passwordLabel, passwordField);
        passwordConfirmBox.getChildren().addAll(passwordConfirmLabel, passwordConfirmField);

        Button connexionButton = new Button("Connexion");
        connexionButton.setStyle("-fx-background-color: \n" +
                "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-color: #ffffff;\n" +
                "-fx-text-fill: black;");
        connexionButton.setCursor(Cursor.HAND);
        connexionButton.setFont(Jeu.policeTRONpetit);
        loginBox.setAlignment(Pos.CENTER);
        passwordBox.setAlignment(Pos.CENTER);
        passwordConfirmBox.setAlignment(Pos.CENTER);
        champConnexion.getChildren().addAll(loginBox, passwordBox, passwordConfirmBox, connexionButton);
        champConnexion.setSpacing(25);
        champConnexion.setAlignment(Pos.CENTER);
        this.add(champConnexion, 2, 2);
        champConnexion.setVisible(false);


        // CONNEXION J1 ------------------------------------------------------------------------------------------------
        buConnexionJ1.setOnMouseClicked(actionEvent -> {
            connexionButton.setText("Connexion");
            passwordConfirmBox.setVisible(false);
            loginField.setText("");
            passwordField.setText("");
            champConnexion.getChildren().add(retour);
            if(!j1.isConnecter()) {
                retour.setOnMouseClicked(retourEvent -> {
                    champConnexion.setVisible(false);
                    listeBouton.setVisible(true);
                    buCompte.setVisible(true);
                });
                listeCompte.setVisible(false);
                champConnexion.setAlignment(Pos.CENTER);
                champConnexion.setSpacing(25);
                champConnexion.setVisible(true);
                retour.setFont(Jeu.policeTRON);
                retour.setStyle("-fx-background-color: \n" +
                        "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                        "-fx-border-width: 3;\n" +
                        "-fx-border-color: #ffffff;\n" +
                        "-fx-text-fill: black;");
                retour.setCursor(Cursor.HAND);
                retour.setFont(Jeu.policeTRONpetit);
                connexionButton.setOnMouseClicked(actionEvent2 -> {
                    if (connexionJoueur(player1, loginField.getText(), passwordField.getText())) {
                        if(player2.getName().equals(loginField.getText())){
                            System.out.println(player2.getName() + " est déjà connecté");
                        }
                        else {
                            System.out.println(player1.getName() + " connecté");
                            j1.setNomJoueur(player1.getName());
                            champConnexion.setVisible(false);
                            listeBouton.setVisible(true);
                            buCompte.setVisible(true);
                            j1.setConnecter(true);
                        }
                    } else {
                        System.out.println("Identifiant ou mot de passe incorrecte");
                    }
                });
            }
            else{
                player1.setName("J1");
                j1.setConnecter(false);
                listeCompte.setVisible(false);
                champConnexion.setVisible(false);
                listeBouton.setVisible(true);
                buCompte.setVisible(true);
                j1.setSprite(new Sprite(new Image("file:src/main/resources/images/moto/TronLegacyBleu.png", 125, 125, true, true), Color.LIGHTBLUE, "Motron Bleu"));
                }
            });

        // CONNEXION J2 ------------------------------------------------------------------------------------------------
        buConnexionJ2.setOnMouseClicked(actionEvent -> {
            connexionButton.setText("Connexion");
            passwordConfirmBox.setVisible(false);
            loginField.setText("");
            passwordField.setText("");
            champConnexion.getChildren().add(retour);
            if(!j2.isConnecter()) {
                retour.setOnMouseClicked(retourEvent -> {
                    champConnexion.setVisible(false);
                    listeBouton.setVisible(true);
                    buCompte.setVisible(true);
                });
                listeCompte.setVisible(false);
                champConnexion.setAlignment(Pos.CENTER);
                champConnexion.setSpacing(25);
                champConnexion.setVisible(true);
                retour.setFont(Jeu.policeTRONpetit);
                retour.setStyle("-fx-background-color: \n" +
                        "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                        "-fx-border-width: 3;\n" +
                        "-fx-border-color: #ffffff;\n" +
                        "-fx-text-fill: black;");
                retour.setCursor(Cursor.HAND);
                connexionButton.setOnMouseClicked(actionEvent2 -> {
                    if (!j2.isConnecter()) {
                        if (connexionJoueur(player2, loginField.getText(), passwordField.getText())) {
                            if (player1.getName().equals(loginField.getText())) {
                                System.out.println(player1.getName() + " est déjà connecté");
                            } else {
                                System.out.println(player2.getName() + " connecté");
                                j2.setNomJoueur(player2.getName());
                                champConnexion.setVisible(false);
                                listeBouton.setVisible(true);
                                buCompte.setVisible(true);
                                j2.setConnecter(true);
                            }
                        } else {
                            System.out.println("Identifiant ou mot de passe incorrecte");
                        }
                    }
                });
            }
            else {
                player2.setName("J2");
                j2.setConnecter(false);
                listeCompte.setVisible(false);
                champConnexion.setVisible(false);
                listeBouton.setVisible(true);
                buCompte.setVisible(true);
                j2.setSprite(new Sprite(new Image("file:src/main/resources/images/moto/TronLegacyJaune.png", 125, 125, true, true), Color.YELLOW, "Motron Jaune"));
            }
        });

        buCreeCompte.setOnMouseClicked(actionEvent -> {
            passwordConfirmBox.setVisible(true);
            connexionButton.setText("Créer compte");
            loginField.setText("");
            passwordConfirmField.setText("");
            passwordField.setText("");
            retour.setOnMouseClicked(retourEvent -> {
                champConnexion.setVisible(false);
                listeBouton.setVisible(true);
                buCompte.setVisible(true);
            });
            listeCompte.setVisible(false);
            champConnexion.setAlignment(Pos.CENTER);
            champConnexion.setSpacing(25);
            champConnexion.setVisible(true);

            retour.setStyle("-fx-background-color: \n" +
                    "linear-gradient(#062f83 0%, #ffffff 50%);\n" +
                    "-fx-border-width: 3;\n" +
                    "-fx-border-color: #ffffff;\n" +
                    "-fx-text-fill: black;");
            retour.setCursor(Cursor.HAND);
            connexionButton.setOnMouseClicked(actionEvent2 -> {
                if(loginField.getText().equals("") || passwordField.getText().equals("") || !passwordField.getText().equals(passwordConfirmField.getText())) {
                    System.out.println("Problème lors de la création du compte");
                }
                else if(PlayerManager.getInstance().getPlayer(loginField.getText()) != null){
                    System.out.println("Le compte " + loginField.getText() + " existe déjà");
                }
                else{
                    PlayerManager.getInstance().createPlayer(loginField.getText(), passwordField.getText());
                    champConnexion.setVisible(false);
                    listeBouton.setVisible(true);
                    buCompte.setVisible(true);
                }
            });
        });

        // BACKGROUND --------------------------------------------------------------------------------------------------

        Image background = new Image("file:src/main/resources/images/tronbackground.jpg");
        BackgroundImage bgImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, true, true, false, false));
        this.setBackground(new Background(bgImage));

        this.add(listeBouton, 2, 2);

        HBox hboxCompte = new HBox(buCompte);
        hboxCompte.setPadding(new Insets(10));
        this.add(hboxCompte, 2, 3);
        GridPane.setHalignment(hboxCompte, HPos.RIGHT);
    }



    public boolean isMenuOn() {
        return menuOn;
    }

    public void setMenuOn(boolean menuOn) {
        this.menuOn = menuOn;
    }

    public Jeu creerJeu(){
        j1.setScore(0);
        j2.setScore(0);
        return new Jeu(j1, j2, arene,keyList);
    }

    public LinkedHashMap<ImageView, Color> remplirChoixSkin(){
        LinkedHashMap<ImageView, Color> sprites = new LinkedHashMap<>();
        ImageView batmobile = new ImageView(new Image("file:src/main/resources/images/moto/Batmobile.png",125, 125,true,true));
        ImageView batpod = new ImageView(new Image("file:src/main/resources/images/moto/Batpod.png",125, 125,true,true));
        ImageView motoAkira = new ImageView(new Image("file:src/main/resources/images/moto/MotoAkira.png",125, 125,true,true));
        ImageView speederAnakin = new ImageView(new Image("file:src/main/resources/images/moto/SpeederAnakin.png",125, 125,true,true));
        ImageView motronBleu = new ImageView(new Image("file:src/main/resources/images/moto/TronLegacyBleu.png",125, 125,true,true));
        ImageView motronJaune = new ImageView(new Image("file:src/main/resources/images/moto/TronLegacyJaune.png",125, 125,true,true));
        sprites.put(batmobile, Color.rgb(148,135,83));
        sprites.put(batpod, Color.rgb(213,80,0));
        sprites.put(motoAkira, Color.rgb(216,24,24));
        sprites.put(speederAnakin, Color.rgb(245,148,255));
        sprites.put(motronBleu, Color.LIGHTBLUE);
        sprites.put(motronJaune, Color.YELLOW);
        return sprites;
    }

    public MediaPlayer getStartSound() {
        return startSound;
    }

    public MediaPlayer getMediaPlayerMenu() {
        return mediaPlayerMenu;
    }

    public MediaPlayer getMediaPlayerJeu() {
        return mediaPlayerJeu;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean connexionJoueur(Player player, String login, String password) {
        boolean jConnecte = false;
        AuthPlayer j = PlayerManager.getInstance().getPlayer(login);
        if(j != null)
            try {
                if (Objects.equals(j.getLogin(), login) && Security.checkPassword(password, j.getSalt(), j.getHashedPassword())) {
                    player.setName(login);
                    jConnecte = true;
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        return jConnecte;
    }

    public void recompenseMoto(Moto j, LinkedHashMap<Sprite, Color> recompenses){
        if(j.isConnecter() && PlayerManager.getInstance().getNbPartie(j.getNomJoueur()) >= 10){
            System.out.println(j.getNomJoueur() + " : " + PlayerManager.getInstance().getNbPartie(j.getNomJoueur()));
            Sprite sprite10 = new Sprite(new Image("file:src/main/resources/images/moto/MotoJet.png", 125, 125, true, true), Color.rgb(236,86,0), "Moto-Jet");
            recompenses.put(sprite10, sprite10.getTrailColor());
            if(PlayerManager.getInstance().getNbPartie(j.getNomJoueur()) >= 20){
                Sprite sprite20 = new Sprite(new Image("file:src/main/resources/images/moto/MotoReacteur.png", 125, 125, true, true), Color.rgb(22,255,237), "Moto à réacteur");
                recompenses.put(sprite20, sprite20.getTrailColor());
                if(PlayerManager.getInstance().getNbPartie(j.getNomJoueur()) >= 50){
                    Sprite sprite50 = new Sprite(new Image("file:src/main/resources/images/moto/SkinPremium1.png", 125, 125, true, true), Color.rgb(34,205,0), "VIP");
                    recompenses.put(sprite50, sprite50.getTrailColor());
                }
            }
        }
    }
}
