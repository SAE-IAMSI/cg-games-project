package games.project.galactica;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GameOverFrame extends JFrame {

    Board board;

    public GameOverFrame(Board board) {
        this.board = board;
        ImageIcon imgBackground = new ImageIcon(Objects.requireNonNull(GalacticaClassic.class.getResource("images/background.png")));
        imgBackground = new ImageIcon(imgBackground.getImage().getScaledInstance(1280, 720, Image.SCALE_DEFAULT));
        JLabel background = new JLabel(imgBackground);
        background.setSize(1280, 720);


        JLabel title = new JLabel("Game Over");
        title.setSize(500, 100);
        title.setLocation(510, 250);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setOpaque(false);
        title.setForeground(Color.WHITE);
        add(title);

        JLabel score = new JLabel("Vous avez fait " + board.rightPanel.getScore() + " points");
        score.setSize(500, 100);
        score.setLocation(530, 320);
        score.setFont(new Font("Arial", Font.BOLD, 20));
        score.setOpaque(false);
        score.setForeground(Color.WHITE);
        add(score);

        Button restart = new Button("Recommencer");
        restart.setSize(250, 100);
        restart.setLocation(515, 430);
        restart.setFont(new Font("Arial", Font.BOLD, 30));
        add(restart);

        Button mainMenu = new Button("Menu Principal");
        mainMenu.setSize(250, 100);
        mainMenu.setLocation(515, 540);
        mainMenu.setFont(new Font("Arial", Font.BOLD, 30));
        add(mainMenu);

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

        restart.addActionListener(e -> {
            if (board.game instanceof GalacticaClassic) {
                board.game = new GalacticaClassic();
            } else {
                board.game = new GalacticaInfinite();
            }
            board.game.setVisible(true);
            setVisible(false);
        });

        mainMenu.addActionListener(e -> {
            Galactica.main(new String[0]);
            this.dispose();
        });
        add(background);
    }
}
