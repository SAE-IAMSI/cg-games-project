package games.project.space_invader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Objects;

public class LeftPanel extends JPanel {
    private int lives = 0;
    private final Label livesLabel = new Label();
    private final JLabel gauche = new JLabel();
    private final JLabel droite = new JLabel();
    private final JLabel espace = new JLabel();
    URL imgGauche = SpaceInvadersClassic.class.getResource("images/keyboard/Button_Gauche.png");
    URL imgDroite = SpaceInvadersClassic.class.getResource("images/keyboard/Button_Droite.png");
    URL imgEspace = SpaceInvadersClassic.class.getResource("images/keyboard/Button_Espace.png");
    URL imgGaucheClick = SpaceInvadersClassic.class.getResource("images/keyboard/Button_Gauche_Click.png");
    URL imgDroiteClick = SpaceInvadersClassic.class.getResource("images/keyboard/Button_Droite_Click.png");
    URL imgEspaceClick = SpaceInvadersClassic.class.getResource("images/keyboard/Button_Espace_Click.png");

    /**
     * Constructeur de la classe LeftPanel
     */
    public LeftPanel() {
        initLives();
        updateLives();
        affichageKeyboard();
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
     * Affiche le clavier
     */
    public void affichageKeyboard() {

        ImageIcon imageGauche = new ImageIcon(Objects.requireNonNull(imgGauche));
        imageGauche.setImage(imageGauche.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        gauche.setIcon(imageGauche);

        ImageIcon imageDroite = new ImageIcon(Objects.requireNonNull(imgDroite));
        imageDroite.setImage(imageDroite.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        droite.setIcon(imageDroite);

        ImageIcon imageEspace = new ImageIcon(Objects.requireNonNull(imgEspace));
        imageEspace.setImage(imageEspace.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT));
        espace.setIcon(imageEspace);

        gauche.setPreferredSize(new Dimension(100, 100));
        droite.setPreferredSize(new Dimension(100, 100));
        espace.setPreferredSize(new Dimension(100, 100));

        add(gauche);
        add(droite);
        add(espace);
    }

    /**
     * Redimensionne une image
     *
     * @param imageIcon
     * @param width
     * @param height
     * @return
     */
    public static ImageIcon scaleImageIcon(ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
    }

    /**
     * Définit l'image en fonction de la touche
     *
     * @param keyboard
     */
    public void setKeyboard(String keyboard) {

        ImageIcon imageGauche = scaleImageIcon(new ImageIcon(Objects.requireNonNull(imgGauche)), 50, 50);
        ImageIcon imageDroite = scaleImageIcon(new ImageIcon(Objects.requireNonNull(imgDroite)), 50, 50);
        ImageIcon imageEspace = scaleImageIcon(new ImageIcon(Objects.requireNonNull(imgEspace)), 200, 50);
        ImageIcon imageGaucheClick = scaleImageIcon(new ImageIcon(Objects.requireNonNull(imgGaucheClick)), 50, 50);
        ImageIcon imageDroiteClick = scaleImageIcon(new ImageIcon(Objects.requireNonNull(imgDroiteClick)), 50, 50);
        ImageIcon imageEspaceClick = scaleImageIcon(new ImageIcon(Objects.requireNonNull(imgEspaceClick)), 200, 50);

        switch (keyboard) {
            case "gauche" -> {
                gauche.setIcon(imageGaucheClick);
                droite.setIcon(imageDroite);
                espace.setIcon(imageEspace);
            }
            case "droite" -> {
                gauche.setIcon(imageGauche);
                droite.setIcon(imageDroiteClick);
                espace.setIcon(imageEspace);
            }
            case "espace" -> {
                gauche.setIcon(imageGauche);
                droite.setIcon(imageDroite);
                espace.setIcon(imageEspaceClick);
            }
            case "reset" -> {
                gauche.setIcon(imageGauche);
                droite.setIcon(imageDroite);
                espace.setIcon(imageEspace);
            }
        }
    }

    /**
     * Définit l'image en fonction de la touche appuyée
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            setKeyboard("gauche");
        }
        if (key == KeyEvent.VK_RIGHT) {
            setKeyboard("droite");
        }
        if (key == KeyEvent.VK_SPACE) {
            setKeyboard("espace");
        }
    }

    /**
     * Définit l'image en fonction de la touche relâchée
     *
     * @param e
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            setKeyboard("reset");
        }
        if (key == KeyEvent.VK_RIGHT) {
            setKeyboard("reset");
        }
        if (key == KeyEvent.VK_SPACE) {
            setKeyboard("reset");
        }
    }
}
