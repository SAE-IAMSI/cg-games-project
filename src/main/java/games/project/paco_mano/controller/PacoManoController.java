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


    private final String[] departements = {
            "01 - Ain", "02 - Aisne", "03 - Allier", "04 - Alpes-de-Haute-Provence", "05 - Hautes-Alpes", "06 - Alpes-Maritimes", "07 - Ardèche", "08 - Ardennes", "09 - Ariège", "10 - Aube", "11 - Aude", "12 - Aveyron", "13 - Bouches-du-Rhône", "14 - Calvados", "15 - Cantal", "16 - Charente", "17 - Charente-Maritime", "18 - Cher", "19 - Corrèze", "2A - Corse-du-Sud", "2B - Haute-Corse", "21 - Côte-d'Or", "22 - Côtes-d'Armor", "23 - Creuse", "24 - Dordogne", "25 - Doubs", "26 - Drôme", "27 - Eure", "28 - Eure-et-Loir", "29 - Finistère", "30 - Gard", "31 - Haute-Garonne", "32 - Gers", "33 - Gironde", "34 - Hérault", "35 - Ille-et-Vilaine", "36 - Indre", "37 - Indre-et-Loire", "38 - Isère", "39 - Jura", "40 - Landes", "41 - Loir-et-Cher", "42 - Loire", "43 - Haute-Loire", "44 - Loire-Atlantique", "45 - Loiret", "46 - Lot", "47 - Lot-et-Garonne", "48 - Lozère", "49 - Maine-et-Loire", "50 - Manche", "51 - Marne", "52 - Haute-Marne", "53 - Mayenne", "54 - Meurthe-et-Moselle", "55 - Meuse", "56 - Morbihan", "57 - Moselle", "58 - Nièvre", "59 - Nord", "60 - Oise", "61 - Orne", "62 - Pas-de-Calais", "63 - Puy-de-Dôme", "64 - Pyrénées-Atlantiques", "65 - Hautes-Pyrénées", "66 - Pyrénées-Orientales", "67 - Bas-Rhin", "68 - Haut-Rhin", "69 - Rhône", "70 - Haute-Saône", "71 - Saône-et-Loire", "72 - Sarthe", "73 - Savoie", "74 - Haute-Savoie", "75 - Paris", "76 - Seine-Maritime", "77 - Seine-et-Marne", "78 - Yvelines", "79 - Deux-Sèvres", "80 - Somme", "81 - Tarn", "82 - Tarn-et-Garonne", "83 - Var", "84 - Vaucluse", "85 - Vendée", "86 - Vienne", "87 - Haute-Vienne", "88 - Vosges", "89 - Yonne", "90 - Territoire de Belfort", "91 - Essonne", "92 - Hauts-de-Seine", "93 - Seine-Saint-Denis", "94 - Val-de-Marne", "95 - Val-d'Oise", "99 - Etranger", "971 - Guadeloupe", "972 - Martinique", "973 - Guyane", "974 - La Réunion", "975 - Mayotte"
    };
    @FXML
    private Label idRGPD;

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
    private Pane gameoverPane;
    @FXML
    private Pane playerSigneUp;
    @FXML
    private Pane RGPDPane;

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

    @FXML
    private Label scoreLabel;

    @FXML
    private Label highScoreLabel;

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
    private PasswordField passwordVerify;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private TextField loginFieldSigneUp;
    @FXML
    private ComboBox<String> comboBoxText = new ComboBox<>();
    @FXML
    private Button returnHighscore;
    @FXML
    private CheckBox checkboxRGPD;

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


    // GAME OVER PANE

    @FXML
    private Button restartButton;
    @FXML
    private Button quitGameButton;

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
        comboBoxText.getItems().addAll(departements);
    }
    @FXML
    protected  void signUpToPlayer() {
        playerPane.setVisible(true);
        playerSigneUp.setVisible(false);
        titleBackground.setVisible(false);
        baseBackground.setVisible(true);
    }

    @FXML
    protected void quitGame()
    {
        PacoMano.closeGame();
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
                }
            } else {
                //afficherNotification("warning", "Identifiant ou mot de passe incorrect");
            }
        }
    }

    @FXML
    protected void deconnection(){
        if(Session.getInstance().isConnected()){
            Session.getInstance().disconnect();
        }
    }


    // fonctions game over pane

    @FXML
    protected void restartGame()
    {
        gameoverPane.setVisible(false);
    }

    @FXML
    protected void exitGame()
    {
        PacoMano.closeGame();
    }

    public Pane getPacomanoPane() {
        return pacomanoPane;
    }

    public Pane getGamePane() {
        return gamePane;
    }

    @FXML
    protected void afficheRGPD() {
        idRGPD.setText("Conditions générales d’utilisation\n" +
                "En vigueur au 16/01/2023\n" +
                "Merci de bien vouloir lire les présentes conditions générales d’utilisation:\n\n" +
                "Les présentes conditions générales d'utilisation (dites « CGU ») ont pour objet l'encadrement juridique des modalités de mise à votre disposition par notre CG Games Project. Et de définir les conditions d’accès et d’utilisation des services par « l'Utilisateur ».\n" +
                "Cet accord utilisateur est un accord désignant CG Games Project (ci-après désigné « CG » et « nous ») et vous, que vous soyez un utilisateur enregistré ou que vous accédiez à nos services autrement. Nous nous réservons le droit de modifier les CGU à tout moment.\n\n" +
                "Article 1 : Mention légale\n" +
                "L’édition de CG Games Project est assurée par les équipes de CG dont le siège social est situé à l’IUT de Montpellier.\n" +
                "Adresse email : [adresse email de CG].\n\n" +
                "Article 2 : Collecte de données\n" +
                "Nous collectons des informations sur votre département.\n" +
                "Ces données sont nécessaires au bon fonctionnement des fonctionnalités du jeu dans l’objectif d’établir une expérience compétitive pour les utilisateurs. Nous nous engageons à protéger vos données conformément à la réglementation en vigueur, notamment le Règlement Général sur la Protection des Données (RGPD).\n" +
                "En vertu de la loi Informatique et Libertés, en date du 6 janvier 1978, l'Utilisateur dispose d'un droit d'accès, de rectification, de suppression et d'opposition de ses données personnelles en envoyant un mail à l’adresse : [adresse email de CG]\n\n" +
                "Article 3 : Utilisation des données\n" +
                "Les informations que nous collectons, à savoir votre département, ne seront utilisées que pour améliorer l’expérience utilisateur et nous nous réservons de pouvoir vous offrir des contenus ciblés. Nous ne partagerons pas ces informations à un tiers.\n\n" +
                "Article 4 : Propriété intellectuelle et droit d’auteur\n" +
                "Les marques, logos, signes ainsi que tous les contenus de nos produits (textes, images, son…) font l'objet d'une protection par le Code de la propriété intellectuelle et plus particulièrement par le droit d'auteur.\n" +
                "L'Utilisateur doit solliciter l'autorisation préalable de CG pour toute reproduction, publication ou copie des différents contenus. Il s'engage à une utilisation de nos contenus dans un cadre strictement privé, toute utilisation à des fins commerciales et publicitaires est strictement interdite.\n" +
                "Le non-respect de ses conditions peut amener à des sanctions.\n\n" +
                "Article 5 : Responsabilité\n" +
                "Les sources des informations diffusées sur CG sont fiables mais le produit ne garantit pas qu’il soit exempt de défauts, d’erreurs ou d’omissions. Le produit CG ne peut être tenu pour responsable d’éventuels virus qui pourraient infecter l’ordinateur ou tout matériel informatique de l'utilisateur, suite à une utilisation, à l’accès, ou au téléchargement provenant de ce produit.\n" +
                "Nous ne sommes pas responsables des dommages causés à votre appareil ou de toute perte de données résultant de l'utilisation de notre jeu. Vous utilisez le jeu à vos propres risques.\n\n" +
                "Article 6 : Droit applicable et juridiction compétente\n" +
                "La législation française s'applique au présent contrat et seuls les tribunaux français seront compétents pour résoudre un litige. Pour toute question relative à l’application des présentes CGU, vous pouvez joindre l’éditeur aux coordonnées inscrites à l’article 1.\n\n");
        RGPDPane.setVisible(true);
    }
    @FXML
    protected void retourRGPD() {
        RGPDPane.setVisible(false);
    }
    @FXML
    protected void creerCompteJoueur() {
        if (!loginFieldSigneUp.getText().equals("")) {
            if (!passwordFieldPassword.getText().equals("")) {
                if (passwordFieldPassword.getText().equals(passwordVerify.getText())) {
                    if (passwordFieldPassword.getText().length() < 12) {
                        if (!comboBoxText.getSelectionModel().isEmpty()) {
                            if (PlayerManager.getInstance().getPlayer(loginFieldSigneUp.getText()) == null) {
                                //if (checkboxRGPD.isSelected()) {
                                PlayerManager.getInstance().createPlayer(loginFieldSigneUp.getText(), comboBoxText.getValue().substring(0, 3).strip(), passwordFieldPassword.getText(), false);
                                playerPane.setVisible(true);
                                playerSigneUp.setVisible(false);
                                loginFieldSigneUp.setText("");
                                passwordFieldPassword.setText("");
                                passwordVerify.setText("");
                                //}
                            }
                        }
                    }
                }
            }
        }
    }

    public Pane getGameoverPane() {
        return gameoverPane;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public Label getHighScoreLabel() {
        return highScoreLabel;
    }
}
