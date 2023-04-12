package games.project.galactica;

import javax.swing.*;
import java.awt.*;

public class Galactica extends JFrame {

    LeftPanel leftPanel;
    RightPanel rightPanel;
    Board board;

    /**
     * Méthode main
     * @param args
     */
    public static void main(String[] args) {
        var startFrame = new StartFrame();
        startFrame.setPreferredSize(new Dimension(1280, 720));
        startFrame.setVisible(true);
    }

    /**
     * Constructeur de la classe SpaceInvaders
     */
    public Galactica() {
        leftPanel = new LeftPanel();
        rightPanel = new RightPanel();
        board = new Board(rightPanel, leftPanel, this);
        setLives();
        initialiser();
    }

    /**
     * initialise la fenêtre
     */
    void initialiser() {
        leftPanel.setPreferredSize(new Dimension(320, 720));
        leftPanel.setBackground(Color.BLACK);

        rightPanel.setPreferredSize(new Dimension(320, 720));
        rightPanel.setBackground(Color.BLACK);

        board.setPreferredSize(new Dimension(640, 720));
        board.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 3, Color.DARK_GRAY));

        leftPanel.updateLives();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(board, BorderLayout.CENTER);
        getContentPane().add(rightPanel, BorderLayout.EAST);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Galactica");
        setSize(1280, 720);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Définit le nombre de vies dans l'interface
     */
    public void setLives() {
    }

}
