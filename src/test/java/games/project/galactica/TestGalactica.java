package games.project.galactica;

import games.project.galactica.sprite.Alien;
import games.project.galactica.sprite.Player;
import games.project.galactica.sprite.Shot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class TestGalactica {

    Galactica galactica;
    LeftPanel leftPanel;
    RightPanel rightPanel;
    Board board;
    Player player;
    Alien alien;


    @BeforeEach
    void setUp() {
        leftPanel = new LeftPanel();
        rightPanel = new RightPanel();
        board = new Board(rightPanel, leftPanel, galactica);
        galactica = new GalacticaInfinite();
        galactica.setLives();
        galactica.initialiser();

        player = new Player(board);
        alien = new Alien(100, 100);
    }

    /**
     * Test de la création des aliens
     */
    @Test
    public void nombreAliens() {
        board.gameInit();
        assertEquals(60, board.getAliens().size());
    }

    /**
     * Test de mouvement du player et de l'alien
     */
    @Test
    void mouvement() {
        // Move
        int prevX = player.getX();
        player.act();
        assertEquals(prevX - 60, player.getX());

        // Not Move
        int prevY = player.getY();
        player.act();
        assertEquals(prevY, player.getY());

        // Move
        prevX = alien.getX();
        alien.act(12);
        assertEquals(prevX + 12, alien.getX());

        // Not Move
        prevY = alien.getY();
        alien.act(12);
        assertEquals(prevY, alien.getY());
    }

    @Test
    void affichageVies() {
        assertEquals("Vie(s): 3", leftPanel.getLivesLabel().getText());
        leftPanel.setLives(2);
        leftPanel.updateLives();
        assertEquals("Vie(s): 2", leftPanel.getLivesLabel().getText());
        leftPanel.setLives(1);
        leftPanel.updateLives();
        assertEquals("Vie(s): 1", leftPanel.getLivesLabel().getText());
    }

    @Test
    void affichageScore() {
        assertEquals("Score : 0", rightPanel.getScoreLabel().getText());
        rightPanel.setScore(10);
        rightPanel.updateScore();
        assertEquals("Score : 10", rightPanel.getScoreLabel().getText());
        rightPanel.setScore(100);
        rightPanel.updateScore();
        assertEquals("Score : 100", rightPanel.getScoreLabel().getText());
    }

    @Test
    void KeyPressedAndReleased() {
        // Deplacment du player sur la gauche
        assertEquals(0, player.getDx());
        Button b = new Button();
        KeyEvent e = new KeyEvent(b, 0, 0, 0, 37, 'a');
        player.keyPressed(e);
        assertEquals(-2, player.getDx());
        player.keyReleased(e);
        assertEquals(0, player.getDx());

        // Deplacment du player sur la droite
        assertEquals(0, player.getDx());
        e = new KeyEvent(b, 0, 0, 0, 39, 'a');
        player.keyPressed(e);
        assertEquals(2, player.getDx());
        player.keyReleased(e);
        assertEquals(0, player.getDx());
    }

    @Test
    void killAlien(){
        Shot shot = new Shot(player.getX(), player.getY());
        shot.initShot(alien.getX(), alien.getY() + 10);
        board.update();
        if (shot.getX() >= (alien.getX())
                && shot.getX() <= (alien.getX() + Commons.ALIEN_WIDTH)
                && shot.getY() >= (alien.getY())
                && shot.getY() <= (alien.getY() + Commons.ALIEN_HEIGHT)) {
            System.out.println("Touche");
        }
    }
}
