package games.project.space_invader.sprite;

import games.project.space_invader.SpaceInvadersClassic;

import javax.swing.ImageIcon;
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
    private void initShot(int x, int y) {
        var shotImg = SpaceInvadersClassic.class.getResource("images/shot2.png");
        var ii = new ImageIcon(Objects.requireNonNull(shotImg));
        setImage(ii.getImage());

        int H_SPACE = 15;
        setX(x + H_SPACE);

        int V_SPACE = 3;
        setY(y - V_SPACE);
    }
}
