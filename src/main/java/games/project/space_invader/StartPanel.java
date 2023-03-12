package games.project.space_invader;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JFrame {

    // TODO : Cette classe contient les éléments de la fenêtre de démarrage du jeu
    public StartPanel() {

        Button classic = new Button("Classic");
        classic.setSize(200, 100);
        classic.setLocation(500, 230);
        classic.setFont(new Font("Arial", Font.BOLD, 30));
        add(classic);

        Button infini = new Button("Infini");
        infini.setSize(200, 100);
        infini.setLocation(500, 330);
        infini.setFont(new Font("Arial", Font.BOLD, 30));
        add(infini);

        setTitle("Space Invaders");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        classic.addActionListener(e -> {
            SpaceInvaders spaceInvaders = new SpaceInvaders("classic");
            spaceInvaders.setVisible(true);
            setVisible(false);
        });

        infini.addActionListener(e -> {
            SpaceInvaders spaceInvaders = new SpaceInvaders("infini");
            spaceInvaders.setVisible(true);
            setVisible(false);
        });
    }


}
