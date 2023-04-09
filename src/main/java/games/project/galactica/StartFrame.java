package games.project.galactica;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class StartFrame extends JFrame {

    Galactica galactica;

    /**
     * Constructeur de la classe StartFrame
     */
    public StartFrame() {
        ImageIcon imgBackground = new ImageIcon(Objects.requireNonNull(GalacticaClassic.class.getResource("images/background.png")));
        imgBackground = new ImageIcon(imgBackground.getImage().getScaledInstance(1280, 720, Image.SCALE_DEFAULT));
        JLabel background = new JLabel(imgBackground);
        background.setSize(1280, 720);

        JLabel title = new JLabel("Galactica");
        title.setSize(500, 100);
        title.setLocation(525, 300);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setOpaque(false);
        title.setForeground(Color.WHITE);
        add(title);

        Button classic = new Button("Classic");
        classic.setSize(200, 100);
        classic.setLocation(540, 430);
        classic.setFont(new Font("Arial", Font.BOLD, 30));
        add(classic);

        Button infini = new Button("Infini");
        infini.setSize(200, 100);
        infini.setLocation(540, 540);
        infini.setFont(new Font("Arial", Font.BOLD, 30));
        add(infini);

        JLabel credit = new JLabel("By: @janbodnar");
        credit.setSize(200, 100);
        credit.setLocation(20, 600);
        credit.setFont(new Font("Arial", Font.BOLD, 20));
        credit.setOpaque(false);
        credit.setForeground(Color.WHITE);
        add(credit);

        setTitle("Space Invaders");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        Button close = new Button("Quitter");
        close.setSize(150, 60);
        close.setLocation(1070, 600);
        close.setFont(new Font("Arial", Font.BOLD, 30));
        add(close);

        classic.addActionListener(e -> {
            galactica = new GalacticaClassic();
            visibilityFrame();
        });

        infini.addActionListener(e -> {
            galactica = new GalacticaInfinite();
            visibilityFrame();
        });

        close.addActionListener(e -> dispose());
        add(background);
    }

    /**
     * Rend la fenêtre SpaceInvaders visible et la fenêtre StartFrame invisible
     */
    private void visibilityFrame() {
        galactica.setVisible(true);
        dispose();
    }
}
