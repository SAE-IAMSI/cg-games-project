package games.project.galactica.sprite;

import games.project.galactica.GalacticaClassic;

import javax.swing.*;
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

        var alienImg = GalacticaClassic.class.getResource("images/alien2.png");
        var ii = new ImageIcon(Objects.requireNonNull(alienImg));

        setImage(ii.getImage());
    }

    /**
     * Déplace l'alien
     *
     * @param direction
     */
    public void act(int direction) {
        this.x += direction;
    }

    /**
     * Définit la bombe
     */
    public Bomb getBomb() {
        return bomb;
    }


    public static class Bomb extends Sprite {
        private boolean destroyed;

        /**
         * Constructeur de la classe Bomb
         *
         * @param x
         * @param y
         */
        public Bomb(int x, int y) {
            initBomb(x, y);
        }

        /**
         * Initialise la bombe
         *
         * @param x
         * @param y
         */
        private void initBomb(int x, int y) {

            setDestroyed(true);

            this.x = x;
            this.y = y;

            var bombImg = GalacticaClassic.class.getResource("images/bomb2.png");
            var ii = new ImageIcon(Objects.requireNonNull(bombImg));
            setImage(ii.getImage());
        }

        /**
         * Définit si la bombe est détruite
         *
         * @param destroyed
         */
        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        /**
         * Est détruite
         */
        public boolean isDestroyed() {
            return destroyed;
        }
    }
}
