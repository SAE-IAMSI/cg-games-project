package games.project.space_invader;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {

    private int score = 0;

    private final Label scoreLabel = new Label();

    public RightPanel() {
        initScore();
        updateScore();
    }

    private void initScore() {
        scoreLabel.setText("Score: " + score);
        scoreLabel.setAlignment(Label.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setPreferredSize(new Dimension(320, 30));
        add(scoreLabel);
    }

    public void updateScore() {
        scoreLabel.setText("Score: " + score);
        scoreLabel.repaint();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
