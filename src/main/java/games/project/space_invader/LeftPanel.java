package games.project.space_invader;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {
    private int lives = 0;

    private final Label livesLabel = new Label();

    public LeftPanel() {
        initLives();
        updateLives();
    }

    private void initLives() {
        livesLabel.setText("Vie(s): " + lives);
        livesLabel.setAlignment(Label.CENTER);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 30));
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setPreferredSize(new Dimension(320, 30));
        add(livesLabel);
    }

    public void updateLives() {
        livesLabel.setText("Vie(s): " + lives);
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }
}
