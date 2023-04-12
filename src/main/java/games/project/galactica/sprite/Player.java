package games.project.galactica.sprite;

import games.project.galactica.Board;
import games.project.galactica.Commons;
import games.project.galactica.GalacticaClassic;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class Player extends Sprite {
    private int width;
    private final Board board;

    /**
     * Constructeur de la classe Player
     */
    public Player(Board board) {
        initPlayer();
        this.board = board;
    }

    /**
     * Initialise le joueur
     */
    private void initPlayer() {
        var playerImg = GalacticaClassic.class.getResource("images/player2.png");
        var ii = new ImageIcon(Objects.requireNonNull(playerImg));

        width = ii.getImage().getWidth(null);
        setImage(ii.getImage());

        int START_X = 640;
        setX(START_X);

        int START_Y = 630;
        setY(START_Y);
    }

    /**
     * Déplace le joueur
     */
    public void act() {
        x += dx;
        if (x <= 2) {
            x = 2;
        }
        if (x >= Commons.BOARD_WIDTH - 2 * width) {
            x = Commons.BOARD_WIDTH - 2 * width;
        }
    }

    /**
     * Quand une touche est pressée déplacement du joueur
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = -board.getMovePlayer();
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = board.getMovePlayer();
        }
    }

    /**
     * Quand une touche est relâchée
     *
     * @param e
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
