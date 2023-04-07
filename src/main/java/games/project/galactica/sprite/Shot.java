package games.project.galactica.sprite;

import games.project.galactica.GalacticaClassic;

import javax.swing.*;
import java.util.Objects;

public class Shot extends Sprite {

    /**
     * Constructeur de la classe Shot
     */
    public Shot() {
    }

    /**
     * Constructeur de la classe Shot
     * @param x
     * @param y
     */
    public Shot(int x, int y) {
        initShot(x, y);
    }

    /**
     * Initialise le tir
     * @param x
     * @param y
     */
    public void initShot(int x, int y) {
        var shotImg = GalacticaClassic.class.getResource("images/shot2.png");
        var ii = new ImageIcon(Objects.requireNonNull(shotImg));
        setImage(ii.getImage());

        int H_SPACE = 15;
        setX(x + H_SPACE);

        int V_SPACE = 3;
        setY(y - V_SPACE);
    }
}
