package games.project.equipe6.space_invader;

import java.awt.*;
import javax.swing.*;

public class SpaceInvaders extends JFrame  {

    public SpaceInvaders() {
        initUI();
    }

    private void initUI() {
        add(new Board());

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
