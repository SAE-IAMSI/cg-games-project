package games.project.space_invader;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {

    SpaceInvaders spaceInvaders;
    public StartFrame() {

        Label title = new Label("Space Invaders");
        title.setSize(500, 100);
        title.setLocation(470, 10);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        add(title);

        Button classic = new Button("Classic");
        classic.setSize(200, 100);
        classic.setLocation(540, 230);
        classic.setFont(new Font("Arial", Font.BOLD, 30));
        add(classic);

        Button infini = new Button("Infini");
        infini.setSize(200, 100);
        infini.setLocation(540, 340);
        infini.setFont(new Font("Arial", Font.BOLD, 30));
        add(infini);

        setTitle("Space Invaders");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.lightGray);

        classic.addActionListener(e -> {
            spaceInvaders = new SpaceInvadersClassic();
            visibilityFrame();
        });

        infini.addActionListener(e -> {
            spaceInvaders = new SpaceInvadersInfinite();
            visibilityFrame();
        });
    }

    private void visibilityFrame(){
        spaceInvaders.setVisible(true);
        setVisible(false);
    }
}
