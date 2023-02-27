package games.project.factory_fall.logique;

import javafx.beans.property.IntegerProperty;

public class Joueur {
    private String pseudo;
    private final Score score;

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.score = new Score(0, 0);
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public IntegerProperty getScore() {
        return score.scoreProperty();
    }

    public void setScore(IntegerProperty score) {
        this.score.setScore(score.getValue());
    }

    public IntegerProperty getLignesSup() {
        return score.nbLignesProperty();
    }

    public void setLignesSup(int score) {
        this.score.setNbLignes(score);
    }

    public Score getInstanceScore() {
        return score;
    }
}
