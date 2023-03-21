package games.project.space_invader.sprite;

import games.project.space_invader.Commons;
import games.project.space_invader.SpaceInvadersClassic;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class Player extends Sprite {
    private int width;

    public Player() {
        initPlayer();
    }

    private void initPlayer() {
        var playerImg = SpaceInvadersClassic.class.getResource("images/player2.png");
        var ii = new ImageIcon(Objects.requireNonNull(playerImg));

        width = ii.getImage().getWidth(null);
        setImage(ii.getImage());

        int START_X = 640;
        setX(START_X);

        int START_Y = 630;
        setY(START_Y);
    }

    public void act() {
        x += dx;
        if (x <= 2) {
            x = 2;
        }
        if (x >= Commons.BOARD_WIDTH - 2 * width) {
            x = Commons.BOARD_WIDTH - 2 * width;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
    }

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
