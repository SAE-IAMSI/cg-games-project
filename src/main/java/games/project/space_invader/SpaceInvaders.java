package games.project.space_invader;

import javax.swing.*;
import java.awt.*;

public class SpaceInvaders extends JFrame {

    public SpaceInvaders() {
        initUI();
    }

    private void initUI() {
        LeftPanel leftPanel = new LeftPanel();
        leftPanel.setPreferredSize(new Dimension(320, 720));
        leftPanel.setBackground(Color.DARK_GRAY);

        RightPanel rightPanel = new RightPanel();
        rightPanel.setPreferredSize(new Dimension(320, 720));
        rightPanel.setBackground(Color.DARK_GRAY);

        Board board = new Board(rightPanel, leftPanel);
        board.setPreferredSize(new Dimension(640, 720));
        board.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, Color.DARK_GRAY));

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

    public static void main(String[] args) {
        StartPanel startPanel = new StartPanel();
        startPanel.setPreferredSize(new Dimension(1280, 720));
        startPanel.setVisible(true);
    }
}
