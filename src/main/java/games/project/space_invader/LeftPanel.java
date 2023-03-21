package games.project.space_invader;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {
    private int lives = 0;

    private final Label livesLabel = new Label();

    /**
     * Constructeur de la classe LeftPanel
     */
    public LeftPanel() {
        initLives();
        updateLives();
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
}
