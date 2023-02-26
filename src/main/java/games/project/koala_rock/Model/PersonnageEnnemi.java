package games.project.koala_rock.Model;

import games.project.koala_rock.RessourcesAccess;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Objects;

public class PersonnageEnnemi extends Group {

    private final Rectangle donkey;

    /////////////////////////// Choix personnage ///////////////////////////
    private static String choixPersonnage = "KOALA";

    /**
     * Permet de changer l'image du personnage en fonction du choix du joueur
     *
     * @param choixPersonnage
     * @return
     */
    public Paint setChoixPersonnage_IDLE(String choixPersonnage) {
        return switch (choixPersonnage) {
            case "KOALA" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("personnageEnnemie/Koala.png"))));
            case "NINJA" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("personnageEnnemie/Ninja.png"))));
            default -> null;
        };
    }

    /**
     * Choix de l'image du personnage pour le Lancé d'objet
     *
     * @param choixPersonnage
     * @return
     */
    public Paint setChoixPersonnage_LANCE(String choixPersonnage) {
        return switch (choixPersonnage) {
            case "KOALA" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("personnageEnnemie/Koala-2.png"))));
            case "NINJA" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("personnageEnnemie/Ninja-2.png"))));
            default -> null;
        };
    }
/////////////////////////////////////////////////////////////////////////


    /**
     * Constructeur de la classe DonkeyKong
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public PersonnageEnnemi(int x, int y, int width, int height) {
        this.donkey = new Rectangle(x, y, width, height);
        donkey.setFill(setChoixPersonnage_IDLE(choixPersonnage));
        this.getChildren().add(donkey);

    }

    /**
     * Lancement Tonneaux
     *
     * @param objetAttaque
     */
    public void lance(ObjetAttaque objetAttaque) {
        this.donkey.setFill(setChoixPersonnage_LANCE(choixPersonnage));
        this.donkey.setScaleX(-1);
        gauche();

    }

    /**
     * Animation Donkey Kong Gauche
     */
    public void gauche() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(event -> {
            droite();

        });
        pause.play();
    }

    /**
     * Animation Donkey Kong Droite
     */
    public void droite() {
        this.donkey.setScaleX(1);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(event -> {
            idle();

        });
        pause.play();

    }

    /**
     * Animation Donkey Kong Idle
     */
    public void idle() {
        this.donkey.setFill(setChoixPersonnage_IDLE(choixPersonnage));
    }

    public static void setChoixPersonnage(String choixPersonnage) {
        PersonnageEnnemi.choixPersonnage = choixPersonnage;
    }
}
