package games.project.paco_mano.controller;

import games.project.metier.entite.Player;
import games.project.metier.manager.PlayerManager;
import games.project.metier.entite.AuthPlayer;
import games.project.paco_mano.PacoMano;
import games.project.stockage.Session;
import games.project.stockage.Security;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static javafx.scene.input.KeyCode.*;


public class PacoManoController {

//   Panes

    @FXML
    private Pane menuPane;
    @FXML
    private Pane gamePane;
    @FXML
    private Pane playerPane;
    @FXML
    private Pane highscorePane;
    @FXML
    private Pane settingPane;
    @FXML
    private Pane playerSigneUp;

// Player

    private Player player;
    @FXML
    private TextField loginFieldConnexion;

    @FXML
    private PasswordField passwordFieldConnexion;


//    Menu

    @FXML
    private ImageView titleBackground;
    @FXML
    private ImageView baseBackground;
    @FXML
    private Button playButton;
    @FXML
    private Button playerButton;
    @FXML
    private Button highScore;
    @FXML
    private Button settingButton;
    @FXML
    private Button quitButton;
    @FXML
    private Button returnPlayer;

//    ------------------------------------------------------------------------------------------------------------------

    // JEU
    @FXML
    private Pane pacomanoPane;

    //    Classement

    @FXML
    private Label textTempL1;
    @FXML
    private Label textTempL2;
    @FXML
    private Label textTempL3;
    @FXML
    private Label textTempL4;
    @FXML
    private Label textTempL5;
    @FXML
    private Label textTempL6;
    @FXML
    private Label textTempL7;
    @FXML
    private Label textTempL8;
    @FXML
    private Label textTempL9;
    @FXML
    private Label textTempL10;
    @FXML
    private Label textTempS1;
    @FXML
    private Label textTempS2;
    @FXML
    private Label textTempS3;
    @FXML
    private Label textTempS4;
    @FXML
    private Label textTempS5;
    @FXML
    private Label textTempS6;
    @FXML
    private Label textTempS7;
    @FXML
    private Label textTempS8;
    @FXML
    private Label textTempS9;
    @FXML
    private Label textTempS10;
    @FXML
    private Label textTempD1;
    @FXML
    private Label textTempD2;
    @FXML
    private Label textTempD3;
    @FXML
    private Label textTempD4;
    @FXML
    private Label textTempD5;
    @FXML
    private Label textTempD6;
    @FXML
    private Label textTempD7;
    @FXML
    private Label textTempD8;
    @FXML
    private Label textTempD9;
    @FXML
    private Label textTempD10;
    @FXML
    private Button returnHighscore;

//    ------------------------------------------------------------------------------------------------------------------

//   Settings

    @FXML
    private Button returnSetting;
    @FXML
    private Button upButton;
    @FXML
    private Button leftButton;
    @FXML
    private Button downButton;
    @FXML
    private Button rightButton;

//    ------------------------------------------------------------------------------------------------------------------

// Player pane


    @FXML
    private Button buttonFieldInscrir;

//    ------------------------------------------------------------------------------------------------------------------

//   Pane change functions

    /** To menu */
    @FXML
    protected void backToMenuFromSettings() {
        settingPane.setVisible(false);
        menuPane.setVisible(true);
        titleBackground.setVisible(true);
        baseBackground.setVisible(false);
    }
    @FXML
    protected void backToMenuFromPlayerPane() {
        playerPane.setVisible(false);
        menuPane.setVisible(true);
        titleBackground.setVisible(true);
        baseBackground.setVisible(false);
    }
    @FXML
    protected void backToMenuFromHighScore() {
        highscorePane.setVisible(false);
        menuPane.setVisible(true);
        titleBackground.setVisible(true);
        baseBackground.setVisible(false);
    }
    @FXML
    protected void backToMenuFromGame() {
        gamePane.setVisible(false);
        menuPane.setVisible(true);
        titleBackground.setVisible(true);
        baseBackground.setVisible(false);
    }

    /** Menu to */
    @FXML
    protected void menuToSettings() {
        settingPane.setVisible(true);
        menuPane.setVisible(false);
        titleBackground.setVisible(false);
        baseBackground.setVisible(true);

    }
    @FXML
    protected void menuToPlayerPane() {
        playerPane.setVisible(true);
        menuPane.setVisible(false);
        titleBackground.setVisible(false);
        baseBackground.setVisible(true);
    }
    @FXML
    protected void menuToHighScore() {
        highscorePane.setVisible(true);
        menuPane.setVisible(false);
        titleBackground.setVisible(false);
        baseBackground.setVisible(true);
    }
    @FXML
    protected void menuToGame() {
        gamePane.setVisible(true);
        menuPane.setVisible(false);
        titleBackground.setVisible(false);
        baseBackground.setVisible(true);
    }

    @FXML
    protected  void playerToInscrir() {
        playerPane.setVisible(false);
        playerSigneUp.setVisible(true);
        titleBackground.setVisible(false);
        baseBackground.setVisible(true);
    }
    @FXML
    protected  void signUpToPlayer() {
        playerPane.setVisible(true);
        playerSigneUp.setVisible(false);
        titleBackground.setVisible(false);
        baseBackground.setVisible(true);
    }



    @FXML
    public boolean connexionJoueur(Player player, String login, String password) {
        boolean jConnecte = false;
        AuthPlayer j = PlayerManager.getInstance().getPlayer(login);
        if (j != null)
            try {
                if (Objects.equals(j.getLogin(), login) && Security.checkPassword(password, j.getSalt(), j.getHashedPassword())) {
                    player.setName(login);
                    jConnecte = true;
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        return jConnecte;
    }


    @FXML
    protected void connection() {
        if (!Session.getInstance().isConnected()) {
            player = new Player(loginFieldConnexion.getText());
            if (connexionJoueur(player, loginFieldConnexion.getText(), passwordFieldConnexion.getText())) {
                {
                    //afficherNotification("success", "Le joueur 1: " + player1.getName() + " s'est connecté");
                    Session.getInstance().connect(loginFieldConnexion.getText());
                    System.out.println("ça marche !");
                }
            } else {
                //afficherNotification("warning", "Identifiant ou mot de passe incorrect");
                System.out.println("ça marche pas!");
            }
        }
        else{
            System.out.println("déjà co" );
        }
    }

    @FXML
    protected void deconnection(){
        if(Session.getInstance().isConnected()){
            Session.getInstance().disconnect();
            System.out.println("déco");
        }
        else {
            System.out.println("pas co");
        }
    }

    public Pane getPacomanoPane() {
        return pacomanoPane;
    }

    public Pane getGamePane() {
        return gamePane;
    }

}
