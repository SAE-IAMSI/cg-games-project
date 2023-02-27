package games.project.motron;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;

public class ControllerFXML {

    private Arene arene;
    private MatriceArene matriceArene;
    private boolean menuOn = true;
    private Moto j1;
    private Moto j2;
    private ArrayList<KeyCode> keyList = new ArrayList<>();
    private MediaPlayer startSound;
    private MediaPlayer mediaPlayerMenu;
    private MediaPlayer mediaPlayerJeu;
    private boolean player1Connecte;
    private boolean player2Connecte;

    @FXML
    private ImageView background;

    @FXML
    private ImageView carteBackground;

    @FXML
    private ImageView jeuBackground;

    @FXML
    private Pane menuPane;

    @FXML
    private Pane choixCartePane;

    @FXML
    private Pane jeuPane;

    @FXML
    private Pane persoPane;

    @FXML
    private Pane paramPane;

    @FXML
    private Pane comptePane;

//    Menu
    @FXML
    private Button bouttonJouer;

    @FXML
    private Button bouttonPerso;

    @FXML
    private Button bouttonParam;

    @FXML
    private Button bouttonQuitter;

    @FXML
    private Button bouttonCompte;

    @FXML
    protected void lanceJouer(){
//        background.setVisible(false);
        menuPane.setVisible(false);
        background.setVisible(false);
        carteBackground.setVisible(true);
        choixCartePane.setVisible(true);
    }

    @FXML
    protected void lancePerso() {
        menuPane.setVisible(false);
        persoPane.setVisible(true);
    }

    @FXML
    protected void lanceParam(){
        menuPane.setVisible(false);
        paramPane.setVisible(true);
    }

    @FXML
    protected void lanceCompte() {
        menuPane.setVisible(false);
        comptePane.setVisible(true);
    }

    @FXML
    protected void lanceQuitter() {
        Platform.exit();
    }

//    Sélection de cartes (alias: choixCartePane)
    @FXML
    protected void retourCarte(){
        choixCartePane.setVisible(false);
        carteBackground.setVisible(false);
        background.setVisible(true);
        menuPane.setVisible(true);
    }

    @FXML
    protected void selectCarte1(){
        choixCartePane.setVisible(false);
        carteBackground.setVisible(false);
        jeuBackground.setVisible(true);
        jeuPane.setVisible(true);

        arene = new Arene(matriceArene.getArene1());

        menuOn = false;
    }

    @FXML
    protected void selectCarte2(){
        choixCartePane.setVisible(false);
        carteBackground.setVisible(false);
        jeuBackground.setVisible(true);
        jeuPane.setVisible(true);

        arene = new Arene(matriceArene.getArene1());

    }

    @FXML
    protected void selectCarte3(){
        choixCartePane.setVisible(false);
        carteBackground.setVisible(false);
        jeuBackground.setVisible(true);
        jeuPane.setVisible(true);

        arene = new Arene(matriceArene.getArene1());
    }

    @FXML
    protected void selectCarte4(){
        choixCartePane.setVisible(false);
        carteBackground.setVisible(false);
        jeuBackground.setVisible(true);
        jeuPane.setVisible(true);

        arene = new Arene(matriceArene.getArene1());

    }


//    Jeu
    @FXML
    private Label round;

    @FXML
    private Label labelJ1;

    @FXML
    private Label scoreJ1;

    @FXML
    private ImageView vie1J1;

    @FXML
    private ImageView vie2J1;

    @FXML
    private ImageView vie3J1;

    @FXML
    private Label labelJ2;

    @FXML
    private Label scoreJ2;

    @FXML
    private ImageView vie1J2;

    @FXML
    private ImageView vie2J2;

    @FXML
    private ImageView vie3J2;



//    Personnaliser (alias: perso)
    @FXML
    protected void retourPerso(){
        persoPane.setVisible(false);
        menuPane.setVisible(true);
    }

//    Paramètres (alias: param)
    @FXML
    protected void retourParam(){
        paramPane.setVisible(false);
        menuPane.setVisible(true);
    }

//    Compte
    @FXML
    protected void retourCompte(){
        comptePane.setVisible(false);
        menuPane.setVisible(true);
    }


    public boolean isMenuOn() {
        return menuOn;
    }

    public void setMenuOn(boolean menuOn) {
        this.menuOn = menuOn;
    }
}
