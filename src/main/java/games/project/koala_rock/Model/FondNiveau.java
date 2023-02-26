package games.project.koala_rock.Model;

import games.project.koala_rock.RessourcesAccess;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class FondNiveau extends Group {
    private static String choixFondNiveau = "KOALA";

    public FondNiveau(int x, int y, int width, int height) {
        Rectangle fond = new Rectangle(x, y, width, height);
        fond.setFill(setChoixFond_Img(choixFondNiveau));


        this.getChildren().add(fond);
    }

    public static void setChoixFondNiveau(String choixFondNiveau) {
        FondNiveau.choixFondNiveau = choixFondNiveau;
    }

    /**
     * Methode qui permet de choisir l'image de l'echelle
     *
     * @param choixFond
     * @return
     */
    public Paint setChoixFond_Img(String choixFond) {
        return switch (choixFond) {
            case "KOALA" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("fondNiveau/fondKoala.png"))));
            case "NINJA" ->
                    new ImagePattern(new Image(Objects.requireNonNull(RessourcesAccess.class.getResourceAsStream("fondNiveau/fondNinja.png"))));
            default -> null;
        };
    }
}
