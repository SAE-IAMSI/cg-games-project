package games.project.modules.parametres.controller;

import games.project.metier.entite.AuthPlayer;
import games.project.metier.entite.Player;
import games.project.metier.manager.JeuManager;
import games.project.modules.statistiques.StatsLauncher;
import games.project.modules.tournois.TournamentApplication;
import games.project.metier.manager.PlayerManager;
import games.project.modules.parametres.Parametres;
import games.project.motron.Motron;
import games.project.motron.metier.manager.PlayerManagerMotron;
import games.project.motron.stockage.Security;
import games.project.motron.view.VueSprite;
import games.project.stockage.Session;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javafx.scene.input.KeyCode.ENTER;

public class ControllerFXML {

    private final Session session;
    private final Player player;
    private final MediaPlayer positiveNotificationSound;
    private final MediaPlayer notificationSound;
    private int indexImage;

    @FXML
    private VBox vboxJeux;
    @FXML
    private Pane paneCompte;
    @FXML
    private Pane paneConnexion;
    @FXML
    private Pane paneCreationCompte;
    @FXML
    private Button buttonRetour;
    @FXML
    private ImageView imageFond;
    @FXML
    private Pane infoJeu;
    @FXML
    private HBox visuelJeu;
    @FXML
    private ImageView imageJeu;
    @FXML
    private ImageView flecheGauche;
    @FXML
    private ImageView flecheDroite;
    @FXML
    private HBox hboxPlay;
    @FXML
    private Pane panePlay;
    @FXML
    private Button buttonPlay;
    @FXML
    private Button buttonPlayRetour;
    @FXML
    private Pane menuPane;
    @FXML
    private Pane comptePane;
    @FXML
    private Pane connexionPane;
    @FXML
    private Pane creationComptePane;
    @FXML
    private Label idRGPD;
    @FXML
    private Label notificationLabel;
    @FXML
    private Pane RGPDPane;

