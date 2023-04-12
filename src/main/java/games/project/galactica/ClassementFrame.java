package games.project.galactica;

import games.project.metier.entite.Score;
import games.project.metier.manager.ScoreManager;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassementFrame extends JFrame {


    Galactica galactica;

    /**
     * Constructeur de la classe StartFrame
     */
    public ClassementFrame() {
        ImageIcon imgBackground = new ImageIcon(Objects.requireNonNull(GalacticaClassic.class.getResource("images/background.png")));
        imgBackground = new ImageIcon(imgBackground.getImage().getScaledInstance(1280, 720, Image.SCALE_DEFAULT));
        JLabel background = new JLabel(imgBackground);
        background.setSize(1280, 720);
        JPanel panel = new JPanel();
        panel.setBounds(200,60,850,550);
        panel.setBackground(Color.BLACK);


        GridBagLayout gbClassement = new GridBagLayout();
        GridBagConstraints gbcClassement = new GridBagConstraints();
        gbcClassement.anchor = GridBagConstraints.NORTH;
        gbClassement.rowHeights = new int[]{50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
        gbClassement.columnWeights = new double[]{200, 200, 200};

        gbcClassement.fill = GridBagConstraints.HORIZONTAL;
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        panel.setLayout(gbClassement);

        gbcClassement.gridy = 0;
        gbcClassement.gridx = 0;
        Label rankLabel = new Label("Rang");
        rankLabel.setFont(new Font("Arial", Font.BOLD, 30));
        rankLabel.setForeground(Color.WHITE);
        gbClassement.setConstraints(rankLabel, gbcClassement);
        panel.add(rankLabel);

        gbcClassement.gridx = 1;
        Label loginLabel = new Label("Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 30));
        loginLabel.setForeground(Color.WHITE);
        gbClassement.setConstraints(loginLabel, gbcClassement);
        panel.add(loginLabel);

        gbcClassement.gridx = 2;
        Label scoreLabel = new Label("Score");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setForeground(Color.WHITE);
        gbClassement.setConstraints(scoreLabel, gbcClassement);
        panel.add(scoreLabel);


        int rang = 1;
        for(Score score:this.getLeaderboard()){
            gbcClassement.gridy = rang;
            gbcClassement.gridx = 0;
            Label rangLab = new Label ("" + rang);
            rangLab.setFont(new Font("Arial", Font.BOLD, 30));
            rangLab.setForeground(Color.WHITE);
            gbClassement.setConstraints(rangLab, gbcClassement);
            panel.add(rangLab);

            gbcClassement.gridx = 1;
            Label login = new Label ();
            if(score.getLogin()==null){
                login.setText("Anonyme ");
            }else {
                login.setText(score.getLogin());
            }
            gbClassement.setConstraints(login, gbcClassement);
            login.setFont(new Font("Arial", Font.BOLD, 30));
            login.setForeground(Color.WHITE);
            panel.add(login);


            gbcClassement.gridx = 2;
            Label scoreLab = new Label ("" + score.getScore());
            gbClassement.setConstraints(scoreLab, gbcClassement);
            scoreLab.setFont(new Font("Arial", Font.BOLD, 30));
            scoreLab.setForeground(Color.WHITE);
            panel.add(scoreLab);

            rang++;
        }
        add(panel);




        Button retour = new Button("Retour");
        retour.setSize(150, 60);
        retour.setLocation(1070, 600);
        retour.setFont(new Font("Arial", Font.BOLD, 30));
        add(retour);

        JLabel credit = new JLabel("By: @janbodnar");
        credit.setSize(200, 100);
        credit.setLocation(20, 600);
        credit.setFont(new Font("Arial", Font.BOLD, 20));
        credit.setOpaque(false);
        credit.setForeground(Color.WHITE);
        add(credit);

        setTitle("Galactica");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        retour.addActionListener(e -> {
            Galactica.main(new String[0]);
            this.dispose();
        });

        add(background);
    }

    private List<Score> getLeaderboard(){
        ScoreManager sm = ScoreManager.getInstance();
        return sm.getLeaderboardByGame("GALAC");
    }


}
