package games.project.galactica;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CompteNonConnecteFrame extends JFrame {

    public CompteNonConnecteFrame() {
        ImageIcon imgBackground = new ImageIcon(Objects.requireNonNull(GalacticaClassic.class.getResource("images/background.png")));
        imgBackground = new ImageIcon(imgBackground.getImage().getScaledInstance(1280, 720, Image.SCALE_DEFAULT));
        JLabel background = new JLabel(imgBackground);
        background.setSize(1280, 720);

        JLabel title = new JLabel("Compte");
        title.setSize(500, 100);
        title.setLocation(530, 30);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setOpaque(false);
        title.setForeground(Color.WHITE);
        add(title);

        Button retour = new Button("Retour");
        retour.setSize(150, 40);
        retour.setLocation(70, 50);
        retour.setFont(new Font("Arial", Font.BOLD, 30));
        add(retour);

        JPanel connexionPane = new JPanel();
        connexionPane.setBounds(100,150,400,500);
        connexionPane.setBackground(Color.BLACK);
        GridBagLayout gbConnexion = new GridBagLayout();
        GridBagConstraints gbcConnexion = new GridBagConstraints();
        gbcConnexion.anchor = GridBagConstraints.NORTH;
        gbConnexion.rowHeights = new int[]{60, 60, 60};
        gbcConnexion.fill = GridBagConstraints.HORIZONTAL;
        connexionPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        connexionPane.setLayout(gbConnexion);
        add(connexionPane);

        JLabel connexionLabel = new JLabel("Se connecter");
        connexionLabel.setFont(new Font("Arial", Font.BOLD, 30));
        connexionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        connexionLabel.setOpaque(false);
        connexionLabel.setForeground(Color.WHITE);
        gbcConnexion.gridy = 0;
        gbConnexion.setConstraints(connexionLabel, gbcConnexion);
        connexionPane.add(connexionLabel);

        JTextField connexionPseudo = new JTextField(20);
        connexionPseudo.setFont(new Font("Arial", Font.BOLD, 20));
        gbcConnexion.gridy = 1;
        gbConnexion.setConstraints(connexionPseudo, gbcConnexion);
        connexionPane.add(connexionPseudo);

        JPasswordField connexionMdp = new JPasswordField(20);
        connexionMdp.setFont(new Font("Arial", Font.BOLD, 20));
        gbcConnexion.gridy = 2;
        gbConnexion.setConstraints(connexionMdp, gbcConnexion);
        connexionPane.add(connexionMdp);

        Button connexion = new Button("Connexion");
        connexion.setFont(new Font("Arial", Font.BOLD, 30));
        gbcConnexion.gridy = 3;
        gbcConnexion.weighty = 1;
        gbConnexion.setConstraints(connexion, gbcConnexion);
        connexionPane.add(connexion);

        JPanel inscriptionPane = new JPanel();
        inscriptionPane.setBounds(800,150,400,500);
        inscriptionPane.setBackground(Color.BLACK);
        GridBagLayout gbInscription = new GridBagLayout();
        GridBagConstraints gbcInscription = new GridBagConstraints();
        gbcInscription.anchor = GridBagConstraints.NORTH;
        gbInscription.rowHeights = new int[]{60, 60, 60, 60, 60};
        gbcInscription.fill = GridBagConstraints.HORIZONTAL;
        inscriptionPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        inscriptionPane.setLayout(gbInscription);
        add(inscriptionPane);

        JLabel inscrireLabel = new JLabel("S'inscrire");
        inscrireLabel.setFont(new Font("Arial", Font.BOLD, 30));
        inscrireLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inscrireLabel.setOpaque(false);
        inscrireLabel.setForeground(Color.WHITE);
        gbcInscription.gridy = 0;
        gbInscription.setConstraints(inscrireLabel, gbcInscription);
        inscriptionPane.add(inscrireLabel);

        JTextField inscrirePseudo = new JTextField(20);
        inscrirePseudo.setFont(new Font("Arial", Font.BOLD, 20));
        gbcInscription.gridy = 1;
        gbInscription.setConstraints(inscrirePseudo, gbcInscription);
        inscriptionPane.add(inscrirePseudo);

        JPasswordField inscrireMdp = new JPasswordField(20);
        inscrireMdp.setFont(new Font("Arial", Font.BOLD, 20));
        gbcInscription.gridy = 2;
        gbInscription.setConstraints(inscrireMdp, gbcInscription);
        inscriptionPane.add(inscrireMdp);

        JPasswordField inscrireMdpConfirmation = new JPasswordField(20);
        inscrireMdpConfirmation.setFont(new Font("Arial", Font.BOLD, 20));
        gbcInscription.gridy = 3;
        gbInscription.setConstraints(inscrireMdpConfirmation, gbcInscription);
        inscriptionPane.add(inscrireMdpConfirmation);

        JComboBox<String> departement = new JComboBox<>();
        departement.setFont(new Font("Arial", Font.BOLD, 20));
        gbcInscription.gridy = 4;
        gbInscription.setConstraints(departement, gbcInscription);
        inscriptionPane.add(departement);

        Button inscrire = new Button("S'inscrire");
        inscrire.setFont(new Font("Arial", Font.BOLD, 30));
        gbcInscription.gridy = 5;
        gbcInscription.weighty = 1;
        gbInscription.setConstraints(inscrire, gbcInscription);
        inscriptionPane.add(inscrire);

        setTitle("Space Invaders");
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
}
