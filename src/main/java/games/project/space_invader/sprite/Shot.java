package games.project.space_invader.sprite;

import games.project.space_invader.SpaceInvaders;

import javax.swing.ImageIcon;
import java.util.Objects;

public class Shot extends Sprite {

    public Shot() {
    }

    public Shot(int x, int y) {

        initShot(x, y);
    }

    private void initShot(int x, int y) {
        var shotImg = SpaceInvaders.class.getResource("images/shot2.png");
        var ii = new ImageIcon(Objects.requireNonNull(shotImg));
        setImage(ii.getImage());

        int H_SPACE = 15;
        setX(x + H_SPACE);

        int V_SPACE = 3;
        setY(y - V_SPACE);
    }
}
