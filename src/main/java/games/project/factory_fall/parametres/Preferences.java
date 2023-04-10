package games.project.factory_fall.parametres;

import games.project.factory_fall.FactoryFall;
import games.project.factory_fall.logique.Score;
import games.project.factory_fall.stockage.ScoreManager;
import games.project.factory_fall.stockage.Session;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Objects;

/**
 * La classe Preferences est un singleton qui permet de gérer les préférences de l'utilisateur.
 * <br>
 * Exemple d'utilisation : Preferences.getInstance().getStylePiece();
 */
public class Preferences {

    /**
     * Instance unique de la classe Preferences. À appeler impérativement pour récupérer les préférences du joueur.
     */
    private static Preferences INSTANCE = null;

    /**
     * La préférence de l'image des pièces du plateau. Par défaut, "container".
     */
    private String stylePiece = "conteneur";
    private int stylePieceActuel = 0;
    private ImageView imagePiecePreference = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + stylePiece + "/L.jpg"))));

    /**
     * La préférence du fond.
     */
    private String background = "1";
    private int backgroundActuel = 0;
    private Image imageBackground = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/background/" + background + ".png")));

    private boolean muted = false;

    public static Preferences getInstance() {
        if (INSTANCE == null) INSTANCE = new Preferences();
        return INSTANCE;
    }

    /**
     * Réinitialise toutes les préférences du joueur.
     */
    public void reinitialiser() {
        stylePiece = "conteneur";
        stylePieceActuel = 0;
        imagePiecePreference = new ImageView(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + stylePiece + "/L.jpg"))));

        backgroundActuel = 0;
        background = "industrial";
        imageBackground = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/background/" + background + ".png")));

        muted = false;
    }

    /**
     * Permet de changer l'image de la pièce en fonction du style choisi
     *
     * @param etat permet de savoir si on doit changer l'image vers la gauche ou la droite
     */
    public void changerImage(String etat) {
        switch (etat) {
            case "+" -> stylePieceActuel++;
            case "-" -> stylePieceActuel--;
        }
        if (stylePieceActuel >= 3) {
            stylePieceActuel = 0;
        } else if (stylePieceActuel < 0) {
            stylePieceActuel = 2;
        }
        switch (stylePieceActuel) {
            case 0 -> this.setStylePiece("conteneur");
            case 1 -> this.setStylePiece("brique");
            case 2 -> this.setStylePiece("default");
        }
        // Met à jour l'image
        imagePiecePreference.setImage(new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/" + stylePiece + "/L.jpg"))));
    }

    /**
     * Change le fond en fonction de l'état
     *
     * @param etat + ou - pour changer le fond (flèches de gauche ou droite)
     */
    public void changerFond(String etat) {
        switch (etat) {
            case "+" -> backgroundActuel++;
            case "-" -> backgroundActuel--;
        }
        if (backgroundActuel >= 3) {
            backgroundActuel = 0;
        } else if (backgroundActuel < 0) {
            backgroundActuel = 2;
        }
        switch (backgroundActuel) {
            case 0 ->
                    imageBackground = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/background/1.png")));
            case 1 ->
                    imageBackground = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/background/3.png")));
            case 2 ->
                    imageBackground = new Image(Objects.requireNonNull(FactoryFall.class.getResourceAsStream("images/background/2.png")));
        }
    }

    /**
     * Méthode qui est utilisable pour changer la musique. Pour le moment, l'action ne fait qu'arrêter la musique. Vous pouvez vous inspirer de la méthode :
     *
     * @see Preferences#changerImage(String) : méthode qui permet de changer l'image de la pièce en fonction du style choisi.
     */
    public void changerMusique() {
        Musique.btnMute();
    }

    /**
     * Vérifie que la pièce de personnalisation est débloquable par le joueur en fonction de son meilleur score
     *
     * @param typePerso le type de personnalisation à vérifier
     * @return true si le joueur peut débloquer la personnalisation, false sinon
     */
    public boolean isLocked(String typePerso) {
        Preferences preferences = Preferences.getInstance();
        Session joueur = Session.getInstance();
        Score bestScore = ScoreManager.getInstance().getHighScoreByLogin(joueur.getLogin());
        if (typePerso.equals("pieces")) {
            if (joueur.isConnected()) {
                try {
                    if (bestScore.getScore() >= 40000) {
                        return false;
                    } else if (bestScore.getScore() >= 20000) {
                        if (preferences.getStylePiece().equals("default")) {
                            return true;
                        }
                        if (preferences.getStylePiece().equals("brique")) {
                            return false;
                        }
                    } else {
                        if (preferences.getStylePiece().equals("brique") || preferences.getStylePiece().equals("default")) {
                            return true;
                        }
                    }
                } catch (NullPointerException e) {
                    return true;
                }
            } else {
                if (preferences.getStylePiece().equals("brique") || preferences.getStylePiece().equals("default")) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getStylePiece() {
        return stylePiece;
    }

    public void setStylePiece(String stylePiece) {
        this.stylePiece = stylePiece;
    }

    public Background getBackground() {
        return new Background(new BackgroundImage(imageBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1280, 720, false, false, false, false)));
    }

    public void setMusiqueMute(boolean etat) {
        muted = etat;
    }

    public boolean getMusiqueMute() {
        return muted;
    }

    public Image getImagePiecePreference() {
        return imagePiecePreference.getImage();
    }
}
