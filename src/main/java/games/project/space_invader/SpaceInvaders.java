package games.project.space_invader;

import javax.swing.*;
import java.awt.*;

public abstract class SpaceInvaders extends JFrame {

    LeftPanel leftPanel;
    RightPanel rightPanel;
    Board board;

    public static void main(String[] args) {
        var startFrame = new StartFrame();
        startFrame.setPreferredSize(new Dimension(1280, 720));
        startFrame.setVisible(true);
    }

    public SpaceInvaders() {
        leftPanel = new LeftPanel();
        rightPanel = new RightPanel();
        board = new Board(rightPanel, leftPanel, this);
        setLives();
        initialiser();
    }

    private void initialiser() {
        leftPanel.setPreferredSize(new Dimension(320, 720));
        leftPanel.setBackground(Color.DARK_GRAY);

        rightPanel.setPreferredSize(new Dimension(320, 720));
        rightPanel.setBackground(Color.DARK_GRAY);

        board.setPreferredSize(new Dimension(640, 720));
        board.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, Color.DARK_GRAY));

        leftPanel.updateLives();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(board, BorderLayout.CENTER);
        getContentPane().add(rightPanel, BorderLayout.EAST);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Space Invaders");
        setSize(1280, 720);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public abstract void setLives();


}
