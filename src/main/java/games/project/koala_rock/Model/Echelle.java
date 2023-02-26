package games.project.koala_rock.Model;

import games.project.koala_rock.RessourcesAccess;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Echelle extends Group {

    private static String choixEchelle = "KOALA";

    /**
     * Constructeur de la classe Echelle
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Echelle(int x, int y, int width, int height) {
        Rectangle echelle = new Rectangle(x, y, width, height);
        Rectangle collision = new Rectangle(x - 10, y - 35, (width + 20), (height + 35));
        echelle.setFill(setChoixEchelle_Img(choixEchelle));
        collision.setOpacity(0.0);
        this.getChildren().add(echelle);
        this.getChildren().add(collision);
    }

    public static void setChoixEchelle(String choixEchelle) {
        Echelle.choixEchelle = choixEchelle;
    }

    /**
     * Methode qui permet de choisir l'image de l'echelle
     *
     * @param choixEchelle
     * @return
     */
    public Paint setChoixEchelle_Img(String choixEchelle) {
        return switch (choixEchelle) {
            case "KOALA" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("echelle/echelle_koala.png"))));
            case "NINJA" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("echelle/echelle_ninja.png"))));
            default -> null;
        };
    }
}