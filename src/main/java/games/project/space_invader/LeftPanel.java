package games.project.space_invader;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class LeftPanel extends JPanel {

    private int lives = 0;
    private final Label livesLabel = new Label();
    private JLabel boutonGauche, boutonDroite, boutonEspace, textDeplacement, textTire;
    URL imgGauche = SpaceInvadersClassic.class.getResource("images/keyboard/Button_Gauche.png");
    URL imgDroite = SpaceInvadersClassic.class.getResource("images/keyboard/Button_Droite.png");
    URL imgEspace = SpaceInvadersClassic.class.getResource("images/keyboard/Button_Espace.png");

    /**
     * Constructeur de la classe LeftPanel
     */
    public LeftPanel() {
        initLives();
        updateLives();
        affichageKeyboard();
        textKeyboard();

        JPanel keyboardJPanel = new JPanel(null);
        keyboardJPanel.setPreferredSize(new Dimension(320, 720));
        keyboardJPanel.setBackground(Color.BLACK);
        keyboardJPanel.add(boutonGauche);
        keyboardJPanel.add(boutonDroite);
        keyboardJPanel.add(boutonEspace);
        keyboardJPanel.add(textDeplacement);
        keyboardJPanel.add(textTire);
        keyboardJPanel.getComponent(0).setBounds(40, 475, 100, 50);
        keyboardJPanel.getComponent(1).setBounds(180, 475, 100, 50);
        keyboardJPanel.getComponent(2).setBounds(60, 570, 200, 50);
        keyboardJPanel.getComponent(3).setBounds(110, 520, 150, 50);
        keyboardJPanel.getComponent(4).setBounds(140, 610, 200, 50);
        add(keyboardJPanel);

    }

    /**
     * Initialise le nombre de vies
     */
    private void initLives() {
        livesLabel.setText("Vie(s): " + lives);
        livesLabel.setAlignment(Label.CENTER);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 30));
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setPreferredSize(new Dimension(320, 30));
        add(livesLabel);
    }

    /**
     * Met à jour le nombre de vies
     */
    public void updateLives() {
        livesLabel.setText("Vie(s): " + lives);
    }

    /**
     * Définit le nombre de vies
     *
     * @param lives
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Retourne le nombre de vies
     *
     * @return
     */
    public int getLives() {
        return lives;
    }

    /**
     * Affiche les touches du clavier avec ses images
     */
    public void affichageKeyboard() {

        ImageIcon imageGauche = new ImageIcon(Objects.requireNonNull(imgGauche));
        imageGauche.setImage(imageGauche.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        boutonGauche = new JLabel(imageGauche);

        ImageIcon imageDroite = new ImageIcon(Objects.requireNonNull(imgDroite));
        imageDroite.setImage(imageDroite.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        boutonDroite = new JLabel(imageDroite);

        ImageIcon imageEspace = new ImageIcon(Objects.requireNonNull(imgEspace));
        imageEspace.setImage(imageEspace.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT));
        boutonEspace = new JLabel(imageEspace);

        boutonGauche.setPreferredSize(new Dimension(100, 100));
        boutonDroite.setPreferredSize(new Dimension(100, 100));
        boutonEspace.setPreferredSize(new Dimension(100, 100));
    }

    /**
     * Affiche les textes des touches du clavier
     */
    public void textKeyboard() {
        textDeplacement = new JLabel("Déplacement");
        textTire = new JLabel("Tire");

        textDeplacement.setFont(new Font("Arial", Font.BOLD, 17));
        textDeplacement.setForeground(Color.WHITE);

        textTire.setFont(new Font("Arial", Font.BOLD, 17));
        textTire.setForeground(Color.WHITE);

        textDeplacement.setPreferredSize(new Dimension(100, 100));
        textTire.setPreferredSize(new Dimension(100, 100));
    }

}
