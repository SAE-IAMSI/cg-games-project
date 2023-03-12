package games.project.equipe6.space_invader;

import javafx.scene.layout.Background;

import java.awt.*;
import javax.swing.*;

public class SpaceInvaders extends JFrame  {

    public SpaceInvaders() {
        initUI();
    }

    private void initUI() {
        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(320, 720));
        panel1.setBackground(Color.green);
        Board b = new Board();
        b.setPreferredSize(new Dimension(640, 720));
        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(320, 720));
        panel2.setBackground(Color.blue);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel1, BorderLayout.WEST);
        getContentPane().add(b, BorderLayout.CENTER);
        getContentPane().add(panel2, BorderLayout.EAST);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Space Invaders");
        setSize(1280, 720);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ex = new SpaceInvaders();
            ex.setVisible(true);
        });
    }
}
