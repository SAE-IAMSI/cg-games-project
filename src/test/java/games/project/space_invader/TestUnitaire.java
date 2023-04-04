package games.project.space_invader;

import games.project.space_invader.sprite.Alien;
import games.project.space_invader.sprite.Player;
import games.project.space_invader.sprite.Shot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnitaire {

    SpaceInvaders spaceInvaders;
    LeftPanel leftPanel;
    RightPanel rightPanel;
    Board board;
    Player player;
    Alien alien;


    @BeforeEach
    void setUp() {
        leftPanel = new LeftPanel();
        rightPanel = new RightPanel();
        board = new Board(rightPanel, leftPanel, spaceInvaders);
        spaceInvaders = new SpaceInvadersInfinite();
        player = new Player(board);
        alien = new Alien(10,10);
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
    void testMove() {
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

    /**
     * Test de collision (perdre vie)
     */
    @Test
    public void testCollision() {
        Shot shot = new Shot(player.getX(), player.getY());
        shot.setVisible(true);
        shot.setY(shot.getY() - 1);
        assertTrue(shot.isVisible());
       // assertEquals(2, leftPanel.getLives());
    }
}
