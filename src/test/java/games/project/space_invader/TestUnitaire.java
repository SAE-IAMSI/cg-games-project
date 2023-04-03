package games.project.space_invader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUnitaire {

    SpaceInvaders spaceInvaders;
    LeftPanel leftPanel;
    RightPanel rightPanel;
    Board board;

    @BeforeEach
    void setUp() {
        leftPanel = new LeftPanel();
        rightPanel = new RightPanel();
        board = new Board(rightPanel, leftPanel, spaceInvaders);
        board.gameInit();
        spaceInvaders = new SpaceInvadersInfinite();

    }
    
    @Test
    public void nombreAliens() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Test
    public void nombreAliensTues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Test
    public void incrementationScore() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Test
    public void decrementassionsVies() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
