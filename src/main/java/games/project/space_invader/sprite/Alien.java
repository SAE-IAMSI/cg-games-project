package games.project.space_invader.sprite;

import games.project.space_invader.SpaceInvadersClassic;

import javax.swing.ImageIcon;
import java.util.Objects;

public class Alien extends Sprite {

    private Bomb bomb;

    public Alien(int x, int y) {

        initAlien(x, y);
    }

    private void initAlien(int x, int y) {

        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);

        var alienImg = SpaceInvadersClassic.class.getResource("images/alien2.png");
        var ii = new ImageIcon(Objects.requireNonNull(alienImg));

        setImage(ii.getImage());
    }

    public void act(int direction) {
        this.x += direction;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public static class Bomb extends Sprite {
        private boolean destroyed;

        public Bomb(int x, int y) {
            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);

            this.x = x;
            this.y = y;

            var bombImg = SpaceInvadersClassic.class.getResource("images/bomb2.png");
            var ii = new ImageIcon(Objects.requireNonNull(bombImg));
            setImage(ii.getImage());
        }

        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
            return destroyed;
        }
    }
}
