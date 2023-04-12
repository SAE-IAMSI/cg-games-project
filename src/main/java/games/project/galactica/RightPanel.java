package games.project.galactica;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {

    private int score = 0;

    private final Label scoreLabel = new Label();

    /**
     * COnstructeur de la classe RightPanel
     */
    public RightPanel() {
        initScore();
        updateScore();
    }

    /**
     * Initialise le score
     */
    private void initScore() {
        scoreLabel.setText("Score : " + score);
        scoreLabel.setAlignment(Label.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setPreferredSize(new Dimension(320, 30));
        add(scoreLabel);
    }

    /**
     * Met à jour le score
     */
    public void updateScore() {
        scoreLabel.setText("Score : " + score);
        scoreLabel.repaint();
    }

    /**
     * Définit le score
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Retourne le score
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }
}
