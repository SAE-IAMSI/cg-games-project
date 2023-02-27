package games.project.koala_rock.Model;

import games.project.koala_rock.RessourcesAccess;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class EchelleBroken extends Group {

    private final Rectangle echelle;
    private final Rectangle collision;
    private static String choixEchelleBroken = "KOALA";

    /**
     * Constructeur de la classe EchelleBroken
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public EchelleBroken(int x, int y, int width, int height) {
        this.echelle = new Rectangle(x, y, width, height);
        this.collision = new Rectangle(x - 10, y, (width + 20), (height));
        collision.setFill(Paint.valueOf("red"));
        echelle.setFill(setChoixEchelleBroken_Img(choixEchelleBroken));
        collision.setOpacity(0);
        this.getChildren().add(echelle);
        this.getChildren().add(collision);
    }

    public static void setChoixEchelleBroken(String choixEchelleBroken) {
        EchelleBroken.choixEchelleBroken = choixEchelleBroken;
    }

    /**
     * Methode qui permet de choisir l'image de l'echelle
     *
     * @param choixEchelleBroken
     * @return
     */
    public Paint setChoixEchelleBroken_Img(String choixEchelleBroken) {
        return switch (choixEchelleBroken) {
            case "KOALA" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("echelle/broken/echelle_broken_koala.png"))));
            case "NINJA" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("echelle/broken/echelle_broken_ninja.png"))));
            default -> null;
        };
    }
}