    @FXML
    public void setGames() {
        List<String> jeuPath = new ArrayList<>(JeuManager.getInstance().getPaths());
        System.out.println(jeuPath);
        for (int i = 0; i < jeuPath.size(); i = i + 2) {
            final int fin = i + 1;
            Button b = new Button(jeuPath.get(i));
            b.setOnAction(actionEvent -> {
                File file = new File(jeuPath.get(fin).split("\\.")[3]);
                try {
                    Application m = (Application) Class.forName(jeuPath.get(fin)).newInstance();
                    lanceInfoJeu(m);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            vboxJeux.getChildren().add(b);
        }
    }

    @FXML
    public void lanceCompte(){
        resetPane();
        imageFond.setOpacity(0.5);
        paneCompte.setVisible(true);
        buttonRetour.setVisible(true);
        buttonRetour.setOnMouseClicked(e -> {
            resetPane();
            buttonRetour.setVisible(false);
        });
    }

    @FXML
    public void lanceConnexion(){
        resetPane();
        imageFond.setOpacity(0.5);
        paneConnexion.setVisible(true);
        buttonRetour.setVisible(true);
        buttonRetour.setOnMouseClicked(e -> lanceCompte());
    }

    @FXML
    public void lanceCreationCompte(){
        resetPane();
        imageFond.setOpacity(0.5);
        paneCreationCompte.setVisible(true);
        buttonRetour.setVisible(true);
        buttonRetour.setOnMouseClicked(e -> lanceCompte());
    }

    @FXML
    public void resetPane(){
        paneCompte.setVisible(false);
        paneConnexion.setVisible(false);
        paneCreationCompte.setVisible(false);
        infoJeu.setVisible(false);
        buttonRetour.setVisible(false);
        imageFond.setOpacity(1);
    }

    @FXML
    public void lanceInfoJeu(Application jeu) {
        resetPane();
        indexImage = 0;
        imageFond.setOpacity(0.5);
        infoJeu.setVisible(true);
        ArrayList<Image> wallpaper = setWallpaper(jeu);
        imageJeu.setImage(wallpaper.get(indexImage));
        imageJeu.setOpacity(1);
        flecheDroite.setOpacity(1);
        flecheGauche.setOpacity(1);
        flecheGauche.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                indexImage --;
                if (indexImage < 0) {
                    indexImage = 2;
                }
                changerImageJeu(wallpaper.get(indexImage));

            }
        });
        flecheDroite.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                indexImage ++;
                if (indexImage > 2) {
                    indexImage = 0;
                }
                changerImageJeu(wallpaper.get(indexImage));
            }
        });
        hboxPlay.setOpacity(1);
        panePlay.setOpacity(0.5);
        buttonPlay.setOnAction(actionEvent -> Platform.runLater(() -> {
            try {
                jeu.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        buttonPlayRetour.setOnAction(e -> resetPane());
    }

    public void changerImageJeu(Image image) {
        imageJeu.setImage(image);
    }

    public ArrayList<Image> setWallpaper(Application jeu) {
        ArrayList<Image> wallpaper = new ArrayList<>();
        wallpaper.add(new Image(String.valueOf(jeu.getClass().getResource("images/wallpaper/presentation1.png"))));
        wallpaper.add(new Image(String.valueOf(jeu.getClass().getResource("images/wallpaper/presentation2.png"))));
        wallpaper.add(new Image(String.valueOf(jeu.getClass().getResource("images/wallpaper/presentation3.png"))));
        return wallpaper;
    }

    @FXML
    public void lanceTournois(){
        Platform.runLater(() -> {
            try {
                new TournamentApplication().start(new Stage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void lanceStatistiques(){
        Platform.runLater(() -> {
            new StatsLauncher().start(new Stage());
        });

    }

    @FXML
    private Label labelConnecte;

    @FXML
    private Button bouttonConnecter;

    @FXML
    private Button bouttonDeconnexion;

    @FXML
    private Label label;


    @FXML
    protected void retourCompte() {
        comptePane.setVisible(false);
        menuPane.setVisible(true);
    }

    @FXML
    protected void connecter() {
        comptePane.setVisible(false);
        connexionPane.setVisible(true);
    }

    @FXML
    protected void creerCompte() {
        comptePane.setVisible(false);
        creationComptePane.setVisible(true);
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
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        return jConnecte;
    }

    //    Connexion
    @FXML
    private TextField textFieldConnexion;

    @FXML
    private PasswordField passwordFieldConnexion;

    public void resetConnexionPane() {
        textFieldConnexion.clear();
        passwordFieldConnexion.clear();
    }

    @FXML
    protected void EnterPressed(KeyEvent event) {
        if (event.getCode() == ENTER) {
            connexion();
        }
    }

    @FXML
    protected void connexion() {
        if (!session.isConnected()) {
            if (connexionJoueur(player, textFieldConnexion.getText(), passwordFieldConnexion.getText())) {
                    afficherNotification("success", "Le joueur : " + player.getName() + " s'est connecté");
                    labelConnecte.setText(player.getName());
                    label.setText(player.getName());
                    bouttonConnecter.setVisible(false);
                    bouttonDeconnexion.setVisible(true);
                    connexionPane.setVisible(false);
                    comptePane.setVisible(true);
                    session.connect(player.getName());
                    resetConnexionPane();
            } else {
                afficherNotification("warning", "Identifiant ou mot de passe incorrect");

            }
        } else {
            afficherNotification("warning", "Le joueur 1: " + player.getName() + " est deja connecte");
        }
    }

    @FXML
    protected void deconnexion() {
        if (session.isConnected()) {
            player.setName("");
            session.disconnect();
            bouttonDeconnexion.setVisible(false);
            bouttonConnecter.setVisible(true);
            labelConnecte.setText("Connexion ...");
            label.setText("Joueur");
            afficherNotification("success", "Le joueur : " + player.getName() + " a bien été déconnecté");
        } else {
            afficherNotification("warning", "Le joueur n'est pas connecté");
        }

    }

    @FXML
    protected void retourConnexion() {
        connexionPane.setVisible(false);
        comptePane.setVisible(true);
        resetConnexionPane();
    }


//    Création de compte

    private final String[] departements = {
            "01 - Ain", "02 - Aisne", "03 - Allier", "04 - Alpes-de-Haute-Provence", "05 - Hautes-Alpes", "06 - Alpes-Maritimes", "07 - Ardèche", "08 - Ardennes", "09 - Ariège", "10 - Aube", "11 - Aude", "12 - Aveyron", "13 - Bouches-du-Rhône", "14 - Calvados", "15 - Cantal", "16 - Charente", "17 - Charente-Maritime", "18 - Cher", "19 - Corrèze", "2A - Corse-du-Sud", "2B - Haute-Corse", "21 - Côte-d'Or", "22 - Côtes-d'Armor", "23 - Creuse", "24 - Dordogne", "25 - Doubs", "26 - Drôme", "27 - Eure", "28 - Eure-et-Loir", "29 - Finistère", "30 - Gard", "31 - Haute-Garonne", "32 - Gers", "33 - Gironde", "34 - Hérault", "35 - Ille-et-Vilaine", "36 - Indre", "37 - Indre-et-Loire", "38 - Isère", "39 - Jura", "40 - Landes", "41 - Loir-et-Cher", "42 - Loire", "43 - Haute-Loire", "44 - Loire-Atlantique", "45 - Loiret", "46 - Lot", "47 - Lot-et-Garonne", "48 - Lozère", "49 - Maine-et-Loire", "50 - Manche", "51 - Marne", "52 - Haute-Marne", "53 - Mayenne", "54 - Meurthe-et-Moselle", "55 - Meuse", "56 - Morbihan", "57 - Moselle", "58 - Nièvre", "59 - Nord", "60 - Oise", "61 - Orne", "62 - Pas-de-Calais", "63 - Puy-de-Dôme", "64 - Pyrénées-Atlantiques", "65 - Hautes-Pyrénées", "66 - Pyrénées-Orientales", "67 - Bas-Rhin", "68 - Haut-Rhin", "69 - Rhône", "70 - Haute-Saône", "71 - Saône-et-Loire", "72 - Sarthe", "73 - Savoie", "74 - Haute-Savoie", "75 - Paris", "76 - Seine-Maritime", "77 - Seine-et-Marne", "78 - Yvelines", "79 - Deux-Sèvres", "80 - Somme", "81 - Tarn", "82 - Tarn-et-Garonne", "83 - Var", "84 - Vaucluse", "85 - Vendée", "86 - Vienne", "87 - Haute-Vienne", "88 - Vosges", "89 - Yonne", "90 - Territoire de Belfort", "91 - Essonne", "92 - Hauts-de-Seine", "93 - Seine-Saint-Denis", "94 - Val-de-Marne", "95 - Val-d'Oise", "99 - Etranger", "971 - Guadeloupe", "972 - Martinique", "973 - Guyane", "974 - La Réunion", "975 - Mayotte"
    };

    @FXML
    private TextField loginCreerCompte;
    @FXML
    private PasswordField mdpCreerCompte;
    @FXML
    private PasswordField verifMdpCreerCompte;
    @FXML
    private ComboBox<String> comboBoxText = new ComboBox<>();
    @FXML
    private CheckBox checkboxRGPD;

    public void resetCreationPane() {
        loginCreerCompte.clear();
        mdpCreerCompte.clear();
        verifMdpCreerCompte.clear();
        comboBoxText.getSelectionModel().clearSelection();
        checkboxRGPD.setSelected(false);
    }

    @FXML
    protected void retourNouveauCompte() {
        creationComptePane.setVisible(false);
        comptePane.setVisible(true);
        resetCreationPane();
    }

    @FXML
    protected void creerCompteJoueur() {
        if (!loginCreerCompte.getText().equals("")) {
            if (!mdpCreerCompte.getText().equals("")) {
                if (mdpCreerCompte.getText().equals(verifMdpCreerCompte.getText())) {
                    if (mdpCreerCompte.getText().length() < 12) {
                        if (!comboBoxText.getSelectionModel().isEmpty()) {
                            if (PlayerManager.getInstance().getPlayer(loginCreerCompte.getText()) == null) {
                                if (checkboxRGPD.isSelected()) {
                                    PlayerManagerMotron.getInstance().createPlayer(loginCreerCompte.getText(), comboBoxText.getValue().substring(0, 3).strip(), mdpCreerCompte.getText(), false);
                                    afficherNotification("success", "Bienvenue dans nos rangs, " + loginCreerCompte.getText() + " !");
                                    creationComptePane.setVisible(false);
                                    comptePane.setVisible(true);
                                } else {
                                    afficherNotification("warning", "Vous devez lire et accepter la charte de confidentialité avant de continuer");
                                }
                            } else {
                                afficherNotification("warning", "Le compte " + loginCreerCompte.getText() + " existe deja");
                            }
                        } else {
                            afficherNotification("warning", "Veuillez choisir un département");
                        }
                    } else {
                        afficherNotification("warning", "Mot de passe trop long");
                    }
                } else {
                    afficherNotification("warning", "Les mots de passes ne sont pas identiques");
                }
            } else {
                afficherNotification("warning", "Le champ 'mot de passe' n'est pas rempli");
            }
        } else {
            afficherNotification("warning", "Le champ 'login' n'est pas rempli");
        }
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

    public void afficherNotification(String type, String message) {
        notificationLabel.getStyleClass().clear();
        if (type.equals("success")) {
            positiveNotificationSound.seek(Duration.millis(0));
            positiveNotificationSound.play();
            notificationLabel.getStyleClass().add("notificationSuccess");
        }
        if (type.equals("warning")) {
            notificationSound.seek(Duration.millis(0));
            notificationSound.play();
            notificationLabel.getStyleClass().add("notificationWarning");
        }
        if (type.equals("error")) {
            notificationSound.seek(Duration.millis(0));
            notificationSound.play();
            notificationLabel.getStyleClass().add("notificationError");
        }
        notificationLabel.setText(message);
        notificationLabel.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.millis(2000));
        pause.setOnFinished(e -> notificationLabel.setVisible(false));
        pause.play();
    }

    public ControllerFXML() {
        session = Session.getInstance();
        player = new Player("J");

        Media notifMedia = new Media(String.valueOf(Motron.class.getResource("music/notif.mp3")));
        notificationSound = new MediaPlayer(notifMedia);
        notificationSound.setVolume(0.5);


        Media pNotifMedia = new Media(String.valueOf(Motron.class.getResource("music/positiveNotif.wav")));
        positiveNotificationSound = new MediaPlayer(pNotifMedia);
        positiveNotificationSound.setVolume(0.1);
    }
}
